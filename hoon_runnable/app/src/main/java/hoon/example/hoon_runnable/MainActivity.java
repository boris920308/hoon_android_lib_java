package hoon.example.hoon_runnable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import hoon.example.hoon_runnable.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}