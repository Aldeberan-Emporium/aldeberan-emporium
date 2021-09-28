package com.example.aldeberan;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Database {

    public String DB = "https://aldeberan-emporium.herokuapp.com/";

    //Post data to database
    public String postData(@NonNull JSONObject data) throws IOException {
        URL url = new URL (this.DB);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes("UTF-8"));
        os.close();

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(StringEscapeUtils.unescapeHtml3(responseLine.trim()));
            }
            return response.toString();
        }
    }

    //Admin create new product
    public void addProduct(String prodName, String prodSKU, int prodAvail, int prodStock, double prodPrice, String prodImg) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addProduct");
        json.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        json.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        json.put("product_availability", prodAvail);
        json.put("product_stock", prodStock);
        json.put("product_price", prodPrice);
        json.put("product_img", StringEscapeUtils.escapeHtml3(prodImg));
        this.postData(json);
    }

    //Admin update existing product
    public void updateProduct(int prodID, String prodName, String prodSKU, int prodAvail, int prodStock, double prodPrice, String prodImg) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "updateProduct");
        json.put("product_id", prodID);
        json.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        json.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        json.put("product_availability", prodAvail);
        json.put("product_stock", prodStock);
        json.put("product_price", prodPrice);
        json.put("product_img", StringEscapeUtils.escapeHtml3(prodImg));
        this.postData(json);
    }

    //Admin delete existing product
    public void deleteProduct(int prodID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "deleteProduct");
        json.put("product_id", prodID);
        this.postData(json);
    }

    //Admin read all products
    public JsonObject readProductAll() throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readProductAll");
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Read product by ID
    public JsonObject readProductById(int prodID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readProductById");
        json.put("product_id", prodID);
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Add quote
    public void addQuote(String userID, double subtotal, double total, int prodStock, double prodPrice, String prodImg) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addQuote");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        json.put("subtotal", subtotal);
        json.put("product_stock", prodStock);
        json.put("product_price", prodPrice);
        json.put("product_img", StringEscapeUtils.escapeHtml3(prodImg));
        this.postData(json);
    }
}
