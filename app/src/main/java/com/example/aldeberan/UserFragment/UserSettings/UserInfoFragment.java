package com.example.aldeberan.UserFragment.UserSettings;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aldeberan.R;
import com.example.aldeberan.UserFragment.UserSettingFragment;
import com.example.aldeberan.storage.UserStorage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
Leong Kah Ming  1171100884
Ong Shuoh Chwen 1171102212
Yong Wen Kai    1171101664
Chong Wai Hou   1161104445

We work and discuss together for majority of the featuers in user setting.
*/

public class UserInfoFragment extends Fragment {

    View userInfoView;
    private DatabaseReference mDatabase;
    public int childrenCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userInfoView = inflater.inflate(R.layout.fragment_user_info, container, false);

        ImageView userImg = userInfoView.findViewById(R.id.userPic);
        TextView userName = userInfoView.findViewById(R.id.userName);
        TextView userEmail = userInfoView.findViewById(R.id.userEmail);
        TextView userID = userInfoView.findViewById(R.id.userID);
        Button backBtn = userInfoView.findViewById(R.id.backInfoBtn);
        backBtn.setOnClickListener(view -> {
            getActivity().finish();
        });

        UserStorage us = new UserStorage(getActivity());

        Glide.with(userInfoView).load(Uri.parse(us.getPhotoURL())).into(userImg);
        userName.setText(us.getName());
        userEmail.setText(us.getEmail());
        userID.setText(us.getID());

        Button reportBugBtn = userInfoView.findViewById(R.id.reportBugBtn);
        reportBugBtn.setOnClickListener(view -> showDialog(us.getID(), us.getName()));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("bug-report/"+us.getID())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            childrenCount = (int) dataSnapshot.getChildrenCount();
                            childrenCount++;
                        }
                        else{
                            childrenCount = 0;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return userInfoView;
    }

    private void showDialog(String userID, String userName){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText edittext = new EditText(getActivity());
        builder.setTitle("Bug Report");
        builder.setMessage("By " + userName);
        builder.setView(edittext);

        builder.setPositiveButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        }).setNegativeButton("Submit", (dialog, which) -> {
        });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view -> {
            if (edittext.getText().toString().matches("")){
                Toast.makeText(getActivity(), "Bug report cannot be empty!", Toast.LENGTH_LONG).show();
            }
            else{
                submitBugReport(userID, edittext.getText().toString());
                Toast.makeText(getActivity(), "Bug report submitted!", Toast.LENGTH_LONG).show();
                alert.dismiss();
            }
        });
    }

    public void submitBugReport(String userId, String report) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        mDatabase.child("bug-report").child(userId+"/"+childrenCount).child("BugReported").setValue(report);
        mDatabase.child("bug-report").child(userId+"/"+childrenCount).child("DateSubmitted").setValue(currentTime);
    }
}