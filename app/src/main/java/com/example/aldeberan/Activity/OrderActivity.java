package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private OrderModel om;
    private List<Order> orderList;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    private UserStorage us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        recyclerView = findViewById(R.id.orderRecyclerView);

        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        om = new OrderModel();
        us = new UserStorage(this);
        String userID = us.getID();
        om.readOrderByUser(userID, response -> {
            orderList = response;
            PutDataIntoRecyclerView(response);
        });
    }

    public void PutDataIntoRecyclerView(List<Order> orderList) {
            orderAdapter = new OrderAdapter(OrderActivity.this, orderList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderAdapter);
    }

}