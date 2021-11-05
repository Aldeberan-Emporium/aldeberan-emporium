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
import com.example.aldeberan.UserFragment.UserSettings.UserUpdateAddressFragment;
import com.example.aldeberan.databinding.UserSettingsAddressRowBinding;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Address;
import com.example.aldeberan.structures.Product;

import java.util.List;

/*
Leong Kah Ming  1171100884
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664
Chong Wai Hou   1161104445

We work and discuss together for majority of the featuers in user setting.
*/

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
                        String.valueOf(mData.get(getAbsoluteAdapterPosition()).getIsDefault()));
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
        holder.addressRowBinding.executePendingBindings();

        holder.addressRowBinding.addRecipientLbl.setText(mData.get(position).getAddRecipient());
        holder.addressRowBinding.addContactLbl.setText("60"+mData.get(position).getAddContact());
        holder.addressRowBinding.addLine1n2Lbl.setText(mData.get(position).getAddLine1()+", "+mData.get(position).getAddLine2());
        holder.addressRowBinding.add3CLbl.setText(mData.get(position).getAddCode()+", "+mData.get(position).getAddCity()+", "+mData.get(position).getAddState()+", "+mData.get(position).getAddCountry());

        boolean prodAvailDisplay = mData.get(position).getIsDefault() == 1 ? true : false;
        if (prodAvailDisplay){
            holder.addressRowBinding.isDefaultLbl.setVisibility(View.VISIBLE);
        }
        else{
            holder.addressRowBinding.isDefaultLbl.setVisibility(View.INVISIBLE);
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
