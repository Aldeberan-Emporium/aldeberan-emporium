package com.example.aldeberan.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.AllProductFragment;
import com.example.aldeberan.CartFragment;
import com.example.aldeberan.HomepageFragment;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettingFragment;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bottomNavigationView = findViewById(R.id.botNavView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.botNavHome);

        getSupportActionBar().hide();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.botNavHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new HomepageFragment()).addToBackStack(null).commit();
                Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.botNavProducts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new AllProductFragment()).addToBackStack(null).commit();
                Toast.makeText(this, "Products Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.botNavCart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new CartFragment()).addToBackStack(null).commit();
                Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.botNavUser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new UserSettingFragment()).addToBackStack(null).commit();
                //Intent intent = new Intent(Homepage.this, Login.class);
                //startActivity(intent);
                Toast.makeText(this, "User CLicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        setBotNavView(0);

        Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show();
    }


    public void setBotNavView(int selected) {
        switch (selected) {
            case 0:
                Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
                bottomNavigationView.setSelectedItemId(R.id.botNavHome);
                break;
            case 1:
                Toast.makeText(this, "Products Clicked", Toast.LENGTH_SHORT).show();
                bottomNavigationView.setSelectedItemId(R.id.botNavProducts);
                break;
            case 2:
                Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show();
                bottomNavigationView.setSelectedItemId(R.id.botNavCart);
                break;
            case 3:
                Toast.makeText(this, "User CLicked", Toast.LENGTH_SHORT).show();
                bottomNavigationView.setSelectedItemId(R.id.botNavUser);
                break;
        }
    }
}