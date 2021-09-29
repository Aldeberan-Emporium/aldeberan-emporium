package models;

import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AddressModel extends DatabaseModel {

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
    public JSONObject readAddressByUser(String userID) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("action", "readAddressByUser");
        json.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        JSONObject data = new Gson().fromJson(this.postData(json), JSONObject.class);
        return data;
    }
}
