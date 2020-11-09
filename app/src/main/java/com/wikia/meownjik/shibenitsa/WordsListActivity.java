package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wikia.meownjik.shibenitsa.database.WordModel;

public class WordsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);
    }

    public void showWordEditDialog(WordModel word) {
        EditWordFragment fragment = EditWordFragment.newInstance(word);
        fragment.show(getSupportFragmentManager(), "editWords");
    }
}