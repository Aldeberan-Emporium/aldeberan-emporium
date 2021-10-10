package com.example.aldeberan.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class MapStorage {

    private Context context;
    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MapStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LiveLocation",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    };

    public void saveGeocode(String polyline){
        editor.putString("polyline", polyline);
        editor.apply();
    }

    public void savePolyIndex(int index, int next){
        editor.putInt("index", index);
        editor.putInt("next", next);
        editor.apply();
    }

    public void saveLatLng(double prevLat, double prevLng, double currLat, double currLng){
        editor.putLong("prevLat", Double.doubleToRawLongBits(prevLat));
        editor.putLong("prevLng", Double.doubleToRawLongBits(prevLng));
        editor.putLong("currLat", Double.doubleToRawLongBits(currLat));
        editor.putLong("currLng", Double.doubleToRawLongBits(currLng));
        editor.apply();
    }

    public void saveDistance(int distance){
        editor.putInt("distance", distance);
        editor.apply();
    }

    public void saveDuration(double duration){
        editor.putLong("duration", Double.doubleToRawLongBits(duration));
        editor.apply();
    }

    public void saveStatus(String status){
        editor.putString("status", status);
        editor.apply();
    }

    public String getPolyline(){return sharedPreferences.getString("polyline", "");}
    public int getIndex(){return sharedPreferences.getInt("index", -1);}
    public int getNext(){return sharedPreferences.getInt("next", 1);}
    public double getPrevLat(){return Double.longBitsToDouble(sharedPreferences.getLong("prevLat", 0));}
    public double getPrevLng(){return Double.longBitsToDouble(sharedPreferences.getLong("prevLng", 0));}
    public double getLat(){return Double.longBitsToDouble(sharedPreferences.getLong("currLat", 0));}
    public double getLng(){return Double.longBitsToDouble(sharedPreferences.getLong("currLng", 0));}
    public int getDistance(){return sharedPreferences.getInt("distance", 0);}
    public double getDuration(){return Double.longBitsToDouble(sharedPreferences.getLong("duration", 0));}
    public String getStatus(){return sharedPreferences.getString("status", "");}

    public void removeStatus () {
        editor.remove("status").commit();
    }

}
