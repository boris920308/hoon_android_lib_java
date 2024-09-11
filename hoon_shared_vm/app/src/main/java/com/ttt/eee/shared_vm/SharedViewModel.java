package com.ttt.eee.shared_vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> _sharedValue = new MutableLiveData<>();
    public LiveData<String> sharedValue = _sharedValue;

    public SharedViewModel() {
        _sharedValue.setValue("default");
    }

    public void setApple() {
        _sharedValue.setValue("Apple");
    }

    public void setBanana() {
        _sharedValue.setValue("Banana");
    }

    public void setCherry() {
        _sharedValue.setValue("Cherry");
    }


}
