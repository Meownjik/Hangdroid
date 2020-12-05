package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wikia.meownjik.shibenitsa.businesslogic.Game;
import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initComponents();
        changePicture();
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
        if(game.isVictory()) {
            Toast.makeText(GameActivity.this,
                    "Victory!!!",
                    Toast.LENGTH_LONG).show();
            removeLettersFragment();
        }
        else if (game.isFailure()) {
            Toast.makeText(GameActivity.this,
                    "Game over...",
                    Toast.LENGTH_LONG).show();
            removeLettersFragment();
        }
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
}