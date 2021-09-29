package com.example.aldeberan.ui.orderHistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class orderHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public orderHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is orderHistory fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}