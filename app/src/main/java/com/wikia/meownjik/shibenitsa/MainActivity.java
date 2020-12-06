package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.wikia.meownjik.shibenitsa.businesslogic.Languages;
import com.wikia.meownjik.shibenitsa.database.CRUD;
import com.wikia.meownjik.shibenitsa.database.DBHelper;
import com.wikia.meownjik.shibenitsa.database.WordModel;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "shibenitsaLogs";
    private Button play1;
    private Button play2;
    private Button words;
    private Spinner langs;
    private ImageView picture;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initListeners();

        dbHelper = new DBHelper(this);
        Log.d(TAG,"MainActivity onCreate() done ");
    }

    private void initComponents() {
        play1 = (Button) findViewById(R.id.buttonPlay1);
        play2 = (Button) findViewById(R.id.buttonPlay2);
        words = (Button) findViewById(R.id.buttonWords);

        picture = (ImageView) findViewById(R.id.picture);

        langs = (Spinner) findViewById(R.id.dropdownLang);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.supportedLanguages, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        langs.setAdapter(adapter);
    }

    private void initListeners() {
        play1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,
                                GameActivity.class);
                        intent.putExtra("lang", langs.getSelectedItem().toString());
                        String langCode = Languages.getByName(langs.getSelectedItem().toString()).getLangCode();
                        DBHelper db = new DBHelper(MainActivity.this);
                        ArrayList<WordModel> words = new ArrayList<>(CRUD.selectWordsByString2(
                                db.getReadableDatabase(), langCode));
                        Random rand = new Random();
                        WordModel word = words.get(rand.nextInt(words.size()));
                        intent.putExtra("word", word.getWord());
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,
                                word.getCategory().getName() + ". " + word.getDescription(),
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG,"onClick() buttonPlay1 done");
                    }
                }
        );

        play2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, InputWordActivity.class);
                        intent.putExtra("players", 2);
                        intent.putExtra("lang", langs.getSelectedItemId());
                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "Starting two players game",
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onClick() buttonPlay2 done");
                    }
                }
        );

        words.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, WordsListActivity.class);
                        intent.putExtra("lang", langs.getSelectedItemId());
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Let's update the words database",
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onClick() buttonWords done");
                    }
                }
        );

        langs.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String lang = adapterView.getItemAtPosition(i).toString();
//                        Toast.makeText(MainActivity.this, lang + " language selected",
//                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onItemSelected() dropdownLang done, chosen " + lang);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
//                        Toast.makeText(MainActivity.this, "No language selected",
//                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onItemSelected() dropdownLang done, nothing chosen");
                    }
                }
        );
    }
}