package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wikia.meownjik.shibenitsa.businesslogic.Game;
import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

public class GameActivity extends AppCompatActivity {
    public static final String TAG = "shibenitsaLogs";
    private LettersRuFragment ruFragment;
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
    }

    private void initComponents() {
        ruFragment = new LettersRuFragment();
        picture = (ImageView) findViewById(R.id.picture);
        frameLayout = (FrameLayout) findViewById(R.id.frgmContainer);

        Bundle passedData = getIntent().getExtras();
        lang = Languages.getByName(passedData.getString("lang", "English"));

        fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.remove(frag2);
        switch (lang) {
            case RUSSIAN:
                fTrans.add(R.id.frgmContainer, ruFragment);
                break;
            case ENGLISH:
                break;
            case UKRAINIAN:
                break;
            default:
                break;
        }
        fTrans.commit();

        wordView = (TextView) findViewById(R.id.textViewWord);
        game = new Game(lang, passedData.getString("word"));
        wordView.setText(game.getHiddenWord());
    }

    public void tryLetter(String letter) {
        game.tryLetter(letter);
        wordView.setText(game.getHiddenWord());
        if(game.isVictory()) {
            Toast.makeText(GameActivity.this,
                    "Victory!!!",
                    Toast.LENGTH_LONG).show();
        }
        else if (game.isFailure()) {
            Toast.makeText(GameActivity.this,
                    "Game over...",
                    Toast.LENGTH_LONG).show();
        }
    }
}