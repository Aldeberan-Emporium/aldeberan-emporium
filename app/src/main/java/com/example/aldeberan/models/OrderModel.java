package com.example.aldeberan.models;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Order;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
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

    //Callback function for orderList response
    public interface OnResponseRefCallback {
        public void onResponse(String response);
    }

    //Validate order reference
    public void orderRefValidator(final String orderRef, OnResponseRefCallback callback){
        this.readOrderRefAll((response -> {
            String ref = orderRef;
            for (int i = 0; i < response.size(); i++){
                if (ref == response.get(i).getOrderRef()){
                    ref = this.orderRefGenerator();
                }
            }
            callback.onResponse(ref);
        }));
    }


    //Add order
    public void addOrder(String userID, String orderDate, double subtotal, double total, String orderStatus){
        String orderRef = orderRefGenerator();

        this.orderRefValidator(orderRef, response -> {
            String ref = response;
            RequestParams params = new RequestParams();
            params.put("action", "addAddress");
            params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
            params.put("order_reference", StringEscapeUtils.escapeHtml4(ref));
            params.put("order_date", orderDate);
            params.put("subtotal", String.valueOf(subtotal));
            params.put("total", String.valueOf(total));
            params.put("order_status", orderStatus);
            this.postData(params);
        });
    }

    //Callback function for orderList response
    public interface OnResponseCallback {
        public void onResponse(List<Order> response);
    }

    //Read all order references
    public void readOrderRefAll(OnResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "readOrderRefAll");
        this.getData(params, (success, response) -> {
            List<Order> orderList = new ArrayList<>();
            String data = response;
            Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String orderRef = StringEscapeUtils.unescapeHtml4(object.getString("order_reference"));

                    Order order = new Order();
                    order.setOrderRef(orderRef);

                    orderList.add(order);

                }
            }catch (Exception e){}
            Log.i("PL", String.valueOf(orderList));
            callback.onResponse(orderList);
        });
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
                    double subtotal = Double.parseDouble(object.getString("subtotal"));
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
                    order.setSubtotal(subtotal);
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
                    double subtotal = Double.parseDouble(object.getString("subtotal"));
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
                    order.setSubtotal(subtotal);
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

    //Update order status -> shipping, delivered or add what you needed (in lowercase)
    public void updateOrderStatus(int orderID, String orderStatus){
        RequestParams params = new RequestParams();
        params.put("action", "updateOrderStatus");
        params.put("order_id", orderID);
        params.put("order_status", orderStatus);
        this.postData(params);
    }

    //Add order item
    public void addOrderItem(int orderID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg){
        RequestParams params = new RequestParams();
        params.put("action", "addOrderItem");
        params.put("order_id", orderID);
        params.put("product_name", StringEscapeUtils.escapeHtml4(prodName));
        params.put("product_SKU", StringEscapeUtils.escapeHtml4(prodSKU));
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
}
