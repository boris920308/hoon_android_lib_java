package hoon.example.hoon_hw_stress;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hoon.example.hoon_hw_stress.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private boolean isTesting = false;
    private Handler handler = new Handler();
    private Runnable stressTestRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddCpuStress.setOnClickListener(view -> {
            startStressTest();
        });

        binding.btnInitCpuStress.setOnClickListener(view -> {
            stopStressTest();
        });

    }

    private void startStressTest() {
        Toast.makeText(this, "start Stress Test", Toast.LENGTH_SHORT).show();
        isTesting = true;

        stressTestRunnable = new Runnable() {
            @Override
            public void run() {
                // CPU를 집중적으로 사용하게 하는 작업 수행
                for (int i = 0; i < 100000; i++) {
                    double result = Math.sqrt(Math.random()) * Math.sin(Math.random());
                    Log.d("CPUStressTest", "Result: " + result);
                }
                // 작업이 끝나면 다시 실행
                if (isTesting) {
                    handler.post(this);
                }
            }
        };

        // 첫 번째 실행
        handler.post(stressTestRunnable);
    }

    private void stopStressTest() {
        Toast.makeText(this, "Stop Stress Test", Toast.LENGTH_SHORT).show();
        isTesting = false;
        handler.removeCallbacks(stressTestRunnable);
    }
}