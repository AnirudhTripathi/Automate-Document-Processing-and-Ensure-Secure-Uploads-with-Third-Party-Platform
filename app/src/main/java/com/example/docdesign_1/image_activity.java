package com.example.docdesign_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.voice.VoiceInteractionSession;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class image_activity extends AppCompatActivity {

    ImageView imageView;


    String url=null;
    Dialog d;
    String imgName=null;
    BitmapDrawable bitDraw;
    String encodedimg;
    Bitmap bitmp;
    String email=null;
    Uri image_uri;
    String res = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image);
        url = "http://192.168.137.221:5000/upload";
        imageView = (ImageView)findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("email");
            String imageUriString = bundle.getString("image_uri");
            image_uri = Uri.parse(imageUriString);
            try {
                bitmp = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        encodedbitmap(bitmp);
        imageView.setImageURI(image_uri);
    }

    private void encodedbitmap(Bitmap bitmp) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bitmp.compress(Bitmap.CompressFormat.JPEG,100,bout);

        byte []byteImg = bout.toByteArray();
        encodedimg = android.util.Base64.encodeToString(byteImg, Base64.DEFAULT);

    }

    private void uploadtoServer()
    {
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError ve)
            {
                Toast.makeText(image_activity.this, "Error"+ve.toString(), Toast.LENGTH_LONG).show();
            }
    })
        {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("offset","1");
                params.put("email", email);
                params.put("doc_name", imgName);
                params.put("doc_file", encodedimg);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(req);
    }

    public void upload(View v)
    {
        d = new Dialog(image_activity.this);
        d.setContentView(R.layout.file_name_alert);
        d.show();
    }

    public void SaveName(View v)
    {
        EditText ed = d.findViewById(R.id.docNameEt);
        imgName = ed.getText().toString();
        d.dismiss();
        uploadtoServer();
        Toast.makeText(this, "File saved :"+email, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(image_activity.this, MainActivity2.class);
        intent.putExtra("Email",email);
        startActivity(intent);
    }

    public void cancel(View v)
    {
        d.dismiss();
        Intent intent = new Intent(image_activity.this, MainActivity2.class);
        startActivity(intent);
    }
}