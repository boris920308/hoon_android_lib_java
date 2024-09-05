package hoon.example.hoon_hw_stress;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import hoon.example.hoon_hw_stress.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private HoonGLRenderer hoonGLRenderer;

    private Handler handler = new Handler();
    private Runnable runnable;

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
            runGpuStress();
        });

        binding.btnStop.setOnClickListener(view -> {
//            hoonGLRenderer.stopLoad();
            binding.glSurfaceView.requestRender();
            handler.removeCallbacks(runnable);

        });
    }

    private void runGpuStress() {
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("MainActivity", "run . . . ");
//                hoonGLRenderer.startLoad();
                binding.glSurfaceView.requestRender();
                handler.postDelayed(this, 100);
            }
        };

        handler.postDelayed(runnable, 100);
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
}