package com.ttt.eee.shared_vm;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ttt.eee.shared_vm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                    .replace(binding.containerFrag.getId(), CherryFragment.class, null)
                    .commit();
        });
    }
}