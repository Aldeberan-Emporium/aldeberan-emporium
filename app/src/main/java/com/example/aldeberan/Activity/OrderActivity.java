package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aldeberan.Adapter.OrderAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Order;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements Serializable {
    private OrderModel om;
    private UserStorage us;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefresh;

    TextView emptyText;
    ShimmerFrameLayout shimmerOrderHistoryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Order History");
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.orderRecyclerView);
        emptyText = findViewById(R.id.orderEmptyText);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        shimmerOrderHistoryLayout = findViewById(R.id.shimmerOrderHistoryBox);
        shimmerOrderHistoryLayout.startShimmerAnimation();

        ConstructRecyclerView();
        pullToRefresh.setOnRefreshListener(() -> {
            if(orderAdapter != null){
                ConstructRecyclerView();
                emptyText.setVisibility(View.GONE);
            }
            pullToRefresh.setRefreshing(false);
        });
    }

    public void ConstructRecyclerView(){
        om = new OrderModel();
        us = new UserStorage(this);
        String userID = us.getID();
        om.readOrderByUser(userID, response -> {
            if(response.isEmpty()){
                emptyText.setText("No order found in history.");
               emptyText.setVisibility(View.VISIBLE);
                shimmerOrderHistoryLayout.stopShimmerAnimation();
                shimmerOrderHistoryLayout.setVisibility(View.GONE);
            }
            else{
                PutDataIntoRecyclerView(response);
            }
        });
    }

    public void PutDataIntoRecyclerView(List<Order> orderList) {
        orderAdapter = new OrderAdapter(OrderActivity.this, orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        shimmerOrderHistoryLayout.stopShimmerAnimation();
        shimmerOrderHistoryLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}



