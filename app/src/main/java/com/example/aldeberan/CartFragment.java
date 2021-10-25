package com.example.aldeberan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aldeberan.Activity.checkoutActivity;
import com.example.aldeberan.Adapter.CartAdapter;
import com.example.aldeberan.UserFragment.checkoutFragment;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myCartFragmentView = inflater.inflate(R.layout.cart, container, false);
        cm = new CartModel();
        os = new OrderStorage(getActivity());
        recyclerView = myCartFragmentView.findViewById(R.id.cartRecyclerView);
        checkoutBtn = myCartFragmentView.findViewById(R.id.checkoutButton);
        totalPrice = myCartFragmentView.findViewById(R.id.totalPrice);
        textLabel = myCartFragmentView.findViewById(R.id.textLabel);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), checkoutActivity.class);
                startActivity(intent);
            }
        });

        ConstructRecyclerView();
        calculateTotalPrice();

        pullToRefresh = myCartFragmentView.findViewById(R.id.cartPullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            if(adapter != null){
                ConstructRecyclerView();
                calculateTotalPrice();
                adapter.notifyDataSetChanged();
            }
            pullToRefresh.setRefreshing(false);
        });

        return myCartFragmentView;
    }

    private void ConstructRecyclerView(){
        CartModel cm = new CartModel();
        us = new UserStorage(getActivity());
        int quoteID = us.getQuoteID();
        cm.readQuoteItemByQuote(quoteID, response -> {

            cartList = response;
            if(cartList.size() == 0){
                checkoutBtn.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                textLabel.setText("Your cart is empty -.-");
            }
            else{
                PutDataIntoRecyclerView(cartList);
                calculateTotalPrice();
            }
        });
    }

    private void PutDataIntoRecyclerView(List<Cart> cartList) throws JSONException {
        ProductModel pm = new ProductModel();
        pm.readProductAll(response -> {
            adapter = new CartAdapter(getContext(), cartList, response);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        });
    }

    public void calculateTotalPrice(){
        cm.updateQuoteRecal(us.getQuoteID());
        cm.readQuoteByUser(us.getID(), response -> {
            if(response != null){
                totalPriceStr = String.valueOf(response.get(0).getTotal());
                totalPrice.setText("RM " + totalPriceStr);
                os.saveTotal(response.get(0).getTotal());
            }
        });
    }
}