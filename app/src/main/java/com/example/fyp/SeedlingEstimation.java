package com.example.fyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SeedlingEstimation extends AppCompatActivity {

    private EditText paEditText;
    private EditText pa2EditText;
    private EditText ssEditText;
    private EditText rwEditText;
    private EditText nsEditText;
    private TextView tsTextView;
    private TextView tsrTextView;
    private TextView rtTextview;
    private Button seSubmit;
    private Button seCancle;
    private static String STRING_EMPTY = "";
    private static final String KEY_EMPTY = "";

    float value1;
    float value2;
    float value3;
    float value4;
    float value5;
    float value6;
    float value7;
    float value8;
    float value9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seedling_estimation);

        paEditText = (EditText) findViewById(R.id.paEditText);
        pa2EditText = (EditText) findViewById(R.id.pa2EditText);
        ssEditText = (EditText) findViewById(R.id.ssEditText);
        rwEditText = (EditText) findViewById(R.id.rwEditText);
        nsEditText = (EditText) findViewById(R.id.nsEditText);
        seSubmit = (Button) findViewById(R.id.seSubmit);
        seCancle = (Button) findViewById(R.id.seCancle);

        tsTextView = (TextView) findViewById(R.id.tsTextView);
        tsrTextView = (TextView) findViewById(R.id.tsrTextView);
        rtTextview = (TextView) findViewById(R.id.rtTextView);


        seSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!STRING_EMPTY.equals(paEditText.getText().toString()) &&
                        !STRING_EMPTY.equals(ssEditText.getText().toString()) &&
                        !STRING_EMPTY.equals(ssEditText.getText().toString())&&
                        !STRING_EMPTY.equals(rwEditText.getText().toString()) &&
                        !STRING_EMPTY.equals(nsEditText.getText().toString())) {
                    
                    value1 = Float.parseFloat(paEditText.getText() + "");
                    value2 = Float.parseFloat(pa2EditText.getText() + "");
                    value3 = Float.parseFloat(ssEditText.getText() + "");
                    value4 = Float.parseFloat(rwEditText.getText() + "");
                    value5 = Float.parseFloat(nsEditText.getText() + "");

                    value6 = ((value1 * value2)*value5)/value3;
                    value7 = value1/value4;
                    value8 = value6/value7;
                    value9 = value2 / value8;
                    tsTextView.setText(Math.round(value6)+" Seedlings");
                    rtTextview.setText(Math.round(value7));
                    tsrTextView.setText(Math.round(value8) + " per row");

                } else {
                    Toast.makeText(SeedlingEstimation.this,
                            "One or more fields left empty!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        seCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeedlingEstimation.this, MainPage.class);
                startActivity(i);

            }
        });
    }

    private boolean validateInputs() {

        if (KEY_EMPTY.equals(value1)) {
            paEditText.setError("Full Name cannot be empty");
            paEditText.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(value2)) {
            pa2EditText.setError("Username cannot be empty");
            pa2EditText.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(value3)) {
            ssEditText.setError("Password cannot be empty");
            ssEditText.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(value4)) {
            ssEditText.setError("Confirm Password cannot be empty");
            ssEditText.requestFocus();
            return false;
        }
        if (!KEY_EMPTY.equals(value5)) {
            nsEditText.setError("Password and Confirm Password does not match");
            nsEditText.requestFocus();
            return false;
        }

        return true;
    }
}
