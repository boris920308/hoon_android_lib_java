package hoon.lib.hoon_recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hoon.lib.hoon_recyclerview.databinding.ItemMainBinding;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private ArrayList<String> items;

    public MainRecyclerAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMainBinding binding = ItemMainBinding.inflate(layoutInflater, parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        ItemMainBinding itemMainBinding;

        public MainViewHolder(ItemMainBinding itemMainBinding) {
            super(itemMainBinding.getRoot());
            this.itemMainBinding = itemMainBinding;
        }

        public void onBind(String item) {
            itemMainBinding.tvTitle.setText(item);
        }
    }
}