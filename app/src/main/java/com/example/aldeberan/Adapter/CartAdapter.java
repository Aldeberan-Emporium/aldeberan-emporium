package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.databinding.ProductDetailCRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Cart;
import com.bumptech.glide.Glide;
import com.example.aldeberan.structures.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    public List<Cart> mData;
    private FragmentCommunication mCommunicator;
    CartModel cm = new CartModel();

    public CartAdapter(Context mContext, List<Cart> mData, FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        CartDetailCRowBinding cartDetailCRowBinding;
        FragmentCommunication mCommunication;

        public CartViewHolder(CartDetailCRowBinding cartDetailCRowBinding, CartAdapter.FragmentCommunication mCommunication) {
            super(cartDetailCRowBinding.getRoot());
            this.cartDetailCRowBinding = cartDetailCRowBinding;
            this.mCommunication = mCommunication;

        }
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartDetailCRowBinding cartRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.cart_detail_c_row, parent, false);
        CartAdapter.CartViewHolder holder = new CartAdapter.CartViewHolder(cartRowBinding, mCommunicator);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final Cart c = mData.get(position);
        //c.
        //holder.cartDetailCRowBinding.setProduct(c);
        holder.cartDetailCRowBinding.executePendingBindings();
        holder.cartDetailCRowBinding.cartProdNameLbl.setText("Product: " + mData.get(position).getProdName());
        //holder.cartDetailCRowBinding.cartProdIDLbl.setText("Product ID: " + mData.get(position).getProdID());
        holder.cartDetailCRowBinding.cartProdPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.cartDetailCRowBinding.cartProdImgView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodImg, String prodPrice);
    }
}