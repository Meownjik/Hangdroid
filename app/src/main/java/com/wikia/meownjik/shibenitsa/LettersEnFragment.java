package com.wikia.meownjik.shibenitsa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class LettersEnFragment extends Fragment implements View.OnClickListener {
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    Button buttonE;
    Button buttonF;
    Button buttonG;
    Button buttonH;
    Button buttonI;
    Button buttonJ;
    Button buttonK;
    Button buttonL;
    Button buttonM;
    Button buttonN;
    Button buttonO;
    Button buttonP;
    Button buttonQ;
    Button buttonR;
    Button buttonS;
    Button buttonT;
    Button buttonU;
    Button buttonV;
    Button buttonW;
    Button buttonX;
    Button buttonY;
    Button buttonZ;

    GameActivity gameActivity;

    public LettersEnFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context activity) {
        //public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(GameActivity.TAG, "LettersEnFragment onAttach()");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(GameActivity.TAG, "LettersEnFragment onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(GameActivity.TAG, "LettersEnFragment onCreateView()");

        View view = inflater.inflate(R.layout.fragment_letters_en, container, false);
        initButtons(view);
        initButtonsOnClick();

        return view;
    }

    private void initButtons(View view) {
        buttonA = view.findViewById(R.id.buttonA);
        buttonB = view.findViewById(R.id.buttonB);
        buttonC = view.findViewById(R.id.buttonC);
        buttonD = view.findViewById(R.id.buttonD);
        buttonE = view.findViewById(R.id.buttonE);
        buttonF = view.findViewById(R.id.buttonF);
        buttonG = view.findViewById(R.id.buttonG);
        buttonH = view.findViewById(R.id.buttonH);
        buttonI = view.findViewById(R.id.buttonI);
        buttonJ = view.findViewById(R.id.buttonJ);
        buttonK = view.findViewById(R.id.buttonK);
        buttonL = view.findViewById(R.id.buttonL);
        buttonM = view.findViewById(R.id.buttonM);
        buttonN = view.findViewById(R.id.buttonN);
        buttonO = view.findViewById(R.id.buttonO);
        buttonP = view.findViewById(R.id.buttonP);
        buttonQ = view.findViewById(R.id.buttonQ);
        buttonR = view.findViewById(R.id.buttonR);
        buttonS = view.findViewById(R.id.buttonS);
        buttonT = view.findViewById(R.id.buttonT);
        buttonU = view.findViewById(R.id.buttonU);
        buttonV = view.findViewById(R.id.buttonV);
        buttonW = view.findViewById(R.id.buttonW);
        buttonX = view.findViewById(R.id.buttonX);
        buttonY = view.findViewById(R.id.buttonY);
        buttonZ = view.findViewById(R.id.buttonZ);
    }

    private void initButtonsOnClick() {
        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
        buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this);
        buttonG.setOnClickListener(this);
        buttonH.setOnClickListener(this);
        buttonI.setOnClickListener(this);
        buttonJ.setOnClickListener(this);
        buttonK.setOnClickListener(this);
        buttonL.setOnClickListener(this);
        buttonM.setOnClickListener(this);
        buttonN.setOnClickListener(this);
        buttonO.setOnClickListener(this);
        buttonP.setOnClickListener(this);
        buttonQ.setOnClickListener(this);
        buttonR.setOnClickListener(this);
        buttonS.setOnClickListener(this);
        buttonT.setOnClickListener(this);
        buttonU.setOnClickListener(this);
        buttonV.setOnClickListener(this);
        buttonW.setOnClickListener(this);
        buttonX.setOnClickListener(this);
        buttonY.setOnClickListener(this);
        buttonZ.setOnClickListener(this);
    }

    private void restoreButtonsState() {
        buttonA.setEnabled(gameActivity.isLetterNotTried(buttonA.getText().toString()));
        buttonB.setEnabled(gameActivity.isLetterNotTried(buttonB.getText().toString()));
        buttonC.setEnabled(gameActivity.isLetterNotTried(buttonV.getText().toString()));
        buttonD.setEnabled(gameActivity.isLetterNotTried(buttonG.getText().toString()));
        buttonE.setEnabled(gameActivity.isLetterNotTried(buttonD.getText().toString()));
        buttonF.setEnabled(gameActivity.isLetterNotTried(buttonF.getText().toString()));
        buttonG.setEnabled(gameActivity.isLetterNotTried(buttonG.getText().toString()));
        buttonH.setEnabled(gameActivity.isLetterNotTried(buttonH.getText().toString()));
        buttonI.setEnabled(gameActivity.isLetterNotTried(buttonZ.getText().toString()));
        buttonJ.setEnabled(gameActivity.isLetterNotTried(buttonI.getText().toString()));
        buttonK.setEnabled(gameActivity.isLetterNotTried(buttonJ.getText().toString()));
        buttonL.setEnabled(gameActivity.isLetterNotTried(buttonK.getText().toString()));
        buttonM.setEnabled(gameActivity.isLetterNotTried(buttonL.getText().toString()));
        buttonN.setEnabled(gameActivity.isLetterNotTried(buttonM.getText().toString()));
        buttonO.setEnabled(gameActivity.isLetterNotTried(buttonN.getText().toString()));
        buttonP.setEnabled(gameActivity.isLetterNotTried(buttonO.getText().toString()));
        buttonQ.setEnabled(gameActivity.isLetterNotTried(buttonP.getText().toString()));
        buttonR.setEnabled(gameActivity.isLetterNotTried(buttonR.getText().toString()));
        buttonS.setEnabled(gameActivity.isLetterNotTried(buttonS.getText().toString()));
        buttonT.setEnabled(gameActivity.isLetterNotTried(buttonT.getText().toString()));
        buttonU.setEnabled(gameActivity.isLetterNotTried(buttonU.getText().toString()));
        buttonV.setEnabled(gameActivity.isLetterNotTried(buttonF.getText().toString()));
        buttonW.setEnabled(gameActivity.isLetterNotTried(buttonH.getText().toString()));
        buttonX.setEnabled(gameActivity.isLetterNotTried(buttonX.getText().toString()));
        buttonY.setEnabled(gameActivity.isLetterNotTried(buttonY.getText().toString()));
        buttonZ.setEnabled(gameActivity.isLetterNotTried(buttonZ.getText().toString()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gameActivity = (GameActivity) getActivity();

        Log.d(GameActivity.TAG, "LettersEnFragment onActivityCreated()");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(GameActivity.TAG, "LettersEnFragment onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(GameActivity.TAG, "LettersEnFragment onResume()");
        restoreButtonsState(); //Only here Activity.onRestoreInstanceState is done
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Button button = (Button) view.findViewById(id);
        String letter = button.getText().toString();
        Log.d(GameActivity.TAG, "id = " + id + ", letter = " + letter);
        button.setEnabled(false);
        try {
            gameActivity.tryLetter(letter);
        }
        catch (IllegalArgumentException er) {
            Log.e(GameActivity.TAG, er.toString());
        }
    }
}