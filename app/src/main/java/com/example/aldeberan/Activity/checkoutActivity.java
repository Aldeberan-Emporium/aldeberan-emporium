package com.example.aldeberan.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aldeberan.Adapter.CheckoutAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class checkoutActivity extends AppCompatActivity {

    private List<Cart> cartList;
    private UserStorage userStorage;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefresh;
    private CheckoutAdapter checkoutAdapter;

    private OrderStorage os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        
        Button confirmButton = findViewById(R.id.confirmOrder);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payIntent = new Intent(checkoutActivity.this, StripePaymentCheckOut.class);
                startActivity(payIntent);
            }
        });

        os = new OrderStorage(this);

        TextView price = findViewById(R.id.price);
        price.setText(String.valueOf(os.getTotal()));

        recyclerView = findViewById(R.id.checkoutRecyclerView);

        /*
        pullToRefresh = findViewById(R.id.checkoutPullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            System.out.println("apalumao zz");
            ConstructRecyclerView();
            checkoutAdapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });
        
         */

        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        CartModel cm = new CartModel();
        userStorage = new UserStorage(this);
        int quoteID = userStorage.getQuoteID();
        System.out.println("apalumao quoteID: " + quoteID);

        try {
            cm.readQuoteItemByQuote(quoteID, response -> {
                cartList = response;
                PutDataIntoRecyclerView(cartList);
                System.out.println("xx" + cartList);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void PutDataIntoRecyclerView(List<Cart> cartList){
        checkoutAdapter = new CheckoutAdapter(this, cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(checkoutAdapter);
        Log.i("PLOPE", String.valueOf(cartList));
    }
}