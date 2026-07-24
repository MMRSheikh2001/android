package com.MMRSheikh2001.workbridgeandroid.repository;

import android.content.Context;

import com.MMRSheikh2001.workbridgeandroid.api.ApiClient;
import com.MMRSheikh2001.workbridgeandroid.api.ApiService;
import com.MMRSheikh2001.workbridgeandroid.request.LoginRequestDTO;
import com.MMRSheikh2001.workbridgeandroid.response.LoginResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthRepository {


    private final ApiService apiService;

    public AuthRepository(Context context) {
        apiService = ApiClient.getClient(context);
    }

    public void login(LoginRequestDTO request,
                      Callback<LoginResponseDTO> callback) {

        Call<LoginResponseDTO> call = apiService.login(request);

        call.enqueue(callback);
    }


}
