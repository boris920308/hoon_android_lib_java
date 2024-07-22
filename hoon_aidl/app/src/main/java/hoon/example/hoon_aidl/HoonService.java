package hoon.example.hoon_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class HoonService extends Service {

    private final IHoonService.Stub mBinder = new IHoonService.Stub() {
        @Override
        public String getGreeting(String name) throws RemoteException {
            return "Hello, " + name + "!";
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
