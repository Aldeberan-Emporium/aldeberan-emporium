package com.example.aldeberan.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.databinding.WishlistRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.models.WishlistModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.example.aldeberan.structures.Product;
import com.example.aldeberan.structures.Wishlist;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private Context wContext;
    public List<Wishlist> wData;
    CartModel cm = new CartModel();
    WishlistModel wm = new WishlistModel();
    UserStorage userStorage;

    public WishlistAdapter(Context mContext, List<Wishlist> wData) {
        this.wContext = mContext;
        this.wData = wData;
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder{
        WishlistRowBinding wishlistRowBinding;
        public WishlistViewHolder(WishlistRowBinding wishlistRowBinding) {
            super(wishlistRowBinding.getRoot());
            this.wishlistRowBinding = wishlistRowBinding;
        }
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WishlistRowBinding wishlistRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.wishlist_row, parent, false);
        WishlistAdapter.WishlistViewHolder holder = new WishlistAdapter.WishlistViewHolder(wishlistRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, @SuppressLint("RecyclerView") int position) {

        userStorage = new UserStorage(wContext);
        String userID = userStorage.getID();
        String tempName = wData.get(position).getProdName();
        String partialName = StringUtils.substring(tempName, 0, 10);

        holder.wishlistRowBinding.executePendingBindings();
        holder.wishlistRowBinding.wishProdName.setText(partialName + "...");
        holder.wishlistRowBinding.wishProdPrice.setText("RM " + String.valueOf(wData.get(position).getProdPrice()));
        Glide.with(wContext).load(wData.get(position).getProdImg()).override(450, 450).into(holder.wishlistRowBinding.wishProdImgView);

        if(userID != null) {
            holder.wishlistRowBinding.addWCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int quoteID = userStorage.getQuoteID();
                    String prodName = String.valueOf(wData.get(position).getProdName());
                    String prodSKU = String.valueOf(wData.get(position).getProdSKU());
                    Double prodPrice = Double.parseDouble(String.valueOf(wData.get(position).getProdPrice()));
                    String prodImg = String.valueOf(wData.get(position).getProdImg());

                    cm.addQuoteItem(quoteID, prodName, prodSKU, 1, prodPrice, prodImg);
                    cm.updateQuoteRecal(quoteID);
                }
            });

            holder.wishlistRowBinding.deleteWish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(wContext);
                    builder.setMessage("Do you want to remove this item from the wishlist?");
                    builder.setCancelable(true);
                    int wishlistID = Integer.parseInt(String.valueOf(wData.get(position).getWishID()));

                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    wm.removeFromWishlist(wishlistID);
                                    notifyItemRemoved(position);
                                    dialog.cancel();
                                }
                            });

                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return wData.size();
    }
}
