package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.Adapter.SearchProductAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        getSupportActionBar().hide();

        searchProdBox = findViewById(R.id.searchProdBox);
        productList = new ArrayList<>();
        searchProductList = new ArrayList<>();

        onBackBtn = findViewById(R.id.onBackBtn);
        onBackBtn.setOnClickListener(view -> {
            finish();
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
            pm.readProductAll((response) -> {
                productList = response;
                PutDataIntoSearchProductBox(searchProductList);
            });
        } catch (JSONException e) {
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
                if (item.getProdName().toLowerCase().contains(input.toLowerCase())){
                    filteredProductList.add(item);
                }
            }
        }

        adapter.filteredProductList(filteredProductList);
    }
}