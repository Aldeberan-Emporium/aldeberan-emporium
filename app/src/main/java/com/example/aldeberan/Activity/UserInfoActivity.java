package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aldeberan.R;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().setTitle("User Info");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}