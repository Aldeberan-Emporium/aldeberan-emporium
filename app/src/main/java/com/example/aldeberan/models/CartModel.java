package com.example.aldeberan.models;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;
import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CartModel extends DatabaseModel{
    //Add quote
    public void addQuote(String userID, double subtotal, double total, String createdAt, String updatedAt, int quoteStatus){
        RequestParams params = new RequestParams();
        params.put("action", "addQuote");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        params.put("subtotal", String.valueOf(subtotal));
        params.put("total", String.valueOf(total));
        params.put("created_at", createdAt);
        params.put("updated_at", updatedAt);
        params.put("quote_status", quoteStatus);
        this.postData(params);
    }

    //Update quote
    public void updateQuote(int quoteID, String userID, double subtotal, double total, String createdAt, String updatedAt, int quoteStatus){
        RequestParams params = new RequestParams();
        params.put("action", "updateQuote");
        params.put("quote_id", quoteID);
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        params.put("subtotal", String.valueOf(subtotal));
        params.put("total", String.valueOf(total));
        params.put("created_at", createdAt);
        params.put("updated_at", updatedAt);
        params.put("quote_status", quoteStatus);
        this.postData(params);
    }

    //Delete quote
    public void deleteQuote(int quoteID){
        RequestParams params = new RequestParams();
        params.put("action", "deleteQuote");
        params.put("quote_id", quoteID);
        this.postData(params);
    }

    //Read quote by user id
    public Cart readQuoteByUser(String userID){
        RequestParams params = new RequestParams();
        params.put("action", "readQuoteByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        Gson gson = new Gson();
        String data = this.getData(params);
        Cart cart = gson.fromJson(data, Cart.class);
        return cart;
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
