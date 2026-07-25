package com.MMRSheikh2001.workbridgeandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.MMRSheikh2001.workbridgeandroid.session.SessionManager;
import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    private MaterialButton btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        init();

        btnLogout.setOnClickListener(v->logout());
    }

    private  void init(){
        btnLogout=findViewById(R.id.btnLogout);

        sessionManager=new SessionManager(this);
    }
    private void logout(){
        sessionManager.logout();
        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


}