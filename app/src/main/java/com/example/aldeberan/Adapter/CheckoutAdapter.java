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

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
    private Context mContext;
    public List<Cart> mData;
    public List<Product> pData;

    public CheckoutAdapter(Context mContext, List<Cart> mData, List<Product> pData) {
        this.mContext = mContext;
        this.mData = mData;
        this.pData = pData;
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
        String partialName = StringUtils.substring(tempName, 0, 10);

        String itemSKU = mData.get(position).getProdSKU();
        Double tempPrice = mData.get(position).getProdPrice();
        int tempQuantity = mData.get(position).getProdQuantity();
        Double itemTotal = tempPrice*tempQuantity;

        holder.checkoutDetailCRowBinding.executePendingBindings();
        holder.checkoutDetailCRowBinding.checkoutName.setText(partialName + "...");
        holder.checkoutDetailCRowBinding.checkoutTotalPrice.setText("RM " + String.valueOf(itemTotal));
        holder.checkoutDetailCRowBinding.checkoutItemNum.setText(String.valueOf(tempQuantity) + "x");

        int getLatestStock = updateStock(itemSKU, tempQuantity);
        System.out.println("Newest: " + getLatestStock);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int updateStock(String temp_name, int quantity){
        int temp_quantity = 0;

        for(int i = 0; i < pData.size(); i++){
            if(temp_name.equals(pData.get(i).getProdSKU())){
                //currentStock = pData.get(i).getProdStock();
                //holder.cartDetailCRowBinding.cartNumButton.setRange(0, currentStock);
                temp_quantity = pData.get(i).getProdStock() - quantity;
                return temp_quantity;
            }
        }

        return temp_quantity;
    }
}
