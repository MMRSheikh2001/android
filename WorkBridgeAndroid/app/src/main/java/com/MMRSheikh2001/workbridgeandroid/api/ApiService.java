package com.MMRSheikh2001.workbridgeandroid.api;

import com.MMRSheikh2001.workbridgeandroid.masterdata.response.CategoryResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.CountryResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.DistrictResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.DivisionResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.PoliceStationResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.SkillResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.request.ForgotPasswordRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.request.JobApplicationRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.request.JobSearchRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.request.LoginRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.request.UserRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.response.AIInterviewSessionResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.response.JobApplicationResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.response.JobResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.response.UserResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    //Register
    @POST("/api/users/register")
    Call<UserResponseDTO> register(@Body UserRequestDTO request);

    //Login
    @POST("api/auth/login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO request);

    //Forgot password
    @POST("api/auth/forgot-password")
    Call<String> forgotPassword(@Body ForgotPasswordRequestDTO request);

    // ==========================
    // Job
    // ==========================

    @POST("api/jobs/search")
    Call<List<JobResponseDTO>> searchJobs(@Body JobSearchRequestDTO request);

    @GET("api/jobs/{id}")
    Call<JobResponseDTO> getJobById(@Path("id") Long id);


// Job Application
// ==========================

    // Apply Job
    @POST("api/jobapplications/")
    Call<JobApplicationResponseDTO> applyJob(
            @Body JobApplicationRequestDTO request);

    // Find By Id
    @GET("api/jobapplications/{id}")
    Call<JobApplicationResponseDTO> getJobApplicationById(
            @Path("id") Long id);

    // Find By User Profile
    @GET("api/jobapplications/userprofile/{userProfileId}")
    Call<List<JobApplicationResponseDTO>> getApplicationsByUserProfileId(
            @Path("userProfileId") Long userProfileId);

    // Withdraw Application
    @PATCH("api/jobapplications/withdraw/{applicationId}/userprofile/{userProfileId}")
    Call<JobApplicationResponseDTO> withdrawApplication(
            @Path("applicationId") Long applicationId,
            @Path("userProfileId") Long userProfileId);

    // Count Applications
    @GET("api/jobapplications/count/userprofile/{userProfileId}")
    Call<Long> countApplicationsByUserProfileId(
            @Path("userProfileId") Long userProfileId);

    // Check Already Applied
    @GET("api/jobapplications/exist/job/{jobId}/userprofile/{userProfileId}")
    Call<Boolean> existsApplication(
            @Path("jobId") Long jobId,
            @Path("userProfileId") Long userProfileId);

    // Find Application By Job + User
    @GET("api/jobapplications/job/{jobId}/userprofile/{userProfileId}")
    Call<JobApplicationResponseDTO> getApplicationByJobAndUser(
            @Path("jobId") Long jobId,
            @Path("userProfileId") Long userProfileId);


    // ==========================
// AI Interview
// ==========================

    @POST("api/ai/interview/start/{applicationId}")
    Call<AIInterviewSessionResponseDTO> startInterview(
            @Path("applicationId") Long applicationId);

    @POST("api/ai/interview/submit")
    Call<AIInterviewSessionResponseDTO> submitInterview(
            @Body AIInterviewSessionResponseDTO dto);

    @GET("api/ai/interview/{applicationId}")
    Call<AIInterviewSessionResponseDTO> getInterviewByApplicationId(
            @Path("applicationId") Long applicationId);





    // Category
    @GET("api/categories/")
    Call<List<CategoryResponseDTO>> getAllCategories();

    // Skill
    @GET("api/skills/")
    Call<List<SkillResponseDTO>> getAllSkills();

    @GET("api/skills/category/{id}")
    Call<List<SkillResponseDTO>> getSkillsByCategoryId(@Path("id") Long id);

    // Country
    @GET("api/countries/")
    Call<List<CountryResponseDTO>> getAllCountries();

    // Division
    @GET("api/divisions/")
    Call<List<DivisionResponseDTO>> getAllDivisions();

    @GET("api/divisions/country/{id}")
    Call<List<DivisionResponseDTO>> getDivisionsByCountryId(@Path("id") Long id);

    // District
    @GET("api/districts/")
    Call<List<DistrictResponseDTO>> getAllDistricts();

    @GET("api/districts/division/{id}")
    Call<List<DistrictResponseDTO>> getDistrictsByDivisionId(@Path("id") Long id);

    // Police Station
    @GET("api/policestations/")
    Call<List<PoliceStationResponseDTO>> getAllPoliceStations();

    @GET("api/policestations/district/{id}")
    Call<List<PoliceStationResponseDTO>> getPoliceStationsByDistrictId(@Path("id") Long id);


}
