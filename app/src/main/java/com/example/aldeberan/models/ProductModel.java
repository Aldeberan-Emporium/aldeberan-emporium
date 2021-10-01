package com.example.aldeberan.models;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Product;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductModel extends DatabaseModel {

    public String data;
    public List<Product> productList = new ArrayList<>();

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

    //Callback function for readProductAll response
    public interface OnResponseCallback {
        public void onResponse(List<Product> response);
    }

    //Admin read all products
    public void readProductAll(OnResponseCallback callback) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("action", "readProductAll");
        this.getData(params, (success, response) -> {
            data = response;
            Log.i("DATAIN", data);

            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int prodID = Integer.parseInt(object.getString("product_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodAvail = Integer.parseInt(object.getString("product_availability"));
                    int prodStock = Integer.parseInt(object.getString("product_stock"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));

                    Product product = new Product();
                    product.setProdID(prodID);
                    product.setProdName(prodName);
                    product.setProdSKU(prodSKU);
                    product.setProdImg(prodImg);
                    product.setProdAvail(prodAvail);
                    product.setProdStock(prodStock);
                    product.setProdPrice(prodPrice);

                    productList.add(product);

                }
            }catch (Exception e){}
            Log.i("PL", String.valueOf(productList));
            callback.onResponse(productList);
        });
    }

    //Read product by ID
    public void readProductById(int prodID){
        RequestParams params = new RequestParams();
        params.put("action", "readProductById");
        params.put("product_id", prodID);
    }
}
