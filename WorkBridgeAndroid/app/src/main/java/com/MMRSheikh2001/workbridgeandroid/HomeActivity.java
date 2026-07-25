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
    private MaterialCardView cardFindJobs;
    private MaterialCardView cardMyApplications;
    private MaterialCardView cardProfile;
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

        cardFindJobs=findViewById(R.id.cardFindJobs);
        cardMyApplications=findViewById(R.id.cardApplications);
        cardProfile=findViewById(R.id.cardProfile);

        cardFindJobs.setOnClickListener(v->goToJobList());
        cardMyApplications.setOnClickListener(v->goToMyApplications());
        cardProfile.setOnClickListener(v->goToMyProfile());

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

    }
    private void goToMyProfile(){

    }


}