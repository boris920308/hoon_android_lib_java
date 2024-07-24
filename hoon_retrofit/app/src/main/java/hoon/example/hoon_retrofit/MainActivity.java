package hoon.example.hoon_retrofit;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import hoon.example.hoon_retrofit.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getUser(1);

        binding.btnGet.setOnClickListener(view -> call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call1, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            binding.tvData.setText("onResponse, response.isSuccessful(), user = " + user.getUserName());
                        } else {
                            binding.tvData.setText("onResponse, response.isSuccessful(), request fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call1, Throwable t) {
                        binding.tvData.setText("onFailure");
                    }
                })
        );
    }


}