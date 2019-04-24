package com.example.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class adminMain extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmain);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        TextView fname = findViewById(R.id.adminname);
        fname.setText(user.getFullName());

        Button addnew = findViewById(R.id.addnewproduct);
        Button delete = findViewById(R.id.deleteproduct);
        Button Logout = findViewById(R.id.Logoutbtnadmin);

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminMain.this, addPrice.class);
                startActivity(i);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminMain.this, deletePrice.class);
                startActivity(i);

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminMain.this, MainActivity.class);
                startActivity(i);


            }
        });

    }


}
