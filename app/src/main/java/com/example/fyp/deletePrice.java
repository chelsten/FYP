package com.example.fyp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp.Adapter.adapter2List;
import com.example.fyp.Adapter.adapterList;
import com.example.fyp.Adapter.rv2Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class deletePrice extends AppCompatActivity {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "https://farmaid1.000webhostapp.com/member/db/fetch_market.php";
    // private RecyclerView rv;
    //a list to store all the products
    List<adapter2List> productList;

    //the recyclerview
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteprice);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.marketList2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();
        System.out.println("At onCreate Tag1");
        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
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
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new adapter2List(
                                        product.getString("market_id"),
                                        product.getString("plantType"),
                                        product.getString("datePrice"),
                                        product.getString("TotalPrice")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            rv2Adapter adapter2 = new rv2Adapter(deletePrice.this, productList);
                            recyclerView.setAdapter(adapter2);
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
        Volley.newRequestQueue(deletePrice.this).add(stringRequest);
    }
}
