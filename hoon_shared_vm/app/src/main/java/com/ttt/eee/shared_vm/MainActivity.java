package com.ttt.eee.shared_vm;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ttt.eee.shared_vm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.btnApple.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.containerFrag.getId(), AppleFragment.class, null)
                    .commit();
        });

        binding.btnBanana.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.containerFrag.getId(), BananaFragment.class, null)
                    .commit();
        });

        binding.btnCherry.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.containerFragCherry.getId(), CherryFragment.class, null)
                    .commit();
        });

        binding.btnSetApple.setOnClickListener(view -> {
            viewModel.setApple();
        });

        binding.btnSetBanana.setOnClickListener(view -> {
            viewModel.setBanana();
        });

        binding.btnSetCherry.setOnClickListener(view -> {
            viewModel.setCherry();
        });

        viewModel = new ViewModelProvider(this, SharedViewModel.factory()).get(SharedViewModel.class);

        viewModel.sharedValue.observe(this, s -> {
            binding.tvVmValue.setText(s);
        });

    }
}