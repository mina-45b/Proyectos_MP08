package com.example.messageapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.messageapp.databinding.FragmentNuevoMensajeBinding;


public class NuevoMensajeFragment extends Fragment {

    private FragmentNuevoMensajeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNuevoMensajeBinding.inflate(inflater, container, false)).getRoot();
    }

    public void setupSpinnerBasic() {
        Spinner spinner = binding.listaContactos;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.contactos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public String obtenerTextoSpinner() {
        Spinner spinner = binding.listaContactos;

        Object itemSeleccionado = spinner.getSelectedItem();

        return (String) itemSeleccionado;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSpinnerBasic();

        MensajesViewModel mensajesViewModel = new ViewModelProvider(requireActivity()).get(MensajesViewModel.class);
        NavController navController = Navigation.findNavController(view);

        binding.enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = "Mariana López";
                String destinatario = obtenerTextoSpinner();
                String asunto = binding.asunto.getText().toString();
                String contenido = binding.contenido.getText().toString();

                boolean esEnviado = true;

                //Obtiene el tiempo
                long timestampMillis = System.currentTimeMillis();

                mensajesViewModel.insertar(new Mensaje(usuario, destinatario, asunto, contenido, timestampMillis));

                navController.popBackStack();
            }
        });
    }


}