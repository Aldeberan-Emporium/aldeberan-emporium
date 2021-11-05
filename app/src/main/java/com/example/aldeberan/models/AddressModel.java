package com.example.aldeberan.models;

import android.util.Log;

import com.codepath.asynchttpclient.RequestParams;
import com.example.aldeberan.structures.Address;
import com.example.aldeberan.structures.Order;
import com.google.gson.Gson;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664

Ong and Yong are responsible for this. 
Ong setup the database using heroku and linked it to android studio.

Yong using PHP to write the SQL command for the backend.

For debugging every members are involved.
*/

public class AddressModel extends DatabaseModel{

    //Add address
    public void addAddress(String userID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry, int isDefault){
        RequestParams params = new RequestParams();
        params.put("action", "addAddress");
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        params.put("address_recipient", StringEscapeUtils.escapeHtml3(addRecipient));
        params.put("address_contact", StringEscapeUtils.escapeHtml3(addContact));
        params.put("address_line1", StringEscapeUtils.escapeHtml3(addLine1));
        params.put("address_line2", StringEscapeUtils.escapeHtml3(addLine2));
        params.put("address_code", StringEscapeUtils.escapeHtml3(addCode));
        params.put("address_city", StringEscapeUtils.escapeHtml3(addCity));
        params.put("address_state", StringEscapeUtils.escapeHtml3(addState));
        params.put("address_country", StringEscapeUtils.escapeHtml3(addCountry));
        params.put("is_default", isDefault);
        this.postData(params);
    }

    //Update address
    public void updateAddress(int addID, String userID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry, int isDefault){
        RequestParams params = new RequestParams();
        params.put("action", "updateAddress");
        params.put("address_id", addID);
        params.put("user_id", StringEscapeUtils.escapeHtml3(userID));
        params.put("address_recipient", StringEscapeUtils.escapeHtml3(addRecipient));
        params.put("address_contact", StringEscapeUtils.escapeHtml3(addContact));
        params.put("address_line1", StringEscapeUtils.escapeHtml3(addLine1));
        params.put("address_line2", StringEscapeUtils.escapeHtml3(addLine2));
        params.put("address_code", StringEscapeUtils.escapeHtml3(addCode));
        params.put("address_city", StringEscapeUtils.escapeHtml3(addCity));
        params.put("address_state", StringEscapeUtils.escapeHtml3(addState));
        params.put("address_country", StringEscapeUtils.escapeHtml3(addCountry));
        params.put("is_default", isDefault);
        this.postData(params);
    }

    //Delete address
    public void deleteAddress(int addID){
        RequestParams params = new RequestParams();
        params.put("action", "deleteAddress");
        params.put("address_id", addID);
        this.postData(params);
    }

    //Callback function for addressList response
    public interface OnResponseCallback {
        public void onResponse(List<Address> response);
    }

    //Read address by user
    public void readAddressByUser(String userID, OnResponseCallback callback){
        RequestParams params = new RequestParams();
        params.put("action", "readAddressByUser");
        params.put("user_id", StringEscapeUtils.escapeHtml4(userID));
        this.getData(params, (success, response) -> {
            List<Address> addressList = new ArrayList<>();
            String data = response;
            Log.i("DATAIN", data);
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int addressID = Integer.parseInt(object.getString("address_id"));
                    String addRecipient = StringEscapeUtils.unescapeHtml4(object.getString("address_recipient"));
                    String addContact = StringEscapeUtils.unescapeHtml4(object.getString("address_contact"));
                    String addLine1 = StringEscapeUtils.unescapeHtml4(object.getString("address_line1"));
                    String addLine2 = StringEscapeUtils.unescapeHtml4(object.getString("address_line2"));
                    String addCode = StringEscapeUtils.unescapeHtml4(object.getString("address_code"));
                    String addCity = StringEscapeUtils.unescapeHtml4(object.getString("address_city"));
                    String addState = StringEscapeUtils.unescapeHtml4(object.getString("address_state"));
                    String addCountry = StringEscapeUtils.unescapeHtml4(object.getString("address_country"));
                    int isDefault = Integer.parseInt(object.getString("is_default"));

                    Address address = new Address();
                    address.setAddID(addressID);
                    address.setAddRecipient(addRecipient);
                    address.setAddContact(addContact);
                    address.setAddLine1(addLine1);
                    address.setAddLine2(addLine2);
                    address.setAddCode(addCode);
                    address.setAddCity(addCity);
                    address.setAddState(addState);
                    address.setAddCountry(addCountry);
                    address.setIsDefault(isDefault);

                    addressList.add(address);

                }
            }catch (Exception e){}
            Log.i("PL", String.valueOf(addressList));
            callback.onResponse(addressList);
        });
    }
}
