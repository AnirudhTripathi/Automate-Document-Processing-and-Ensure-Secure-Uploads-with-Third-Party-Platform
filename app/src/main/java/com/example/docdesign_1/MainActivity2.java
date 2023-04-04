package com.example.docdesign_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.docdesign_1.databinding.ActivityHome2Binding;
import com.example.docdesign_1.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ActivityHome2Binding binding;

    private static final int permission_code = 1000;
    private static final int imageCaptureCode = 1001;
    private ActivityResultLauncher<Intent> cameraLauncher;

    Uri imageUri;
    String Email=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_home);

        binding =ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new homeFragment());
        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");
        Toast.makeText(this, Email, Toast.LENGTH_SHORT).show();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.home:
                    replaceFragment(new homeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new settingsFragment());
                    break;

            }
        return true;
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {

                        // The image is captured successfully, do something with it
                        Intent i = new Intent(MainActivity2.this, image_activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("email", Email);
                        bundle.putString("image_uri", imageUri.toString());
                        i.putExtras(bundle);
                        startActivity(i);

                    } else {
                        // The image capture is cancelled or failed

                    }
                });

    }
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    public void check(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, permission_code);

            } else {
                OpenCamera();
            }

        } else {
            OpenCamera();
        }
    }

    private void OpenCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new Image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        cameraLauncher.launch(cameraIntent);

    }

//    public void generateIds(View v)
//    {
//        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
//        ImageView btn =(ImageView) findViewById(R.id.docum);
//        Intent in = new Intent(getApplicationContext(),FetchFiles.class);
//        in.putExtra("Email",Email);
//        startActivity(in);
//    }
}