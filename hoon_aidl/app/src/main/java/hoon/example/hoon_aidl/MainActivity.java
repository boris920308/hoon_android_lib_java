package hoon.example.hoon_aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import hoon.example.hoon_aidl.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private IHoonService mService;
    private boolean mBound = false;
    ActivityMainBinding binding;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 서비스 연결시 호출
            mService = IHoonService.Stub.asInterface(iBinder);
            mBound = true;
            try {
                setGreeting(mService.getGreeting("World"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // 서비스가 비정상적으로 종료되면 호출됨
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent();
        intent.setClassName("hoon.example.hoon_aidl", "hoon.example.hoon_aidl.HoonService");
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private void setGreeting(String greeting) {
        binding.tvGreeting.setText(greeting);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}