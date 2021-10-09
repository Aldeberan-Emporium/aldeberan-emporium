package com.example.aldeberan.UserFragment.UserSettings;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.R;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.models.MapModel;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.storage.UserStorage;
import com.google.firebase.storage.StorageReference;

public class UserUpdateAddressFragment extends Fragment implements View.OnClickListener{

    Button submitBtn;
    Button resetBtn;
    ProgressBar onSubmitThrobber;
    View onSubmitView;
    public AlphaAnimation alphaAnimation;
    EditText addRecipient;
    EditText addContact;
    EditText addLine1;
    EditText addLine2;
    EditText addCode;
    EditText addCity;
    EditText addState;
    EditText addCountry;
    Switch isDefaultSwitch;

    MapModel mm;

    public String prevAddRecipient;
    public String prevAddContact;
    public String prevAddLine1;
    public String prevAddLine2;
    public String prevAddCode;
    public String prevAddCity;
    public String prevAddState;
    public String prevAddCountry;
    public int prevIsDefault;
    public int prevAddID;

    View userNewAddView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userNewAddView = inflater.inflate(R.layout.fragment_user_update_address, container, false);

        ((home_product) getActivity()).setActionBarTitle("Update Address");

        mm = new MapModel();

        //Buttons
        submitBtn = userNewAddView.findViewById(R.id.submitBtnUpdateAdd);
        submitBtn.setOnClickListener(this);
        resetBtn = userNewAddView.findViewById(R.id.resetBtnUpdateAdd);
        resetBtn.setOnClickListener(this);

        //On Submit
        onSubmitThrobber = userNewAddView.findViewById(R.id.onSubmitThrobberUpdateAdd);
        onSubmitView = userNewAddView.findViewById(R.id.onSubmitViewUpdateAdd);

        //Declare all inputs
        addRecipient = userNewAddView.findViewById(R.id.addRecipientUpdateAdd);
        addContact = userNewAddView.findViewById(R.id.addContactUpdateAdd);
        addLine1 = userNewAddView.findViewById(R.id.addLine1UpdateAdd);
        addLine2 = userNewAddView.findViewById(R.id.addLine2UpdateAdd);
        addCode = userNewAddView.findViewById(R.id.addCodeUpdateAdd);
        addCity = userNewAddView.findViewById(R.id.addCityUpdateAdd);
        addState = userNewAddView.findViewById(R.id.addStateUpdateAdd);
        addCountry = userNewAddView.findViewById(R.id.addCountryUpdateAdd);
        isDefaultSwitch = userNewAddView.findViewById(R.id.isDefaultUpdateAdd);

        retrieveData();

        addRecipient.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addContact.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addLine1.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addLine2.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addCity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addState.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        addCountry.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        return userNewAddView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submitBtnUpdateAdd) {
            String recipient = addRecipient.getText().toString();
            String contact = addContact.getText().toString();
            String line1 = addLine1.getText().toString();
            String line2 = addLine2.getText().toString();
            String code = addCode.getText().toString();
            String city = addCity.getText().toString();
            String state = addState.getText().toString();
            String country = addCountry.getText().toString();
            int isDefault = isDefaultSwitch.isChecked() ? 1 : 0;

            String address = concatAddress(line1, line2, code, city, state, country);

            if (recipient != null && contact != null && line1 != null && code != null && city != null && state != null && country != null) {
                mm.isValidAddress(address, (status, msg) -> {
                    if (status == 500) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    } else {
                        submitBtn.setVisibility(View.GONE);
                        resetBtn.setVisibility(View.GONE);
                        onSubmitThrobber.setVisibility(View.VISIBLE);
                        onSubmitView.setVisibility(View.VISIBLE);
                        addRecipient.setEnabled(false);
                        addContact.setEnabled(false);
                        addLine1.setEnabled(false);
                        addLine2.setEnabled(false);
                        addCode.setEnabled(false);
                        addCity.setEnabled(false);
                        addState.setEnabled(false);
                        addCountry.setEnabled(false);
                        isDefaultSwitch.setEnabled(false);

                        UserStorage us = new UserStorage(getActivity());
                        String userID = us.getID();

                        AddressModel am = new AddressModel();
                        am.updateAddress(prevAddID, userID, recipient, contact, line1, line2, code, city, state, country, isDefault);
                        onSubmitAnim();
                        //Redirect back to load products fragment
                        UserAddressFragment addressFragment = new UserAddressFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, addressFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
            } else {
                Toast.makeText(getContext(), "All inputs are required!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            retrieveData();
        }
    }

    public String concatAddress(String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry){
        String address = addLine1+addLine2+addCode+addCity+addState+addCountry;
        return address;
    }

    //Hide keyboard when out of focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) this.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void onSubmitAnim() {
        //On load throbber fade out
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        onSubmitThrobber.startAnimation(alphaAnimation);
        onSubmitView.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onSubmitThrobber.setVisibility(View.VISIBLE);
                onSubmitView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onSubmitThrobber.setVisibility(View.GONE);
                onSubmitView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void retrieveData(){
        prevAddID = Integer.parseInt(getArguments().getString("addID"));
        prevAddRecipient = getArguments().getString("addRecipient");
        prevAddContact = getArguments().getString("addContact");
        prevAddLine1 = getArguments().getString("addLine1");
        prevAddLine2 = getArguments().getString("addLine2");
        prevAddCode = getArguments().getString("addCode");
        prevAddCity = getArguments().getString("addCity");
        prevAddState = getArguments().getString("addState");
        prevAddCountry = getArguments().getString("addCountry");
        prevIsDefault = getArguments().getInt("isDefault");

        addRecipient.setText(prevAddRecipient);
        addContact.setText(prevAddContact);
        addLine1.setText(prevAddLine1);
        addLine2.setText(prevAddLine2);
        addCode.setText(prevAddCode);
        addCity.setText(prevAddCity);
        addState.setText(prevAddState);
        addCountry.setText(prevAddCountry);

        Log.i("ISDEFAULT", String.valueOf(prevIsDefault));

        boolean isChecked = prevIsDefault == 1 ? true : false;
        isDefaultSwitch.setChecked(isChecked);
    }
}