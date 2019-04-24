package com.example.fyp.remotemysqlconnection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.SessionHandler;
import com.example.fyp.User;
import com.example.fyp.remotemysqlconnection.helper.CheckNetworkStatus;
import com.example.fyp.remotemysqlconnection.helper.HttpJsonParser;
import com.example.fyp.MainPage;
import com.example.fyp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExpensesPage extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ETME = "etmE";
    private static final String KEY_ETSWW = "etsWW";
    private static final String KEY_ETLR = "etLR";
    private static final String KEY_ETFT = "etft";
    private static final String KEY_ETFL = "etfl";
    private static final String KEY_ETRC = "etrc";
    private static final String KEY_ETEE = "etee";
    private static final String KEY_ETAIR = "etair";
    private static final String KEY_ETCC = "etcc";
    private static final String KEY_ETPB = "etpb";
    private static final String KEY_ETSS = "etss";
    private static final String KEY_ETTS = "etts";
    private static final String KEY_ETLLS = "etlls";
    private static final String KEY_ETMC = "etmc";
    private static final String KEY_ETFC = "etfc";
    private static final String KEY_ETLLK = "etllk";
    private static final String KEY_EMPTY = "";
    private static String STRING_EMPTY = "";
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";
    private SessionHandler session;
    private EditText meEditText;
    private EditText swwEditText;
    private EditText LREditText;
    private EditText ftEditText;
    private EditText flEditText;
    private EditText rcEditText;
    private EditText eeEditText;
    private EditText airEditText;
    private EditText ccEditText;
    private EditText pbEditText;
    private EditText ssEditText;
    private EditText tsEditText;
    private EditText llsEditText;
    private EditText mcEditText;
    private EditText fcEditText;
    private EditText llkEditText;
    private String meST;
    private String swwST;
    private String LRST;
    private String ftST;
    private String flST;
    private String rcST;
    private String eeST;
    private String airST;
    private String ccST;
    private String pbST;
    private String ssST;
    private String tsST;
    private String llsST;
    private String mcST;
    private String fcST;
    private String llkST;
    private String usernametemp;
    private String username;
    private Button epsubmit;
    private Button epcancle;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_page);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();
        username = usernametemp;

        meEditText = (EditText) findViewById(R.id.etmE);
        swwEditText = (EditText) findViewById(R.id.etsWW);
        LREditText = (EditText) findViewById(R.id.etLR);
        ftEditText = (EditText) findViewById(R.id.etft);
        flEditText = (EditText) findViewById(R.id.etfl);
        rcEditText = (EditText) findViewById(R.id.etrc);
        eeEditText = (EditText) findViewById(R.id.etee);
        airEditText = (EditText) findViewById(R.id.etair);
        ccEditText = (EditText) findViewById(R.id.etcc);
        pbEditText = (EditText) findViewById(R.id.etpb);
        ssEditText = (EditText) findViewById(R.id.etss);
        tsEditText = (EditText) findViewById(R.id.etts);
        llsEditText = (EditText) findViewById(R.id.etlls);
        mcEditText = (EditText) findViewById(R.id.etmc);
        fcEditText = (EditText) findViewById(R.id.etfc);
        llkEditText = (EditText) findViewById(R.id.etllk);
        epsubmit = (Button) findViewById(R.id.epsubmit);
        epcancle = (Button) findViewById(R.id.epcancle);

        epcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(ExpensesPage.this, MainPage.class);
                    startActivity(i);
                }

            }
        });

        epsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addProfit();
                } else {
                    Toast.makeText(ExpensesPage.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    /**
     * Checks whether all files are filled. If so then calls AddMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addProfit() {

        if (!STRING_EMPTY.equals(meEditText.getText().toString()) &&
                !STRING_EMPTY.equals(swwEditText.getText().toString()) &&
                !STRING_EMPTY.equals(LREditText.getText().toString())&&
                !STRING_EMPTY.equals(ftEditText.getText().toString()) &&
                !STRING_EMPTY.equals(flEditText.getText().toString()) &&
                !STRING_EMPTY.equals(rcEditText.getText().toString()) &&
                !STRING_EMPTY.equals(eeEditText.getText().toString()) &&
                !STRING_EMPTY.equals(airEditText.getText().toString())&&
                !STRING_EMPTY.equals(ccEditText.getText().toString()) &&
                !STRING_EMPTY.equals(pbEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ssEditText.getText().toString()) &&
                !STRING_EMPTY.equals(tsEditText.getText().toString()) &&
                !STRING_EMPTY.equals(llsEditText.getText().toString())&&
                !STRING_EMPTY.equals(mcEditText.getText().toString()) &&
                !STRING_EMPTY.equals(fcEditText.getText().toString()) &&
                !STRING_EMPTY.equals(llkEditText.getText().toString()) ) {
            meST = meEditText.getText().toString();
            swwST = swwEditText.getText().toString();
            LRST = LREditText.getText().toString();
            ftST = ftEditText.getText().toString();
            flST = flEditText.getText().toString();
            rcST = rcEditText.getText().toString();
            eeST = eeEditText.getText().toString();
            airST = airEditText.getText().toString();
            ccST = ccEditText.getText().toString();
            pbST = pbEditText.getText().toString();
            ssST = ssEditText.getText().toString();
            tsST = tsEditText.getText().toString();
            llsST = llsEditText.getText().toString();
            mcST = mcEditText.getText().toString();
            fcST = fcEditText.getText().toString();
            llkST = llkEditText.getText().toString();
            new AddProfitAsyncTask().execute();


        }else{
                Toast.makeText(ExpensesPage.this,
                        "One or more fields left empty!",
                        Toast.LENGTH_LONG).show();

        }
    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddProfitAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(ExpensesPage.this);
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
            httpParams.put(KEY_USERNAME,username);
            httpParams.put(KEY_ETME, meST);
            httpParams.put(KEY_ETSWW, swwST);
            httpParams.put(KEY_ETLR, LRST);
            httpParams.put(KEY_ETFT, ftST);
            httpParams.put(KEY_ETFL, flST);
            httpParams.put(KEY_ETRC, rcST);
            httpParams.put(KEY_ETEE, eeST);
            httpParams.put(KEY_ETAIR, airST);
            httpParams.put(KEY_ETCC, ccST);
            httpParams.put(KEY_ETPB, pbST);
            httpParams.put(KEY_ETSS, ssST);
            httpParams.put(KEY_ETTS, tsST);
            httpParams.put(KEY_ETLLS, llsST);
            httpParams.put(KEY_ETMC, mcST);
            httpParams.put(KEY_ETFC, fcST);
            httpParams.put(KEY_ETLLK, llkST);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_expenses.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
                Log.i("tagconvertstr", "["+success+"]");
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
                        Toast.makeText(ExpensesPage.this,
                                "Profit Added", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ExpensesPage.this, MainPage.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(ExpensesPage.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private boolean validateInputs() {

        if (KEY_EMPTY.equals(meST)) {
            meEditText.setError("cannot be empty");
            meEditText.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(swwST)) {
            swwEditText.setError("cannot be empty");
            swwEditText.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(LRST)) {
            LREditText.setError("cannot be empty");
            LREditText.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(ftST)) {
            ftEditText.setError("cannot be empty");
            ftEditText.requestFocus();
            return false;
        }
        if (!KEY_EMPTY.equals(flST)) {
            flEditText.setError("cannot be empty");
            flEditText.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(rcST)) {
            rcEditText.setError("cannot be empty");
            rcEditText.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(eeST)) {
            eeEditText.setError("cannot be empty");
            eeEditText.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(airST)) {
            airEditText.setError("cannot be empty");
            airEditText.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(ccST)) {
            ccEditText.setError("cannot be empty");
            ccEditText.requestFocus();
            return false;
        }
        if (!KEY_EMPTY.equals(pbST)) {
            pbEditText.setError("cannot be empty");
            pbEditText.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(ssST)) {
            ssEditText.setError("cannot be empty");
            ssEditText.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(tsST)) {
            tsEditText.setError("cannot be empty");
            tsEditText.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(llsST)) {
            llsEditText.setError("cannot be empty");
            llsEditText.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(mcST)) {
            mcEditText.setError("cannot be empty");
            mcEditText.requestFocus();
            return false;
        }
        if (!KEY_EMPTY.equals(fcST)) {
            fcEditText.setError("cannot be empty");
            fcEditText.requestFocus();
            return false;
        }
        if (!KEY_EMPTY.equals(llkST)) {
            llkEditText.setError("cannot be empty");
            llkEditText.requestFocus();
            return false;
        }


        return true;
    }
}
