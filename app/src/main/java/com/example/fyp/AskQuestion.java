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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.fyp.Adapter.comAdapter;
import com.example.fyp.Adapter.commentAdapterList;
import com.example.fyp.Adapter.msglistAdapter;
import com.example.fyp.remotemysqlconnection.helper.CheckNetworkStatus;
import com.example.fyp.remotemysqlconnection.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AskQuestion extends AppCompatActivity {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "https://farmaid.000webhostapp.com/member/db/fetch_comment.php";
    // private RecyclerView rv;
    //a list to store all the products
    List<commentAdapterList> commentList;
    private static final String KEY_DATA1 = "data1";
    private static final String KEY_DATA = "data";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_COMMENT_ID = "comment_id";
    private static final String KEY_PROFILE_PIC = "pic_path";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_COMMENT = "farmerPost";
    private static final String KEY_STATUS = "status";
    private static final String KEY_SUCCESS = "success";
    private ProgressDialog pDialog;
    private SessionHandler session;
    private String username;
    private EditText comment;
    private Button postbutton;
    private Button cancleaq;
    private String pp1 = "";
    private String commentString;
    private ImageView profile_pic;
    private  String usernametemp;
    private ArrayList<HashMap<String, String>> movieList;
    private int status;
    private static final String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";

    //the recyclerview
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();
        username = usernametemp;
        profile_pic =findViewById(R.id.profilepicAQ);
        postbutton = findViewById(R.id.farmerButton);
        comment = findViewById(R.id.farmerEditText);
        Button btn = findViewById(R.id.askbtn);
        //cancleaq = findViewById(R.id.cancleaq);

        new FetchProfilePicAsyncTask().execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(AskQuestion.this, MainPage.class);
                    startActivity(i);

                }

            }
        });

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerViewAQ);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AskQuestion.this));

        //initializing the productlist
        commentList = new ArrayList<>();
        System.out.println("At onCreate Tag1");
        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                commentString = comment.getText().toString().trim();
                new postAsyncTask().execute();

            }
        });


       /* cancleaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(AskQuestion.this, MainPage.class);
                    startActivity(i);
                    finish();
                }

            }
        });*/
    }

    private void msglist() {
        Intent i = new Intent(getApplicationContext(), msgList.class);
        startActivity(i);
        finish();

    }

    private class postAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AskQuestion.this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_USERNAME, username);
            httpParams.put(KEY_COMMENT,commentString);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "postcomment.php", "POST", httpParams);
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
            runOnUiThread(new Runnable() {
                public void run() {
                    if (status == 0) {

                        //Display success message
                        Toast.makeText(AskQuestion.this,
                                "Post Successful..", Toast.LENGTH_LONG).show();
                        MainPage();

                    }
                    else {
                        Toast.makeText(AskQuestion.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


    }

    private void MainPage() {
        Intent i = new Intent(getApplicationContext(), AskQuestion.class);
        startActivity(i);
        finish();

    }

    private class FetchProfilePicAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AskQuestion.this);
            pDialog.setMessage("Loading Please wait...");
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

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            loadFromSite();
            pDialog.dismiss();

        }


    }

    private void loadFromSite(){
        System.out.println("irl is " + pp1);
        Glide.with(AskQuestion.this)
                .load(pp1)
                .into(profile_pic);
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject comment = array.getJSONObject(i);

                                //adding the product to product list
                                commentList.add(new commentAdapterList(
                                        comment.getString("full_name"),
                                        comment.getString("farmerPost"),
                                        comment.getString("pic"),
                                        comment.getString("comment_id")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            comAdapter comadapter = new comAdapter(AskQuestion.this, commentList);
                            recyclerView.setAdapter(comadapter);
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
        Volley.newRequestQueue(AskQuestion.this).add(stringRequest);
    }
}
