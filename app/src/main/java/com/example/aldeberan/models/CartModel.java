package com.example.aldeberan.models;

import com.codepath.asynchttpclient.RequestParams;
import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CartModel extends DatabaseModel{
    //Add quote
    public void addQuote(String userID, double subtotal, double total, String createdAt, String updatedAt, int quoteStatus) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "addQuote");
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        params.put("subtotal", String.valueOf(subtotal));
        params.put("total", String.valueOf(total));
        params.put("created_at", createdAt);
        params.put("updated_at", updatedAt);
        params.put("quote_status", quoteStatus);
        this.postData(params);
    }

    //Update quote
    public void updateQuote(int quoteID, String userID, double subtotal, double total, String createdAt, String updatedAt, int quoteStatus) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "updateQuote");
        params.put("quote_id", quoteID);
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        params.put("subtotal", String.valueOf(subtotal));
        params.put("total", String.valueOf(total));
        params.put("created_at", createdAt);
        params.put("updated_at", updatedAt);
        params.put("quote_status", quoteStatus);
        this.postData(params);
    }

    //Delete quote
    public void deleteQuote(int quoteID) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "deleteQuote");
        params.put("quote_id", quoteID);
        this.postData(params);
    }
    /*
    //Read quote by user id
    public JSONObject readQuoteByUser(String userID) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "readQuoteByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }

     */

    //Add quote item
    public void addQuoteItem(int quoteID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "addQuoteItem");
        params.put("quote_id", quoteID);
        params.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        params.put("product_quantity", prodQuantity);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Update quote item
    public void updateQuoteItem(int quoteItemID, int quoteID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "updateQuoteItem");
        params.put("quote_item_id", quoteItemID);
        params.put("quote_id", quoteID);
        params.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        params.put("product_quantity", prodQuantity);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Delete quote item
    public void deleteQuoteItem(int quoteItemID) throws JSONException, IOException {
        RequestParams params = new RequestParams();
        params.put("action", "deleteQuoteItem");
        params.put("quote_item_id", quoteItemID);
        this.postData(params);
    }
}
