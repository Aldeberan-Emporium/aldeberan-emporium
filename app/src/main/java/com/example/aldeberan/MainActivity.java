package com.example.aldeberan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for test another activity pages.
        Intent intent = new Intent(MainActivity.this, home_product.class);
        startActivity(intent);
    }
}