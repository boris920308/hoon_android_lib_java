package com.ttt.eee.shared_vm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ttt.eee.shared_vm.databinding.FragmentAppleBinding;

public class AppleFragment extends Fragment {

    private FragmentAppleBinding binding;
    SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAppleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), SharedViewModel.factory()).get(SharedViewModel.class);

        viewModel.sharedValue.observe(requireActivity(), s -> {
            binding.tvVmValue.setText(s);
        });
    }
}
