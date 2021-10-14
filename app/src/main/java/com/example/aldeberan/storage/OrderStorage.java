package com.example.aldeberan.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class OrderStorage {

    private Context context;
    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor myEdit;

    public OrderStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("OrderDetail",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
    };

    public OrderStorage() {}

    public void saveTotal(double total){
        myEdit.putLong("total", Double.doubleToRawLongBits(total));

        myEdit.apply();
    }

    public void saveOrderID(int orderID){
        myEdit.putInt("orderID", orderID);
    }
    
    public void saveAddress(String recipient, String contact, String line1, String line2, String code, String city, String state, String country){
        myEdit.putString("recipient", recipient);
        myEdit.putString("contact", contact);
        myEdit.putString("line1", line1);
        myEdit.putString("line2", line2);
        myEdit.putString("code", code);
        myEdit.putString("city", city);
        myEdit.putString("state", state);
        myEdit.putString("country", country);
        myEdit.apply();
    }

    public double getTotal(){return Double.longBitsToDouble(sharedPreferences.getLong("total", 0));}
    public int getOrderID(){return sharedPreferences.getInt("orderID", 0);}

    public String getRecipient(){return sharedPreferences.getString("recipient", "");}
    public String getContact(){return sharedPreferences.getString("contact", "");}
    public String getLine1(){return sharedPreferences.getString("line1", "");}
    public String getLine2(){return sharedPreferences.getString("line2", "");}
    public String getCode(){return sharedPreferences.getString("code", "");}
    public String getCity(){return sharedPreferences.getString("city", "");}
    public String getState(){return sharedPreferences.getString("state", "");}
    public String getCountry(){return sharedPreferences.getString("country", "");}

}
