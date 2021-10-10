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
import com.example.aldeberan.databinding.ProductCardBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import java.util.List;

public class ProductListingDetailAdapter extends RecyclerView.Adapter<ProductListingDetailAdapter.ProductViewHolder>{

    private Context mContext;
    public List<Product> mData;
    public FragmentCommunication mCommunicator;
    ProductModel pm = new ProductModel();
    CartModel cm = new CartModel();
    UserStorage userStorage;


    public ProductListingDetailAdapter(Context mContext, List<Product> mData, FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductCardBinding productCardBinding;
        FragmentCommunication mCommunication;

        public ProductViewHolder(ProductCardBinding productCardBinding, ProductListingDetailAdapter.FragmentCommunication mCommunication) {
            super(productCardBinding.getRoot());
            this.productCardBinding = productCardBinding;
            this.mCommunication = mCommunication;

            productCardBinding.buttonAddCart.setOnClickListener(view -> {
                //String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()));
                mCommunication.respond(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdName()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdImg()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdPrice()));
            });
        }
    }

    @NonNull
    @Override
    public ProductListingDetailAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductCardBinding productCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.product_card, parent, false);
        ProductListingDetailAdapter.ProductViewHolder holder = new ProductListingDetailAdapter.ProductViewHolder(productCardBinding, mCommunicator);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.productCardBinding.executePendingBindings();
        String prodNameLbl = mData.get(position).getProdName();
        holder.productCardBinding.cusProdNameLbl.setText(prodNameLbl.substring(0, prodNameLbl.indexOf(' ', prodNameLbl.indexOf(' ')+2)));
        //holder.productRowBinding.cusProdSKULbl.setText("SKU: " + mData.get(position).getProdSKU());
        //holder.productRowBinding.cusProdIDLbl.setText("Product ID: " + mData.get(position).getProdID());

        //String prodAvail = mData.get(position).getProdAvail() ? "Active" : "Inactive";

        //holder.productRowBinding.cusProdAvailLbl.setText("Availability: " + prodAvail);
        //holder.productRowBinding.cusProdStockLbl.setText("Stock: " + mData.get(position).getProdStock());
        holder.productCardBinding.cusProdPriceLbl.setText("RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productCardBinding.cusProdImgView);

        holder.productCardBinding.buttonAddCart.setOnClickListener(view -> {
            mCommunicator.respond(String.valueOf(mData.get(position).getProdName()),
                    String.valueOf(mData.get(position).getProdID()),
                    String.valueOf(mData.get(position).getProdImg()),
                    String.valueOf(mData.get(position).getProdPrice()));

            userStorage = new UserStorage(mContext);
            String userID = userStorage.getID();
            cm.checkIfUserExist(userID);

            int quoteID = userStorage.getQuoteID();
            String prodName = String.valueOf(mData.get(position).getProdName());
            String prodSKU = String.valueOf(mData.get(position).getProdSKU());
            Double prodPrice = Double.parseDouble(String.valueOf(mData.get(position).getProdPrice()));
            String prodImg = String.valueOf(mData.get(position).getProdImg());

            cm.addQuoteItem(quoteID, prodName, prodSKU, 1, prodPrice, prodImg);
            cm.updateQuoteRecal(quoteID);

            });

            //addQuoteItem(String.valueOf(cData.get(getAbsoluteAdapterPosition()).getQuoteID());
    };



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodImg, String prodPrice);
    }

}
