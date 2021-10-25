package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.aldeberan.Adapter.CheckoutAdapter;
import com.example.aldeberan.Adapter.WishlistAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.models.WishlistModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Wishlist;

import org.json.JSONException;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private List<Wishlist> wishlist;
    private UserStorage userStorage;
    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;
    private WishlistModel wm = new WishlistModel();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        recyclerView = findViewById(R.id.wishlistView);
        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        CartModel cm = new CartModel();
        userStorage = new UserStorage(this);
        String userID = userStorage.getID();

        try {
           wm.readWishlistByUser(userID, response -> {
               wishlist = response;
               PutDataIntoRecyclerView(wishlist);
           });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void PutDataIntoRecyclerView(List<Wishlist> wishList) throws JSONException {
        wishlistAdapter = new WishlistAdapter(this, wishList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wishlistAdapter);
    }
}