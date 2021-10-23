package com.example.aldeberan.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.Adapter.OrderDetailAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.OrderModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Order;

import java.io.Serializable;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity implements Serializable {
    private OrderModel om;
    private UserStorage us;
    private List<Order> orderList;
    private OrderDetailAdapter orderDetailAdapter;
    private RecyclerView recyclerView;
    private ImageView tickImage;
    private String orderStatus;
    private TextView orderStatusText;
    private TextView addressText;
    private TextView paymentMethodText;
    private TextView productTotalText;
    private TextView detailTotalPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);
        recyclerView = findViewById(R.id.orderDetailRecyclerView);
        tickImage = findViewById(R.id.tickImg);
        orderStatusText = findViewById(R.id.orderStatusText);
        addressText = findViewById(R.id.addressDetail);
        paymentMethodText = findViewById(R.id.paymentMethod);
        productTotalText = findViewById(R.id.productTotal);
        detailTotalPriceText = findViewById(R.id.detailTotalPrice);

        String address = (String) getIntent().getSerializableExtra ("address");
        String paymentMethod = (String) getIntent().getSerializableExtra ("paymentMethod");
        String productTotal = (String) getIntent().getSerializableExtra("productTotal");
        String orderTotal = (String) getIntent().getSerializableExtra("orderTotal");
        orderTotal = productTotal + 5;

        System.out.println("lanjiao product total: " + productTotal);
        System.out.println("lanjiao order total: " + orderTotal);

        om = new OrderModel();

        String orderStatus = (String) getIntent().getSerializableExtra("orderStatus");

        System.out.println("lanjiao" + orderStatus);

        if (orderStatus == "shipping"){
            orderStatusText.setText("Your order is on the way!");
            tickImage.setVisibility(View.INVISIBLE);
        }

        else if (orderStatus == "completed"){
            orderStatusText.setText("Your order is completed!");
            tickImage.setVisibility(View.VISIBLE);
        }

        addressText.setText(address);

        ConstructRecyclerView();

        paymentMethodText.setText(paymentMethod);
        productTotalText.setText(productTotal);
        detailTotalPriceText.setText(orderTotal);
    }


    private void ConstructRecyclerView(){
        om = new OrderModel();
        us = new UserStorage(this);
        String userID = us.getID();

        om.readOrderItem(userID, response -> {
            orderList = response;
            PutDataIntoRecyclerView(response, orderStatus);
        });
    }

    public void PutDataIntoRecyclerView(List<Order> orderList, String orderStatus) {
        orderDetailAdapter = new OrderDetailAdapter(OrderDetailActivity.this, orderList, orderStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderDetailAdapter);
    }

}
