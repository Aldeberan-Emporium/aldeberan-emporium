package com.example.aldeberan.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MapModel {

    public MapModel(){}

    //Preprocess address for api call
    public String preprocessAddress (String address) {
        //Replace whitespace and symbols with plus symbol
        String processed = StringEscapeUtils.escapeHtml4(address.replaceAll("[\\s:?!@#$%^&*(),/.]+", "+"));
        return processed;
    }

    //Callback function for getLatLng response
    public interface OnGetLatLngResponseCallback {
        public void onResponse(double lat, double lng);
    }

    //Get latlng from Google Maps API
    public void getLatLng(String address, MapModel.OnGetLatLngResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("address", preprocessAddress(address));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://aldeberan-emporium-nodejs.herokuapp.com/latlng", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.i("JSON", response);
                Log.i("STATUS", String.valueOf(statusCode));

                try {
                    JSONObject latLngObj = new JSONObject(response);
                    double lat = latLngObj.getDouble("lat");
                    double lng = latLngObj.getDouble("lng");
                    callback.onResponse(lat, lng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
                Log.i("STATUS", String.valueOf(statusCode));
                callback.onResponse(0.0, 0.0);
            }
        });
    }

    //Callback function for isValidAddress response
    public interface OnIsValidAddressResponseCallback {
        public void onResponse(int status, String message);
    }

    //Check if address is within West Malaysia
    public void isValidAddress(String address, MapModel.OnIsValidAddressResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("address", preprocessAddress(address));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://aldeberan-emporium-nodejs.herokuapp.com/isaddvalid", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.i("JSON", response);
                Log.i("STATUS", String.valueOf(statusCode));

                try {
                    JSONObject resObj = new JSONObject(response);
                    int status = resObj.getInt("status");
                    String msg = resObj.getString("msg");
                    callback.onResponse(status, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
                Log.i("STATUS", String.valueOf(statusCode));
                callback.onResponse(500, null);
            }
        });
    }
}
