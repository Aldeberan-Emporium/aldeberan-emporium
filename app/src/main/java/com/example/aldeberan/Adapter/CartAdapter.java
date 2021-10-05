package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    public List<Cart> mData;
    private CartAdapter.FragmentCommunication mCommunicator;
    CartModel cm = new CartModel();

    public CartAdapter(Context mContext, List<Cart> mData, ProductAdapter.FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        CartDetailCRowBinding cartDetailCRowBinding;
        FragmentCommunication mCommunication;

        public CartViewHolder(CartDetailCRowBinding cartDetailCRowBinding, FragmentCommunication mCommunication) {
            super(cartDetailCRowBinding.getRoot());
            this.cartDetailCRowBinding = cartDetailCRowBinding;
            this.mCommunication = mCommunication;

            //cartDetailCRowBinding.button_add_cart
        }

        public CartDetailCRowBinding getCartDetailCRowBinding() {

        }
    }

    public class FragmentCommunication {
    }
}
