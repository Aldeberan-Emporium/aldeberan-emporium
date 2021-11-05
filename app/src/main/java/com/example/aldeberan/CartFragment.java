package com.example.aldeberan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.checkoutActivity;
import com.example.aldeberan.Adapter.CartAdapter;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/*
Leong Kah Ming  1171100884
Chong Wai Hou   1161104445

Leong and Chong are responsible for this feature.
They connected/linked the functions and debug the problems.

*/

public class CartFragment extends Fragment {

    private View myCartFragmentView;
    private RecyclerView recyclerView;
    private List<Cart> cartList;
    private CartAdapter adapter;
    private UserStorage us;
    private Button checkoutBtn;
    private TextView totalPrice, textLabel;
    private String totalPriceStr;
    private CartModel cm;
    private OrderStorage os;
    SwipeRefreshLayout pullToRefresh;
    ShimmerFrameLayout shimmerCartLayout;
    private ImageView fine;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myCartFragmentView = inflater.inflate(R.layout.cart, container, false);
        cm = new CartModel();
        os = new OrderStorage(getActivity());
        recyclerView = myCartFragmentView.findViewById(R.id.cartRecyclerView);
        checkoutBtn = myCartFragmentView.findViewById(R.id.checkoutButton);
        totalPrice = myCartFragmentView.findViewById(R.id.totalPrice);
        textLabel = myCartFragmentView.findViewById(R.id.textLabel);
        fine = myCartFragmentView.findViewById(R.id.this_is_fine);

        shimmerCartLayout = myCartFragmentView.findViewById(R.id.shimmerCartBox);
        shimmerCartLayout.startShimmerAnimation();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), checkoutActivity.class);
                startActivity(intent);
            }
        });

        ConstructRecyclerView();
        calculateTotalPrice();
        ((Homepage) getActivity()).setCartBtnBadge();

        pullToRefresh = myCartFragmentView.findViewById(R.id.cartPullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            if(adapter != null){
                ConstructRecyclerView();
                calculateTotalPrice();
                adapter.notifyDataSetChanged();
            }
            pullToRefresh.setRefreshing(false);
            ((Homepage) getActivity()).setCartBtnBadge();
        });

        return myCartFragmentView;
    }

    private void ConstructRecyclerView(){
        CartModel cm = new CartModel();
        us = new UserStorage(getActivity());
        int quoteID = us.getQuoteID();
        cm.readQuoteItemByQuote(quoteID, response -> {
            cartList = response;
            if(cartList.isEmpty()){
                checkoutBtn.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                fine.setVisibility(View.VISIBLE);
                shimmerCartLayout.stopShimmerAnimation();
                shimmerCartLayout.setVisibility(View.GONE);
                Glide.with(this).load(R.raw.empty_cart).override(800, 800).into(fine);
                textLabel.setText("No item(s) in cart at the moment");
            }
            else{
                PutDataIntoRecyclerView(cartList);
                calculateTotalPrice();
                shimmerCartLayout.stopShimmerAnimation();
                shimmerCartLayout.setVisibility(View.GONE);
            }
        });
    }

    CartAdapter.FragmentCommunication updateCart = (status)->{
        ((Homepage) getActivity()).setCartBtnBadge();
        ((Homepage) getActivity()).displayItemRemovedSnackbar(status);
        calculateTotalPrice();
        checkSize();
    };

    private void PutDataIntoRecyclerView(List<Cart> cartList) throws JSONException {
        ProductModel pm = new ProductModel();
        pm.readProductAll(response -> {
            adapter = new CartAdapter(getContext(), cartList, response, updateCart);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        });
    }

    public void calculateTotalPrice(){
        cm.updateQuoteRecal(us.getQuoteID());
        cm.readQuoteByUser(us.getID(), response -> {
            if(!response.isEmpty()){
                totalPriceStr = String.valueOf(response.get(0).getTotal());
                totalPrice.setText("RM " + totalPriceStr);
                os.saveTotal(response.get(0).getTotal());
            }
        });
    }

    public void checkSize(){
        CartModel cm = new CartModel();
        us = new UserStorage(getActivity());
        int quoteID = us.getQuoteID();
        cm.readQuoteItemByQuote(quoteID, response -> {
            if(response.isEmpty()){
                checkoutBtn.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                fine.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.raw.empty_cart).override(800, 800).into(fine);
                textLabel.setText("No item(s) in cart at the moment");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if(adapter != null){
            ConstructRecyclerView();
            calculateTotalPrice();
            adapter.notifyDataSetChanged();
        }
        ((Homepage) getActivity()).setCartBtnBadge();
    }
}