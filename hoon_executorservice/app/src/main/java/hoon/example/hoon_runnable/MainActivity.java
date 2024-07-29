package hoon.example.hoon_runnable;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hoon.example.hoon_runnable.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        HoonRunnable hoonRunnable = new HoonRunnable();


        binding.btnRun.setOnClickListener(view -> {
            executorService.submit(hoonRunnable);
        });

        binding.btnStop.setOnClickListener(view -> {
            if (!(executorService.isShutdown())) {
                executorService.shutdownNow();
            }
        });
    }
}