package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aldeberan.Adapter.AddressAdapter;
import com.example.aldeberan.Adapter.AddressSelectionAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserUpdateAddressFragment;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressSelection extends AppCompatActivity {

    public List<Address> addressList;
    public RecyclerView recyclerView;
    public AddressSelectionAdapter adapter;

    UserStorage us;
    OrderStorage os;
    AddressModel am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection);

        getSupportActionBar().setTitle("Address Book");

        recyclerView = findViewById(R.id.addressSelectBox);
        addressList = new ArrayList<>();
        us = new UserStorage(this);
        os = new OrderStorage(this);
        am = new AddressModel();

        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        try {
            am.readAddressByUser("XHXDzxi0ZMM4I8dEwLYoTNIGkb93", (response) -> {
                addressList = response;
                PutDataIntoRecyclerView(response);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AddressSelectionAdapter.FragmentCommunication communication= (addID, addRecipient, addContact, addLine1, addLine2, addCode, addCity, addState, addCountry, isDefault) -> {
        UserUpdateAddressFragment updateAddressFragment = new UserUpdateAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("addID", addID);
        bundle.putString("addRecipient", addRecipient);
        bundle.putString("addContact", addContact);
        bundle.putString("addLine1", addLine1);
        bundle.putString("addLine2", addLine2);
        bundle.putString("addCode", addCode);
        bundle.putString("addCity", addCity);
        bundle.putString("addState", addState);
        bundle.putString("addCountry", addCountry);
        bundle.putInt("isDefault", Integer.parseInt(isDefault));
        updateAddressFragment.setArguments(bundle);
    };

    private void PutDataIntoRecyclerView(List<Address> addressList){
        adapter = new AddressSelectionAdapter(this, addressList, communication);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(addressList));
    }
}