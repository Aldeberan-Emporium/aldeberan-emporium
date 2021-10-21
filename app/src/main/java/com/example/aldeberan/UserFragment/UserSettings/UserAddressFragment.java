package com.example.aldeberan.UserFragment.UserSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.Adapter.AddressAdapter;
import com.example.aldeberan.AdminFragment.AdminPanelUpdateProductFragment;
import com.example.aldeberan.R;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.storage.UserStorage;
import com.example.aldeberan.structures.Address;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserAddressFragment extends Fragment implements View.OnClickListener{

    public List<Address> addressList;
    public RecyclerView recyclerView;
    public AddressAdapter adapter;
    public TextView noProdLbl;
    public ProgressBar onLoadThrobber;
    public Handler mHandler;
    public AlphaAnimation alphaAnimation;
    View userAddressView;
    //Homepage homepage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userAddressView = inflater.inflate(R.layout.fragment_user_address_book, container, false);

        //((home_product) getActivity()).setActionBarTitle("Address Book");

        //getActivity().onBackPressed();

        //(homepage).setOnBackPressedListener(new BaseBackPressedListener(getActivity());

        addressList = new ArrayList<>();
        recyclerView = userAddressView.findViewById(R.id.addressRecyclerView);
        noProdLbl = userAddressView.findViewById(R.id.noAddressLbl);
        onLoadThrobber = userAddressView.findViewById(R.id.onLoadThrobberAddBook);
        mHandler = new Handler();

        FloatingActionButton newAddBtn = userAddressView.findViewById(R.id.newAddressBtn);
        newAddBtn.setOnClickListener(this);

        ConstructRecyclerView();
        SwipeRefreshLayout pullToRefresh = userAddressView.findViewById(R.id.pullToRefreshAddress);

        pullToRefresh.setOnRefreshListener(() -> {
            ConstructRecyclerView();
            pullToRefresh.setRefreshing(false);
        });

        return userAddressView;
    }

    @Override
    public void onClick(View view) {
        UserAddAddressFragment newAddressFragment = new UserAddAddressFragment();
        if (getActivity().getClass().getSimpleName().contains("AddressSelectionToBook")){
            //Redirect to update product fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.addressFragmentView, newAddressFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else{
            //Redirect to update product fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newAddressFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void onLoadThrobber() {
        //On load throbber fade out
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        onLoadThrobber.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onLoadThrobber.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onLoadThrobber.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void ConstructRecyclerView(){
        AddressModel am = new AddressModel();
        UserStorage us = new UserStorage(getActivity());
        onLoadThrobber();
        try {
            am.readAddressByUser(us.getID(), (response) -> {
                addressList = response;
                if (addressList.isEmpty()){
                    noProdLbl.setVisibility(View.VISIBLE);
                }
                else{
                    noProdLbl.setVisibility(View.GONE);
                    PutDataIntoRecyclerView(response);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AddressAdapter.FragmentCommunication communication= (addID, addRecipient, addContact, addLine1, addLine2, addCode, addCity, addState, addCountry, isDefault) -> {
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

        if (getActivity().getClass().getSimpleName() == "AddressSelectionToBook"){
            //Redirect to update product fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.addressFragmentView, updateAddressFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else{
            //Redirect to update product fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, updateAddressFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

    private void PutDataIntoRecyclerView(List<Address> addressList){
        adapter = new AddressAdapter(getContext(), addressList, communication);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.i("PLOPE", String.valueOf(addressList));
    }

}