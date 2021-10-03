package com.example.aldeberan;

import static android.provider.Settings.NameValueTable.VALUE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import org.w3c.dom.Text;

public class home_product extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private int requestCode;
    private int resultCode;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sh = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBarTitle("Home");

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.current_user);
        navigationView.setNavigationItemSelectedListener(this);

        String s1 = sh.getString("name", "");

        if (navUsername != null){
            navUsername.setText(s1);
        }else{
            System.out.println("textview object error");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeProductFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home_product);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeProductFragment()).commit();
                setActionBarTitle("Home");
                break;
            case R.id.nav_order_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new orderHistoryFragment()).commit();
                setActionBarTitle("Order History");
                break;
            case R.id.nav_wallet:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new tempFragment()).commit();
                setActionBarTitle("E-Wallet");
                break;
            case R.id.nav_user_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new userSettingFragment()).commit();
                setActionBarTitle("User Settings");
                break;
            case R.id.nav_login:
                finish();
                Intent intent = new Intent(home_product.this, Login.class);
                startActivity(intent);
                break;
            case R.id.nav_admin_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminPanelAddProductFragment()).commit();
                break;
            case R.id.nav_admin_load:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminPanelLoadProductFragment()).commit();
                break;

            /*
            case R.id.nav_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                TextView cuser = findViewById(R.id.current_user);
                cuser.setText("Please Sign in");
                break;

            */
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}