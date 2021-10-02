package com.example.aldeberan;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.databinding.ActivityMainBinding;
import com.example.aldeberan.databinding.AdminPanelProductRowBinding;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context mContext;
    public List<Product> mData;
    ProductModel pm = new ProductModel();


    public ProductAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        AdminPanelProductRowBinding productRowBinding;

        public ProductViewHolder(AdminPanelProductRowBinding productRowBinding) {
            super(productRowBinding.getRoot());
            this.productRowBinding = productRowBinding;

            productRowBinding.updateBtn.setOnClickListener(view -> {
                Log.i("UPDATE", String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()));
                //Show update screen
            });

            productRowBinding.deleteBtn.setOnClickListener(view -> {
                Log.i("DELETE", String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()));
                showDialog(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdName()),
                        Integer.parseInt(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID())),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdSKU()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdImg()));
            });
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdminPanelProductRowBinding productRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.admin_panel_product_row, parent, false);

        return new ProductViewHolder(productRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final Product p = mData.get(position);
        holder.productRowBinding.setProduct(p);
        holder.productRowBinding.executePendingBindings();

        holder.productRowBinding.prodNameLbl.setText(mData.get(position).getProdName());
        holder.productRowBinding.prodSKULbl.setText("SKU: " + mData.get(position).getProdSKU());
        holder.productRowBinding.prodIDLbl.setText("ID: (" + mData.get(position).getProdID() + ")");

        String prodAvail = mData.get(position).getProdAvail() ? "Active" : "Inactive";

        holder.productRowBinding.prodAvailLbl.setText("Availability: " + prodAvail);
        holder.productRowBinding.prodStockLbl.setText("Stock: " + mData.get(position).getProdStock());
        holder.productRowBinding.prodPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productRowBinding.prodImgView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    private void showDialog(String prodName, int prodID, String prodSKU, String prodImg){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to remove " + prodName + "?");

        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).setNegativeButton("Confirm", (dialog, which) -> {
            pm.deleteProduct(prodID, prodSKU, prodImg);
            dialog.dismiss();
            Toast.makeText(mContext, prodName + " deleted!", Toast.LENGTH_LONG).show();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
