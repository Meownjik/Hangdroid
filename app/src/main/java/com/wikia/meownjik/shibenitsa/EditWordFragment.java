package com.wikia.meownjik.shibenitsa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wikia.meownjik.shibenitsa.database.WordModel;

public class EditWordFragment extends DialogFragment {
    private EditText wordField;
    private EditText descriptionField;

    private WordModel word;

//    public EditWordFragment(WordModel word) {
//        this.word = word;
//    }

    public static EditWordFragment newInstance(WordModel word) {
        EditWordFragment f = new EditWordFragment();
        Gson gson = new Gson();

        Bundle args = new Bundle();
        args.putString("word", gson.toJson(word, WordModel.class));
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        word = gson.fromJson(getArguments().getString("word"), WordModel.class);
        Log.d(MainActivity.TAG, word.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_word, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_edit_word, null))
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Saving (no)", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditWordFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        wordField = (EditText) getView().findViewById(R.id.wordCaption);
        descriptionField = (EditText) getView().findViewById(R.id.wordDescription);

        wordField.setText(word.getWord()); //TODO text is not set! Why?!
        descriptionField.setText(word.getDescription());
    }
}