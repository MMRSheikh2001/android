package com.MMRSheikh2001.workbridgeandroid.api;

import com.MMRSheikh2001.workbridgeandroid.masterdata.response.CategoryResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.CountryResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.DistrictResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.DivisionResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.PoliceStationResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.masterdata.response.SkillResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.request.JobSearchRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.request.LoginRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.response.JobResponseDTO;
import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //Login
    @POST("api/auth/login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO request);

    // ==========================
    // Job
    // ==========================

    @POST("api/jobs/search")
    Call<List<JobResponseDTO>> searchJobs(@Body JobSearchRequestDTO request);

    @GET("api/jobs/{id}")
    Call<JobResponseDTO> getJobById(@Path("id") Long id);


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
