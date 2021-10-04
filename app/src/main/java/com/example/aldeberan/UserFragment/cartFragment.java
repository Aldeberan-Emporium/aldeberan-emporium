package com.example.aldeberan.UserFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class cartFragment extends Fragment {

    private View myCartFragmentView;
    public List<Product> productList;
    public RecyclerView recyclerView;
    public ProductListingDetailAdapter adapter;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myCartFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);
        productList = new ArrayList<>();
        recyclerView = myCartFragmentView.findViewById(R.id.cartRecyclerView);


        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = myCartFragmentView.findViewById(R.id.cartPullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });

        return myCartFragmentView;
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

    private void PutDataIntoRecyclerView(List<Product> productList){
        adapter = new ProductListingDetailAdapter(getContext(), productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }
}
