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

import org.json.JSONException;

import java.io.IOException;

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
        Switch prodAvailSwitch = findViewById(R.id.prodAvail);
        int prodAvail = prodAvailSwitch.isChecked() ? 1 : 0;

        try {
            pm.addProduct(prodName.getEditableText().toString(), prodSKU.getEditableText().toString(), prodAvail, Integer.parseInt(prodStock.getEditableText().toString()), Double.parseDouble(prodPrice.getEditableText().toString()), prodImg);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}