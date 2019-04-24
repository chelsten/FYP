package com.example.fyp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.Message;
import com.example.fyp.R;
import com.example.fyp.adminMain;
import com.example.fyp.deletePrice;
import com.example.fyp.remotemysqlconnection.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ROG on 4/4/2018.
 */

public class rv2Adapter extends RecyclerView.Adapter<rv2Adapter.HistoryViewHolder> {
    private Context mCtx;
    private List<adapter2List> productList;
    private ProgressDialog pDialog;
    private static final String KEY_MARKET_ID = "market_id";
    private static final String KEY_SUCCESS = "success";
    private int success;
    private String marketid;
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";

    public rv2Adapter(Context mCtx, List<adapter2List> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listitemdelete, null);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        adapter2List product = productList.get(position);
        holder.pt.setText(product.getPt());
        holder.tp.setText("RM " + product.getPrice());
        holder.date.setText(product.getDate());
        holder.market_id.setText(product.getmarketId());
        marketid = product.getmarketId();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView pt;
        TextView tp;
        TextView date;
        TextView market_id;
        LinearLayout deleteid;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            pt = itemView.findViewById(R.id.plantType2);
            tp = itemView.findViewById(R.id.totalPrice2);
            date = itemView.findViewById(R.id.dateprice2);
            deleteid = itemView.findViewById(R.id.deletell1);
            market_id = itemView.findViewById(R.id.marketid);
            System.out.println("is is::"+marketid);

            deleteid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v == deleteid){
                            new AddProfitAsyncTask().execute();
                    }
                }
            });
        }

        private class AddProfitAsyncTask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Display proggress bar
            }

            @Override
            protected String doInBackground(String... params) {
                HttpJsonParser httpJsonParser = new HttpJsonParser();
                Map<String, String> httpParams = new HashMap<>();
                //Populating request parameters
                System.out.println("yeah id" + marketid);
                httpParams.put(KEY_MARKET_ID, marketid);

                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        BASE_URL + "deleteprice.php", "POST", httpParams);
                try {
                    success = jsonObject.getInt(KEY_SUCCESS);
                    System.out.println("MEssage is: " + jsonObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String result) {
                Intent i = new Intent(mCtx, adminMain.class);
                mCtx.startActivity(i);
            }
        }
    }
}
