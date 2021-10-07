package com.example.aldeberan.MapFragment;

import com.example.aldeberan.MapFragment.DirectionHelpers.IGoogleAPI;
import com.example.aldeberan.MapFragment.DirectionHelpers.RetrofitClient;

public class Common {

    public static final String baseURL = "https://googleapis.com";
    public static IGoogleAPI getGoogleAPI () {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
}
