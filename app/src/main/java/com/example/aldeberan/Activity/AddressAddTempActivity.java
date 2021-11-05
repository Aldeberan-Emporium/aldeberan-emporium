package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.aldeberan.R;
import com.example.aldeberan.models.AddressModel;
import com.example.aldeberan.models.MapModel;
import com.example.aldeberan.storage.OrderStorage;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.material.snackbar.Snackbar;

/*
Leong Kah Ming  1171100884
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664
Chong Wai Hou   1161104445

They work and discuss together for majority of the featuers in user setting.
*/

public class AddressAddTempActivity extends AppCompatActivity implements View.OnClickListener {

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

    MapModel mm;
    OrderStorage os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add_temp);

        getSupportActionBar().setTitle("Add Temporary Delivery Address");

        mm = new MapModel();
        os = new OrderStorage(this);

        //Submit Button
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        //On Submit
        onSubmitThrobber = findViewById(R.id.onSubmitThrobber2);
        onSubmitView = findViewById(R.id.onSubmitView2);

        //Declare all inputs
        addRecipient = findViewById(R.id.addTempRecipient);
        addContact = findViewById(R.id.addTempContact);
        addLine1 = findViewById(R.id.addTempLine1);
        addLine2 = findViewById(R.id.addTempLine2);
        addCode = findViewById(R.id.addTempCode);
        addCity = findViewById(R.id.addTempCity);
        addState = findViewById(R.id.addTempState);
        addCountry = findViewById(R.id.addTempCountry);

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
        Switch saveAddressComp = findViewById(R.id.saveAddress);
        boolean isSaveAddress = saveAddressComp.isChecked() ? true : false;

        String address = concatAddress(line1, line2, code, city, state, country);

        if (!TextUtils.isEmpty(recipient) && !TextUtils.isEmpty(contact) && !TextUtils.isEmpty(line1) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(country)){
            mm.isValidAddress(address, (status, msg) -> {
                if (status == 500){
                    onReverseSubmitAnim();
                    showSnackbar(msg);
                    onSubmitAnim();
                }
                else{
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
                    saveAddressComp.setEnabled(false);

                    //If the user wanted to save the temporary address into their address book
                    if (isSaveAddress){
                        UserStorage us = new UserStorage(this);
                        String userID = us.getID();
                        AddressModel am = new AddressModel();
                        am.addAddress(userID, recipient, contact, line1, line2, code, city, state, country, 0);
                        os.saveTemp(false);
                    }
                    else{
                        os.saveTemp(true);
                    }

                    //Save the temporary address to SharedPrefs
                    os.saveAddress(recipient, contact, line1, line2, code, city, state, country);

                    onSubmitAnim();
                    onBackPressed();
                }
            });
        }
        else{
            showSnackbar("All inputs are required!");
        }
    }

    public void showSnackbar(String string){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public String concatAddress(String addLine1, String addLine2, String addCode, String addCity, String addState, String addCountry){
        String address = addLine1+","+addLine2+","+addCode+addCity+addState+addCountry;
        return address;
    }

    //Hide keyboard when out of focus
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
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
                onSubmitThrobber.setVisibility(View.GONE);
                onSubmitView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent addressIntent = new Intent(AddressAddTempActivity.this, AddressSelection.class);
        startActivity(addressIntent);
    }
}