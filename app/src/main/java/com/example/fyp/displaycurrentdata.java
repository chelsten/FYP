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

public class displaycurrentdata extends AppCompatActivity {

    private static final String KEY_DATA1 = "data1";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_managementExpenses1 = "managementExpenses";
    private static final String KEY_salaryAndWagesWorker1 = "salaryAndWagesWorker";
    private static final String KEY_landRental1 = "landRental";
    private static final String KEY_fertilizer1 = "fertilizer";
    private static final String KEY_foliar1 = "foliar";
    private static final String KEY_racun1 = "racun";
    private static final String KEY_electric1 = "electric";
    private static final String KEY_water1 = "water";
    private static final String KEY_cocopeat1 = "cocopeat";
    private static final String KEY_polybag1 = "polybag";
    private static final String KEY_silvershine1 = "silvershine";
    private static final String KEY_traySemaian1 = "traySemaian";
    private static final String KEY_lainsusutnilai1 = "lainsusutnilai";
    private static final String KEY_marketingCost1 = "marketingCost";
    private static final String KEY_FinancialCost1 = "FinancialCost";
    private static final String KEY_llk1 = "llk";
    private static final String KEY_jualanBGredA1 = "jualanBGredA";
    private static final String KEY_jualanBGredB1 = "jualanBGredB";
    private static final String KEY_jualanBGredC1 = "jualanBGredC";
    private static final String KEY_jualanLGredA1 = "jualanLGredA";
    private static final String KEY_jualanLGredB1 = "jualanLGredB";
    private static final String KEY_jualanLGredC1 = "jualanLGredC";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_STATUS = "status";
    private ProgressDialog pDialog;
    private SessionHandler session;
    private String managementExpenses;
    private String salaryAndWagesWorker;
    private String landRental;
    private String fertilizer;
    private String foliar;
    private String racun;
    private String electric;
    private String water;
    private String cocopeat;
    private String polybag;
    private String silvershine;
    private String traySemaian;
    private String lainsusutnilai;
    private String marketingCost;
    private String FinancialCost;
    private String llk;
    private String jualanBGredA;
    private String jualanBGredB;
    private String jualanBGredC;
    private String jualanLGredA;
    private String jualanLGredB;
    private String jualanLGredC;
    private String username;
    private TextView jbgredAD;
    private TextView jbgredBD;
    private TextView jbgredCD;
    private TextView jlgredAD;
    private TextView jlgredBD;
    private TextView jlgredCD;
    private TextView etmED;
    private TextView etsWWD;
    private TextView etLRD;
    private TextView etftD;
    private TextView etflD;
    private TextView etrcD;
    private TextView eteeD;
    private TextView etairD;
    private TextView etccD;
    private TextView etpbD;
    private TextView etssD;
    private TextView ettsD;
    private TextView etllsD;
    private TextView etmcD;
    private TextView etfcD;
    private TextView etllkD;


    private String usernametemp;
    private String id;
    private String n1, n2;
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/fetch_currentsavedata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayfulldata);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();
        username = usernametemp;

        jbgredAD = findViewById(R.id.jbgredAD);
        jbgredBD = findViewById(R.id.jbgredBD);
        jbgredCD = findViewById(R.id.jbgredCD);
        jlgredAD = findViewById(R.id.jlgredAD);
        jlgredBD = findViewById(R.id.jlgredBD);
        jlgredCD = findViewById(R.id.jlgredCD);
        etmED = findViewById(R.id.etmED);
        etsWWD = findViewById(R.id.etsWWD);
        etLRD = findViewById(R.id.etLRD);
        etftD = findViewById(R.id.etftD);
        etflD = findViewById(R.id.etflD);
        etrcD = findViewById(R.id.etrcD);
        eteeD = findViewById(R.id.eteeD);
        etairD = findViewById(R.id.etairD);
        etccD = findViewById(R.id.etccD);
        etpbD = findViewById(R.id.etpbD);
        etssD = findViewById(R.id.etssD);
        ettsD = findViewById(R.id.ettsD);
        etllsD = findViewById(R.id.etllsD);
        etmcD = findViewById(R.id.etmcD);
        etfcD = findViewById(R.id.etfcD);
        etllkD = findViewById(R.id.etllkD);

        new FetchProfilePicAsyncTask().execute();


    }

    private void MainPage() {
        Intent i = new Intent(getApplicationContext(), MainPage.class);
        startActivity(i);
        finish();

    }

    private class FetchProfilePicAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(displaycurrentdata.this);
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
                    BASE_URL, "GET", httpParams);
            System.out.println("username??"+BASE_URL+username);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject jo;
                System.out.println(success);
                if (success == 1) {
                    //Parse the JSON response
                    jo = jsonObject.getJSONObject(KEY_DATA1);
                    managementExpenses = jo.getString(KEY_managementExpenses1);
                    salaryAndWagesWorker = jo.getString(KEY_salaryAndWagesWorker1);
                    landRental = jo.getString(KEY_landRental1);
                    fertilizer = jo.getString(KEY_fertilizer1);
                    foliar = jo.getString(KEY_foliar1);
                    racun = jo.getString(KEY_racun1);
                    electric = jo.getString(KEY_electric1);
                    water = jo.getString(KEY_water1);
                    cocopeat = jo.getString(KEY_cocopeat1);
                    polybag = jo.getString(KEY_polybag1);
                    silvershine = jo.getString(KEY_silvershine1);
                    traySemaian = jo.getString(KEY_traySemaian1);
                    lainsusutnilai = jo.getString(KEY_lainsusutnilai1);
                    marketingCost = jo.getString(KEY_marketingCost1);
                    FinancialCost = jo.getString(KEY_FinancialCost1);
                    llk = jo.getString(KEY_llk1);
                    jualanBGredA = jo.getString(KEY_jualanBGredA1);
                    jualanBGredB = jo.getString(KEY_jualanBGredB1);
                    jualanBGredC = jo.getString(KEY_jualanBGredC1);
                    jualanLGredA = jo.getString(KEY_jualanLGredA1);
                    jualanLGredB = jo.getString(KEY_jualanLGredB1);
                    jualanLGredC = jo.getString(KEY_jualanLGredC1);
                    System.out.println("here you see it"+jualanBGredC+jualanLGredA+jualanLGredB+jualanLGredC);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            jbgredAD.setText("RM " + jualanBGredA);
            jbgredBD.setText("RM " + jualanBGredB);
            jbgredCD.setText("RM " + jualanBGredC);
            jlgredAD.setText("RM " + jualanLGredA);
            jlgredBD.setText("RM " + jualanLGredB);
            jlgredCD.setText("RM " + jualanLGredC);
            etmED.setText("RM " + managementExpenses);
            etsWWD.setText("RM " + salaryAndWagesWorker);
            etLRD.setText("RM " + landRental);
            etftD.setText("RM " + fertilizer);
            etflD.setText("RM " + foliar);
            etrcD.setText("RM " + racun);
            eteeD.setText("RM " + electric);
            etairD.setText("RM " + water);
            etccD.setText("RM " + cocopeat);
            etpbD.setText("RM " + polybag);
            etssD.setText("RM " + silvershine);
            ettsD.setText("RM " + traySemaian);
            etllsD.setText("RM " + lainsusutnilai);
            etmcD.setText("RM " + marketingCost);
            etfcD.setText("RM " + FinancialCost);
            etllkD.setText("RM " + llk);

            pDialog.dismiss();

        }


    }
}
