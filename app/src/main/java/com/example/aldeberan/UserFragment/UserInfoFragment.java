package com.example.aldeberan.UserFragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.storage.UserStorage;

public class UserInfoFragment extends Fragment {

    View userInfoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userInfoView = inflater.inflate(R.layout.fragment_user_info, container, false);

        ImageView userImg = userInfoView.findViewById(R.id.userPic);
        TextView userName = userInfoView.findViewById(R.id.userName);
        TextView userEmail = userInfoView.findViewById(R.id.userEmail);
        TextView userJoinedDate = userInfoView.findViewById(R.id.userJoinedDate);
        Button backBtn = userInfoView.findViewById(R.id.backInfoBtn);
        backBtn.setOnClickListener(view -> getActivity().onBackPressed());

        UserStorage us = new UserStorage();


        Glide.with(userInfoView).load(Uri.parse(us.getPhotoURL())).into(userImg);

        return userInfoView;
    }
}