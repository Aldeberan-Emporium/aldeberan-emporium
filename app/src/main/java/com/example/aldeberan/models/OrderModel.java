package com.example.aldeberan.models;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Order;
import com.google.gson.Gson;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class OrderModel extends DatabaseModel{

    //Order reference generator
    public String orderRefGenerator(){
        int length = 15;
        String prefix = "AE_";
        String postfix = "";
        char[] chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
        Random rand = new Random();
        for (int i = 0; i < length; i++){
            int index = rand.nextInt(chars.length);
            chars = ArrayUtils.remove(chars, index);
            postfix += chars[index];
        }
        return prefix+postfix;
    }

    //Validate order reference
    public String orderRefValidator(String orderRef){
        Order data = this.readOrderRefAll();

        while (data.getOrderRef() != orderRef){
            orderRef = orderRefGenerator();
        }

        return orderRef;
    }


    //Add order
    public void addOrder(String userID, String orderDate, double subtotal, double total, String orderStatus){
        String orderRef = orderRefGenerator();

        orderRef = this.orderRefValidator(orderRef);

        RequestParams params = new RequestParams();
        params.put("action", "addAddress");
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        params.put("order_reference", StringEscapeUtils.escapeHtml3(orderRef));
        params.put("order_date", orderDate);
        params.put("subtotal", String.valueOf(subtotal));
        params.put("total", String.valueOf(total));
        params.put("order_status", orderStatus);
        this.postData(params);
    }

    //Read all order references
    public Order readOrderRefAll(){
        RequestParams params = new RequestParams();
        params.put("action", "readOrderRefAll");
        Gson gson = new Gson();
        String data = this.getData(params);
        Order order = gson.fromJson(data, Order.class);
        return order;
    }

    //Admin read all orders
    public Order readOrderAll() {
        RequestParams params = new RequestParams();
        params.put("action", "readOrderAll");
        Gson gson = new Gson();
        String data = this.getData(params);
        Order order = gson.fromJson(data, Order.class);
        return order;
    }

    //Read order by user id
    public Order readOrderByUser(String userID) {
        RequestParams params = new RequestParams();
        params.put("action", "readOrderByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        Gson gson = new Gson();
        String data = this.getData(params);
        Order order = gson.fromJson(data, Order.class);
        return order;
    }

    //Add order item
    public void addOrderItem(int orderID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg){
        RequestParams params = new RequestParams();
        params.put("action", "addOrderItem");
        params.put("order_id", orderID);
        params.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        params.put("product_quantity", prodQuantity);
        params.put("product_price", String.valueOf(prodPrice));
        params.put("product_img", prodImg);
        this.postData(params);
    }

    //Add order address
    public void addOrderAddress(int orderID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry) {
        RequestParams params = new RequestParams();
        params.put("action", "addOrderAddress");
        params.put("order_id", orderID);
        params.put("address_recipient", StringEscapeUtils.escapeHtml3(addRecipient));
        params.put("address_contact", StringEscapeUtils.escapeHtml3(addContact));
        params.put("address_line1", StringEscapeUtils.escapeHtml3(addLine1));
        params.put("address_line2", StringEscapeUtils.escapeHtml3(addLine2));
        params.put("address_code", StringEscapeUtils.escapeHtml3(addCode));
        params.put("address_city", StringEscapeUtils.escapeHtml3(addCity));
        params.put("address_state", StringEscapeUtils.escapeHtml3(addState));
        params.put("address_country", StringEscapeUtils.escapeHtml3(addCountry));
        this.postData(params);
    }

    //Add order payment
    public void addOrderPayment(int orderID, String payType, String payID){
        RequestParams params = new RequestParams();
        params.put("action", "addOrderPayment");
        params.put("order_id", orderID);
        params.put("payment_type", StringEscapeUtils.escapeHtml3(payType));
        params.put("payment_id", StringEscapeUtils.escapeHtml3(payID));
        this.postData(params);
    }
}
