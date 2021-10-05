package com.example.aldeberan.UserFragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aldeberan.Adapter.CartAdapter;
import com.example.aldeberan.Adapter.ProductAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.AdminFragment.AdminPanelUpdateProductFragment;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class homeProductFragment extends Fragment{

    UserStorage user;
    CartModel cm = new CartModel();
    private View myProductFragmentView;
    //private View myCartFragmentView;
    public List<Product> productList;
    public RecyclerView recyclerView;
    public ProductListingDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myProductFragmentView = inflater.inflate(R.layout.fragment_home_product, container, false);
        //myCartFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);

        productList = new ArrayList<>();
        recyclerView = myProductFragmentView.findViewById(R.id.cRecyclerView);

        
        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = myProductFragmentView.findViewById(R.id.cPullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            //adapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });

        return myProductFragmentView;
    }

    private void ConstructRecyclerView(){
        ProductModel pm = new ProductModel();
        try {
            pm.readProductAll((response) -> {
                productList = response;
                PutDataIntoRecyclerView(response);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    ProductListingDetailAdapter.FragmentCommunication home_communication = (prodName, prodID, prodImg, prodPrice) -> {
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

    private void PutDataIntoRecyclerView(List<Product> productList){
        adapter = new ProductListingDetailAdapter(getContext(), productList, home_communication);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }
}
