package com.wikia.meownjik.shibenitsa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wikia.meownjik.shibenitsa.database.CRUD;
import com.wikia.meownjik.shibenitsa.database.DBHelper;

/**
 * A fragment representing a list of Items.
 */
public class WordFragment extends Fragment {
    private int mColumnCount = 1;

    private DBHelper dbHelper;
    private Context context;

    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            dbHelper = new DBHelper(context);

            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            recyclerView.setAdapter(new WordRecyclerViewAdapter(CRUD.selectAllWords(db)));
        }
        return view;
    }

    public void refreshAdapter() {
        //recyclerView.getAdapter().notifyDataSetChanged();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        recyclerView.swapAdapter(new WordRecyclerViewAdapter(CRUD.selectAllWords(db)), true);
    }

    public void refreshAdapter(final String searchText) {
        Thread newThread = new Thread() {
            @Override
            public void run() {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                recyclerView.setAdapter(new WordRecyclerViewAdapter(CRUD.selectWordsByString2(db, searchText)));
                //Not swapAdapter!
            }
        };
        newThread.start();
    }
}