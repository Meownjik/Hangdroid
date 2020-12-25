package com.wikia.meownjik.shibenitsa;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wikia.meownjik.shibenitsa.database.WordModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WordModel}.
 */
public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.ViewHolder> {

    private final List<WordModel> mValues;
    private Context context;

    public WordRecyclerViewAdapter(List<WordModel> items) {
        mValues = items;
        Log.d(MainActivity.TAG, "items ("+ items.size() + "): " + items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.fragment_words_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(MainActivity.TAG, "onBindViewHolder, position: " + position);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(position + 1));
        holder.mContentView.setText(mValues.get(position).toString());

        context = holder.mView.getContext();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public WordModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    WordModel w = mValues.get(position);
                    ((WordsListActivity) context).showWordEditDialog(w);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}