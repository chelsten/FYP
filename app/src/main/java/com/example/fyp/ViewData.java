package com.example.fyp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp.Adapter.adapter3List;
import com.example.fyp.Adapter.adapterList;
import com.example.fyp.Adapter.rv3Adapter;
import com.example.fyp.Adapter.rvAdapter;
import com.example.fyp.remotemysqlconnection.helper.CheckNetworkStatus;
import com.example.fyp.remotemysqlconnection.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewData extends AppCompatActivity {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "https://farmaid.000webhostapp.com/member/db/fetch_savedata.php?userid=";
    // private RecyclerView rv;
    //a list to store all the products
    List<adapter3List> productList;
    private SessionHandler session;
    private  String usernametemp;
    private String success;
    private ProgressDialog pDialog;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DATA1 = "data1";
    private static final String KEY_SUCCESS = "success";
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";
    private int id;
    private static final String KEY_userid = "userid";

    //the recyclerview
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storenetprofit);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();

        Button btn = findViewById(R.id.savedatabtn);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.dataList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initializing the productlist
        productList = new ArrayList<>();
        System.out.println("At onCreate Tag1");
        //this method will fetch and parse json
        //to display it in recyclerview
        new postAsyncTask().execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(ViewData.this, MainPage.class);
                    startActivity(i);
                }

            }
        });

    }

    private class postAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ViewData.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_USERNAME, usernametemp);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "getid.php", "POST", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject jo;
                System.out.println(success);
                if (success == 1) {
                    jo = jsonObject.getJSONObject(KEY_DATA1);
                    id = jo.getInt(KEY_userid);
                    System.out.println("this::" + id);
                    loadProducts();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();

        }
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new adapter3List(
                                        product.getString("saveid"),
                                        product.getString("jumlahPendapatantemp"),
                                        product.getString("jumlahPerbelanjaantemp"),
                                        product.getString("netProfittemp")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            rv3Adapter adapter = new rv3Adapter(ViewData.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(ViewData.this).add(stringRequest);
    }
}

