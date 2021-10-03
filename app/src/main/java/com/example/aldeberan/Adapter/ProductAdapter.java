package com.example.aldeberan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.AdminFragment.AdminFunction.AdminPanel_UpdateProduct;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.AdminPanelProductRowBinding;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

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

                String prodAvail = String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdAvail()) == "true" ? "1" : "0";
                //Show update screen
                toUpdate(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdName()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdSKU()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdImg()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdStock()),
                        prodAvail, String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdPrice()));
            });

            productRowBinding.deleteBtn.setOnClickListener(view -> {
                Log.i("DELETE", String.valueOf(getAbsoluteAdapterPosition()));
                showDialog(Integer.parseInt(String.valueOf(getAbsoluteAdapterPosition())),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdName()),
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
        holder.productRowBinding.prodIDLbl.setText("ID: " + mData.get(position).getProdID());

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


    private void showDialog(int index, String prodName, int prodID, String prodSKU, String prodImg){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to remove " + prodName + "?");

        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).setNegativeButton("Confirm", (dialog, which) -> {
            pm.deleteProduct(prodID, prodSKU, prodImg);
            dialog.dismiss();
            Toast.makeText(mContext, prodName + " deleted!", Toast.LENGTH_LONG).show();
            mData.remove(index);
            notifyItemRemoved(index);
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void toUpdate(String prodName, String prodID, String prodSKU, String prodImg, String prodStock, String prodAvail, String prodPrice){
        Intent intent = new Intent(mContext, AdminPanel_UpdateProduct.class);
        intent.putExtra("prodName", prodName);
        intent.putExtra("prodID", prodID);
        intent.putExtra("prodSKU", prodSKU);
        intent.putExtra("prodImg", prodImg);
        intent.putExtra("prodStock", prodStock);
        intent.putExtra("prodAvail", prodAvail);
        intent.putExtra("prodPrice", prodPrice);
        mContext.startActivity(intent);
    }

}
