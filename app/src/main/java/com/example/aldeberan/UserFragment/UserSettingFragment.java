package com.example.aldeberan.UserFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.MapFragment.MapMainFragment;
import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserInfoFragment;
import com.example.aldeberan.models.MapModel;

public class UserSettingFragment extends Fragment implements View.OnClickListener{

    View userSettingsView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userSettingsView = inflater.inflate(R.layout.fragment_user_setting, container, false);

        Button addressBtn = userSettingsView.findViewById(R.id.editAddBtn);
        addressBtn.setOnClickListener(this);
        Button infoBtn = userSettingsView.findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(this);
        Button wishlistBtn = userSettingsView.findViewById(R.id.wishlistBtn);
        wishlistBtn.setOnClickListener(this);

        return userSettingsView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editAddBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserAddressFragment()).commit();
                ((home_product) getActivity()).setActionBarTitle("Address Book");
                break;
            case R.id.infoBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserInfoFragment()).commit();
                ((home_product) getActivity()).setActionBarTitle("User Info");
                break;
            case R.id.wishlistBtn:
                //Pass in Order ID and Order Address
                //String address = line1+line2+code+city+state+country; //from order_address
                /*
                //Get latlng first before entering
                MapModel mm = new MapModel();
                mm.getLatLng(address, (lat, lng) -> {
                    //Insert here
                });
                */
                //Put remaining lines into mm.getLatLng
                Intent mapIntent = new Intent(getActivity(), MapsActivity.class); //-->this line
                //mapIntent.putExtra("orderID", orderID); //-->this line
                //mapIntent.putExtra("lat", lat); //-->this line
                //mapIntent.putExtra("lng", lng); //-->this line
                getActivity().startActivity(mapIntent); //-->this line
                ((home_product) getActivity()).setActionBarTitle("My Wishlist");
                break;
        }
    }
}
