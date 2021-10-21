package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;
import com.example.aldeberan.UserFragment.cartFragment;

public class AddressSelectionToBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection_to_book);

        getSupportActionBar().setTitle("Address Book");
    }
}