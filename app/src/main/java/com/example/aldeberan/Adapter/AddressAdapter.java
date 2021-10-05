package com.example.aldeberan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.databinding.UserSettingsAddressRowBinding;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Address;
import com.example.aldeberan.structures.Product;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    private Context mContext;
    public List<Address> mData;
    private FragmentCommunication mCommunicator;
    AddressModel am = new AddressModel();


    public AddressAdapter(Context mContext, List<Address> mData, FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder{

        UserSettingsAddressRowBinding addressRowBinding;
        FragmentCommunication mCommunication;

        public AddressViewHolder(UserSettingsAddressRowBinding addressRowBinding, FragmentCommunication mCommunication) {
            super(addressRowBinding.getRoot());
            this.addressRowBinding = addressRowBinding;
            this.mCommunication = mCommunication;

            addressRowBinding.editAddBtn.setOnClickListener(view -> {
                Log.i("UPDATE", String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddID()));

                String isDefault = String.valueOf(mData.get(getAbsoluteAdapterPosition()).getIsDefault()) == "true" ? "1" : "0";
                //Show update screen
                mCommunication.respond(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddID()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddRecipient()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddContact()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddLine1()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddLine2()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddCode()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddCity()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddState()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddCountry()),
                        isDefault);
            });

            addressRowBinding.deleteAddBtn.setOnClickListener(view -> {
                Log.i("DELETE", String.valueOf(getAbsoluteAdapterPosition()));
                showDialog(Integer.parseInt(String.valueOf(getAbsoluteAdapterPosition())),
                        Integer.parseInt(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddID())));
            });
        }
    }

    @NonNull
    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UserSettingsAddressRowBinding addressRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.user_settings_address_row, parent, false);
        AddressAdapter.AddressViewHolder holder = new AddressAdapter.AddressViewHolder(addressRowBinding, mCommunicator);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {

        final Address a = mData.get(position);
        //holder.addressRowBinding.setAddress(a);
        holder.addressRowBinding.executePendingBindings();

        holder.addressRowBinding.addRecipientLbl.setText(mData.get(position).getAddRecipient());
        holder.addressRowBinding.addContactLbl.setText(mData.get(position).getAddContact());
        holder.addressRowBinding.addLine1n2Lbl.setText(mData.get(position).getAddLine1()+", "+mData.get(position).getAddLine2());
        holder.addressRowBinding.add3CLbl.setText(mData.get(position).getAddCode()+", "+mData.get(position).getAddCity()+", "+mData.get(position).getAddState()+", "+mData.get(position).getAddCountry());

        boolean prodAvailDisplay = mData.get(position).getIsDefault() == 1 ? true : false;
        if (prodAvailDisplay){
            holder.addressRowBinding.isDefaultLbl.setVisibility(View.VISIBLE);
        }
        else{
            holder.addressRowBinding.isDefaultLbl.setVisibility(View.INVISIBLE);
        }

        /*
        //Set data to pass to update fragment
        UserSettings updateProductFragment = new AdminPanelUpdateProductFragment();
        Bundle bundle=new Bundle();
        bundle.putString("prodName", mData.get(position).getProdName());
        bundle.putString("prodID", String.valueOf(mData.get(position).getProdID()));
        bundle.putString("prodSKU", mData.get(position).getProdSKU());
        bundle.putString("prodImg", mData.get(position).getProdImg());
        bundle.putString("prodStock", String.valueOf(mData.get(position).getProdStock()));
        bundle.putString("prodAvail", prodAvail);
        bundle.putString("prodPrice", String.valueOf(mData.get(position).getProdPrice()));
        updateProductFragment.setArguments(bundle);

         */
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String addID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry, String isDefault);
    }

    private void showDialog(int index, int addID){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to remove this address?");

        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).setNegativeButton("Confirm", (dialog, which) -> {
            am.deleteAddress(addID);
            dialog.dismiss();
            Toast.makeText(mContext, "Address deleted!", Toast.LENGTH_LONG).show();
            mData.remove(index);
            notifyItemRemoved(index);
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
