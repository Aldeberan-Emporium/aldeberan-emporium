package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CheckoutDetailCRowBinding;
import com.example.aldeberan.structures.Cart;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
    private Context mContext;
    public List<Cart> mData;

    public CheckoutAdapter(Context mContext, List<Cart> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {
        CheckoutDetailCRowBinding checkoutDetailCRowBinding;
        public CheckoutViewHolder(CheckoutDetailCRowBinding checkoutDetailCRowBinding) {
            super(checkoutDetailCRowBinding.getRoot());
            this.checkoutDetailCRowBinding = checkoutDetailCRowBinding;
        }
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CheckoutDetailCRowBinding checkoutDetailCRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.checkout_detail_c_row, parent, false);
        CheckoutAdapter.CheckoutViewHolder holder = new CheckoutAdapter.CheckoutViewHolder(checkoutDetailCRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        holder.checkoutDetailCRowBinding.executePendingBindings();
        holder.checkoutDetailCRowBinding.checkoutName.setText(mData.get(position).getProdName());
        holder.checkoutDetailCRowBinding.checkoutTotalPrice.setText(String.valueOf(mData.get(position).getProdPrice()));
        holder.checkoutDetailCRowBinding.checkoutItemNum.setText(String.valueOf(mData.get(position).getProdQuantity()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
