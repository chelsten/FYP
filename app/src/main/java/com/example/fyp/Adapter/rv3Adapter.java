package com.example.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fyp.R;
import com.example.fyp.displayfulldata;

import java.util.List;

/**
 * Created by ROG on 4/4/2018.
 */

public class rv3Adapter extends RecyclerView.Adapter<rv3Adapter.HistoryViewHolder> {
    private Context mCtx;
    private List<adapter3List> productList;

    public rv3Adapter(Context mCtx, List<adapter3List> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public HistoryViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.storeprofitlist, null);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  HistoryViewHolder holder, int position) {
        adapter3List product = productList.get(position);
        holder.jpen1.setText(product.getPt());
        holder.jper2.setText(product.getPrice());
        holder.netp.setText(product.getDate());
        holder.saveid.setText(product.getSaveid());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView jpen1;
        TextView jper2;
        TextView netp;
        TextView saveid;
        LinearLayout ll2;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            jpen1 = itemView.findViewById(R.id.jpen1);
            jper2 = itemView.findViewById(R.id.jper2);
            netp = itemView.findViewById(R.id.netp);
            ll2 = itemView.findViewById(R.id.ll2);
            saveid = itemView.findViewById(R.id.saveid);

            ll2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, displayfulldata.class);
                    String commentID = saveid.getText().toString();
                    i.putExtra("id", commentID);
                    mCtx.startActivity(i);


                }
            });

        }

    }
}
