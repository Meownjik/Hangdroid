package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.wikia.meownjik.shibenitsa.database.WordModel;

public class WordsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);
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