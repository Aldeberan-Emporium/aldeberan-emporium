package com.example.aldeberan.models;

import android.net.Uri;
import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Product;
import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        params.put("product_img", StringEscapeUtils.escapeHtml4(prodImg));
        this.postData(params);
    }

    //Admin update existing product
    public void updateProduct(int prodID, String prodName, String prodSKU, int prodAvail, int prodStock, double prodPrice, String prodImg) {
        RequestParams params = new RequestParams();
        params.put("action", "updateProduct");
        params.put("product_id", prodID);
        params.put("product_name", StringEscapeUtils.escapeHtml4(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml4(prodSKU));
        params.put("product_availability", prodAvail);
        params.put("product_stock", prodStock);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", StringEscapeUtils.escapeHtml4(prodImg));
        this.postData(params);
    }

    //Admin delete existing product
    public void deleteProduct(int prodID){
        RequestParams params = new RequestParams();
        params.put("action", "deleteProduct");
        params.put("product_id", prodID);
        this.postData(params);
    }

    //Admin read all products
    public ArrayList readProductAll() throws JSONException {
        RequestParams params = new RequestParams();
        params.put("action", "readProductAll");

        ArrayList productList = new ArrayList<>();
        this.getData(params);
        String data = this.getRes();

        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String prodID = object.getString("product_id");
                String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                String prodAvail = object.getString("product_availability");
                String prodStock = object.getString("product_stock");
                String prodPrice = object.getString("product_price");

                HashMap<String, String> product = new HashMap<>();
                product.put("product_id", prodID);
                product.put("product_name", prodName);
                product.put("product_SKU", prodSKU);
                product.put("product_img", prodImg);
                product.put("product_availability", prodAvail);
                product.put("product_stock", prodStock);
                product.put("product_price", prodPrice);

                productList.add(product);

            }
        }catch (Exception e){}
        Log.i("PL", String.valueOf(productList));
        return productList;
    }

    //Read product by ID
    public void readProductById(int prodID){
        RequestParams params = new RequestParams();
        params.put("action", "readProductById");
        params.put("product_id", prodID);
    }
}
