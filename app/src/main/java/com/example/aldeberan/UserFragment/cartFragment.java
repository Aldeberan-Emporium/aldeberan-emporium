package com.example.aldeberan.UserFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.aldeberan.Activity.MainActivity;
import com.example.aldeberan.Activity.checkoutActivity;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.Adapter.CartAdapter;
import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class cartFragment extends Fragment {

    private View myCartFragmentView;
    private List<Product> productList;
    private RecyclerView recyclerView;
    private List<Cart> cartList;
    private CartAdapter adapter;
    private UserStorage us;
    private Button checkoutBtn;
    private TextView totalPrice;
    private String totalPriceStr;
    private CartModel cm;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myCartFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);
        productList = new ArrayList<>();
        cm = new CartModel();
        recyclerView = myCartFragmentView.findViewById(R.id.cartRecyclerView);
        checkoutBtn = myCartFragmentView.findViewById(R.id.checkoutButton);
        totalPrice = myCartFragmentView.findViewById(R.id.totalPrice);


        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), checkoutActivity.class);
                startActivity(intent);
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new checkoutFragment()).commit();
            }
        });

        ConstructRecyclerView();
        calculateTotalPrice();
        SwipeRefreshLayout pullToRefresh = myCartFragmentView.findViewById(R.id.cartPullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
            calculateTotalPrice();
            pullToRefresh.setRefreshing(false);
        });
        return myCartFragmentView;
    }

    private void ConstructRecyclerView(){
        CartModel cm = new CartModel();
        us = new UserStorage(getActivity());

        int quoteID = us.getQuoteID();
        System.out.println("quoteID: " + quoteID);

            cm.readQuoteItemByQuote(quoteID, response -> {
                cartList = response;
                PutDataIntoRecyclerView(response);
            });
        }

    private void PutDataIntoRecyclerView(List<Cart> cartList) throws JSONException {
        ProductModel pm = new ProductModel();
        pm.readProductAll(response -> {
            adapter = new CartAdapter(getContext(), cartList, response);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            Log.i("PLOPE", String.valueOf(cartList));
        });

    }

    public void calculateTotalPrice(){
        cm.readQuoteByUser(us.getID(), response -> {
            totalPriceStr = String.valueOf(response.get(0).getTotal());
            totalPrice.setText("RM" + totalPriceStr);
        });
    }
}
