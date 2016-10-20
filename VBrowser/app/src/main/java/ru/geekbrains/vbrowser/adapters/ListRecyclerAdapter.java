package ru.geekbrains.vbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.geekbrains.vbrowser.R;

/**
 * Created by Prilepishev Vadim on 19.10.2016.
 */

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.RecyclerViewHolder> {
    ArrayList<String> dataArray;
    final Context context;

    public ListRecyclerAdapter(ArrayList<String> dataArray, Context context){
        this.dataArray = dataArray;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view,parent,false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textView.setText(dataArray.get(position));
    }

    @Override
    public int getItemCount() {
        return dataArray == null ? 0: dataArray.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cv;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView){
            cv = (CardView)itemView.findViewById(R.id.cardView);
            textView = (TextView)itemView.findViewById(R.id.textViewHistory);
        }

    }
}
