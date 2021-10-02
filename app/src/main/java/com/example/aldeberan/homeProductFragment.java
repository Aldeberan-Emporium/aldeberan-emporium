package com.example.aldeberan;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class homeProductFragment extends Fragment{

    private View myFragmentView;
    public List<Product> productList;
    public RecyclerView recyclerView;
    public ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_home_product, container, false);
        productList = new ArrayList<>();
        recyclerView = myFragmentView.findViewById(R.id.recyclerView);

        /*
        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = myFragmentView.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });
        */

        return myFragmentView;
    }

    private void ConstructRecyclerView(){
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
        adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }

    private void onProductDeleted(int index){
        adapter.notifyItemRemoved(index);
    }

}

/*
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getImage = databaseReference.child("image");

        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // getting a DataSnapshot for the location at the specified
                // relative path and getting in the link variable
                String link = dataSnapshot.getValue(String.class);

                // loading that data into rImage
                // variable which is ImageView
                System.out.println("whatlink" + link);
                if (link != null)
                    Picasso.get().load(link).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

         */
