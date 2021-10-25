package com.example.aldeberan;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.aldeberan.Adapter.AllProductAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllProductFragment extends Fragment {

    List<Product> productList;
    AllProductAdapter adapter;
    RecyclerView allProdBox;
    EditText searchBar;
    View allProdView;

    ShimmerFrameLayout shimmerAllProdBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        allProdView = inflater.inflate(R.layout.fragment_all_product, container, false);

        allProdBox = allProdView.findViewById(R.id.allProdBox);
        productList = new ArrayList<>();

        shimmerAllProdBox = allProdView.findViewById(R.id.shimmerAllProdBox);
        shimmerAllProdBox.startShimmerAnimation();

        searchBar = allProdView.findViewById(R.id.searchInput);

        searchBar.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

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

        ConstructRecyclerView();

        return allProdView;
    }

    private void ConstructRecyclerView(){
        ProductModel pm = new ProductModel();
        try {
            pm.readProductAll((response) -> {
                productList = response;
                PutDataIntoAllProdBox(response);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AllProductAdapter.FragmentCommunication comm = (prodName, prodID, prodImg, prodPrice) -> {
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

    private void PutDataIntoAllProdBox(List<Product> productList){
        adapter = new AllProductAdapter(getContext(), productList, comm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        allProdBox.setLayoutManager(gridLayoutManager);
        allProdBox.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
        shimmerAllProdBox.stopShimmerAnimation();
        shimmerAllProdBox.setVisibility(View.GONE);
    }

    private int calculateScreenWidth () {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.i("SCREENWIDTH", String.valueOf(width));
        int cardWidth = 540;
        return width/cardWidth;
    }

    //Hide keyboard when out of focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) this.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    //Filter recycler view items
    private void filterProducts(String input){
        ArrayList<Product> filteredProductList = new ArrayList<>();

        for (Product item : productList) {
            if (item.getProdName().toLowerCase().contains(input.toLowerCase())){
                filteredProductList.add(item);
            }
        }

        adapter.filteredProductList(filteredProductList);
    }
}