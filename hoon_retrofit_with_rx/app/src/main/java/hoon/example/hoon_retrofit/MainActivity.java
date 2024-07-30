package hoon.example.hoon_retrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hoon.example.hoon_retrofit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, user -> {
            binding.tvData.setText("success, user.name = " + user.getName());
        });

        binding.btnGet.setOnClickListener(view -> {
            binding.tvData.setText("click btn, loading...");
            userViewModel.fetchUser();
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}