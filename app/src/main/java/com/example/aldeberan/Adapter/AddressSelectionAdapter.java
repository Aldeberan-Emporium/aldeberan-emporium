package com.example.aldeberan.Adapter;

import android.app.Activity;
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

import com.example.aldeberan.Activity.AddressSelection;
import com.example.aldeberan.Activity.checkoutActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserUpdateAddressFragment;
import com.example.aldeberan.databinding.AddressSelectionRowBinding;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.structures.Address;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/*
Leong Kah Ming  1171100884
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664
Chong Wai Hou   1161104445

We work and discuss together for majority of the featuers in user setting.
*/

public class AddressSelectionAdapter extends RecyclerView.Adapter<AddressSelectionAdapter.AddressViewHolder>{

    private Context mContext;
    public List<Address> mData;
    private FragmentCommunication mCommunicator;
    OrderStorage os;

    public AddressSelectionAdapter(Context mContext, List<Address> mData, FragmentCommunication mCommunicator) {
        this.mContext = mContext;
        this.mData = mData;
        this.mCommunicator = mCommunicator;
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder{

        AddressSelectionRowBinding addressRowBinding;
        FragmentCommunication mCommunication;

        public AddressViewHolder(AddressSelectionRowBinding addressRowBinding, FragmentCommunication mCommunication) {
            super(addressRowBinding.getRoot());
            this.addressRowBinding = addressRowBinding;
            this.mCommunication = mCommunication;

            addressRowBinding.addressSelectBtn.setOnClickListener(view -> {
                showDialog(String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddRecipient()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddContact()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddLine1()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddLine2()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddCode()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddCity()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddState()),
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getAddCountry()));
            });
        }
    }

    @NonNull
    @Override
    public AddressSelectionAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AddressSelectionRowBinding addressRowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.address_selection_row, parent, false);
        AddressSelectionAdapter.AddressViewHolder holder = new AddressSelectionAdapter.AddressViewHolder(addressRowBinding, mCommunicator);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {

        Address a = mData.get(position);
        holder.addressRowBinding.setAddress(a);
        holder.addressRowBinding.executePendingBindings();

        holder.addressRowBinding.addRecipientLbl.setText(mData.get(position).getAddRecipient());
        holder.addressRowBinding.addContactLbl.setText("60"+mData.get(position).getAddContact());
        holder.addressRowBinding.addLine1n2Lbl.setText(mData.get(position).getAddLine1()+", "+mData.get(position).getAddLine2());
        holder.addressRowBinding.add3CLbl.setText(mData.get(position).getAddCode()+", "+mData.get(position).getAddCity()+", "+mData.get(position).getAddState()+", "+mData.get(position).getAddCountry());

        boolean prodAvailDisplay = mData.get(position).getIsDefault() == 0 ? true : false;
        if (prodAvailDisplay){
            holder.addressRowBinding.isDefaultLbl.setVisibility(View.INVISIBLE);
        }
        else{
            if (mData.get(position).getIsDefault() == -1){
                holder.addressRowBinding.isDefaultLbl.setText("TEMPORARY");
            }
            holder.addressRowBinding.isDefaultLbl.setVisibility(View.VISIBLE);
        }


        //Set data to pass to update fragment
        UserUpdateAddressFragment updateAddressFragment = new UserUpdateAddressFragment();
        Bundle bundle=new Bundle();
        bundle.putString("addID", String.valueOf(mData.get(position).getAddID()));
        bundle.putString("addRecipient", String.valueOf(mData.get(position).getAddRecipient()));
        bundle.putString("addContact", String.valueOf(mData.get(position).getAddContact()));
        bundle.putString("addLine1", String.valueOf(mData.get(position).getAddLine1()));
        bundle.putString("addLine2", String.valueOf(mData.get(position).getAddLine2()));
        bundle.putString("addCode", String.valueOf(mData.get(position).getAddCode()));
        bundle.putString("addCity", String.valueOf(mData.get(position).getAddCity()));
        bundle.putString("addState", String.valueOf(mData.get(position).getAddState()));
        bundle.putString("addCountry", String.valueOf(mData.get(position).getAddCountry()));
        bundle.putString("isDefault", String.valueOf(mData.get(position).getIsDefault()));
        updateAddressFragment.setArguments(bundle);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface FragmentCommunication {
        void respond(String addID, String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry, String isDefault);
    }

    private void showDialog(String addRecipient, String addContact, String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to select this address?");

        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).setNegativeButton("Confirm", (dialog, which) -> {
            os = new OrderStorage(mContext);
            os.saveAddress(addRecipient, addContact, addLine1, addLine2, addCode, addCity, addState, addCountry);
            ((AddressSelection)mContext).finish();
            Intent intent = new Intent(mContext, checkoutActivity.class);
            mContext.startActivity(intent);
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
