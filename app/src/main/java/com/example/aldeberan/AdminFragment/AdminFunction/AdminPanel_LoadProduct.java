package com.example.aldeberan.AdminFragment.AdminFunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aldeberan.Adapter.ProductAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AdminPanel_LoadProduct extends AppCompatActivity{

    public List<Product> productList;
    public RecyclerView recyclerView;
    public ProductAdapter adapter;
    public TextView noProdLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_load_product);

        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        noProdLbl = findViewById(R.id.noProdLbl);

        ConstructRecyclerView();

        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefreshProduct);
        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });
    }

    private void ConstructRecyclerView(){
        ProductModel pm = new ProductModel();
        try {
            pm.readProductAll((response) -> {
                productList = response;
                if (productList.isEmpty()){
                    noProdLbl.setVisibility(View.VISIBLE);
                }
                else{
                    noProdLbl.setVisibility(View.GONE);
                    //PutDataIntoRecyclerView(response);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*

    private void PutDataIntoRecyclerView(List<Product> productList){
        adapter = new ProductAdapter(this, productList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }


     */
}