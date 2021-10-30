package com.example.aldeberan.UserFragment.UserSettings;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.aldeberan.Activity.AddressSelectionToBook;
import com.example.aldeberan.Activity.Homepage;
import com.example.aldeberan.Activity.MainActivity;
import com.example.aldeberan.Activity.UserAddressBookActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.models.MapModel;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.material.snackbar.Snackbar;

public class UserAddAddressFragment extends Fragment implements View.OnClickListener{

    Button submitBtn;
    Button cancelBtn;
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
    View mView;

    MapModel mm;

    View userNewAddView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userNewAddView = inflater.inflate(R.layout.fragment_user_add_address, container, false);

        mm = new MapModel();

        //Submit Button
        submitBtn = userNewAddView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        //On Submit
        onSubmitThrobber = userNewAddView.findViewById(R.id.onSubmitThrobber2);
        onSubmitThrobber.setVisibility(View.GONE);
        onSubmitView = userNewAddView.findViewById(R.id.onSubmitView2);
        onSubmitView.setVisibility(View.GONE);

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

        cancelBtn = userNewAddView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(view -> {
            //Redirect back to load products fragment
            if (getActivity().getClass().getSimpleName().contains("AddressSelectionToBook")){
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.addressFragmentView, new UserAddressFragment()).addToBackStack(null).commit();
                ((AddressSelectionToBook) getActivity()).setTitleBar("Address Book");
            }
            else{
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userAddressBookFragmentView, new UserAddressFragment()).addToBackStack(null).commit();
                ((UserAddressBookActivity) getActivity()).setTitleBar("Address Book");
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

        String address = concatAddress(line1, line2, code, city, state, country);

        if (!TextUtils.isEmpty(recipient) && !TextUtils.isEmpty(contact) && !TextUtils.isEmpty(line1) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(country)){
            mm.isValidAddress(address, (status, msg) -> {
                if (status == 500){
                    onReverseSubmitAnim();
                    showSnackbar(msg);
                    onSubmitAnim();
                }
                else{
                    onSubmitAnim();
                    submitBtn.setVisibility(GONE);
                    //onSubmitThrobber.setVisibility(View.VISIBLE);
                    //onSubmitView.setVisibility(View.VISIBLE);
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
                    //onSubmitAnim();

                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //Code for test another activity pages.
                            if (getActivity().getClass().getSimpleName().contains("AddressSelectionToBook")){
                                getActivity().getSupportFragmentManager().popBackStack();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.addressFragmentView, new UserAddressFragment()).addToBackStack(null).commit();
                                ((AddressSelectionToBook) getActivity()).setTitleBar("Address Book");
                            }
                            else{
                                getActivity().getSupportFragmentManager().popBackStack();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.userAddressBookFragmentView, new UserAddressFragment()).addToBackStack(null).commit();
                                ((UserAddressBookActivity) getActivity()).setTitleBar("Address Book");
                            }
                        }

                    }, 3000L);
                    //Redirect back to load products fragment


                }
            });
        }
        else{
            showSnackbar("All inputs are required!");
        }
    }

    public void showSnackbar(String string){
        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG);
        snackBar.show();
    }

    public String concatAddress(String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry){
        String address = addLine1+","+addLine2+","+addCode+addCity+addState+addCountry;
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
                onSubmitThrobber.setVisibility(GONE);
                onSubmitView.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void onReverseSubmitAnim() {
        //On load throbber fade out
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
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
                onSubmitThrobber.setVisibility(GONE);
                onSubmitView.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}