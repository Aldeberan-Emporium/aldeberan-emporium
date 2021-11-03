package com.example.aldeberan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.SearchProduct;
import com.example.aldeberan.Activity.Login;
import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailVerticalAdapter;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

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
    UserStorage us;
    Button viewMoreBtn;

    ShimmerFrameLayout shimmerBestSellerBox;
    ShimmerFrameLayout shimmerNewArrivalBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homepageView = inflater.inflate(R.layout.fragment_homepage, container, false);

        //getActivity().getSupportActionBar().hide();

        us = new UserStorage(getActivity());

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

        viewMoreBtn = homepageView.findViewById(R.id.viewMoreBtn);
        viewMoreBtn.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new AllProductFragment()).addToBackStack(null).commit();
            ((Homepage) getActivity()).setBotNavView(1);
        });

        ConstructRecyclerView();

        return homepageView;
    }

    private void ConstructRecyclerView(){
        ProductModel pm = new ProductModel();

        pm.readBestSellers(us.getID(), (response) -> {
            PutDataIntoBestSellerBox(response);
        });

        pm.readNewArrival(us.getID(), (response) -> {
            PutDataIntoNewArrivalBox(response);
        });
    }

    ProductListingDetailAdapter.FragmentCommunication bestSellerComm = (prodName, prodID, prodSKU ,prodImg, prodPrice, prodStock) -> {
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

    ProductListingDetailAdapter.GuestFragmentCommunication guestUserComm = () -> {
        Intent loginIntent = new Intent(getActivity(), Login.class);
        startActivity(loginIntent);
        Toast.makeText(getActivity(), "Yeet", Toast.LENGTH_SHORT);
    };

    private void PutDataIntoBestSellerBox(List<Product> productList){
        adapterBS = new ProductListingDetailAdapter(getContext(), productList, bestSellerComm, guestUserComm);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        bestSellerBox.setLayoutManager(gridLayoutManager);
        bestSellerBox.setAdapter(adapterBS);
        Log.i("PLOPE", String.valueOf(productList));
        shimmerBestSellerBox.stopShimmerAnimation();
        shimmerBestSellerBox.setVisibility(View.GONE);
    }

    ProductListingDetailVerticalAdapter.FragmentCommunication newArrivalComm = (prodName, prodID, prodSKU ,prodImg, prodPrice, prodStock) -> {
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

    ProductListingDetailVerticalAdapter.GuestFragmentCommunicationVert guestUserComm1 = () -> {
        Intent loginIntent = new Intent(getActivity(), Login.class);
        startActivity(loginIntent);
    };

    private void PutDataIntoNewArrivalBox(List<Product> productList){
        adapterNA = new ProductListingDetailVerticalAdapter(getContext(), productList, newArrivalComm, guestUserComm1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        newArrivalBox.setLayoutManager(gridLayoutManager);
        newArrivalBox.setAdapter(adapterNA);
        Log.i("PLOPE", String.valueOf(productList));
        //calculateScreenWidth();
        shimmerNewArrivalBox.stopShimmerAnimation();
        shimmerNewArrivalBox.setVisibility(View.GONE);
    }
}