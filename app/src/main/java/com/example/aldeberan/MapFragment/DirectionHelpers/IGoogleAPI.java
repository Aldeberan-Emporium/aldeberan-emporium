package com.example.aldeberan.MapFragment.DirectionHelpers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPI {

    @GET
    Call<String> getDataFromGoogleApi (@Url String url);
}
