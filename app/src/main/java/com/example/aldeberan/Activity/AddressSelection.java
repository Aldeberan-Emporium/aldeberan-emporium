package com.example.aldeberan.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.aldeberan.Adapter.AddressSelectionAdapter;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserUpdateAddressFragment;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Address;

import java.util.ArrayList;
import java.util.List;

/*
Leong Kah Ming  1171100884
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664
Chong Wai Hou   1161104445

They work and discuss together for majority of the featuers in user setting.
*/

public class AddressSelection extends AppCompatActivity {

    public List<Address> addressList;
    public RecyclerView recyclerView;
    public AddressSelectionAdapter adapter;
    Button addTempAddressBtn;

    UserStorage us;
    OrderStorage os;
    AddressModel am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection);

        getSupportActionBar().setTitle("Delivery Address Book");

        recyclerView = findViewById(R.id.addressSelectBox);
        addressList = new ArrayList<>();
        us = new UserStorage(this);
        os = new OrderStorage(this);
        am = new AddressModel();

        addTempAddressBtn = findViewById(R.id.addTempAddressBtn);
        addTempAddressBtn.setOnClickListener(view -> {
            if (os.getIsSaveTemp()){
                showDialog();
            }
            else{
                finish();
                Intent addTempIntent = new Intent(AddressSelection.this, AddressAddTempActivity.class);
                startActivity(addTempIntent);
            }
        });

        ConstructRecyclerView();
    }

    private void ConstructRecyclerView(){
        try {
            am.readAddressByUser(us.getID(), (response) -> {
                addressList = response;
                if (os.getIsSaveTemp()){
                    Address address = new Address();
                    address.setAddID(-1);
                    address.setAddRecipient(os.getRecipient());
                    address.setAddContact(os.getContact());
                    address.setAddLine1(os.getLine1());
                    address.setAddLine2(os.getLine2());
                    address.setAddCode(os.getCode());
                    address.setAddCity(os.getCity());
                    address.setAddState(os.getState());
                    address.setAddCountry(os.getCountry());
                    address.setIsDefault(-1); //-1 for temporary address
                    addressList.add(0, address);
                }
                PutDataIntoRecyclerView(addressList);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.address_selection, menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addressBookBtn:
                Intent addBookIntent = new Intent(AddressSelection.this, AddressSelectionToBook.class);
                startActivity(addBookIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning");
        builder.setMessage("The existing temporary address will be replaced! Do you want to continue?");

        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).setNegativeButton("Confirm", (dialog, which) -> {
            finish();
            Intent addTempIntent = new Intent(AddressSelection.this, AddressAddTempActivity.class);
            startActivity(addTempIntent);
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent checkoutIntent = new Intent(AddressSelection.this, checkoutActivity.class);
        startActivity(checkoutIntent);
    }
}