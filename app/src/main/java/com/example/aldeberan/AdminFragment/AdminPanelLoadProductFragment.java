package com.example.aldeberan.AdminFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aldeberan.Adapter.ProductAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelLoadProductFragment extends Fragment {
    private View myFragmentView;
    public List<Product> productList;
    public RecyclerView recyclerView;
    public ProductAdapter adapter;
    public TextView noProdLbl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.activity_admin_panel_load_product, container, false);
        ((home_product) getActivity()).setActionBarTitle("Manage Bread(s)");
        productList = new ArrayList<>();
        recyclerView = myFragmentView.findViewById(R.id.recyclerView);
        noProdLbl = myFragmentView.findViewById(R.id.noProdLbl);

        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = myFragmentView.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });

        return myFragmentView;
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

    ProductAdapter.FragmentCommunication communication= (prodName, prodID, prodSKU, prodImg, prodStock, prodAvail, prodPrice) -> {
        AdminPanelUpdateProductFragment updateProductFragment = new AdminPanelUpdateProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prodName", prodName);
        bundle.putString("prodID", prodID);
        bundle.putString("prodSKU", prodSKU);
        bundle.putString("prodImg", prodImg);
        bundle.putString("prodStock", prodStock);
        bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", prodPrice);
        updateProductFragment.setArguments(bundle);

        //Redirect to update product fragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, updateProductFragment)
                .addToBackStack(null)
                .commit();
    };

    private void PutDataIntoRecyclerView(List<Product> productList){
        adapter = new ProductAdapter(getContext(), productList, communication);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }
}
