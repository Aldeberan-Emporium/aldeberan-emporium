package com.example.aldeberan.models;

import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProductModel extends DatabaseModel {

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
    public JSONObject readProductAll() throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readProductAll");
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }

    //Read product by ID
    public JSONObject readProductById(int prodID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readProductById");
        json.put("product_id", prodID);
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }
}
