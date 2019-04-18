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

public class ProfitPage extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_JBGA = "etjbgredA";
    private static final String KEY_JBGB = "etjbgredB";
    private static final String KEY_JBGC = "etjbgredC";
    private static final String KEY_JLGA = "etjlgredA";
    private static final String KEY_JLGB = "etjlgredB";
    private static final String KEY_JLGC = "etjlgredC";
    private static String STRING_EMPTY = "";
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";
    private SessionHandler session;
    private EditText jlgredAEditText;
    private EditText jlgredBEditText;
    private EditText jlgredCEditText;
    private EditText jbgredAEditText;
    private EditText jbgredBEditText;
    private EditText jbgredCEditText;
    private String jbgredA;
    private String jbgredB;
    private String jbgredC;
    private String jlgredA;
    private String jlgredB;
    private String jlgredC;
    private String usernametemp;
    private String username;
    private Button submit;
    private Button cancle;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_page);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();
        username = usernametemp;

        jbgredAEditText = (EditText) findViewById(R.id.jbgredA);
        jbgredBEditText = (EditText) findViewById(R.id.jbgredB);
        jbgredCEditText = (EditText) findViewById(R.id.jbgredC);
        jlgredAEditText = (EditText) findViewById(R.id.jlgredA);
        jlgredBEditText = (EditText) findViewById(R.id.jlgredB);
        jlgredCEditText = (EditText) findViewById(R.id.jlgredC);
        submit = (Button) findViewById(R.id.submit);
        cancle = (Button) findViewById(R.id.cancle);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(ProfitPage.this, MainPage.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addProfit();
                } else {
                    Toast.makeText(ProfitPage.this,
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
        if (!STRING_EMPTY.equals(jbgredAEditText.getText().toString()) &&
                !STRING_EMPTY.equals(jbgredBEditText.getText().toString()) &&
                !STRING_EMPTY.equals(jbgredCEditText.getText().toString())&&
                !STRING_EMPTY.equals(jlgredAEditText.getText().toString()) &&
                !STRING_EMPTY.equals(jlgredBEditText.getText().toString()) &&
                !STRING_EMPTY.equals(jlgredCEditText.getText().toString()) ) {

            jbgredA = jbgredAEditText.getText().toString();
            jbgredB = jbgredBEditText.getText().toString();
            jbgredC = jbgredCEditText.getText().toString();
            jlgredA = jlgredAEditText.getText().toString();
            jlgredB = jlgredBEditText.getText().toString();
            jlgredC = jlgredCEditText.getText().toString();

            new AddProfitAsyncTask().execute();
        } else {
            Toast.makeText(ProfitPage.this,
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
            pDialog = new ProgressDialog(ProfitPage.this);
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
            httpParams.put(KEY_JBGA, jbgredA);
            httpParams.put(KEY_JBGB, jbgredB);
            httpParams.put(KEY_JBGC, jbgredC);
            httpParams.put(KEY_JLGA, jlgredA);
            httpParams.put(KEY_JLGB, jlgredB);
            httpParams.put(KEY_JLGC, jlgredC);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_profit.php", "POST", httpParams);
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
                        Toast.makeText(ProfitPage.this,
                                "Profit Added", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ProfitPage.this, MainPage.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(ProfitPage.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
