package com.example.aldeberan.UserFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.Login;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.AdminFragment.AdminPanelLoadProductFragment;
import com.example.aldeberan.HomepageFragment;
import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserInfoFragment;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettingFragment extends Fragment implements View.OnClickListener{

    private GoogleSignInClient mGoogleSignInClient;
    View userSettingsView;
    UserStorage us;
    private FirebaseAuth mAuth;
    Button addressBtn;
    Button infoBtn;
    Button wishlistBtn;
    Button orderBtn;
    Button loginBtn;
    Button logoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userSettingsView = inflater.inflate(R.layout.fragment_user_setting, container, false);

        //(signOutInterface) getActivity();

        addressBtn = userSettingsView.findViewById(R.id.editAddBtn);
        addressBtn.setOnClickListener(this);
        infoBtn = userSettingsView.findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(this);
        wishlistBtn = userSettingsView.findViewById(R.id.wishlistBtn);
        wishlistBtn.setOnClickListener(this);
        orderBtn = userSettingsView.findViewById(R.id.orderBtn);
        orderBtn.setOnClickListener(this);
        loginBtn = userSettingsView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        logoutBtn = userSettingsView.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);

        us = new UserStorage(getActivity());
        switchSession();

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

                HomepageFragment homepageFragment= new HomepageFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, homepageFragment)
                        .addToBackStack(null)
                        .commit();

                ((Homepage) getActivity()).setBotNavView(0);
                break;
            case R.id.logoutBtn:
                us.logoutUser(getActivity());
                FirebaseAuth.getInstance().signOut();
                switchSession();
                break;

        }
    }

    public void switchSession() {
        if (us.getID() == ""){
            loginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
            addressBtn.setVisibility(View.GONE);
            infoBtn.setVisibility(View.GONE);
            wishlistBtn.setVisibility(View.GONE);
            orderBtn.setVisibility(View.GONE);
        }
        else{
            loginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
            addressBtn.setVisibility(View.VISIBLE);
            infoBtn.setVisibility(View.VISIBLE);
            wishlistBtn.setVisibility(View.VISIBLE);
            orderBtn.setVisibility(View.VISIBLE);
        }
    }
}
