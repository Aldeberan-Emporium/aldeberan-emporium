package com.example.aldeberan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.aldeberan.AdminFragment.AdminPanelAddProductFragment;
import com.example.aldeberan.AdminFragment.AdminPanelLoadProductFragment;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettings.UserAddAddressFragment;
import com.example.aldeberan.UserFragment.UserSettings.UserAddressFragment;


/*
Ong Shuoh Chwen 1171102212

Ong is responsible for this admin features which included add, update, and delete items.
*/

public class AdminPanelActivity extends AppCompatActivity {

    public MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        getSupportActionBar().setTitle("Admin Panel");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.admin_add, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuItem = item;
        switch (item.getItemId()) {
            case R.id.addProdBtn:
                AdminPanelAddProductFragment adminPanelAddProductFragment = new AdminPanelAddProductFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.adminFragmentView, adminPanelAddProductFragment)
                        .addToBackStack(null)
                        .commit();
                setTitleBar("Add New Product");
                menuItem.setVisible(false);
                return true;

            default:
                menuItem.setVisible(true);
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTitleBar(String title){
        getSupportActionBar().setTitle(title);
    }

    public void setMenuItem(boolean isVisible){
        menuItem.setVisible(isVisible);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            finish();
        }
        else {
            final Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.adminFragmentView);
            if (fragmentInFrame instanceof AdminPanelLoadProductFragment){
                super.onBackPressed();
                finish();
            }
            else{
                setTitleBar("Admin Panel");
                getSupportFragmentManager().popBackStack();
            }
        }
    }
}