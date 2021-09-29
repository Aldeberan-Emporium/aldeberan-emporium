package models;

import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CartModel extends DatabaseModel {

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
    public JSONObject readQuoteByUser(String userID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readQuoteByUser");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
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
}
