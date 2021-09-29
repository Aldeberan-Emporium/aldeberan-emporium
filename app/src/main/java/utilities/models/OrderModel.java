package models;

import com.google.gson.Gson;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class OrderModel extends DatabaseModel {

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
    public String orderRefValidator(String orderRef) throws IOException, JSONException {
        JSONObject data = this.readOrderRefAll();
        Iterator<String> keys = data.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (data.get(key) instanceof JSONObject) {
               while (((JSONObject) data.get(key)).toString() != orderRef){
                   orderRef = this.orderRefGenerator();
               }
            }
        }
        return orderRef;
    }

    //Add order
    public void addOrder(String userID, String orderDate, double subtotal, double total, String orderStatus) throws JSONException, IOException {
        String orderRef = orderRefGenerator();

        orderRef = this.orderRefValidator(orderRef);

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

    //Read all order references
    public JSONObject readOrderRefAll() throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readOrderRefAll");
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }

    //Admin read all orders
    public JSONObject readOrderAll() throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readOrderAll");
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }

    //Read order by user id
    public JSONObject readOrderByUser(String userID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readOrderByUser");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
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
