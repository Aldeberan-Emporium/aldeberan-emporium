package com.example.aldeberan.models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;
import com.example.aldeberan.structures.Order;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Headers;

/*
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664

Ong and Yong are responsible for this. 
Ong setup the database using heroku and linked it to android studio.

Yong using PHP to write the SQL command for the backend.

For debugging every members are involved.
*/

public class OrderModel extends DatabaseModel{
    //Callback function for orderID response
    public interface OnOrderIDResponseCallback {
        public void onResponse(int orderID);
    }

    //Add order
    public void addOrder(String userID, String orderDate, double total, String orderStatus, OnOrderIDResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "addOrder");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        params.put("order_date", orderDate);
        params.put("total", String.valueOf(total));
        params.put("order_status", orderStatus);
        this.getData(params, (success, res) -> {
            String data = res;
            //Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int orderID = object.getInt("order_id");
                    callback.onResponse(orderID);
                }
            }catch (Exception e){
                Log.i("EXCEPTION-ORDER", e.toString());
            }
        });
    }

    //Callback function for orderList response
    public interface OnResponseCallback {
        public void onResponse(List<Order> response);
    }

    //Admin read all orders
    public void readOrderAll(OnResponseCallback callback) {
        RequestParams params = new RequestParams();
        params.put("action", "readOrderAll");
        this.getData(params, (success, response) -> {
            List<Order> orderList = new ArrayList<>();
            String data = response;
            Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int orderID = Integer.parseInt(object.getString("order_id"));
                    String orderRef = StringEscapeUtils.unescapeHtml4(object.getString("order_reference"));
                    String orderDate = object.getString("order_date");
                    double total = Double.parseDouble(object.getString("total"));
                    String orderStatus = object.getString("order_status");
                    int orderItemID = Integer.parseInt(object.getString("order_item_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodQuantity = Integer.parseInt(object.getString("product_quantity"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));
                    int orderAddressID = Integer.parseInt(object.getString("order_address_id"));
                    String addRecipient = StringEscapeUtils.unescapeHtml4(object.getString("address_recipient"));
                    String addContact = StringEscapeUtils.unescapeHtml4(object.getString("address_contact"));
                    String addLine1 = StringEscapeUtils.unescapeHtml4(object.getString("address_line1"));
                    String addLine2 = StringEscapeUtils.unescapeHtml4(object.getString("address_line2"));
                    String addCode = StringEscapeUtils.unescapeHtml4(object.getString("address_code"));
                    String addCity = StringEscapeUtils.unescapeHtml4(object.getString("address_city"));
                    String addState = StringEscapeUtils.unescapeHtml4(object.getString("address_state"));
                    String addCountry = StringEscapeUtils.unescapeHtml4(object.getString("address_country"));
                    int orderPaymentID = Integer.parseInt(object.getString("order_payment_id"));
                    String payType = StringEscapeUtils.unescapeHtml4(object.getString("payment_type"));
                    String payID = StringEscapeUtils.unescapeHtml4(object.getString("payment_id"));

                    Order order = new Order();
                    order.setOrderID(orderID);
                    order.setOrderRef(orderRef);
                    order.setOrderDate(orderDate);
                    order.setTotal(total);
                    order.setOrderStatus(orderStatus);
                    order.setOrderItemID(orderItemID);
                    order.setProdName(prodName);
                    order.setProdSKU(prodSKU);
                    order.setProdImg(prodImg);
                    order.setProdQuantity(prodQuantity);
                    order.setProdPrice(prodPrice);
                    order.setOrderAddressID(orderAddressID);
                    order.setAddRecipient(addRecipient);
                    order.setAddContact(addContact);
                    order.setAddLine1(addLine1);
                    order.setAddLine2(addLine2);
                    order.setAddCode(addCode);
                    order.setAddCity(addCity);
                    order.setAddState(addState);
                    order.setAddCountry(addCountry);
                    order.setOrderPaymentID(orderPaymentID);
                    order.setPayType(payType);
                    order.setPayID(payID);

                    orderList.add(order);

                }
            }catch (Exception e){}
            Log.i("PL", String.valueOf(orderList));
            callback.onResponse(orderList);
        });
    }

    //Read order by user id
    public void readOrderByUser(String userID, OnResponseCallback callback) {
        RequestParams params = new RequestParams();
        params.put("action", "readOrderByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        this.getData(params, (success, response) -> {
            List<Order> orderList = new ArrayList<>();
            String data = response;
            Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int orderID = Integer.parseInt(object.getString("order_id"));
                    String orderRef = StringEscapeUtils.unescapeHtml4(object.getString("order_reference"));
                    String orderDate = object.getString("order_date");
                    double total = Double.parseDouble(object.getString("order_total"));
                    String orderStatus = object.getString("order_status");
                    int orderItemID = Integer.parseInt(object.getString("order_item_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodQuantity = Integer.parseInt(object.getString("product_quantity"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));
                    int orderAddressID = Integer.parseInt(object.getString("order_address_id"));
                    String addRecipient = StringEscapeUtils.unescapeHtml4(object.getString("address_recipient"));
                    String addContact = StringEscapeUtils.unescapeHtml4(object.getString("address_contact"));
                    String addLine1 = StringEscapeUtils.unescapeHtml4(object.getString("address_line1"));
                    String addLine2 = StringEscapeUtils.unescapeHtml4(object.getString("address_line2"));
                    String addCode = StringEscapeUtils.unescapeHtml4(object.getString("address_code"));
                    String addCity = StringEscapeUtils.unescapeHtml4(object.getString("address_city"));
                    String addState = StringEscapeUtils.unescapeHtml4(object.getString("address_state"));
                    String addCountry = StringEscapeUtils.unescapeHtml4(object.getString("address_country"));
                    int orderPaymentID = Integer.parseInt(object.getString("order_payment_id"));
                    String payType = StringEscapeUtils.unescapeHtml4(object.getString("payment_type"));
                    String payID = StringEscapeUtils.unescapeHtml4(object.getString("payment_id"));
                    int totalItems = object.getInt("total_items");

                    Order order = new Order();
                    order.setOrderID(orderID);
                    order.setOrderRef(orderRef);
                    order.setOrderDate(orderDate);
                    order.setTotal(total);
                    order.setOrderStatus(orderStatus);
                    order.setOrderItemID(orderItemID);
                    order.setProdName(prodName);
                    order.setProdSKU(prodSKU);
                    order.setProdImg(prodImg);
                    order.setProdQuantity(prodQuantity);
                    order.setProdPrice(prodPrice);
                    order.setOrderAddressID(orderAddressID);
                    order.setAddRecipient(addRecipient);
                    order.setAddContact(addContact);
                    order.setAddLine1(addLine1);
                    order.setAddLine2(addLine2);
                    order.setAddCode(addCode);
                    order.setAddCity(addCity);
                    order.setAddState(addState);
                    order.setAddCountry(addCountry);
                    order.setOrderPaymentID(orderPaymentID);
                    order.setPayType(payType);
                    order.setPayID(payID);
                    order.setTotalItems(totalItems);

                    orderList.add(order);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i("PL", String.valueOf(orderList));
            callback.onResponse(orderList);
        });
    }

    //Update order status -> shipping, delivered or add what you needed (in lowercase)
    public void updateOrderStatus(int orderID, String orderStatus){
        RequestParams params = new RequestParams();
        params.put("action", "updateOrderStatus");
        params.put("order_id", orderID);
        params.put("order_status", orderStatus);
        this.postData(params);
    }

    //Read order item by User ID
    public void readOrderItem(int orderID, OnResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "readOrderItem");
        params.put("order_id", orderID);
        this.getData(params, (success, response) -> {
            List<Order> orderList = new ArrayList<>();
            String data = response;
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int orderItemID = Integer.parseInt(object.getString("order_item_id"));
                    String prodName = StringEscapeUtils.unescapeHtml4(object.getString("product_name"));
                    String prodSKU = StringEscapeUtils.unescapeHtml4(object.getString("product_SKU"));
                    String prodImg = StringEscapeUtils.unescapeHtml4(object.getString("product_img"));
                    int prodQuantity = Integer.parseInt(object.getString("product_quantity"));
                    double prodPrice = Double.parseDouble(object.getString("product_price"));

                    Order order = new Order();
                    order.setOrderItemID(orderItemID);
                    order.setProdName(prodName);
                    order.setProdSKU(prodSKU);
                    order.setProdImg(prodImg);
                    order.setProdQuantity(prodQuantity);
                    order.setProdPrice(prodPrice);

                    orderList.add(order);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            callback.onResponse(orderList);
        });
    }

    //Add order item
    public void addOrderItem(int orderID, int quoteID){
        RequestParams params = new RequestParams();
        params.put("action", "addOrderItem");
        params.put("order_id", orderID);
        params.put("quote_id", quoteID);
        this.postData(params);
    }

    //Add order address
    public void addOrderAddress(int orderID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry) {
        RequestParams params = new RequestParams();
        params.put("action", "addOrderAddress");
        params.put("order_id", orderID);
        params.put("address_recipient", StringEscapeUtils.escapeHtml4(addRecipient));
        params.put("address_contact", StringEscapeUtils.escapeHtml4(addContact));
        params.put("address_line1", StringEscapeUtils.escapeHtml4(addLine1));
        params.put("address_line2", StringEscapeUtils.escapeHtml4(addLine2));
        params.put("address_code", StringEscapeUtils.escapeHtml4(addCode));
        params.put("address_city", StringEscapeUtils.escapeHtml4(addCity));
        params.put("address_state", StringEscapeUtils.escapeHtml4(addState));
        params.put("address_country", StringEscapeUtils.escapeHtml4(addCountry));
        this.postData(params);
    }

    //Add order payment
    public void addOrderPayment(int orderID, String payType, String payID){
        RequestParams params = new RequestParams();
        params.put("action", "addOrderPayment");
        params.put("order_id", orderID);
        params.put("payment_type", StringEscapeUtils.escapeHtml4(payType));
        params.put("payment_id", StringEscapeUtils.escapeHtml4(payID));
        this.postData(params);
    }

    //Send email to user after order is initiated
    public void sendOrderMail(String name, String email, int orderID){
        RequestParams params = new RequestParams();
        params.put("name", StringEscapeUtils.escapeHtml4(name));
        params.put("email", email);
        params.put("order_id", orderID);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://aldeberan-emporium-mailer.herokuapp.com", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.i("MAIL", "Email sent to "+email);
            }

            @Override
            public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
            }
        });
    }
}
