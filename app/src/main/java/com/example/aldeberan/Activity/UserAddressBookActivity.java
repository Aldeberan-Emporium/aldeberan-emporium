package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;

public class UserAddressBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address_book);

        getSupportActionBar().setTitle("Address Book");
    }

    public void setTitleBar(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            finish();
        }
        else {
            final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.userAddressBookFragmentView);
            if (fragmentInFrame instanceof UserAddressFragment){
                super.onBackPressed();
                finish();
            }
            else{
                setTitleBar("Address Book");
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}