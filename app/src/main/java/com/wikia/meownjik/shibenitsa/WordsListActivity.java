package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wikia.meownjik.shibenitsa.database.WordModel;

public class WordsListActivity extends AppCompatActivity {
    private Button buttonAddWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);
        buttonAddWord = (Button) findViewById(R.id.buttonAddWord);
        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWordEditDialog(new WordModel(-1, null, null, null));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, "WordsListActivity onResume() done");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, "WordsListActivity onPause() done");
    }

    public void showWordEditDialog(WordModel word) {
        EditWordFragment fragment = EditWordFragment.newInstance(word);
        fragment.show(getSupportFragmentManager(), "editWords");
    }
}