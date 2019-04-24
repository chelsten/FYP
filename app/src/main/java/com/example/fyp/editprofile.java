package com.example.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fyp.remotemysqlconnection.ProfitPage;
import com.example.fyp.remotemysqlconnection.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PROFILE_IMAGE = "profile_image";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private static String STRING_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFullName;
    private ImageView profileImage;
    private String username;
    private String usernametemp;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String encodedImage = "";
    private ProgressDialog pDialog;
    private String BASE_URL = "https://farmaid.000webhostapp.com/member/db/";
    private SessionHandler session;
    private int status;

    private Bitmap image;
    Boolean imagepresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.editprofile);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        usernametemp = user.getUsername();
        username = usernametemp;
        profileImage = (ImageView) findViewById(R.id.editpic);

        etUsername = findViewById(R.id.editusername);
        etPassword = findViewById(R.id.editpss);
        etConfirmPassword = findViewById(R.id.editcpss);
        etFullName = findViewById(R.id.editfullname);

        Button bck = findViewById(R.id.btneditbck);
        Button edit = findViewById(R.id.btnedit);
        Button reset = findViewById(R.id.btnreset);

        profileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent galleryIntent;
                galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

            }
        });


        //Launch Login screen when Login Button is clicked
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editprofile.this, MainActivity.class);
                startActivity(i);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts

                if (!STRING_EMPTY.equals(etPassword.getText().toString()) &&
                        !STRING_EMPTY.equals(etConfirmPassword.getText().toString())&&
                        !STRING_EMPTY.equals(etFullName.getText().toString()) &&
                        profileImage.getDrawable() != null) {
                    password = etPassword.getText().toString().trim();
                    confirmPassword = etConfirmPassword.getText().toString().trim();
                    fullName = etFullName.getText().toString().trim();
                    registerUser();


                } else {
                    Toast.makeText(editprofile.this,
                            "One or more fields left empty!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void MainPage() {
        Intent i = new Intent(getApplicationContext(), MainPage.class);
        startActivity(i);


    }


    private void registerUser(){
        new RegisterAsyncTask().execute();
    }

    private class RegisterAsyncTask extends AsyncTask<String, String, String> {
        Bitmap image = ((BitmapDrawable)profileImage.getDrawable()).getBitmap();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(editprofile.this);
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
            encodedImage = convertImage(image);
            httpParams.put(KEY_USERNAME, username);
            httpParams.put(KEY_PROFILE_IMAGE,encodedImage);
            httpParams.put(KEY_PASSWORD, password);
            httpParams.put(KEY_FULL_NAME, fullName);
            System.out.println(username+" "+encodedImage+" "+password+" "+fullName);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "editprofile.php", "POST", httpParams);
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
                        Toast.makeText(editprofile.this,
                                "edit profile Successful..", Toast.LENGTH_LONG).show();
                        session.loginUser(username,fullName);
                        MainPage();
                    }
                    else {
                        Toast.makeText(editprofile.this,
                                "Some error occurred..",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private String convertImage(Bitmap image){
        String encoded;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        return encoded;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                profileImage.setImageURI(selectedImage);
                imagepresent = true;
            }
        }

    }
    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {

        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}