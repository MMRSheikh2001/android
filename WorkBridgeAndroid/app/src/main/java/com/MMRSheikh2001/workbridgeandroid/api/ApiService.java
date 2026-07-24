package com.MMRSheikh2001.workbridgeandroid.api;

import com.MMRSheikh2001.workbridgeandroid.request.LoginRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO request);

}
