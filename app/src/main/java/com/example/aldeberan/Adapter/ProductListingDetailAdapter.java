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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.CartDetailCRowBinding;
import com.example.aldeberan.databinding.ProductDetailCRowBinding;
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

        ProductDetailCRowBinding productRowBinding;
        FragmentCommunication mCommunication;

        public ProductViewHolder(ProductDetailCRowBinding productRowBinding, ProductListingDetailAdapter.FragmentCommunication mCommunication) {
            super(productRowBinding.getRoot());
            this.productRowBinding = productRowBinding;
            this.mCommunication = mCommunication;

            productRowBinding.buttonAddCart.setOnClickListener(view -> {
                mCommunication.respond(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdName()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdID()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdSKU()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdImg()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdPrice()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getProdStock()));
            });
        }
    }

    @NonNull
    @Override
    public ProductListingDetailAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductDetailCRowBinding productRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.product_detail_c_row, parent, false);
        ProductListingDetailAdapter.ProductViewHolder holder = new ProductListingDetailAdapter.ProductViewHolder(productRowBinding, mCommunicator);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ElegantNumberButton numberButton;
        int quantity = 0;
        final Product p = mData.get(position);
        holder.productRowBinding.setProduct(p);
        holder.productRowBinding.executePendingBindings();

        holder.productRowBinding.cusProdNameLbl.setText("Product: " + mData.get(position).getProdName());
        holder.productRowBinding.cusProdSKULbl.setText("SKU: " + mData.get(position).getProdSKU());
        holder.productRowBinding.cusProdIDLbl.setText("Product ID: " + mData.get(position).getProdID());

        //String prodAvail = mData.get(position).getProdAvail() ? "Active" : "Inactive";

        //holder.productRowBinding.cusProdAvailLbl.setText("Availability: " + prodAvail);
        //holder.productRowBinding.cusProdStockLbl.setText("Stock: " + mData.get(position).getProdStock());
        holder.productRowBinding.cusProdPriceLbl.setText("Price: RM " + mData.get(position).getProdPrice());

        Glide.with(mContext).load(mData.get(position).getProdImg()).override(450, 450).into(holder.productRowBinding.cusProdImgView);

        //Not finish yet.
        holder.productRowBinding.quantityButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton button, int oldValue, int newValue) {
                String quantity = button.getNumber();

                int itemQuantity = Integer.parseInt(quantity);
                mCommunicator.respond(String.valueOf(mData.get(position).getProdName()),
                        String.valueOf(mData.get(position).getProdID()),
                        String.valueOf(mData.get(position).getProdSKU()),
                        String.valueOf(mData.get(position).getProdImg()),
                        String.valueOf(mData.get(position).getProdPrice()),
                        String.valueOf(mData.get(position).getProdStock()));

                userStorage = new UserStorage(mContext);
                String userID = userStorage.getID();
                cm.checkIfUserExist(userID);

                int quoteID = userStorage.getQuoteID();
                String prodName = String.valueOf(mData.get(position).getProdName());
                String prodSKU = String.valueOf(mData.get(position).getProdSKU());
                Double prodPrice = Double.parseDouble(String.valueOf(mData.get(position).getProdPrice()));
                String prodImg = String.valueOf(mData.get(position).getProdImg());
                String stockQuantity = String.valueOf(mData.get(position).getProdStock());

                System.out.println("Item quantity:" + itemQuantity);
                cm.addQuoteItem(quoteID, prodName, prodSKU, itemQuantity, prodPrice, prodImg);
                cm.updateQuoteRecal(quoteID);
            }
        });

        holder.productRowBinding.buttonAddCart.setOnClickListener(view -> {
            mCommunicator.respond(String.valueOf(mData.get(position).getProdName()),
                    String.valueOf(mData.get(position).getProdID()),
                    String.valueOf(mData.get(position).getProdSKU()),
                    String.valueOf(mData.get(position).getProdImg()),
                    String.valueOf(mData.get(position).getProdPrice()),
                    String.valueOf(mData.get(position).getProdStock()));

            userStorage = new UserStorage(mContext);
            String userID = userStorage.getID();
            cm.checkIfUserExist(userID);

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
        });
    };

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String prodName, String prodID, String prodSKU, String prodImg, String prodPrice, String prodStock);
    }

}
