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
import com.example.aldeberan.structures.Product;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/*
Leong Kah Ming  1171100884
Chong Wai Hou   1161104445

Leong and Chong are reponsible for this feature.
They connected/linked the functions and debug the problems.

*/

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
        String tempName = mData.get(position).getProdName();
        String partialName = StringUtils.substring(tempName, 0, 25) + "...";
        Double tempPrice = mData.get(position).getProdPrice();
        int tempQuantity = mData.get(position).getProdQuantity();
        Double itemTotal = tempPrice*tempQuantity;

        holder.checkoutDetailCRowBinding.executePendingBindings();
        if(tempName.length() > 25 ){
            holder.checkoutDetailCRowBinding.checkoutName.setText(partialName);
        }
        else{
            holder.checkoutDetailCRowBinding.checkoutName.setText(tempName);
        }
        holder.checkoutDetailCRowBinding.checkoutTotalPrice.setText("RM " + String.format("%.2f", itemTotal));
        holder.checkoutDetailCRowBinding.checkoutItemNum.setText(String.valueOf(tempQuantity) + "x");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
