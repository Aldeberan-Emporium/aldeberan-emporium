package com.example.aldeberan.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664

Ong and Yong are responsible for this. 
Ong setup the database using heroku and linked it to android studio.

Yong using PHP to write the SQL command for the backend.

For debugging every members are involved.
*/

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
    public void deleteProduct(int prodID, String prodSKU, String prodImg){
        RequestParams params = new RequestParams();
        params.put("action", "deleteProduct");
        params.put("product_id", prodID);
        this.postData(params);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageRef.child("products/"+prodSKU+"."+extValidator(prodImg));

        // Delete the file
        fileRef.delete().addOnSuccessListener(aVoid -> {
            Log.i("DELETESUCCESS", "File deleted from firebase!");
        }).addOnFailureListener(exception -> {
            Log.i("DELETEFAILED", "File failed to delete!");
        });
    }

    //Callback function for readProductAll response
    public interface OnResponseCallback {
        public void onResponse(List<Product> response);
    }

    //Admin read all products
    public void readProductAll(OnResponseCallback callback) {
        RequestParams params = new RequestParams();
        params.put("action", "readProductAll");
        this.getData(params, (success, response) -> {
            List<Product> productList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int prodID = Integer.parseInt(object.getString("product_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodAvail = Integer.parseInt(object.getString("product_availability"));
                    int prodStock = Integer.parseInt(object.getString("product_stock"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));
                    int prodSold = Integer.parseInt(object.getString("product_sold"));
                    int wishID = Integer.parseInt(object.getString("wishlist_id"));

                    //Only top 4 items will display top seller
                    Product product = new Product();
                    product.setProdID(prodID);
                    product.setProdName(prodName);
                    product.setProdSKU(prodSKU);
                    product.setProdImg(prodImg);
                    product.setProdAvail(prodAvail);
                    product.setProdStock(prodStock);
                    product.setProdPrice(prodPrice);
                    product.setProdSold(prodSold);
                    product.setWishID(wishID);
                    if (i < 4) { product.setIsTopSeller(true); }
                    else {product.setIsTopSeller(false);}
                    productList.add(product);
                }
            }catch (Exception e){}
            Log.i("ALLPROD", String.valueOf(productList));
            callback.onResponse(productList);
        });
    }

    //Read product by ID
    public void readProductById(int prodID){
        RequestParams params = new RequestParams();
        params.put("action", "readProductById");
        params.put("product_id", prodID);
    }

    //Check file extension from url
    public String extValidator(String prodImg){
        //Only jpg, jpeg, png files are valid atm
        String[] extArr = {"jpg", "jpeg", "png"};
        String ext = "";
        for (int i = 0; i < extArr.length; i++){
            if (prodImg.contains(extArr[i])){
                ext = extArr[i];
            }
        }
        return ext;
    }

    //Read product with wishlist
    public void readProductAndWishlist(OnResponseCallback callback) {
        RequestParams params = new RequestParams();
        params.put("action", "readProductAndWishlist");
        this.getData(params, (success, response) -> {
            List<Product> productList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int prodID = Integer.parseInt(object.getString("product_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodAvail = Integer.parseInt(object.getString("product_availability"));
                    int prodStock = Integer.parseInt(object.getString("product_stock"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));
                    int prodSold = Integer.parseInt(object.getString("product_sold"));
                    int wishID = Integer.parseInt(object.getString("wishlist_id"));

                    //Only top 4 items will display top seller
                    Product product = new Product();
                    product.setProdID(prodID);
                    product.setProdName(prodName);
                    product.setProdSKU(prodSKU);
                    product.setProdImg(prodImg);
                    product.setProdAvail(prodAvail);
                    product.setProdStock(prodStock);
                    product.setProdPrice(prodPrice);
                    product.setProdSold(prodSold);
                    product.setWishID(wishID);
                    if (i < 4) { product.setIsTopSeller(true); }
                    else {product.setIsTopSeller(false);}
                    productList.add(product);

                }
            }catch (Exception e){}
            callback.onResponse(productList);
        });
    }

    //Read product with wishlist (Best Sellers)
    public void readBestSellers(String userID, OnResponseCallback callback) {
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("action", "readBestSellers");
        this.getData(params, (success, response) -> {
            List<Product> productList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int prodID = Integer.parseInt(object.getString("product_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodAvail = Integer.parseInt(object.getString("product_availability"));
                    int prodStock = Integer.parseInt(object.getString("product_stock"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));
                    int prodSold = Integer.parseInt(object.getString("product_sold"));
                    int wishID = Integer.parseInt(object.getString("wishlist_id"));

                    Product product = new Product();
                    product.setProdID(prodID);
                    product.setProdName(prodName);
                    product.setProdSKU(prodSKU);
                    product.setProdImg(prodImg);
                    product.setProdAvail(prodAvail);
                    product.setProdStock(prodStock);
                    product.setProdPrice(prodPrice);
                    product.setProdSold(prodSold);
                    product.setWishID(wishID);

                    productList.add(product);

                }
            }catch (Exception e){}
            callback.onResponse(productList);
        });
    }

    //Read product with wishlist (New Arrival)
    public void readNewArrival(String userID, OnResponseCallback callback) {
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("action", "readNewArrival");
        this.getData(params, (success, response) -> {
            List<Product> productList = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int prodID = Integer.parseInt(object.getString("product_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodAvail = Integer.parseInt(object.getString("product_availability"));
                    int prodStock = Integer.parseInt(object.getString("product_stock"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));
                    int prodSold = Integer.parseInt(object.getString("product_sold"));
                    int wishID = Integer.parseInt(object.getString("wishlist_id"));

                    Product product = new Product();
                    product.setProdID(prodID);
                    product.setProdName(prodName);
                    product.setProdSKU(prodSKU);
                    product.setProdImg(prodImg);
                    product.setProdAvail(prodAvail);
                    product.setProdStock(prodStock);
                    product.setProdPrice(prodPrice);
                    product.setProdSold(prodSold);
                    product.setWishID(wishID);

                    productList.add(product);

                }
            }catch (Exception e){}
            callback.onResponse(productList);
        });
    }
}
