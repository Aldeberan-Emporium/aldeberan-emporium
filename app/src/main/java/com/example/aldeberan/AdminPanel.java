package com.example.aldeberan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.aldeberan.models.ProductModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener{

    ProductModel pm = new ProductModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
    }

    public void pickImgFromGallery(){

    }
    /*
    private boolean isPermissionGranted(){

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        else{
            int readExternalStorage = ContextCompat.checkSelfPermission(this.Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }
    */

    @Override
    public void onClick(View view) {
        TextView prodName = findViewById(R.id.prodName);
        TextView prodSKU = findViewById(R.id.prodSKU);
        TextView prodStock = findViewById(R.id.prodStock);
        TextView prodPrice = findViewById(R.id.prodPrice);
        String prodImg = "";
        Switch prodAvail = findViewById(R.id.prodAvail);

        //pm.addProduct(prodName.getEditableText(), prodSKU.getEditableText(), prodAvail, prodStock.getEditableText(), prodPrice.getEditableText(), prodImg);
    }
}