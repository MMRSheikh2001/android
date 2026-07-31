package com.MMRSheikh2001.workbridgeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private MaterialButton btnFindJobs;
    private MaterialButton btnMyApplications;
    private MaterialButton btnProfile;
    private MaterialButton btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        init();

        LoginResponseDTO user=sessionManager.getUser();
        if(user!=null){
            tvWelcome.setText("Welcome, "+user.getDisplayName()+" !");
        }

        btnLogout.setOnClickListener(v->logout());
    }

    private  void init(){
        tvWelcome=findViewById(R.id.tvWelcome);

        btnFindJobs=findViewById(R.id.btnFindJobs);
        btnMyApplications=findViewById(R.id.btnApplications);
        btnProfile=findViewById(R.id.btnProfile);

        btnFindJobs.setOnClickListener(v->goToJobList());
        btnMyApplications.setOnClickListener(v->goToMyApplications());
        btnProfile.setOnClickListener(v->goToMyProfile());

        btnLogout=findViewById(R.id.btnLogout);

        sessionManager=new SessionManager(this);
    }
    private void logout(){
        sessionManager.logout();
        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


        finish();
    }

    private void goToJobList(){

        Intent intent=new Intent(HomeActivity.this, JobListActivity.class);
        startActivity(intent);

    }

    private void goToMyApplications(){

        Intent intent = new Intent(
                HomeActivity.this,
                MyApplications.class);

        startActivity(intent);

    }
    private void goToMyProfile(){

    }


}