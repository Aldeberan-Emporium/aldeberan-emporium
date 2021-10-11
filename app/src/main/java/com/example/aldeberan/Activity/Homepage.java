package com.example.aldeberan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {

    List<Product> productList;
    ProductListingDetailAdapter adapterBS;
    ProductListingDetailVerticalAdapter adapterNA;
    RecyclerView bestSellerBox;
    RecyclerView newArrivalBox;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bottomNavigationView = findViewById(R.id.botNavView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.botNavHome);

        getSupportActionBar().hide();



        bestSellerBox = findViewById(R.id.bestSellerBox);
        newArrivalBox = findViewById(R.id.newArrivalBox);
        productList = new ArrayList<>();

        ConstructRecyclerView();
    }



    //@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.botNavHome:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();
                Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.botNavProducts:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                Toast.makeText(this, "Products Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.botNavCart:
                Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.botNavUser:
                Intent intent = new Intent(Homepage.this, Login.class);
                startActivity(intent);
                Toast.makeText(this, "User CLicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    private void ConstructRecyclerView(){
        ProductModel pm = new ProductModel();
        try {
            pm.readProductAll((response) -> {
                productList = response;
                PutDataIntoBestSellerBox(response);
                PutDataIntoNewArrivalBox(response);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ProductListingDetailAdapter.FragmentCommunication bestSellerComm = (prodName, prodID, prodImg, prodPrice) -> {
        homeProductFragment homepage = new homeProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prodName", prodName);
        bundle.putString("prodID", prodID);
        //bundle.putString("prodSKU", prodSKU);
        bundle.putString("prodImg", prodImg);
        //bundle.putString("prodStock", prodStock);
        //bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", prodPrice);
        homepage.setArguments(bundle);
    };

    private void PutDataIntoBestSellerBox(List<Product> productList){
        adapterBS = new ProductListingDetailAdapter(this, productList, bestSellerComm);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bestSellerBox.setLayoutManager(horizontalLayoutManagaer);
        bestSellerBox.setAdapter(adapterBS);
        Log.i("PLOPE", String.valueOf(productList));
    }

    ProductListingDetailVerticalAdapter.FragmentCommunication newArrivalComm = (prodName, prodID, prodImg, prodPrice) -> {
        homeProductFragment homepage = new homeProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prodName", prodName);
        bundle.putString("prodID", prodID);
        //bundle.putString("prodSKU", prodSKU);
        bundle.putString("prodImg", prodImg);
        //bundle.putString("prodStock", prodStock);
        //bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", prodPrice);
        homepage.setArguments(bundle);
    };

    private void PutDataIntoNewArrivalBox(List<Product> productList){
        adapterNA = new ProductListingDetailVerticalAdapter(this, productList, newArrivalComm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateScreenWidth());
        newArrivalBox.setLayoutManager(gridLayoutManager);
        newArrivalBox.setAdapter(adapterNA);
        Log.i("PLOPE", String.valueOf(productList));
        calculateScreenWidth();
    }

    private int calculateScreenWidth () {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.i("SCREENWIDTH", String.valueOf(width));
        int cardWidth = 540;
        return width/cardWidth;
    }


}