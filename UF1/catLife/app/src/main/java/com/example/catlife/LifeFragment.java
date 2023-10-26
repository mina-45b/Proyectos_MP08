package com.example.catlife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.catlife.databinding.FragmentLifeBinding;

import java.util.Objects;

public class LifeFragment extends Fragment {
    private FragmentLifeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentLifeBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LifeCatViewModel lifeCatViewModel = new ViewModelProvider(this).get(LifeCatViewModel.class);

        lifeCatViewModel.obtenerCat().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer cat) {
                Glide.with(LifeFragment.this).load(cat).into(binding.cat);
            }
        });

        lifeCatViewModel.obtenerMonths().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String moths) {
                if (moths.equals("cycle1")){
                    binding.cycle1.setVisibility(View.VISIBLE);
                } else {
                    binding.cycle1.setVisibility(View.GONE);
                }
                binding.months.setText(moths);
            }
        });

    }
}