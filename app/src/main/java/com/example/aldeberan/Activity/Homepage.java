package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aldeberan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity {

    BottomNavigationView botNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        botNavView = findViewById(R.id.botNavView);

    }
}