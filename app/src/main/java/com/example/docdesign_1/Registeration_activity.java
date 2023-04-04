package com.example.docdesign_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Registeration_activity extends AppCompatActivity {
    EditText email_edit_text,password_edit_text,password_confirm_text,fname,lname,phone;
    Button reg;
    String res=null;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flag = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        email_edit_text = findViewById(R.id.email_edit_text);
        password_confirm_text = findViewById(R.id.password_confirm_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        reg = findViewById(R.id.register_button);
        fname = findViewById(R.id.first_name_edit_text);
        lname = findViewById(R.id.last_name_edit_text);
        phone = findViewById(R.id.contact_edit_text);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAll();
                if(flag)
                    postDataUsingVolley(fname.getText().toString(), lname.getText().toString(),email_edit_text.getText().toString(),phone.getText().toString(),password_edit_text.getText().toString());
            }
        });
    }

    private void postDataUsingVolley(String fn, String ln, String email, String call, String pass) {
        String url = "http://192.168.137.221:5000/register";

        RequestQueue queue = Volley.newRequestQueue(Registeration_activity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    res = js.getString("msg");
                    if(res.equals("true"))
                    {
                        Toast.makeText(Registeration_activity.this, "Registration successfull !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registeration_activity.this, MainActivity2.class);
                        intent.putExtra("Email",email);
                        startActivity(intent);
                    }
                    else {
                        email_edit_text.setText("");
                        password_edit_text.setText("");
                        password_confirm_text.setText("");
                        lname.setText("");
                        fname.setText("");
                        phone.setText("");
                        Toast.makeText(Registeration_activity.this, "You are already registered with this email..! ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // on below line we are displaying a success toast message.

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Registeration_activity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", fn);
                params.put("lastname", ln);
                params.put("email", email);
                params.put("password", pass);
                params.put("phone", call);
                return params;
            }
        };
        queue.add(request);
    }

    private void checkAll() {
        if(email_edit_text.getText().toString().length() <5 || password_edit_text.getText().toString().length() < 5 || password_confirm_text.getText().toString().length() <5)
        {
            Toast.makeText(Registeration_activity.this, "Enter complete details", Toast.LENGTH_SHORT).show();
            email_edit_text.setText("");password_edit_text.setText("");password_confirm_text.setText("");
        }
        else if(!password_edit_text.getText().toString().equals(password_confirm_text.getText().toString()))
        {
            Toast.makeText(this, "Enter correct password"+password_edit_text.getText().toString()+" "+password_confirm_text.getText().toString(), Toast.LENGTH_SHORT).show();
            password_edit_text.setText("");password_confirm_text.setText("");
        }
        else {
            flag = true;
        }
    }
}