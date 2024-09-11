package com.ttt.eee.shared_vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

    public static ViewModelProvider.Factory factory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(SharedViewModel.class)) {
                    return (T) new SharedViewModel();
                }
                throw new IllegalStateException("wrong viewmodel class");
            }
        };
    }

}
