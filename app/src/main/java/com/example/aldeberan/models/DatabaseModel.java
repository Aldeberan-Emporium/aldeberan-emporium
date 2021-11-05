package com.example.aldeberan.models;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

/*
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664

Ong and Yong are reponsible for this. 
Ong setup the database using heroku and linked it to android studio.

Yong using PHP to write the SQL command for the backend.

For debugging every members are involved.
*/

public class DatabaseModel {

    public String res;

    //Post data to database
    public void postData(@NonNull RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://aldeberan-emporium.herokuapp.com/", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.i("JSON", response);
                Log.i("STATUS", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
                Log.i("STATUS", String.valueOf(statusCode));
            }
        });
    }

    //Callback function for getData response
    public interface OnResponseCallback {
        public void onResponse(boolean success, String response) throws JSONException;
    }

    //Get data from database
    public void getData(@NonNull RequestParams params, OnResponseCallback callback){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://aldeberan-emporium.herokuapp.com/", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.i("JSON", response);
                Log.i("STATUS", String.valueOf(statusCode));
                try {
                    callback.onResponse(true, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
                Log.i("STATUS-GET-FAIL", String.valueOf(statusCode));
                try {
                    callback.onResponse(false, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
