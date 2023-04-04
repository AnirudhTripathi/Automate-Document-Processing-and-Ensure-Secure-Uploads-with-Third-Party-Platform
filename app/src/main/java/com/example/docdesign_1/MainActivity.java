package com.example.docdesign_1;


import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText nameEdt, jobEdt;
    private TextView tv;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;
    String res=null;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // initializing our views
        nameEdt = findViewById(R.id.editTextTextPersonName);
        jobEdt = findViewById(R.id.editTextTextPassword);
        postDataBtn = findViewById(R.id.button_login);
        tv = findViewById(R.id.textView2);
        sp = getSharedPreferences("myLogin", Context.MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("Email", sp.getString("email", ""));
            startActivity(intent);
            finish();
        }

        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postDataUsingVolley(nameEdt.getText().toString(), jobEdt.getText().toString());
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Registeration_activity.class);
                startActivity(i);
            }
        });
    }



    private void postDataUsingVolley(String email, String password) {
        // url to post our data
        String url = "http://192.168.137.221:5000/login";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty

                try { 
                    JSONObject js = new JSONObject(response);
                    res = js.getString("login");
                    if(res.equals("true"))
                    {
                        Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Logging In", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        intent.putExtra("Email",email);
                        startActivity(intent);
                    }
                    else {
                        nameEdt.setText("");
                        jobEdt.setText("");
                        Toast.makeText(MainActivity.this, "Enter valid credentials !", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "exception", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }

                // on below line we are displaying a success toast message.

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("email", email);
                params.put("password", password);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);

    }
}