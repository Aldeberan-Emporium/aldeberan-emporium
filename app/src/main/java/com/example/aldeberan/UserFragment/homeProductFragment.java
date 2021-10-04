package com.example.aldeberan.UserFragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aldeberan.Adapter.ProductListingDetailAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class homeProductFragment extends Fragment implements View.OnClickListener{

    UserStorage user;
    CartModel cm = new CartModel();
    private View myProductFragmentView;
    //private View myCartFragmentView;
    public List<Product> productList;
    public RecyclerView recyclerView;
    public ProductListingDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myProductFragmentView = inflater.inflate(R.layout.fragment_home_product, container, false);
        //myCartFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);

        productList = new ArrayList<>();
        recyclerView = myProductFragmentView.findViewById(R.id.cRecyclerView);

        
        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = myProductFragmentView.findViewById(R.id.cPullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            adapter.notifyDataSetChanged();
            pullToRefresh.setRefreshing(false);
        });

        return myProductFragmentView;
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
        adapter = new ProductListingDetailAdapter(getContext(), productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(productList));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_add_cart) {
            Switch prodAvailSwitch = getActivity().findViewById(R.id.cusProdIDLbl);
            TextView prodNameLbl = getActivity().findViewById(R.id.cusProdNameLbl);
            TextView prodPriceLbl = getActivity().findViewById(R.id.prodPrice);
            ImageView img = getActivity().findViewById(R.id.cusProdImgView);

            //int prodAvail = prodAvailSwitch.isChecked() ? 1 : 0;


            //String prodName = prodNameLbl.getText().toString();
            //String prodPriceStr = prodPriceLbl.getText().toString();
            //String prodPriceStr = prodPriceLbl.getText().toString();

            //cm.addQuote();
            user = new UserStorage(getContext());
            if(!cm.checkIfUserExist(user.getID())){
                cm.addQuoteItem(cus);

            }



            cartFragment productFragment= new cartFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, productFragment)
                                    .addToBackStack(null)
                                    .commit();



        }
    }
}
