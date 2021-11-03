package com.example.aldeberan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Activity.Homepage;
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
    public GuestFragmentCommunication guestFragmentCommunication;
    ProductModel pm = new ProductModel();
    CartModel cm = new CartModel();
    WishlistModel wm = new WishlistModel();
    UserStorage userStorage;

    public AllProductAdapter(Context mContext, List<Product> mData, FragmentCommunication mCommunicator, GuestFragmentCommunication guestFragmentCommunication) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
        this.guestFragmentCommunication = guestFragmentCommunication;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductCardBinding productCardBinding;
        FragmentCommunication mCommunication;
        GuestFragmentCommunication guestFragmentCommunication;

        public ProductViewHolder(ProductCardBinding productCardBinding, AllProductAdapter.FragmentCommunication mCommunication, AllProductAdapter.GuestFragmentCommunication guestFragmentCommunication) {
            super(productCardBinding.getRoot());
            this.productCardBinding = productCardBinding;
            this.mCommunication = mCommunication;
            this.guestFragmentCommunication = guestFragmentCommunication;

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
        AllProductAdapter.ProductViewHolder holder = new AllProductAdapter.ProductViewHolder(productCardBinding, mCommunicator, guestFragmentCommunication);
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
        holder.productCardBinding.cusProdPriceLbl.setText("RM " + String.format("%.2f", mData.get(position).getProdPrice()));
        holder.productCardBinding.cusExtraLbl.setText(String.valueOf(mData.get(position).getProdSold()));
        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productCardBinding.cusProdImgView);

        Glide.with(mContext).load(R.raw.top_seller_fire).override(150,150).into(holder.productCardBinding.fireImg);

        if (mData.get(position).getIsTopSeller()) {
            holder.productCardBinding.topSellerLabel.setVisibility(View.VISIBLE);
        } else {
            holder.productCardBinding.topSellerLabel.setVisibility(View.GONE);
        }

        if (mData.get(position).getWishID() != -1){
            holder.productCardBinding.buttonAddWishlist.setVisibility(View.GONE);
            holder.productCardBinding.buttonDelWishlist.setVisibility(View.VISIBLE);
        }

        holder.productCardBinding.buttonAddCart.setOnClickListener(view -> {
            if (!userID.contains("guest")){
                mCommunicator.respond(String.valueOf(mData.get(position).getProdName()),
                        String.valueOf(mData.get(position).getProdID()),
                        String.valueOf(mData.get(position).getProdImg()),
                        String.valueOf(mData.get(position).getProdPrice()));

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
                guestFragmentCommunication.guestUser();
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
                guestFragmentCommunication.guestUser();
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
                guestFragmentCommunication.guestUser();
            }
        });
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

    public interface GuestFragmentCommunication {
        void guestUser();
    }

}
