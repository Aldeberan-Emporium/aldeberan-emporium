package com.example.aldeberan.UserFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myCartFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);
        productList = new ArrayList<>();
        recyclerView = myCartFragmentView.findViewById(R.id.cartRecyclerView);

        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = myCartFragmentView.findViewById(R.id.cartPullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
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
                System.out.println("hello" + cartList);
                PutDataIntoRecyclerView(response);
            });
        }



        /*
    CartAdapter.FragmentCommunication cart_communication = (prodName, prodID, prodSKU, prodQuantity, prodImg, prodPrice) -> {
        cartFragment cart = new cartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("prodName", prodName);
        bundle.putString("prodID", prodID);
        bundle.putString("prodSKU", prodSKU);
        bundle.putInt("prodQuantity", prodQuantity);
        bundle.putString("prodImg", prodImg);
        //bundle.putString("prodStock", prodStock);
        //bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", prodPrice);
        cart.setArguments(bundle);


    };

         */

    private void PutDataIntoRecyclerView(List<Cart> cartList){
        adapter = new CartAdapter(getContext(), cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(cartList));
    }


}
