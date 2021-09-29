package com.example.aldeberan;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Database {

    public String DB = "https://aldeberan-emporium.herokuapp.com/";

    //Post data to database
    public String postData(@NonNull JSONObject data) throws IOException {
        URL url = new URL (this.DB);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes("UTF-8"));
        os.close();

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(StringEscapeUtils.unescapeHtml3(responseLine.trim()));
            }
            return response.toString();
        }
    }

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
    public JsonObject readProductAll() throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readProductAll");
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Read product by ID
    public JsonObject readProductById(int prodID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readProductById");
        json.put("product_id", prodID);
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Add quote
    public void addQuote(String userID, double subtotal, double total, String createdAt, String updatedAt, int quoteStatus) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addQuote");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        json.put("subtotal", subtotal);
        json.put("total", total);
        json.put("created_at", createdAt);
        json.put("updated_at", updatedAt);
        json.put("quote_status", quoteStatus);
        this.postData(json);
    }

    //Update quote
    public void updateQuote(int quoteID, String userID, double subtotal, double total, String createdAt, String updatedAt, int quoteStatus) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "updateQuote");
        json.put("quote_id", quoteID);
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        json.put("subtotal", subtotal);
        json.put("total", total);
        json.put("created_at", createdAt);
        json.put("updated_at", updatedAt);
        json.put("quote_status", quoteStatus);
        this.postData(json);
    }

    //Delete quote
    public void deleteQuote(int quoteID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "deleteQuote");
        json.put("quote_id", quoteID);
        this.postData(json);
    }

    //Read quote by user id
    public JsonObject readQuoteByUser(String userID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readQuoteByUser");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Add quote item
    public void addQuoteItem(int quoteID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addQuoteItem");
        json.put("quote_id", quoteID);
        json.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        json.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        json.put("product_quantity", prodQuantity);
        json.put("product_price", prodPrice);
        json.put("product_img", prodImg);
        this.postData(json);
    }

    //Update quote item
    public void updateQuoteItem(int quoteItemID, int quoteID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "updateQuoteItem");
        json.put("quote_item_id", quoteItemID);
        json.put("quote_id", quoteID);
        json.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        json.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        json.put("product_quantity", prodQuantity);
        json.put("product_price", prodPrice);
        json.put("product_img", prodImg);
        this.postData(json);
    }

    //Delete quote item
    public void deleteQuoteItem(int quoteItemID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "deleteQuoteItem");
        json.put("quote_item_id", quoteItemID);
        this.postData(json);
    }

    //Add address
    public void addAddress(String userID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addAddress");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        json.put("address_recipient", StringEscapeUtils.escapeHtml3(addRecipient));
        json.put("address_contact", StringEscapeUtils.escapeHtml3(addContact));
        json.put("address_line1", StringEscapeUtils.escapeHtml3(addLine1));
        json.put("address_line2", StringEscapeUtils.escapeHtml3(addLine2));
        json.put("address_code", StringEscapeUtils.escapeHtml3(addCode));
        json.put("address_city", StringEscapeUtils.escapeHtml3(addCity));
        json.put("address_state", StringEscapeUtils.escapeHtml3(addState));
        json.put("address_country", StringEscapeUtils.escapeHtml3(addCountry));
        this.postData(json);
    }

    //Update address
    public void updateAddress(int addID, String userID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "updateAddress");
        json.put("address_id", addID);
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        json.put("address_recipient", StringEscapeUtils.escapeHtml3(addRecipient));
        json.put("address_contact", StringEscapeUtils.escapeHtml3(addContact));
        json.put("address_line1", StringEscapeUtils.escapeHtml3(addLine1));
        json.put("address_line2", StringEscapeUtils.escapeHtml3(addLine2));
        json.put("address_code", StringEscapeUtils.escapeHtml3(addCode));
        json.put("address_city", StringEscapeUtils.escapeHtml3(addCity));
        json.put("address_state", StringEscapeUtils.escapeHtml3(addState));
        json.put("address_country", StringEscapeUtils.escapeHtml3(addCountry));
        this.postData(json);
    }

    //Delete address
    public void deleteAddress(int addID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "deleteAddress");
        json.put("address_id", addID);
        this.postData(json);
    }

    //Read address by user
    public JsonObject readAddressByUser(String userID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readAddressByUser");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Add order
    public void addOrder(String userID, String orderRef, String orderDate, double subtotal, double total, String orderStatus) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addAddress");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        json.put("order_reference", StringEscapeUtils.escapeHtml3(orderRef));
        json.put("order_date", orderDate);
        json.put("subtotal", subtotal);
        json.put("total", total);
        json.put("order_status", orderStatus);
        this.postData(json);
    }

    //Admin read all orders
    public JsonObject readOrderAll() throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readOrderAll");
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Read order by user id
    public JsonObject readOrderByUser(String userID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readOrderByUser");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JsonObject data = new Gson().fromJson(this.postData(json), JsonObject.class);
        return data;
    }

    //Add order item
    public void addOrderItem(int orderID, String prodName, String prodSKU, int prodQuantity, double prodPrice, String prodImg) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addOrderItem");
        json.put("order_id", orderID);
        json.put("product_name", StringEscapeUtils.escapeHtml3(prodName));
        json.put("product_SKU", StringEscapeUtils.escapeHtml3(prodSKU));
        json.put("product_quantity", prodQuantity);
        json.put("product_price", prodPrice);
        json.put("product_img", prodImg);
        this.postData(json);
    }

    //Add order address
    public void addOrderAddress(int orderID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addOrderAddress");
        json.put("order_id", orderID);
        json.put("address_recipient", StringEscapeUtils.escapeHtml3(addRecipient));
        json.put("address_contact", StringEscapeUtils.escapeHtml3(addContact));
        json.put("address_line1", StringEscapeUtils.escapeHtml3(addLine1));
        json.put("address_line2", StringEscapeUtils.escapeHtml3(addLine2));
        json.put("address_code", StringEscapeUtils.escapeHtml3(addCode));
        json.put("address_city", StringEscapeUtils.escapeHtml3(addCity));
        json.put("address_state", StringEscapeUtils.escapeHtml3(addState));
        json.put("address_country", StringEscapeUtils.escapeHtml3(addCountry));
        this.postData(json);
    }

    //Add order payment
    public void addOrderPayment(int orderID, String payType, String payID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "addOrderPayment");
        json.put("order_id", orderID);
        json.put("payment_type", StringEscapeUtils.escapeHtml3(payType));
        json.put("payment_id", StringEscapeUtils.escapeHtml3(payID));
        this.postData(json);
    }
}
