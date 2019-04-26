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

public class displayfulldata extends AppCompatActivity {

    private static final String KEY_DATA1 = "data1";
    private static final String KEY_saveid = "saveid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PROFILE_PIC = "pic_path";
    private static final String KEY_JUMLAH_PENDAPATAN = "jumlahPendapatan";
    private static final String KEY_JUMLAH_PERBELANJAAN = "jumlahPerbelanjaan";
    private static final String KEY_NET_PROFIT = "netProfit";
    private static final String KEY_managementExpenses = "managementExpenses";
    private static final String KEY_salaryAndWagesWorker = "salaryAndWagesWorker";
    private static final String KEY_landRental = "landRental";
    private static final String KEY_fertilizer = "fertilizer";
    private static final String KEY_foliar = "foliar";
    private static final String KEY_racun = "racun";
    private static final String KEY_electric = "electric";
    private static final String KEY_water = "water";
    private static final String KEY_cocopeat = "cocopeat";
    private static final String KEY_polybag = "polybag";
    private static final String KEY_silvershine = "silvershine";
    private static final String KEY_traySemaian = "traySemaian";
    private static final String KEY_lainsusutnilai = "lainsusutnilai";
    private static final String KEY_marketingCost = "marketingCost";
    private static final String KEY_FinancialCost = "FinancialCost";
    private static final String KEY_llk = "llk";
    private static final String KEY_jualanBGredA = "jualanBGredA";
    private static final String KEY_jualanBGredB = "jualanBGredB";
    private static final String KEY_jualanBGredC = "jualanBGredC";
    private static final String KEY_jualanLGredA = "jualanLGredA";
    private static final String KEY_jualanLGredB = "jualanLGredB";
    private static final String KEY_jualanLGredC = "jualanLGredC";
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
    private static final String BASE_URL = "https://farmaid1.000webhostapp.com/member/db/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayfulldata);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

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

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        System.out.println("id is: " + id);

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
            pDialog = new ProgressDialog(displayfulldata.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_allsavedata.php?saveid="+id, "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject jo;
                System.out.println(success);
                if (success == 1) {
                    //Parse the JSON response
                    jo = jsonObject.getJSONObject(KEY_DATA1);
                    managementExpenses = jo.getString(KEY_managementExpenses);
                    salaryAndWagesWorker = jo.getString(KEY_salaryAndWagesWorker);
                    landRental = jo.getString(KEY_landRental);
                    fertilizer = jo.getString(KEY_fertilizer);
                    foliar = jo.getString(KEY_foliar);
                    racun = jo.getString(KEY_racun);
                    electric = jo.getString(KEY_electric);
                    water = jo.getString(KEY_water);
                    cocopeat = jo.getString(KEY_cocopeat);
                    polybag = jo.getString(KEY_polybag);
                    silvershine = jo.getString(KEY_silvershine);
                    traySemaian = jo.getString(KEY_traySemaian);
                    lainsusutnilai = jo.getString(KEY_lainsusutnilai);
                    marketingCost = jo.getString(KEY_marketingCost);
                    FinancialCost = jo.getString(KEY_FinancialCost);
                    llk = jo.getString(KEY_llk);
                    jualanBGredA = jo.getString(KEY_jualanBGredA);
                    jualanBGredB = jo.getString(KEY_jualanBGredB);
                    jualanBGredC = jo.getString(KEY_jualanBGredC);
                    jualanLGredA = jo.getString(KEY_jualanLGredA);
                    jualanLGredB = jo.getString(KEY_jualanLGredB);
                    jualanLGredC = jo.getString(KEY_jualanLGredC);
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
