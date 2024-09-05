package hoon.example.hoon_hw_stress;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hoon.example.hoon_hw_stress.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private HoonGLRenderer hoonGLRenderer;

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
            hoonGLRenderer.startLoad();
            binding.glSurfaceView.requestRender();
        });

        binding.btnStop.setOnClickListener(view -> {
            hoonGLRenderer.stopLoad();
            binding.glSurfaceView.requestRender();
        });
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