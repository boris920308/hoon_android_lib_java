package hoon.example.hoon_hw_stress;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import hoon.example.hoon_hw_stress.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvMainTitle.setText("Hello MainActivity");
    }
}