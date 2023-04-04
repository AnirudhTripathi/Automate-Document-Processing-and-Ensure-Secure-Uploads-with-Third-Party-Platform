package com.example.docdesign_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.docdesign_1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FetchFiles extends AppCompatActivity {

    String []docs = {"d1","d2","d3"};
    ListView lv;
    String Email=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_files);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        Intent in = getIntent();
        Email = in.getStringExtra("Email");
        Toast.makeText(this, "Fetch"+Email, Toast.LENGTH_SHORT).show();
        lv = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adap = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_list_view,R.id.docs,docs);
        lv.setAdapter(adap);
//        postDataUsingVolley(Email);

    }

    private void postDataUsingVolley(String email) {
        String url = "http://192.168.137.221:5000/getData";

        RequestQueue queue = Volley.newRequestQueue(FetchFiles.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    String res = js.getString("data");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // on below line we are displaying a success toast message.

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(FetchFiles.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        queue.add(request);
    }
}