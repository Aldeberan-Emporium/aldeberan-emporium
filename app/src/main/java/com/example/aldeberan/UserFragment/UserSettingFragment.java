package com.example.aldeberan.UserFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.Login;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.MapFragment.MapMainFragment;
import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserInfoFragment;
import com.example.aldeberan.models.MapModel;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class UserSettingFragment extends Fragment implements View.OnClickListener{

    private GoogleSignInClient mGoogleSignInClient;
    View userSettingsView;
    UserStorage us;

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
        Button orderBtn = userSettingsView.findViewById(R.id.orderBtn);
        orderBtn.setOnClickListener(this);
        Button loginBtn = userSettingsView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        Button logoutBtn = userSettingsView.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);

        us = new UserStorage(getActivity());
        if (us.getID() == ""){
            loginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        }
        else{
            loginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        }

        return userSettingsView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editAddBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new UserAddressFragment()).addToBackStack(null).commit();
                //((home_product) getActivity()).setActionBarTitle("Address Book");
                break;
            case R.id.infoBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new UserInfoFragment()).addToBackStack(null).commit();
                //((home_product) getActivity()).setActionBarTitle("User Info");
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
            case R.id.loginBtn:
                Intent loginIntent = new Intent(getActivity(), Login.class); //-->this line
                startActivity(loginIntent);
                break;
            case R.id.logoutBtn:
                us.logoutUser(getActivity());
                //Intent logoutIntent = new Intent(getActivity(), Login.class); //-->this line
                //startActivity(logoutIntent);
                break;

        }
    }
}
