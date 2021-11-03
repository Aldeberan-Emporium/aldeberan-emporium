package com.example.aldeberan.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.ProductCardBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.models.WishlistModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ProductListingDetailAdapter extends RecyclerView.Adapter<ProductListingDetailAdapter.ProductViewHolder>{

    private Context mContext;
    public List<Product> mData;
    public FragmentCommunication mCommunicator;
    public GuestFragmentCommunication guestCommunicator;
    ProductModel pm = new ProductModel();
    CartModel cm = new CartModel();
    WishlistModel wm = new WishlistModel();
    UserStorage userStorage;

    public ProductListingDetailAdapter(Context mContext, List<Product> mData, FragmentCommunication mCommunicator, GuestFragmentCommunication guestCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
        this.guestCommunicator = guestCommunicator;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductCardBinding productCardBinding;
        FragmentCommunication mCommunication;
        GuestFragmentCommunication guestCommunicator;

        public ProductViewHolder(ProductCardBinding productCardBinding, ProductListingDetailAdapter.FragmentCommunication mCommunication, ProductListingDetailAdapter.GuestFragmentCommunication guestCommunicator) {
            super(productCardBinding.getRoot());
            this.productCardBinding = productCardBinding;
            this.mCommunication = mCommunication;
            this.guestCommunicator = guestCommunicator;

            productCardBinding.buttonAddCart.setOnClickListener(view -> {
                //String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()));
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
    public ProductListingDetailAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductCardBinding productCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.product_card, parent, false);
        ProductListingDetailAdapter.ProductViewHolder holder = new ProductListingDetailAdapter.ProductViewHolder(productCardBinding, mCommunicator, guestCommunicator);
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
        final Product p = mData.get(position);
        holder.productCardBinding.setProduct(p);
        holder.productCardBinding.executePendingBindings();
        holder.productCardBinding.cusProdPriceLbl.setText("RM " + String.format("%.2f", mData.get(position).getProdPrice()));
        holder.productCardBinding.cusExtraLbl.setText(String.valueOf(mData.get(position).getProdSold()));
        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productCardBinding.cusProdImgView);

        Glide.with(mContext).load(R.raw.top_seller_fire).override(150,150).into(holder.productCardBinding.fireImg);

        if (mData.get(position).getWishID() != -1){
            holder.productCardBinding.buttonAddWishlist.setVisibility(View.GONE);
            holder.productCardBinding.buttonDelWishlist.setVisibility(View.VISIBLE);
        }

        holder.productCardBinding.buttonAddCart.setOnClickListener(view -> {
            if (!userID.contains("guest")){
                mCommunicator.respond(String.valueOf(mData.get(position).getProdName()),
                        String.valueOf(mData.get(position).getProdID()),
                        String.valueOf(mData.get(position).getProdSKU()),
                        String.valueOf(mData.get(position).getProdImg()),
                        String.valueOf(mData.get(position).getProdPrice()),
                        String.valueOf(mData.get(position).getProdStock()));

                int quoteID = userStorage.getQuoteID();
                String prodName = String.valueOf(mData.get(position).getProdName());
                String prodSKU = String.valueOf(mData.get(position).getProdSKU());
                Double prodPrice = Double.parseDouble(String.valueOf(mData.get(position).getProdPrice()));
                String prodImg = String.valueOf(mData.get(position).getProdImg());
                String stockQuantity = String.valueOf(mData.get(position).getProdStock());

                System.out.println("stockQuantity: " + stockQuantity);
                //cm.addQuoteItem(quoteID, prodName, 1, prodPrice, prodImg);
                cm.addQuoteItem(quoteID, prodName, prodSKU, 1, prodPrice, prodImg);
                cm.updateQuoteRecal(quoteID);
            }
            else{
                guestCommunicator.guestUser();
            }
        });

        holder.productCardBinding.buttonAddWishlist.setOnClickListener(view -> {
            if (!userID.contains("guest")){
                int prodID = mData.get(position).getProdID();
                wm.addToWishlist(userID, prodID);
                System.out.println("Added to wishlist.");

                holder.productCardBinding.buttonAddWishlist.setVisibility(View.GONE);
                holder.productCardBinding.buttonDelWishlist.setVisibility(View.VISIBLE);
            }
            else{
                guestCommunicator.guestUser();
            }
        });

        holder.productCardBinding.buttonDelWishlist.setOnClickListener(view -> {
            if (!userID.contains("guest")) {
                int wishListID = mData.get(position).getWishID();
                wm.removeFromWishlist(wishListID);
                System.out.println("Removed wishlist from homepage.");

                holder.productCardBinding.buttonDelWishlist.setVisibility(View.GONE);
                holder.productCardBinding.buttonAddWishlist.setVisibility(View.VISIBLE);
            }
            else{
                guestCommunicator.guestUser();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodSKU, String prodImg, String prodPrice, String prodStock);
    }

    public interface GuestFragmentCommunication {
        void guestUser();
    }
}
