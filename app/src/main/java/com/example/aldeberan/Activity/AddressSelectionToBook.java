package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserUpdateAddressFragment;

/*
Leong Kah Ming  1171100884
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664
Chong Wai Hou   1161104445

They work and discuss together for majority of the featuers in user setting.
*/

public class AddressSelectionToBook extends AppCompatActivity {

    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection_to_book);

        getSupportActionBar().setTitle("Address Book");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_address, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuItem = item;
        switch (item.getItemId()) {
            case R.id.addAddressBtn:
                UserAddAddressFragment newAddressFragment = new UserAddAddressFragment();
                //Redirect to update product fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.addressFragmentView, newAddressFragment)
                        .addToBackStack(null)
                        .commit();
                setTitleBar("Add New Address");
                menuItem.setVisible(false);
                return true;

            default:
                menuItem.setVisible(true);
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTitleBar(String title){
        getSupportActionBar().setTitle(title);
    }

    public void setMenuItem(boolean isVisible){
        menuItem.setVisible(isVisible);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            finish();
            Intent addressIntent = new Intent(AddressSelectionToBook.this, AddressSelection.class);
            startActivity(addressIntent);
        }
        else {
            final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.addressFragmentView);
            if (fragmentInFrame instanceof UserAddressFragment){
                super.onBackPressed();
                finish();
                Intent addressIntent = new Intent(AddressSelectionToBook.this, AddressSelection.class);
                startActivity(addressIntent);
            }
            else{
                setTitleBar("Address Book");
                menuItem.setVisible(true);
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}