package com.example.aldeberan;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.structures.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context mContext;
    private List<Product> mData;

    public ProductAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView prodName;
        TextView prodSKU;
        ImageView prodImg;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.prodNameLbl);
            prodSKU = itemView.findViewById(R.id.prodSKULbl);
            prodImg = itemView.findViewById(R.id.prodImgView);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.admin_panel_product_row, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.prodName.setText(mData.get(position).getProdName());
        holder.prodSKU.setText(mData.get(position).getProdSKU());

        holder.prodImg.setImageURI(Uri.parse(mData.get(position).getProdImg()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
