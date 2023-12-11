package com.example.sailorrecyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sailorrecyclerview.databinding.FragmentMostrarSailorBinding;

public class MostrarSailorFragment extends Fragment {

    private FragmentMostrarSailorBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarSailorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        SailorViewModel sailorsViewModel = new ViewModelProvider(requireActivity()).get(SailorViewModel.class);



        sailorsViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Sailor>() {
            @Override
            public void onChanged(Sailor sailor) {
                binding.nombre.setText(sailor.nombre);
                binding.dialogo.setText(sailor.dialogo);


            }
        });

    }
}

