package com.wikia.meownjik.shibenitsa;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

public class LettersEnFragment extends Fragment {

    public LettersEnFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context activity) {
        //public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(MainActivity.TAG, "LettersEnFragment onAttach()");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.TAG, "LettersEnFragment onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_LettersEnFragment, container, false);
        Log.d(MainActivity.TAG, "LettersEnFragment onCreateView()");
        return inflater.inflate(R.layout.fragment_letters_en, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.TAG, "LettersEnFragment onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, "LettersEnFragment onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, "LettersEnFragment onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, "LettersEnFragment onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, "LettersEnFragment onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MainActivity.TAG, "LettersEnFragment onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG, "LettersEnFragment onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.TAG, "LettersEnFragment onDetach()");
    }
}