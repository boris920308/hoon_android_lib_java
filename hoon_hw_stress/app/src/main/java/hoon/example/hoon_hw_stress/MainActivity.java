package hoon.example.hoon_hw_stress;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hoon.example.hoon_hw_stress.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private HoonGLRenderer hoonGLRenderer;

    private Handler stressTestHandler = new Handler();
    private Runnable stressTestRunnable;

    private boolean isTestRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hoonGLRenderer = new HoonGLRenderer(getApplicationContext());
        binding.glSurfaceView.setEGLContextClientVersion(2);
        binding.glSurfaceView.setRenderer(hoonGLRenderer);
        binding.glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        binding.btnStart.setOnClickListener(view -> {
            Log.d("hoon92", "MainActivity, Click btnStart");
            runStressTest();
        });

        binding.btnStop.setOnClickListener(view -> {
            Log.d("hoon92", "MainActivity, Click btnStop");
            stopStressTest();
        });
    }

    private void runStressTest() {
        if (isTestRunning) {
            return;
        }

        isTestRunning = true;

        stressTestRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    binding.glSurfaceView.requestRender();
                }

                // 작업이 끝나면 다시 실행
                if (isTestRunning) {
                    stressTestHandler.post(this);
                }
            }
        };

        // 첫 실행
        stressTestHandler.post(stressTestRunnable);
    }

    private void stopStressTest() {
        isTestRunning = false;
        stressTestHandler.removeCallbacks(stressTestRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.glSurfaceView.onResume();
    }

    private void getCpuTemperature() throws IOException {
        // QCS6490기준으로 작성됨
        // thermal_zone* 이 여러개인 경우 어느 경로를 봐야하는지 확인 필요
        BufferedReader in = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone0/temp"));
        String s;
        int val = 0;

        while ((s = in.readLine()) != null) {
            val = Integer.parseInt(s) / 1000;
            break;
        }
        final String cpuTemp = "" + val;

        Log.d("Hoon92", "cpuTemp = " + cpuTemp + "°C");
//        runOnUiThread(() -> binding.tvCpuTempValue.setText(cpuTemp + " °C"));
        in.close();
    }

    private void getCpuUsage() throws IOException {
        // QCS6490기준으로 작성됨
        Process process = Runtime.getRuntime().exec("top -n 1");
        BufferedReader cpuReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String cpuLine;
        while ((cpuLine = cpuReader.readLine()) != null) {
            if (cpuLine.contains("%cpu")) {
                Pattern pattern = Pattern.compile("(\\d+)%cpu.*?(\\d+)%idle");
                Matcher matcher = pattern.matcher(cpuLine);
                if (matcher.find()) {
                    int totalCpu = Integer.parseInt(matcher.group(1)); // 'cpu' 앞의 값 추출
                    int idleCpu = Integer.parseInt(matcher.group(2));  // 'idle' 앞의 값 추출
                    double cpuUsed = (1 - (double) idleCpu / totalCpu) * 100;

                    // 추출된 값 출력
                    Log.d("Hoon92", "cpuLine = " + cpuUsed + "%");
//                    runOnUiThread(() -> binding.tvCpuUsedValue.setText(String.format("%.2f", cpuUsed) + "%"));
                }
            }
        }
        cpuReader.close();
    }

    private void getGpuUsage() throws IOException {
        // QCS6490기준으로 작성됨, android:sharedUserId="android.uid.system" 권한 필요
        BufferedReader gpuReader = new BufferedReader(new FileReader("/sys/class/kgsl/kgsl-3d0/gpu_busy_percentage"));
        String gpuLine = gpuReader.readLine();
        if (gpuLine != null) {
            Log.d("Hoon92", "gpu_busy_percentage = " + gpuLine);
//            runOnUiThread(() -> binding.tvGpuUsedValue.setText(gpuLine));
        }
        gpuReader.close();
    }
}