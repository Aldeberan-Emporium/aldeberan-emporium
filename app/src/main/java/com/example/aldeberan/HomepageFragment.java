package com.example.aldeberan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.aldeberan.Activity.SearchProduct;
import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment{

    List<Product> productList;
    ProductListingDetailAdapter adapterBS;
    ProductListingDetailVerticalAdapter adapterNA;
    RecyclerView bestSellerBox;
    RecyclerView newArrivalBox;
    Button searchOpenBtn;
    View homepageView;

    ShimmerFrameLayout shimmerBestSellerBox;
    ShimmerFrameLayout shimmerNewArrivalBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homepageView = inflater.inflate(R.layout.fragment_homepage, container, false);

        //getActivity().getSupportActionBar().hide();

        bestSellerBox = homepageView.findViewById(R.id.bestSellerBox);
        newArrivalBox = homepageView.findViewById(R.id.newArrivalBox);
        productList = new ArrayList<>();

        shimmerBestSellerBox = homepageView.findViewById(R.id.shimmerBestSellerBox);
        shimmerBestSellerBox.startShimmerAnimation();

        shimmerNewArrivalBox = homepageView.findViewById(R.id.shimmerNewArrivalBox);
        shimmerNewArrivalBox.startShimmerAnimation();

        searchOpenBtn = homepageView.findViewById(R.id.searchOpenBtn);
        searchOpenBtn.setOnClickListener(view -> {
            Intent searchIntent = new Intent(getActivity(), SearchProduct.class);
            startActivity(searchIntent);
        });

        ConstructRecyclerView();

        return homepageView;
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
        adapterBS = new ProductListingDetailAdapter(getContext(), productList, bestSellerComm);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        bestSellerBox.setLayoutManager(horizontalLayoutManagaer);
        bestSellerBox.setAdapter(adapterBS);
        Log.i("PLOPE", String.valueOf(productList));
        shimmerBestSellerBox.stopShimmerAnimation();
        shimmerBestSellerBox.setVisibility(View.GONE);
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
        adapterNA = new ProductListingDetailVerticalAdapter(getContext(), productList, newArrivalComm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        newArrivalBox.setLayoutManager(gridLayoutManager);
        newArrivalBox.setAdapter(adapterNA);
        Log.i("PLOPE", String.valueOf(productList));
        //calculateScreenWidth();
        shimmerNewArrivalBox.stopShimmerAnimation();
        shimmerNewArrivalBox.setVisibility(View.GONE);
    }

    private int calculateScreenWidth () {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.i("SCREENWIDTH", String.valueOf(width));
        int cardWidth = 540;
        return width/cardWidth;
    }
}