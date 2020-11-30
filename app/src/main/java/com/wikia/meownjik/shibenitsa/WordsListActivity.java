package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wikia.meownjik.shibenitsa.database.WordModel;

public class WordsListActivity extends AppCompatActivity {
    private Button buttonAddWord;
    private EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);
        buttonAddWord = (Button) findViewById(R.id.buttonAddWord);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWordEditDialog(new WordModel(-1, null, null, null));
            }
        });
        inputSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    WordFragment wf = (WordFragment) ((WordsListActivity) v.getContext()).getSupportFragmentManager()
                            .findFragmentById(R.id.fragment_words);
                    wf.refreshAdapter(inputSearch.getText().toString());
                    return true;
                }
                return false;
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