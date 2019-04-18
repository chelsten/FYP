package com.example.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fyp.remotemysqlconnection.ProfitPage;
import com.example.fyp.remotemysqlconnection.ExpensesPage;
import com.example.fyp.remotemysqlconnection.helper.HttpJsonParser;
import com.example.fyp.unitconverter.UnitConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainPage extends AppCompatActivity {

    private static final String KEY_DATA1 = "data1";
    private static final String KEY_DATA = "data";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PROFILE_PIC = "pic_path";
    private static final String KEY_JUMLAH_PENDAPATAN = "jumlahPendapatan";
    private static final String KEY_JUMLAH_PERBELANJAAN = "jumlahPerbelanjaan";
    private static final String KEY_NET_PROFIT = "netProfit";
    private static final String KEY_SUCCESS = "success";
    private ProgressDialog pDialog;
    private SessionHandler session;
    private String username;
    private String jumlahPendapatan;
    private  String jumlahPerbelanjaan;
    private String netProfit;
    private String pp1 = "";
    private TextView profit;
    private TextView expenses;
    private TextView netprofit;
    private ImageView profile_pic;
    private  String usernametemp;
    private int realprofit;
    private String n1,n2;
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        TextView fname = findViewById(R.id.fname);

        usernametemp = user.getUsername();
        username = usernametemp;
        fname.setText(username);
        Button addProfit = findViewById(R.id.add_profit);
        Button addExpenses = findViewById(R.id.add_expenses);
        Button seedlings = findViewById(R.id.seedling_estimation);
        Button market = findViewById(R.id.market_price);
        Button askQuestion = findViewById(R.id.ask_question);
        Button unitConversion = findViewById(R.id.unit_conversion);
        Button Logout = findViewById(R.id.Logoutbtn);
        CardView cv2 = findViewById(R.id.view2);
        profit = findViewById(R.id.totalprofitTextview);
        expenses = findViewById(R.id.expenses);
        netprofit = findViewById(R.id.netProfit);
        profile_pic = findViewById(R.id.profilepic);

        new FetchProfilePicAsyncTask().execute();



        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, editprofile.class);
                startActivity(i);
                finish();
            }
        });
        addProfit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainPage.this, ProfitPage.class);
                    startActivity(i);
                    finish();
                }
            });

        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, ExpensesPage.class);
                startActivity(i);
                finish();
            }
        });

        seedlings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, SeedlingEstimation.class);
                startActivity(i);
                finish();
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, MarketPrice.class);
                startActivity(i);
                finish();
            }
        });

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, AskQuestion.class);
                startActivity(i);
                finish();
            }
        });

        unitConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, UnitConverter.class);
                startActivity(i);
                finish();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(MainPage.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    private class FetchProfilePicAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainPage.this);
            pDialog.setMessage("Loading Your Profile. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_USERNAME, username);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_profile.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject jo;
                System.out.println(success);
                if (success == 1) {
                    //Parse the JSON response
                    jo = jsonObject.getJSONObject(KEY_DATA1);
                    pp1 = jo.getString(KEY_PROFILE_PIC);
                    System.out.println(pp1);
                    jumlahPendapatan = jo.getString(KEY_JUMLAH_PENDAPATAN);
                    jumlahPerbelanjaan = jo.getString(KEY_JUMLAH_PERBELANJAAN);
                    netProfit = jo.getString(KEY_NET_PROFIT);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            expenses.setText("RM "+jumlahPerbelanjaan);
            profit.setText("RM "+jumlahPendapatan);
            netprofit.setText("RM "+netProfit);

            loadFromSite();
            pDialog.dismiss();

        }


    }

    private void loadFromSite(){
        System.out.println("irl is " + pp1);
        Glide.with(MainPage.this)
                .load(pp1)
                .into(profile_pic);
    }


}
