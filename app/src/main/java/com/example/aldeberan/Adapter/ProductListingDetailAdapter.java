package com.example.aldeberan.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.ProductDetailCRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import java.util.List;

public class ProductListingDetailAdapter extends RecyclerView.Adapter<ProductListingDetailAdapter.ProductViewHolder>{

    private Context mContext;
    public List<Product> mData;
    ProductModel pm = new ProductModel();
    CartModel cm = new CartModel();

    public ProductListingDetailAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductDetailCRowBinding productRowBinding;

        public ProductViewHolder(ProductDetailCRowBinding productRowBinding) {
            super(productRowBinding.getRoot());
            this.productRowBinding = productRowBinding;
        }
    }

    @NonNull
    @Override
    public ProductListingDetailAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductDetailCRowBinding productRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.product_detail_c_row, parent, false);

        return new ProductViewHolder(productRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final Product p = mData.get(position);
        holder.productRowBinding.setProduct(p);
        holder.productRowBinding.executePendingBindings();

        holder.productRowBinding.cusProdNameLbl.setText("Product: " + mData.get(position).getProdName());
        //holder.productRowBinding.cusProdSKULbl.setText("SKU: " + mData.get(position).getProdSKU());
        holder.productRowBinding.cusProdIDLbl.setText("Product ID: " + mData.get(position).getProdID());

        //String prodAvail = mData.get(position).getProdAvail() ? "Active" : "Inactive";

        //holder.productRowBinding.cusProdAvailLbl.setText("Availability: " + prodAvail);
        //holder.productRowBinding.cusProdStockLbl.setText("Stock: " + mData.get(position).getProdStock());
        holder.productRowBinding.cusProdPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productRowBinding.cusProdImgView);

        /*
        holder.productRowBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cm.addQuote();
            }
        });
        
         */
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
