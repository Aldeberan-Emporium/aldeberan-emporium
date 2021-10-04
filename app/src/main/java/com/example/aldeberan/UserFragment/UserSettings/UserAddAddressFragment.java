package com.example.aldeberan.UserFragment.UserSettings;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aldeberan.Activity.home_product;
import com.example.aldeberan.R;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.storage.UserStorage;

public class UserAddAddressFragment extends Fragment implements View.OnClickListener{

    Button submitBtn;
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

    View userNewAddView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userNewAddView = inflater.inflate(R.layout.fragment_user_add_address, container, false);

        ((home_product) getActivity()).setActionBarTitle("Add New Address");

        //Submit Button
        submitBtn = userNewAddView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        //On Submit
        onSubmitThrobber = userNewAddView.findViewById(R.id.onSubmitThrobber2);
        onSubmitView = userNewAddView.findViewById(R.id.onSubmitView2);

        //Declare all inputs
        addRecipient = userNewAddView.findViewById(R.id.addRecipient);
        addContact = userNewAddView.findViewById(R.id.addContact);
        addLine1 = userNewAddView.findViewById(R.id.addLine1);
        addLine2 = userNewAddView.findViewById(R.id.addLine2);
        addCode = userNewAddView.findViewById(R.id.addCode);
        addCity = userNewAddView.findViewById(R.id.addCity);
        addState = userNewAddView.findViewById(R.id.addState);
        addCountry = userNewAddView.findViewById(R.id.addCountry);

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
        String recipient = addRecipient.getText().toString();
        String contact = addContact.getText().toString();
        String line1 = addLine1.getText().toString();
        String line2 = addLine2.getText().toString();
        String code = addCode.getText().toString();
        String city = addCity.getText().toString();
        String state = addState.getText().toString();
        String country = addCountry.getText().toString();
        Switch isDefaultComp = userNewAddView.findViewById(R.id.isDefault);
        int isDefault = isDefaultComp.isChecked() ? 1 : 0;

        if (recipient != null && contact != null && line1 != null && code != null && city != null && state != null && country != null){
            submitBtn.setVisibility(View.GONE);
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
            isDefaultComp.setEnabled(false);
            
            UserStorage us = new UserStorage(getActivity());
            String userID = us.getID();

            AddressModel am = new AddressModel();
            am.addAddress(userID, recipient, contact, line1, line2, code, city, state, country, isDefault);
            onSubmitAnim();
            //Redirect back to load products fragment
            UserAddressFragment addressFragment = new UserAddressFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, addressFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else{
            Toast.makeText(getContext(), "All inputs are required!", Toast.LENGTH_LONG).show();
        }
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
}