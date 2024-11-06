package com.example.epet;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.epet.util.DarkModeHelper;
import com.example.epet.util.SessionManager;
import com.example.epet.view.HeroActivity;
import com.example.epet.view.LoginActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (SessionManager.getInstance(this).hasSession()) {
            startActivity(new Intent(this, HeroActivity.class));
            finish();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);

        ActivityCompat.requestPermissions( this,
                new String[]{
                        READ_EXTERNAL_STORAGE,
                        MANAGE_EXTERNAL_STORAGE
                }, 2
        );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()){
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Pet");
                if (!directory.exists()) {
                    boolean isCreated = directory.mkdirs();
                    if (isCreated) {
                        Log.d("MainActivity", "Folder 'laden' created successfully at: " + directory.getAbsolutePath());
                    } else {
                        Log.e("MainActivity", "Failed to create folder 'laden'.");
                    }
                } else {
                    Log.d("MainActivity", "Folder 'laden' already exists at: " + directory.getAbsolutePath());
                }

            }else{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }

        DarkModeHelper.setDarkModeEnabled(this, DarkModeHelper.isDarkModeEnabled(this));


    }
}