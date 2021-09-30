package com.example.aldeberan.models;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProductModel extends DatabaseModel {

    //Admin create new product
    public void addProduct(String prodName, String prodSKU, int prodAvail, int prodStock, double prodPrice, String prodImg){
        RequestParams params = new RequestParams();
        params.put("action", "addProduct");
        params.put("product_name", StringEscapeUtils.escapeHtml4(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml4(prodSKU));
        params.put("product_availability", prodAvail);
        params.put("product_stock", prodStock);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Admin update existing product
    public void updateProduct(int prodID, String prodName, String prodSKU, int prodAvail, int prodStock, double prodPrice, String prodImg) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "updateProduct");
        params.put("product_id", prodID);
        params.put("product_name", StringEscapeUtils.escapeHtml4(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml4(prodSKU));
        params.put("product_availability", prodAvail);
        params.put("product_stock", prodStock);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Admin delete existing product
    public void deleteProduct(int prodID) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "deleteProduct");
        params.put("product_id", prodID);
        this.postData(params);
    }

    //Admin read all products
    public void readProductAll(){
        RequestParams params = new RequestParams();
        params.put("action", "readProductAll");
        this.getData(params);
    }
    /*
    //Read product by ID
    public JSONObject readProductById(int prodID) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "readProductById");
        params.put("product_id", prodID);
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }

     */
}
