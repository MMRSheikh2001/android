package com.MMRSheikh2001.workbridgeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    private static final Integer SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            // Later we'll check JWT here

            Intent intent = new Intent(
                    SplashActivity.this,
                    LoginActivity.class);

            startActivity(intent);
            finish();

        }, SPLASH_DELAY);
    }
}