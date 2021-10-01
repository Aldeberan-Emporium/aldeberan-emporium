package com.example.aldeberan;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.structures.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context mContext;
    private List<Product> mData;

    public ProductAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView prodID;
        TextView prodName;
        TextView prodSKU;
        TextView prodAvail;
        TextView prodStock;
        TextView prodPrice;
        ImageView prodImg;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            prodID = itemView.findViewById(R.id.prodIDLbl);
            prodName = itemView.findViewById(R.id.prodNameLbl);
            prodSKU = itemView.findViewById(R.id.prodSKULbl);
            prodAvail = itemView.findViewById(R.id.prodAvailLbl);
            prodStock = itemView.findViewById(R.id.prodStockLbl);
            prodPrice = itemView.findViewById(R.id.prodPriceLbl);
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
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.prodName.setText(mData.get(position).getProdName());
        holder.prodSKU.setText("SKU: " + mData.get(position).getProdSKU());
        holder.prodID.setText("ID: (" + mData.get(position).getProdID() + ")");

        String prodAvail = mData.get(position).getProdAvail() ? "Active" : "Inactive";

        holder.prodAvail.setText("Availability: " + prodAvail);
        holder.prodStock.setText("Stock: " + mData.get(position).getProdStock());
        holder.prodPrice.setText("Price: RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.prodImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
