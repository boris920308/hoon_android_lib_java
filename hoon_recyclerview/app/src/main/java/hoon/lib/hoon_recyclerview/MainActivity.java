package hoon.lib.hoon_recyclerview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hoon.lib.hoon_recyclerview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainRecyclerAdapter mainRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainRecyclerAdapter = new MainRecyclerAdapter(getSampleItems());
        binding.rv.setAdapter(mainRecyclerAdapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<String> getSampleItems() {
        ArrayList<String> items = new ArrayList<>();

        for (int i = 0; i <= 50; i++) {
            items.add("item no." + i);
        }

        return items;
    }

}