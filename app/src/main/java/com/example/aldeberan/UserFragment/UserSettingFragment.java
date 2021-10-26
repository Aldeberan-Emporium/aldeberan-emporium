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
import com.example.aldeberan.Activity.OrderActivity;
import com.example.aldeberan.Activity.WishlistActivity;
import com.example.aldeberan.AdminFragment.AdminPanelLoadProductFragment;
import com.example.aldeberan.HomepageFragment;
import com.example.aldeberan.MapFragment.MapsActivity;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserInfoFragment;
import com.example.aldeberan.storage.UserStorage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    Button adminBtn;

    //Login login;

    public GoogleSignInClient gsignin;

    String temp_name = "aldeberan.emporium@gmail.com";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userSettingsView = inflater.inflate(R.layout.fragment_user_setting, container, false);

        //(signOutInterface) getActivity();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.def_log_id)).requestEmail().build();
        gsignin = GoogleSignIn.getClient(getActivity(), gso);

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
        adminBtn = userSettingsView.findViewById(R.id.adminBtn);
        adminBtn.setOnClickListener(this);

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
                //Toast.makeText(getActivity(), "lmao", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
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
                switchSession();
                break;
            case R.id.adminBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2, new AdminPanelLoadProductFragment()).addToBackStack(null).commit();
                break;
            case R.id.logoutBtn:
                //mGoogleSignInClient.signOut();
                us.logoutUser(getActivity());
                gsignin.signOut();
                FirebaseAuth.getInstance().signOut();
                switchSession();
                break;
            case R.id.orderBtn:
                Intent hist = new Intent(getActivity(), OrderActivity.class);
                startActivity(hist);

        }
    }

    public void switchSession() {
        //if(temp_name.equals(us.getEmail())) {
        //  Toast.makeText(getActivity(), "betul", Toast.LENGTH_SHORT).show();
        //adminBtn.setVisibility(View.VISIBLE);
    //}

        if (us.getID() == ""){
            loginBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
            addressBtn.setVisibility(View.GONE);
            infoBtn.setVisibility(View.GONE);
            wishlistBtn.setVisibility(View.GONE);
            orderBtn.setVisibility(View.GONE);
            adminBtn.setVisibility(View.GONE);
        } else{
            loginBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
            addressBtn.setVisibility(View.VISIBLE);
            infoBtn.setVisibility(View.VISIBLE);
            wishlistBtn.setVisibility(View.VISIBLE);
            orderBtn.setVisibility(View.VISIBLE);
            adminBtn.setVisibility(View.GONE);
            if(temp_name.equals(us.getEmail())) {
                Toast.makeText(getActivity(), "betul", Toast.LENGTH_SHORT).show();
                adminBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}
