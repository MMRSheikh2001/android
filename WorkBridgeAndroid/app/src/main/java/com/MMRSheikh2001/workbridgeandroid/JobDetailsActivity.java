package com.MMRSheikh2001.workbridgeandroid;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MMRSheikh2001.workbridgeandroid.api.ApiClient;
import com.MMRSheikh2001.workbridgeandroid.repository.JobRepository;
import com.MMRSheikh2001.workbridgeandroid.response.JobResponseDTO;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends AppCompatActivity {







    private Long jobId;

    private JobRepository jobRepository;


    private TextView tvJobTitle;
    private TextView tvCompany;
    private TextView tvLocation;
    private TextView tvEmployment;
    private TextView tvSalary;
    private TextView tvDeadline;

    private TextView tvDescription;
    private TextView tvResponsibilities;
    private TextView tvEducation;
    private TextView tvExperience;
    private TextView tvAdditional;
    private TextView tvBenefits;

    private ImageView imgCompanyLogo;

    private MaterialButton btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_details);
        init();
        jobRepository = new JobRepository(this);
        jobId = getIntent().getLongExtra("JOB_ID", 0);
        if (jobId != 0) {
            loadJobDetails();
        }

    }

    private void init() {

        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvCompany = findViewById(R.id.tvCompany);
        tvLocation = findViewById(R.id.tvLocation);
        tvEmployment = findViewById(R.id.tvEmployment);
        tvSalary = findViewById(R.id.tvSalary);
        tvDeadline = findViewById(R.id.tvDeadline);

        tvDescription = findViewById(R.id.tvDescription);
        tvResponsibilities = findViewById(R.id.tvResponsibilities);
        tvEducation = findViewById(R.id.tvEducation);
        tvExperience = findViewById(R.id.tvExperience);
        tvAdditional = findViewById(R.id.tvAdditional);
        tvBenefits = findViewById(R.id.tvBenefits);

        imgCompanyLogo = findViewById(R.id.imgCompanyLogo);

        btnApply = findViewById(R.id.btnApply);
    }

    private void loadJobDetails() {
        jobRepository.getJobById(jobId, new Callback<JobResponseDTO>() {
            @Override
            public void onResponse(Call<JobResponseDTO> call, Response<JobResponseDTO> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(JobDetailsActivity.this,
                            "Job not found",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                JobResponseDTO job = response.body();

                bindJob(job);
            }

            @Override
            public void onFailure(Call<JobResponseDTO> call, Throwable t) {

                Toast.makeText(JobDetailsActivity.this,
                        "Unable to connect to server",
                        Toast.LENGTH_SHORT).show();

                t.printStackTrace();
            }
        });
    }

    private void bindJob(JobResponseDTO job) {

        tvJobTitle.setText(job.getTitle());

        tvCompany.setText(job.getCompanyName());

        tvLocation.setText(
                job.getLocationPoliceStationName()
                        + ", "
                        + job.getLocationDistrictName());

        tvEmployment.setText(
                job.getEmploymentType()
                        + " | "
                        + job.getWorkPlaceType());

        if (Boolean.TRUE.equals(job.getIsNegotiable())) {

            tvSalary.setText("Salary: Negotiable");

        } else {

            tvSalary.setText(
                    "Salary: "
                            + job.getSalaryMin()
                            + " - "
                            + job.getSalaryMax());

        }

        tvDeadline.setText(
                "Deadline: " + job.getApplicationDeadline());

        tvDescription.setText(job.getJobDescription());

        tvResponsibilities.setText(job.getJobResponsibilities());

        tvEducation.setText(job.getEducationalRequirements());

        tvExperience.setText(job.getExperienceRequirements());

        tvAdditional.setText(job.getAdditionalRequirements());

        tvBenefits.setText(job.getBenefits());

        String logo = job.getCompanyLogo();

        if (logo != null && !logo.isEmpty()) {

            String imageUrl = ApiClient.BASE_URL
                    + "api/files/companyprofiles/"
                    + logo;

            Glide.with(this)
                    .load(ApiClient.getCompanyLogoUrl(job.getCompanyLogo()))
                    .placeholder(R.drawable.ic_company)
                    .error(R.drawable.ic_company)
                    .into(imgCompanyLogo);
        }

    }


}