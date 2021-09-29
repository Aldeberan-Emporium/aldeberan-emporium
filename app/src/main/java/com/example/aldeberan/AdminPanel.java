package com.example.aldeberan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.aldeberan.models.ProductModel;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView prodName = findViewById(R.id.prodName);
        TextView prodSKU = findViewById(R.id.prodSKU);
        TextView prodStock = findViewById(R.id.prodStock);
        TextView prodPrice = findViewById(R.id.prodPrice);
        String prodImg = "";
        Switch prodAvail = findViewById(R.id.prodAvail);

        //addProduct(prodName.getEditableText(), prodSKU.getEditableText(), prodAvail, prodStock.getEditableText(), prodPrice.getEditableText(), prodImg);
    }
}