package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aldeberan.Adapter.WishlistAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.WishlistModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Wishlist;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private List<Wishlist> wishlist;
    private UserStorage userStorage;
    private RecyclerView recyclerView;
    private TextView wishlist_text;
    private WishlistAdapter wishlistAdapter;
    private SwipeRefreshLayout wishPullToRefresh;
    private WishlistModel wm = new WishlistModel();

    ShimmerFrameLayout shimmerWishlistLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.wishlistView);
        wishlist_text = findViewById(R.id.wishlist_text);
        wishPullToRefresh = findViewById(R.id.wishPullToRefresh);

        shimmerWishlistLayout = findViewById(R.id.shimmerWishlistBox);
        shimmerWishlistLayout.startShimmerAnimation();

        ConstructRecyclerView();

        wishPullToRefresh.setOnRefreshListener(() -> {
            if(wishlistAdapter != null){
                ConstructRecyclerView();
                wishlistAdapter.notifyDataSetChanged();
                wishlist_text.setVisibility(View.GONE);
            }
            wishPullToRefresh.setRefreshing(false);
        });
    }

    private void ConstructRecyclerView(){
        userStorage = new UserStorage(this);
        String userID = userStorage.getID();

        try {
           wm.readWishlistByUser(userID, response -> {
               wishlist = response;
               PutDataIntoRecyclerView(wishlist);
               if(wishlist.isEmpty()){
                   wishlist_text.setText("No item found in wishlist.");
                   wishlist_text.setVisibility(View.VISIBLE);
               }
               Log.i("wish", String.valueOf(response));
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
        shimmerWishlistLayout.stopShimmerAnimation();
        shimmerWishlistLayout.setVisibility(View.GONE);
    }
}