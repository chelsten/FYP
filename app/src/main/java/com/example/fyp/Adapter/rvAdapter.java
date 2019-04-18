package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fyp.R;

import java.util.List;

/**
 * Created by ROG on 4/4/2018.
 */

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<adapterList> productList;

    public rvAdapter(Context mCtx, List<adapterList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public HistoryViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item, null);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  HistoryViewHolder holder, int position) {
        adapterList product = productList.get(position);
        holder.pt.setText(product.getPt());
        holder.tp.setText("RM "+product.getPrice());
        holder.date.setText(product.getDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView pt;
        TextView tp;
        TextView date;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            pt = itemView.findViewById(R.id.plantType);
            tp = itemView.findViewById(R.id.totalPrice);
            date = itemView.findViewById(R.id.dateprice);
        }

    }
}
