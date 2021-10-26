package com.example.aldeberan.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aldeberan.Adapter.CheckoutAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

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
        recyclerView = findViewById(R.id.checkoutRecyclerView);
        Button confirmButton = findViewById(R.id.confirmOrder);
        selectedAddressLbl = findViewById(R.id.selectedAddressLbl);
        buttonEditItem = findViewById(R.id.buttonEditItem);
        addressBtn = findViewById(R.id.addressBtn);

        price.setText("RM " + String.valueOf(os.getTotal()));

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //readResponse();
                    finish();
                    Intent payIntent = new Intent(checkoutActivity.this, StripePaymentCheckOut.class);
                    startActivity(payIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void PutDataIntoRecyclerView(List<Cart> cartList) throws JSONException {
        checkoutAdapter = new CheckoutAdapter(this, cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(checkoutAdapter);
    }

    public void updateSelectedAddress(){
        String data = "Receiver               : " + os.getRecipient()+ "\n" +
                "Contact Number: 60"+ os.getContact() + "\n" + "\n" +
                "Address               :" + "\n" +
                os.getLine1()+", "+os.getLine2()+"\n"+
                os.getCode()+", "+os.getCity()+", "+os.getState();

        selectedAddressLbl.setText(data);
    }
/*
    public void readResponse() throws JSONException{
        CartModel cm = new CartModel();
        ProductModel pm = new ProductModel();
        userStorage = new UserStorage(this);
        int quoteID = userStorage.getQuoteID();

        cm.readQuoteItemByQuote(quoteID, response -> {
            cartList = response;
            if(cartList != null){
                pm.readProductAll(pData -> {
                    for(int i = 0; i != pData.size(); i++){
                        for(int j = 0; j != cartList.size(); j++){
                            if(cartList.get(j).getProdSKU().equals(pData.get(i).getProdSKU())){
                                //System.out.println("Mei: " + cartList.get(i).getProdSKU());
                                pm.updateProduct(
                                        pData.get(j).getProdID(),
                                        pData.get(j).getProdName(),
                                        pData.get(j).getProdSKU(),
                                        pData.get(j).getProdAvail()?0:1,
                                        pData.get(j).getProdStock() - cartList.get(j).getProdQuantity(),
                                        pData.get(j).getProdPrice(),
                                        pData.get(j).getProdImg());
                                //break;
                            }
                        }
                    }
                });
            }
        });
    }
 */
}