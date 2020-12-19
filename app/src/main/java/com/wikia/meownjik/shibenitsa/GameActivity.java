package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wikia.meownjik.shibenitsa.businesslogic.Game;
import com.wikia.meownjik.shibenitsa.businesslogic.Languages;
import com.wikia.meownjik.shibenitsa.database.CRUD;
import com.wikia.meownjik.shibenitsa.database.DBHelper;
import com.wikia.meownjik.shibenitsa.database.WordModel;

public class GameActivity extends AppCompatActivity {
    public static final String TAG = "shibenitsaLogs";
    private LettersRuFragment ruFragment;
    private LettersUkFragment ukFragment;
    private LettersEnFragment enFragment;
    private ImageView picture;
    private FragmentTransaction fTrans;
    private FrameLayout frameLayout;

    private TextView wordView;

    Languages lang;
    Game game;
    private boolean dialogShown = false;
    private int numOfPlayers;
    private String hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle passedData = getIntent().getExtras();
        numOfPlayers = passedData.getInt("players", 0);
        hint = passedData.getString("hint", "No hint provided");

        initComponents();
        showHint();
        changePicture();
        initListeners();
    }

    private void initComponents() {
        ruFragment = new LettersRuFragment();
        ukFragment = new LettersUkFragment();
        enFragment = new LettersEnFragment();
        picture = (ImageView) findViewById(R.id.picture);
        frameLayout = (FrameLayout) findViewById(R.id.frgmContainer);

        Bundle passedData = getIntent().getExtras();
        lang = Languages.getByName(passedData.getString("lang", "English"));

        addLettersFragment();

        wordView = (TextView) findViewById(R.id.textViewWord);
        int trials = (lang == Languages.ENGLISH) ? 8 : 9; //Less letters - less trials
        game = new Game(lang, passedData.getString("word"), trials);
        wordView.setText(game.getHiddenWord());
    }

    private void initListeners() {
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numOfPlayers == 1) {
                    showHint();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "GameActivity.onSaveInstanceState");
        Gson gson = new Gson();
        String json = gson.toJson(game);
        savedInstanceState.putString("game", json);
        savedInstanceState.putString("lang", lang.getLangName());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "GameActivity.onRestoreInstanceState");
        Gson gson = new Gson();
        String json = savedInstanceState.getString("game");
        game = gson.fromJson(json, Game.class);
        lang = Languages.getByName(savedInstanceState.getString("lang"));

        wordView.setText(game.getHiddenWord());
        changePicture();
        reactOnGameEnd();
    }

    @Override
    public void onBackPressed() {
        if (game.isVictory() || game.isFailure()) {
            if (dialogShown) {
                quitGame();
            }
            else {
                if(!showAddWordDialog()) {
                    //If the dialog shouldn't be shown
                    quitGame();
                }
                dialogShown = true;
            }
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure to interrupt the game?")
                    .setNegativeButton("No, let's reconsider...", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Yes, quit the game!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            quitGame();
                        }
                    });
            builder.create().show();
        }
    }

    //------------------------------------------------------------------------------

    public void tryLetter(String letter) {
        game.tryLetter(letter);
        wordView.setText(game.getHiddenWord());
        changePicture();
        Log.d(TAG, "Trials left: " + game.getTrialsLeft());
//        Toast.makeText(GameActivity.this,
//                "Trials left: " + game.getTrialsLeft(),
//                Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Tried letters: " + game.getUsedLetters());
        Log.d(TAG, "Not tried letters: " + game.getNotUsedLetters());

        reactOnGameEnd();
    }

    public boolean isLetterNotTried(String letter) {
        return game.getNotUsedLetters().contains(letter.toLowerCase());
    }

    private void addLettersFragment() {
        fTrans = getSupportFragmentManager().beginTransaction();
        switch (lang) {
            case RUSSIAN:
                fTrans.add(R.id.frgmContainer, ruFragment);
                break;
            case UKRAINIAN:
                fTrans.add(R.id.frgmContainer, ukFragment);
                break;
            case ENGLISH:
            default:
                fTrans.add(R.id.frgmContainer, enFragment);
                break;
        }
        fTrans.commit();
    }

    private void removeLettersFragment() {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.remove(ruFragment);
        fTrans.remove(ukFragment);
        fTrans.remove(enFragment);
        fTrans.commit();
    }

    private void reactOnGameEnd() {
        if (game.isVictory() || game.isFailure()) {
            String text = game.isVictory() ? "Victory!!!" : "Game over...";
            Toast.makeText(GameActivity.this, text, Toast.LENGTH_LONG).show();
            removeLettersFragment();
        }
    }

    private boolean showAddWordDialog() {
        SQLiteDatabase db = (new DBHelper(this)).getReadableDatabase();
        if (CRUD.selectWordsByString(db, game.getOriginalWord(), 0, false).size() > 0) {
            return false; //Needn't add if the word is already present
        }
        Log.d(TAG, "AlertDialog builder starts");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save word " + game.getOriginalWord() + " in the database?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        quitGame();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        WordModel word = new WordModel(-1, game.getOriginalWord(), null, null);
                        EditWordFragment fragment = EditWordFragment.newInstance(word);
                        fragment.show(getSupportFragmentManager(), "editWords");
                    }
                });
        builder.create().show();
        Log.d(TAG, "AlertDialog builder done");
        return true;
    }

    private void changePicture() {
        switch(game.getTrialsLeft()) {
            case 10:
            default:
                picture.setImageResource(R.drawable.shibenitsa10);
                break;
            case 9:
                picture.setImageResource(R.drawable.shibenitsa9);
                break;
            case 8:
                picture.setImageResource(R.drawable.shibenitsa8);
                break;
            case 7:
                picture.setImageResource(R.drawable.shibenitsa7);
                break;
            case 6:
                picture.setImageResource(R.drawable.shibenitsa6);
                break;
            case 5:
                picture.setImageResource(R.drawable.shibenitsa5);
                break;
            case 4:
                picture.setImageResource(R.drawable.shibenitsa4);
                break;
            case 3:
                picture.setImageResource(R.drawable.shibenitsa3);
                break;
            case 2:
                picture.setImageResource(R.drawable.shibenitsa2);
                break;
            case 1:
                picture.setImageResource(R.drawable.shibenitsa1);
                break;
            case 0:
                picture.setImageResource(R.drawable.shibenitsa0);
                break;
        }

    }

    private void quitGame() {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        //clear back stack so that you couldn't see the hidden word
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showHint() {
        Toast.makeText(GameActivity.this, hint, Toast.LENGTH_LONG).show();
    }
}