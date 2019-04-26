package com.example.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
    private static final String KEY_JUMLAH_PENDAPATANTEMP = "jumlahPendapatanTemp";
    private static final String KEY_JUMLAH_PERBELANJAANTEMP = "jumlahPerbelanjaanTemp";
    private static final String KEY_NET_PROFITTEMP = "netProfitTemp";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_STATUS = "status";
    private ProgressDialog pDialog;
    private SessionHandler session;
    private String username;
    private String jumlahPendapatan;
    private String jumlahPerbelanjaan;
    private String netProfit;
    private String fullname;
    private String jumlahPendapatantemp;
    private String jumlahPerbelanjaantemp;
    private String netProfittemp;
    private LinearLayout ll4;
    private LinearLayout ll5;
    private LinearLayout ll6;
    private String pp1 = "";
    private TextView profit;
    private int success;
    private TextView expenses;
    private TextView netprofit;
    private ImageView profile_pic;
    private String usernametemp;
    private int status;
    private String n1, n2;
    private static final String BASE_URL = "https://farmaid1.000webhostapp.com/member/db/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        TextView fname = findViewById(R.id.fname);

        fullname = user.getFullName();
        usernametemp = user.getUsername();
        username = usernametemp;
        fname.setText(fullname);
        ImageButton addProfit = findViewById(R.id.add_profit);
        ImageButton addExpenses = findViewById(R.id.add_expenses);
        ImageButton seedlings = findViewById(R.id.seedling_estimation);
        ImageButton market = findViewById(R.id.market_price);
        ImageButton askQuestion = findViewById(R.id.ask_question);
        ImageButton unitConversion = findViewById(R.id.unit_conversion);
        ImageButton resetP = findViewById(R.id.resetP);
        ImageButton resetE = findViewById(R.id.resetE);
        Button Logout = findViewById(R.id.Logoutbtn);
        ImageButton storedata = findViewById(R.id.storedata);
        ImageButton viewdata = findViewById(R.id.viewdata);
        CardView cv2 = findViewById(R.id.view2);
        profit = findViewById(R.id.totalprofitTextview);
        expenses = findViewById(R.id.expenses);
        netprofit = findViewById(R.id.netProfit);
        profile_pic = findViewById(R.id.profilepic);
        ll4 = findViewById(R.id.ll4);
        ll5 = findViewById(R.id.ll5);
        ll6 = findViewById(R.id.ll6);

        new FetchProfilePicAsyncTask().execute();

        jumlahPendapatantemp = profit.getText().toString();
        jumlahPerbelanjaantemp = expenses.getText().toString();
        netProfittemp = netprofit.getText().toString();

        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, ProfitPage.class);
                startActivity(i);
            }
        });

        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, ExpensesPage.class);
                startActivity(i);
            }
        });

        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, displaycurrentdata.class);
                startActivity(i);
            }
        });


        resetE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new resetExpensesAsyncTask().execute();
            }
        });

        resetP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new resetProfitAsyncTask().execute();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, editprofile.class);
                startActivity(i);
            }
        });

        storedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StoreDataAsyncTask().execute();
            }
        });

        addProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, ProfitPage.class);
                startActivity(i);
            }
        });

        viewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, ViewData.class);
                startActivity(i);
            }
        });

        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, ExpensesPage.class);
                startActivity(i);
            }
        });

        seedlings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, SeedlingEstimation.class);
                startActivity(i);
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, MarketPrice.class);
                startActivity(i);
            }
        });

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, AskQuestion.class);
                startActivity(i);
            }
        });

        unitConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, UnitConverter.class);
                startActivity(i);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(MainPage.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void MainPage() {
        Intent i = new Intent(getApplicationContext(), MainPage.class);
        startActivity(i);
        finish();

    }

    private class resetProfitAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(MainPage.this);
            pDialog.setMessage("Submitting. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_USERNAME, username);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "reset2.php", "POST", httpParams);
            try {
                System.out.println("at try");
                status = jsonObject.getInt(KEY_STATUS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            System.out.println("reilst" + result);
            runOnUiThread(new Runnable() {
                public void run() {
                    if (status == 0) {

                        //Display success message
                        Toast.makeText(MainPage.this,
                                "Reset Profit Successful..", Toast.LENGTH_LONG).show();
                        MainPage();

                    }
                    else {
                        Toast.makeText(MainPage.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private class resetExpensesAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(MainPage.this);
            pDialog.setMessage("Submitting. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_USERNAME, username);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "reset.php", "POST", httpParams);
            try {
                System.out.println("at try");
                status = jsonObject.getInt(KEY_STATUS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            System.out.println("reilst" + result);
            runOnUiThread(new Runnable() {
                public void run() {
                    if (status == 0) {

                        //Display success message
                        Toast.makeText(MainPage.this,
                                "Reset Expenses Successful..", Toast.LENGTH_LONG).show();
                        MainPage();

                    }
                    else {
                        Toast.makeText(MainPage.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private class FetchProfilePicAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainPage.this);
            pDialog.setMessage("Loading. Please wait...");
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
            expenses.setText("RM " + jumlahPerbelanjaan);
            profit.setText("RM " + jumlahPendapatan);
            netprofit.setText("RM " + netProfit);

            loadFromSite();
            pDialog.dismiss();

        }


    }

    private void loadFromSite() {
        System.out.println("irl is " + pp1);
        Glide.with(MainPage.this)
                .load(pp1)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(profile_pic);
    }

    private class StoreDataAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainPage.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_USERNAME, username);
            httpParams.put(KEY_JUMLAH_PENDAPATANTEMP, jumlahPendapatan);
            httpParams.put(KEY_JUMLAH_PERBELANJAANTEMP, jumlahPerbelanjaan);
            httpParams.put(KEY_NET_PROFITTEMP, netProfit);
            System.out.println("here the out:" +jumlahPendapatan + jumlahPerbelanjaan + netProfit);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "savedata.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
                System.out.println("MEssage is: " + jsonObject.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(MainPage.this,
                                "Data Added", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainPage.this, MainPage.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(MainPage.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
