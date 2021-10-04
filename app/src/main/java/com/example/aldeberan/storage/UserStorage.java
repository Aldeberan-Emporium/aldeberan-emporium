package com.example.aldeberan.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStorage {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor myEdit;

    public UserStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("CurrentUser",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
    };

    public UserStorage() {

    }

    public void logoutUser(){

        /*
        myEdit.putString("name", "Please Sign In");
        myEdit.putString("id", "");
        myEdit.putString("photoURL", "");
        myEdit.putString("email", "");
*/
        myEdit.clear();
        myEdit.apply();
    }

    public void saveUser(String name, String id, String photoURL, String email){

        myEdit.putString("name", name);
        myEdit.putString("id", id);
        myEdit.putString("photoURL", photoURL);
        myEdit.putString("email", email);

        myEdit.apply();
    }

    public String getName(){
       return sharedPreferences.getString("name", "Please sign in");
    }

    public String getID(){
        return sharedPreferences.getString("id", "");
    }

    public String getPhotoURL(){
       return sharedPreferences.getString("photoURL", "");
    }

    public String getEmail(){
       return sharedPreferences.getString("email", "");
    }
}
