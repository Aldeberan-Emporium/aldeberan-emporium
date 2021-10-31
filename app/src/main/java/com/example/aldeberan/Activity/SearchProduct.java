package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.Adapter.SearchProductAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Product;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SearchProduct extends AppCompatActivity {

    List<Product> productList;
    List<Product> searchProductList;
    EditText searchBar;
    ImageButton onBackBtn;
    RecyclerView searchProdBox;
    SearchProductAdapter adapter;
    UserStorage us;
    ImageView meme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        getSupportActionBar().hide();

        us = new UserStorage(this);

        searchProdBox = findViewById(R.id.searchProdBox);
        meme = findViewById(R.id.imageView5);
        productList = new ArrayList<>();
        searchProductList = new ArrayList<>();

        onBackBtn = findViewById(R.id.onBackBtn);
        onBackBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        searchBar = findViewById(R.id.searchInput);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterProducts(editable.toString());
            }
        });

        searchBar.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        ProductModel pm = new ProductModel();
        try {
            if(us.getID() != null){
                pm.readProductAndWishlist((response) -> {
                    productList = response;
                    PutDataIntoSearchProductBox(searchProductList);
                });
            }
            else{
                pm.readProductAll((response) -> {
                    productList = response;
                    PutDataIntoSearchProductBox(searchProductList);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SearchProductAdapter.FragmentCommunication comm = (prodName, prodID, prodImg, prodPrice) -> {
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

        Homepage.setCartBtnBadge();
        displayItemAddedSnackbar();
    };

    private void PutDataIntoSearchProductBox(List<Product> productList){
        adapter = new SearchProductAdapter(this, productList, comm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        searchProdBox.setLayoutManager(gridLayoutManager);
        searchProdBox.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
        //calculateScreenWidth();
    }

    //Hide keyboard when out of focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    //Filter recycler view items
    private void filterProducts(String input){
        ArrayList<Product> filteredProductList = new ArrayList<>();

        if (!TextUtils.isEmpty(input)){
            for (Product item : productList) {
                if (item.getProdName().contains(input.toUpperCase())){
                    meme.setVisibility(View.GONE);
                    filteredProductList.add(item);
                }else{
                    meme.setVisibility(View.VISIBLE);
                    //Toast.makeText(this, "Quack", Toast.LENGTH_SHORT).show();
                    Glide.with(this).load(R.raw.quack).into(meme);
                }
            }
        }

        adapter.filteredProductList(filteredProductList);
    }

    //Display product added to cart
    public void displayItemAddedSnackbar(){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Product added to cart!", Snackbar.LENGTH_SHORT).setDuration(800);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent homepage = new Intent(SearchProduct.this, Homepage.class);
        startActivity(homepage);
    }
}