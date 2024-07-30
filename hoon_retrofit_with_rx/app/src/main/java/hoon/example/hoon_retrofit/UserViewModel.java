package hoon.example.hoon_retrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> userMutableLiveData;
    private CompositeDisposable compositeDisposable;

    public UserViewModel() {
        userRepository = new UserRepository();
        userMutableLiveData = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<User> getUserLiveData() {
        return userMutableLiveData;
    }

    public void fetchUser() {
        compositeDisposable.add(
                userRepository
                        .getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> userMutableLiveData.setValue(user),
                                throwable -> {
                                    throwable.printStackTrace();
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
