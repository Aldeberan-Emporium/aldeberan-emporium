package com.example.aldeberan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.AdminFragment.AdminPanelUpdateProductFragment;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.AdminPanelProductRowBinding;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;

import java.util.List;

/*
Ong Shuoh Chwen 1171102212

Ong is responsible for this feature.
*/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context mContext;
    public List<Product> mData;
    private FragmentCommunication mCommunicator;
    ProductModel pm = new ProductModel();


    public ProductAdapter(Context mContext, List<Product> mData, FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        AdminPanelProductRowBinding productRowBinding;
        FragmentCommunication mCommunication;

        public ProductViewHolder(AdminPanelProductRowBinding productRowBinding, FragmentCommunication mCommunication) {
            super(productRowBinding.getRoot());
            this.productRowBinding = productRowBinding;
            this.mCommunication = mCommunication;

            productRowBinding.updateBtn.setOnClickListener(view -> {
                Log.i("UPDATE", String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()));

                String prodAvail = String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdAvail()) == "true" ? "1" : "0";
                //Show update screen
                mCommunication.respond(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdName()),
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
        ProductAdapter.ProductViewHolder holder = new ProductAdapter.ProductViewHolder(productRowBinding, mCommunicator);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final Product p = mData.get(position);
        holder.productRowBinding.setProduct(p);
        holder.productRowBinding.executePendingBindings();

        holder.productRowBinding.prodNameLbl.setText(mData.get(position).getProdName());
        holder.productRowBinding.prodSKULbl.setText("SKU: " + mData.get(position).getProdSKU());
        holder.productRowBinding.prodIDLbl.setText("ID: " + mData.get(position).getProdID());

        String prodAvailDisplay = mData.get(position).getProdAvail() ? "Active" : "Inactive";
        String prodAvail = mData.get(position).getProdAvail() ? "true" : "false";

        holder.productRowBinding.prodAvailLbl.setText("Availability: " + prodAvailDisplay);
        holder.productRowBinding.prodStockLbl.setText("Stock: " + mData.get(position).getProdStock());
        holder.productRowBinding.prodPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productRowBinding.prodImgView);

        //Set data to pass to update fragment
        AdminPanelUpdateProductFragment updateProductFragment = new AdminPanelUpdateProductFragment();
        Bundle bundle=new Bundle();
        bundle.putString("prodName", mData.get(position).getProdName());
        bundle.putString("prodID", String.valueOf(mData.get(position).getProdID()));
        bundle.putString("prodSKU", mData.get(position).getProdSKU());
        bundle.putString("prodImg", mData.get(position).getProdImg());
        bundle.putString("prodStock", String.valueOf(mData.get(position).getProdStock()));
        bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", String.valueOf(mData.get(position).getProdPrice()));
        updateProductFragment.setArguments(bundle);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodSKU, String prodImg, String prodStock, String prodAvail, String prodPrice);
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

}
