package com.example.aldeberan.storage;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.aldeberan.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.concurrent.Executor;

public class UserStorage {

    private Context context;
    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor myEdit;

    public UserStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("CurrentUser",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
    };

    public UserStorage() {}

    public void logoutUser(){
        myEdit.clear();
        myEdit.apply();
    }

    public void saveUser(String name, String id, String photoURL, String email, int quoteID){

        myEdit.putString("name", name);
        myEdit.putString("id", id);
        myEdit.putString("photoURL", photoURL);
        myEdit.putString("email", email);
        myEdit.putInt("quoteID", quoteID);

        myEdit.apply();
    }

    public void setQuoteID(int quoteID){
        myEdit.putInt("quoteID", quoteID);
        myEdit.apply();
    }

    public String getName(){
       return sharedPreferences.getString("name", "Please sign in");
    }

    public String getID(){
        return sharedPreferences.getString("id", "guest");
    }

    public String getPhotoURL(){
       return sharedPreferences.getString("photoURL", "");
    }

    public String getEmail(){
       return sharedPreferences.getString("email", "");
    }

    public int getQuoteID() { return sharedPreferences.getInt("quoteID", 0); }
}
