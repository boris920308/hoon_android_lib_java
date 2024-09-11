package com.ttt.eee.shared_vm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ttt.eee.shared_vm.databinding.FragmentBananaBinding;

public class BananaFragment extends Fragment {

    private FragmentBananaBinding binding;
    SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBananaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // owner 를 activity 것이 아닌 Fragment 것으로 사용하는 경우
        // Activity 에서 ViewModel의 data 변경하여도 변경된 값이 아니다.
        // 다른 scope가 다르기 때문에 다른 viewmodel 객체일것이다.
        viewModel = new ViewModelProvider(this, SharedViewModel.factory()).get(SharedViewModel.class);

        viewModel.sharedValue.observe(requireActivity(), s -> {
            binding.tvVmValue.setText(s);
        });
    }
}
