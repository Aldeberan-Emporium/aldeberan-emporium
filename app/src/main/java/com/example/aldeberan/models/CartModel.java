package com.example.aldeberan.models;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Cart;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartModel extends DatabaseModel{

    public CartModel(){};

    //Add quote
    public void addQuote(String userID, double total, int quoteStatus){
        RequestParams params = new RequestParams();
        params.put("action", "addQuote");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        params.put("total", String.valueOf(total));
        params.put("quote_status", quoteStatus);
        this.postData(params);
    }

    //Update quote
    public void updateQuoteStatus(int quoteID){
        RequestParams params = new RequestParams();
        params.put("action", "updateQuoteStatus");
        params.put("quote_id", quoteID);
        this.postData(params);
    }

    //Delete quote
    public void deleteQuote(int quoteID){
        RequestParams params = new RequestParams();
        params.put("action", "deleteQuote");
        params.put("quote_id", quoteID);
        this.postData(params);
    }

    //Update quote by recalculating the total price
    public void updateQuoteRecal(int quoteID){
        RequestParams params = new RequestParams();
        params.put("action", "updateQuoteRecal");
        params.put("quote_id", quoteID);
        this.postData(params);
    }

    //Check if User Exist and create a new quote
    public void checkIfUserExist(String userID){
        RequestParams params = new RequestParams();
        params.put("action", "checkIfUserExist");
        params.put("user_id", userID);

        this.postData(params);
    }

    //Callback function for cartList response
    public interface OnResponseCallback {
        public void onResponse(List<Cart> response) throws JSONException;
    }

    //Read quote by user id
    public void readQuoteByUser(String userID, OnResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "readQuoteByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        this.getData(params, (success, response) -> {
            List<Cart> cartList = new ArrayList<>();
            String data = response;
            //Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int quoteID = Integer.parseInt(object.getString("quote_id"));
                    double total = Double.parseDouble(object.getString("total"));
                    int quoteStatus = Integer.parseInt(object.getString("quote_status"));

                    Cart cart = new Cart();
                    cart.setQuoteID(quoteID);
                    cart.setTotal(total);
                    cart.setQuoteStatus(quoteStatus);

                    cartList.add(cart);

                }
            }catch (Exception e){}
            Log.i("PL", String.valueOf(cartList));
            callback.onResponse(cartList);
        });
    }

    //Read quote item by quote id
    public void readQuoteItemByQuote(int quoteID, OnResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "readQuoteItemByQuote");
        params.put("quote_id", quoteID);
        this.getData(params, (success, response) -> {
            List<Cart> cartList = new ArrayList<>();
            String data = response;
            System.out.println("data:" + data);
            Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int quoteItemID = Integer.parseInt(object.getString("quote_item_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodQuantity = Integer.parseInt(object.getString("product_quantity"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));

                    Cart cart = new Cart();
                    cart.setQuoteItemID(quoteItemID);
                    cart.setProdName(prodName);
                    cart.setProdSKU(prodSKU);
                    cart.setProdImg(prodImg);
                    cart.setProdQuantity(prodQuantity);
                    cart.setProdPrice(prodPrice);

                    cartList.add(cart);
                }
            }catch (Exception e){}
            Log.i("PL", String.valueOf(cartList));
            callback.onResponse(cartList);
        });
    }

    //Add quote item
    public void addQuoteItem(int quoteID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) {
        RequestParams params = new RequestParams();
        params.put("action", "addQuoteItem");
        params.put("quote_id", quoteID);
        params.put("product_name", StringEscapeUtils.escapeHtml4(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml4(prodSKU));
        params.put("product_quantity", prodQuantity);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Update quote item
    public void updateQuoteItem(int quoteItemID, int quoteID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) {
        RequestParams params = new RequestParams();
        params.put("action", "updateQuoteItem");
        params.put("quote_item_id", quoteItemID);
        params.put("quote_id", quoteID);
        params.put("product_name", StringEscapeUtils.escapeHtml4(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml4(prodSKU));
        params.put("product_quantity", prodQuantity);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Delete quote item
    public void deleteQuoteItem(int quoteItemID) {
        RequestParams params = new RequestParams();
        params.put("action", "deleteQuoteItem");
        params.put("quote_item_id", quoteItemID);
        this.postData(params);
    }
}
