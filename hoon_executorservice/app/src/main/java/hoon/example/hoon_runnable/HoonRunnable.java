package hoon.example.hoon_runnable;

import android.util.Log;

public class HoonRunnable implements Runnable{

    @Override
    public void run() {
        Log.d("HoonRunnable", "run(), thread = " + Thread.currentThread().getName());
    }
}
