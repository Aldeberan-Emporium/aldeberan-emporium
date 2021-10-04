package com.example.aldeberan.UserFragment.UserSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldeberan.R;

public class UserAddressFragment extends Fragment {

    View userAddressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userAddressView = inflater.inflate(R.layout.fragment_user_address_book, container, false);



        return userAddressView;
    }
}