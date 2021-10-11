package com.example.aldeberan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.bumptech.glide.Glide;
import com.example.aldeberan.structures.Product;

import org.json.JSONException;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    public List<Cart> mData;
    CartModel cm = new CartModel();
    public List<Product> productList;
    UserStorage userStorage;

    public CartAdapter(Context mContext, List<Cart> mData, List<Product> response) {
        this.mContext = mContext;
        this.mData = mData;
        productList = response;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        CartDetailCRowBinding cartDetailCRowBinding;

        public CartViewHolder(CartDetailCRowBinding cartDetailCRowBinding) {
            super(cartDetailCRowBinding.getRoot());
            this.cartDetailCRowBinding = cartDetailCRowBinding;
        }
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartDetailCRowBinding cartRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.cart_detail_c_row, parent, false);
        CartAdapter.CartViewHolder holder = new CartAdapter.CartViewHolder(cartRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cartDetailCRowBinding.executePendingBindings();
        holder.cartDetailCRowBinding.cartProdNameLbl.setText("Name: " + mData.get(position).getProdName());
        holder.cartDetailCRowBinding.cartProdPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());
        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.cartDetailCRowBinding.cartProdImgView);

        String currentQuantity = toString().valueOf(mData.get(position).getProdQuantity());


        int currentStock;
        /*
        for(int i = 0; i != productList.size(); i++){
            if(mData.get(position).getProdName() == productList.get(i).getProdName()){
                currentStock = Integer.parseInt(String.valueOf(productList.get(i).getProdStock()));
                System.out.println("lankiao " + currentStock);
            }
            else{
                System.out.println("lankiao no found ");
            }
        }

         */

        holder.cartDetailCRowBinding.cartNumButton.setRange(0, 100);
        holder.cartDetailCRowBinding.cartNumButton.setNumber(currentQuantity);


        //holder.cartDetailCRowBinding.checkId.setOnClickListener();
        holder.cartDetailCRowBinding.cartNumButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {


                String prodName = mData.get(position).getProdName();

                int position = mData.indexOf(prodName);

                int maxStock = productList.get(position).getProdStock();
                view.setRange(0,maxStock);
                //String quantity = view.getNumber();

                userStorage = new UserStorage(mContext);
                String userID = userStorage.getID();
                cm.checkIfUserExist(userID);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodSKU, String prodImg, String prodPrice, String prodStock);
    }
}