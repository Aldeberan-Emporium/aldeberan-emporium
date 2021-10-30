package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;

import com.example.aldeberan.Adapter.CartAdapter;
import com.example.aldeberan.Adapter.OrderAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Order;

import org.json.JSONException;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements Serializable {
    private OrderModel om;
    private UserStorage us;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_history);
        recyclerView = findViewById(R.id.orderRecyclerView);

        ConstructRecyclerView();

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConstructRecyclerView();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    public void ConstructRecyclerView(){
        om = new OrderModel();
        us = new UserStorage(this);
        String userID = us.getID();
        om.readOrderByUser(userID, response -> {
            PutDataIntoRecyclerView(response);
        });
    }

    public void PutDataIntoRecyclerView(List<Order> orderList) {
            orderAdapter = new OrderAdapter(OrderActivity.this, orderList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Intent mainint = new Intent(this, Homepage.class);
        //startActivity(mainint);
    }
}



