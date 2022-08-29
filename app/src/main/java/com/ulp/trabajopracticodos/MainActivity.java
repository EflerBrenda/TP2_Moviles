package com.ulp.trabajopracticodos;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button btnActivar, btnDesactivar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solicitarPermisos();
        Intent intent = new Intent(this,VerSmsService.class);

        btnActivar= findViewById(R.id.btnActivar);
        btnActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intent);
                btnActivar.setEnabled(false);
                btnDesactivar.setEnabled(true);
            }
        });

        btnDesactivar = findViewById(R.id.btnDesactivar);
        btnDesactivar.setEnabled(false);
        btnDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
                btnActivar.setEnabled(true);
                btnDesactivar.setEnabled(false);
            }
        });
    }
    private void solicitarPermisos() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
        }
    }



}