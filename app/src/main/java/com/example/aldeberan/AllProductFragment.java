package com.example.aldeberan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.Login;
import com.example.aldeberan.Adapter.AllProductAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/*
Leong Kah Ming  1171100884
Chong Wai Hou   1161104445

Leong and Chong are responsible for this feature.
They connected/linked the functions and debug the problems.

*/

public class AllProductFragment extends Fragment {

    List<Product> productList;
    AllProductAdapter adapter;
    RecyclerView allProdBox;
    EditText searchBar;
    View allProdView;
    UserStorage us;
    ImageView meme;
    TextView memeText;

    ShimmerFrameLayout shimmerAllProdBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        allProdView = inflater.inflate(R.layout.fragment_all_product, container, false);

        us = new UserStorage(getActivity());

        allProdBox = allProdView.findViewById(R.id.allProdBox);
        productList = new ArrayList<>();

        meme = allProdView.findViewById(R.id.meme);
        memeText = allProdView.findViewById(R.id.memeText);

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
            if(!us.getID().contains("guest")){
                pm.readProductAndWishlist((response) -> {
                    productList = response;
                    PutDataIntoAllProdBox(response);
                });
            }
            else{
                pm.readProductAll((response) -> {
                    productList = response;
                    PutDataIntoAllProdBox(response);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AllProductAdapter.FragmentCommunication comm = (prodName, prodID, prodImg, prodPrice) -> {
        Bundle bundle = new Bundle();
        bundle.putString("prodName", prodName);
        bundle.putString("prodID", prodID);
        //bundle.putString("prodSKU", prodSKU);
        bundle.putString("prodImg", prodImg);
        //bundle.putString("prodStock", prodStock);
        //bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", prodPrice);

        ((Homepage) getActivity()).setCartBtnBadge();
        ((Homepage) getActivity()).displayItemAddedSnackbar();
    };

    AllProductAdapter.GuestFragmentCommunication guestUser = () -> {
        Intent loginIntent = new Intent(getActivity(), Login.class);
        startActivity(loginIntent);
    };

    private void PutDataIntoAllProdBox(List<Product> productList){
        adapter = new AllProductAdapter(getContext(), productList, comm, guestUser);
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

        if (filteredProductList.isEmpty() && !TextUtils.isEmpty(input)){
            meme.setVisibility(View.VISIBLE);
            memeText.setVisibility(View.VISIBLE);
            allProdBox.setVisibility(View.GONE);
            Glide.with(this).load(R.raw.quack).override(350,350).into(meme);
        }
        else{
            meme.setVisibility(View.GONE);
            memeText.setVisibility(View.GONE);
            allProdBox.setVisibility(View.VISIBLE);
        }

        adapter.filteredProductList(filteredProductList);
    }
}