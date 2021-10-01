package com.example.aldeberan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        ProductModel pm = new ProductModel();
        try {
            pm.readProductAll((response -> {
                productList = response;
                PutDataIntoRecyclerView(response);
                Log.i("PL", String.valueOf(productList));
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GetData getData = new GetData();
        getData.execute();

    }


    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String res) {

        }
    }

    private void PutDataIntoRecyclerView(List<Product> productList){
        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }
}