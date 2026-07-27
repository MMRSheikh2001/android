package com.MMRSheikh2001.workbridgeandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.MMRSheikh2001.workbridgeandroid.repository.JobRepository;

public class JobDetailsActivity extends AppCompatActivity {

    private Long jobId;

    private JobRepository jobRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_details);
        jobRepository=new JobRepository(this);
        jobId=getIntent().getLongExtra("JOB_ID",0);
        if(jobId!=0){
            loadJobDetails();
        }

    }

    private void loadJobDetails(){

    }
}