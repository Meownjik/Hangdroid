package com.wikia.meownjik.shibenitsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class InputWordActivity extends AppCompatActivity {
    private static final String TAG = "shibenitsaLogs";
    private Button ok;
    private Spinner langs;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_word);

        Bundle passedData = getIntent().getExtras();
        int numOfPlayers = passedData.getInt("players", 0);
        Log.d(TAG,"Players: " + numOfPlayers);

        initComponents();
        initListeners();
        Log.d(TAG,"InputWordActivity onCreate() done ");
    }

    private void initComponents() {
        ok = (Button) findViewById(R.id.buttonOk);

        picture = (ImageView) findViewById(R.id.picture);

        langs = (Spinner) findViewById(R.id.dropdownLang);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.supportedLanguages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langs.setAdapter(adapter);
    }

    private void initListeners() {
        ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(InputWordActivity.this, "Ready for a game!",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG,"onClick() buttonOk done");
                    }
                }
        );

        langs.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String lang = adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(InputWordActivity.this, lang + " language selected",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG,"onItemSelected() dropdownLang done, chosen " + lang);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(InputWordActivity.this, "No language selected",
                                Toast.LENGTH_LONG).show();
                        Log.d(TAG,"onItemSelected() dropdownLang done, nothing chosen");
                    }
                }
        );
    }
}