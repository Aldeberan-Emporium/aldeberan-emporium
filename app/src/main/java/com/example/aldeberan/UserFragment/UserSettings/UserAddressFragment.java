package com.example.aldeberan.UserFragment.UserSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldeberan.AdminFragment.AdminPanelLoadProductFragment;
import com.example.aldeberan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserAddressFragment extends Fragment implements View.OnClickListener{

    View userAddressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userAddressView = inflater.inflate(R.layout.fragment_user_address_book, container, false);

        FloatingActionButton newAddBtn = userAddressView.findViewById(R.id.newAddressBtn);
        newAddBtn.setOnClickListener(this);

        return userAddressView;
    }

    @Override
    public void onClick(View view) {
        //Redirect back to load products fragment
        UserAddAddressFragment newAddressFragment = new UserAddAddressFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newAddressFragment)
                .addToBackStack(null)
                .commit();
    }
}