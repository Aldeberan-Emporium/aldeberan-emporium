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
        myEdit.apply();
    }

    public double getTotal(){return Double.longBitsToDouble(sharedPreferences.getLong("total", 0));}
    public int getOrderID(){return sharedPreferences.getInt("orderID", 0);}
}
