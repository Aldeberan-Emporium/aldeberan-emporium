package com.example.aldeberan;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aldeberan.Adapter.AllProductAdapter;
import com.example.aldeberan.UserFragment.homeProductFragment;
import com.example.aldeberan.models.ProductModel;
import com.example.aldeberan.structures.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    View allProdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        allProdView = inflater.inflate(R.layout.cart, container, false);

        return allProdView;
    }
}