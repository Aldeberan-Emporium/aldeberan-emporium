package com.example.aldeberan.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.models.CartModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Cart;
import com.bumptech.glide.Glide;
import com.example.aldeberan.structures.Product;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    public List<Cart> mData;
    CartModel cm = new CartModel();
    public List<Product> productList;
    UserStorage userStorage;

    public CartAdapter(Context mContext, List<Cart> mData, List<Product> response) {
        this.mContext = mContext;
        this.mData = mData;
        productList = response;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        CartDetailCRowBinding cartDetailCRowBinding;
        public CartViewHolder(CartDetailCRowBinding cartDetailCRowBinding) {
            super(cartDetailCRowBinding.getRoot());
            this.cartDetailCRowBinding = cartDetailCRowBinding;
        }
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartDetailCRowBinding cartRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.cart_detail_c_row, parent, false);
        CartAdapter.CartViewHolder holder = new CartAdapter.CartViewHolder(cartRowBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cartDetailCRowBinding.executePendingBindings();
        holder.cartDetailCRowBinding.cartProdNameLbl.setText("Name: " + mData.get(position).getProdName());
        holder.cartDetailCRowBinding.cartProdPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());
        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.cartDetailCRowBinding.cartProdImgView);

        String currentQuantity = toString().valueOf(mData.get(position).getProdQuantity());
        String prodName = mData.get(position).getProdName();
        List<Product> result = productList.stream()
                .filter(a -> Objects.equals(a.getProdName(), prodName))
                .collect(Collectors.toList());

       int currentStock = result.get(0).getProdStock();

        holder.cartDetailCRowBinding.cartNumButton.setRange(0, currentStock);
        holder.cartDetailCRowBinding.cartNumButton.setNumber(currentQuantity);
        holder.cartDetailCRowBinding.cartNumButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Do you want to remove this product?");
                builder1.setCancelable(true);

                userStorage = new UserStorage(mContext);
                String userID = userStorage.getID();
                cm.checkIfUserExist(userID);

                String quantity = view.getNumber();
                int itemQuantity = Integer.parseInt(quantity);
                int quoteID = userStorage.getQuoteID();
                int quoteItemID = Integer.parseInt(String.valueOf(mData.get(position).getQuoteItemID()));
                String prodName = String.valueOf(mData.get(position).getProdName());
                String prodSKU = String.valueOf(mData.get(position).getProdSKU());
                Double prodPrice = Double.parseDouble(String.valueOf(mData.get(position).getProdPrice()));
                String prodImg = String.valueOf(mData.get(position).getProdImg());

                if(itemQuantity == 0){
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cm.deleteQuoteItem(quoteItemID);
                                    notifyItemRemoved(position);
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    holder.cartDetailCRowBinding.cartNumButton.setNumber("1");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    cm.updateQuoteItem(quoteItemID, quoteID, prodName, prodSKU, itemQuantity, prodPrice, prodImg);
                    cm.updateQuoteRecal(quoteID);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}