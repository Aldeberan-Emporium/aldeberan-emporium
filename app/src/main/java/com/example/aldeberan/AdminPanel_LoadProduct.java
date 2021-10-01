package com.example.aldeberan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel_LoadProduct extends AppCompatActivity {

    public List<Product> productList;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_load_product);
        /*
        Typeface font = Typeface.createFromAsset( getAssets(), "fonts/fontawesome-webfont.ttf" );

        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setTypeface(font);

        Button deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setTypeface(font);

        */

        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        ProductModel pm = new ProductModel();
        try {
            pm.readProductAll((response) -> {
                productList = response;
                PutDataIntoRecyclerView(response);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void PutDataIntoRecyclerView(List<Product> productList){
        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }
}