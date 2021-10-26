package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.ProductCardBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.models.WishlistModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Product;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ProductViewHolder>{

    private Context mContext;
    public List<Product> mData;
    public FragmentCommunication mCommunicator;
    ProductModel pm = new ProductModel();
    CartModel cm = new CartModel();
    WishlistModel wm = new WishlistModel();
    UserStorage userStorage;


    public AllProductAdapter(Context mContext, List<Product> mData, FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductCardBinding productCardBinding;
        FragmentCommunication mCommunication;

        public ProductViewHolder(ProductCardBinding productCardBinding, AllProductAdapter.FragmentCommunication mCommunication) {
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

            //add to wishlist
            productCardBinding.buttonAddWishlist.setOnClickListener(view -> {
                productCardBinding.buttonAddWishlist.setVisibility(View.GONE);
                productCardBinding.buttonDelWishlist.setVisibility(View.VISIBLE);
            });

            //remove from wishlist
            productCardBinding.buttonDelWishlist.setOnClickListener(view -> {
                productCardBinding.buttonDelWishlist.setVisibility(View.GONE);
                productCardBinding.buttonAddWishlist.setVisibility(View.VISIBLE);
            });
        }
    }

    @NonNull
    @Override
    public AllProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductCardBinding productCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.product_card, parent, false);
        AllProductAdapter.ProductViewHolder holder = new AllProductAdapter.ProductViewHolder(productCardBinding, mCommunicator);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        userStorage = new UserStorage(mContext);
        String userID = userStorage.getID();
        holder.productCardBinding.executePendingBindings();
        String tempName = mData.get(position).getProdName();
        String partialName = StringUtils.substring(tempName, 0, 10) + "...";
        holder.productCardBinding.cusProdNameLbl.setText(partialName);
        holder.productCardBinding.cusProdPriceLbl.setText("RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productCardBinding.cusProdImgView);

        holder.productCardBinding.buttonAddCart.setOnClickListener(view -> {
            mCommunicator.respond(String.valueOf(mData.get(position).getProdName()),
                    String.valueOf(mData.get(position).getProdID()),
                    String.valueOf(mData.get(position).getProdImg()),
                    String.valueOf(mData.get(position).getProdPrice()));

            int quoteID = userStorage.getQuoteID();
            String prodName = String.valueOf(mData.get(position).getProdName());
            String prodSKU = String.valueOf(mData.get(position).getProdSKU());
            Double prodPrice = Double.parseDouble(String.valueOf(mData.get(position).getProdPrice()));
            String prodImg = String.valueOf(mData.get(position).getProdImg());

            cm.addQuoteItem(quoteID, prodName, prodSKU, 1, prodPrice, prodImg);
            cm.updateQuoteRecal(quoteID);

            });

        if (mData.get(position).getWishID() != -1){
            holder.productCardBinding.buttonAddWishlist.setVisibility(View.GONE);
            holder.productCardBinding.buttonDelWishlist.setVisibility(View.VISIBLE);
        }

        if(userID != null){
            holder.productCardBinding.buttonAddWishlist.setOnClickListener(view -> {

                int prodID = mData.get(position).getProdID();
                wm.addToWishlist(userID, prodID);
                System.out.println("Added to wishlist.");

                holder.productCardBinding.buttonAddWishlist.setVisibility(View.GONE);
                holder.productCardBinding.buttonDelWishlist.setVisibility(View.VISIBLE);
            });

            holder.productCardBinding.buttonDelWishlist.setOnClickListener(view -> {

                int wishListID = mData.get(position).getWishID();
                wm.removeFromWishlist(wishListID);
                System.out.println("Removed wishlist from homepage.");

                holder.productCardBinding.buttonDelWishlist.setVisibility(View.GONE);
                holder.productCardBinding.buttonAddWishlist.setVisibility(View.VISIBLE);
            });
        }
    };

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodImg, String prodPrice);
    }

    //Notify recycler view on filtering products
    public void filteredProductList (ArrayList<Product> productList) {
        mData = productList;
        notifyDataSetChanged();
    }

}
