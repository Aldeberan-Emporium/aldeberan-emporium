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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aldeberan.Adapter.CheckoutAdapter;
import com.example.aldeberan.CartFragment;
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
    private CheckoutAdapter checkoutAdapter;

    private OrderStorage os;
    TextView addressBtn, buttonEditItem;
    TextView selectedAddressLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setTitle("Items Checkout");

        os = new OrderStorage(this);
        TextView price = findViewById(R.id.price);
        price.setText(String.valueOf(os.getTotal()));
        recyclerView = findViewById(R.id.checkoutRecyclerView);
        Button confirmButton = findViewById(R.id.confirmOrder);
        selectedAddressLbl = findViewById(R.id.selectedAddressLbl);
        buttonEditItem = findViewById(R.id.buttonEditItem);
        addressBtn = findViewById(R.id.addressBtn);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payIntent = new Intent(checkoutActivity.this, StripePaymentCheckOut.class);
                startActivity(payIntent);
            }
        });
        
        buttonEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent cartIntent = new Intent(checkoutActivity.this, CartFragment.class);
                //startActivity(cartIntent);
                onBackPressed();
            }
        });

        addressBtn.setOnClickListener(view -> {
            finish();
            Intent addIntent = new Intent(checkoutActivity.this, AddressSelection.class);
            startActivity(addIntent);
        });

        if (os.getRecipient() == ""){
            selectedAddressLbl.setText("Please select an address.");
        }
        else{
            updateSelectedAddress();
        }

        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        CartModel cm = new CartModel();
        userStorage = new UserStorage(this);
        int quoteID = userStorage.getQuoteID();

        try {
            cm.readQuoteItemByQuote(quoteID, response -> {
                cartList = response;
                PutDataIntoRecyclerView(cartList);
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
        //Log.i("PLOPE", String.valueOf(cartList));
    }

    public void updateSelectedAddress(){
        String data = "Receiver               : " + os.getRecipient()+ "\n" +
                "Contact Number: 60"+ os.getContact() + "\n" + "\n" +
                "Address               :" + "\n" +
                os.getLine1()+", "+os.getLine2()+"\n"+
                os.getCode()+", "+os.getCity()+", "+os.getState();

        selectedAddressLbl.setText(data);
    }
}