package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.R;
import com.example.aldeberan.databinding.OrderDetailCRowBinding;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
    private Context mContext;
    public List<Cart> mData;
    //UserStorage userStorage;

    public CheckoutAdapter(Context mContext, List<Cart> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {
        OrderDetailCRowBinding orderDetailCRowBinding;
        public CheckoutViewHolder(OrderDetailCRowBinding orderDetailCRowBinding) {
            super(orderDetailCRowBinding.getRoot());
            this.orderDetailCRowBinding = orderDetailCRowBinding;
        }
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderDetailCRowBinding orderDetailCRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.order_detail_c_row, parent, false);
        CheckoutAdapter.CheckoutViewHolder holder = new CheckoutAdapter.CheckoutViewHolder(orderDetailCRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        holder.orderDetailCRowBinding.executePendingBindings();
        holder.orderDetailCRowBinding.checkoutName.setText(mData.get(position).getProdName());

        System.out.println("Order: " + mData.get(position).getProdName());
        System.out.println();
        holder.orderDetailCRowBinding.checkoutTotalPrice.setText(String.valueOf(mData.get(position).getProdPrice()));
        holder.orderDetailCRowBinding.checkoutItemNum.setText(String.valueOf(mData.get(position).getProdQuantity()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
