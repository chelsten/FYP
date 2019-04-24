package com.example.fyp;

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

public class addPrice extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_PLANT_NAME = "plantname";
    private static final String KEY_PLANT_PRICE = "plantprice";
    private static String STRING_EMPTY = "";
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";
    private SessionHandler session;
    private EditText plantname;
    private EditText plantprice;
    private String plantnametemp;
    private String plantpricetemp;
    private String usernametemp;
    private String username;
    private Button submit;
    private Button cancle;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addprice);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();
        username = usernametemp;


        plantname = (EditText) findViewById(R.id.plantnameET);
        plantprice = (EditText) findViewById(R.id.plantpriceET);
        submit = (Button) findViewById(R.id.apsubmit);
        cancle = (Button) findViewById(R.id.apcancle);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(addPrice.this, adminMain.class);
                    startActivity(i);
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addProfit();
                } else {
                    Toast.makeText(addPrice.this,
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
        if (!STRING_EMPTY.equals(plantname.getText().toString()) &&
                !STRING_EMPTY.equals(plantprice.getText().toString())) {

            plantnametemp = plantname.getText().toString();
            plantpricetemp = plantprice.getText().toString();

            new AddProfitAsyncTask().execute();
        } else {
            Toast.makeText(addPrice.this,
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
            pDialog = new ProgressDialog(addPrice.this);
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
            httpParams.put(KEY_PLANT_NAME, plantnametemp);
            httpParams.put(KEY_PLANT_PRICE, plantpricetemp);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "addpricemarket.php", "POST", httpParams);
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
                        Toast.makeText(addPrice.this,
                                "Plant market price added..", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(addPrice.this, adminMain.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(addPrice.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
