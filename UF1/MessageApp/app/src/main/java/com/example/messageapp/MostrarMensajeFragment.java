package com.example.messageapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messageapp.databinding.FragmentMostrarMensajeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MostrarMensajeFragment extends Fragment {

    FragmentMostrarMensajeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarMensajeBinding.inflate(inflater, container, false)).getRoot();
    }

    //Formato de hora
    private String formatoFechaHora (long timestampMillis) {
        Date date = new Date(timestampMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MensajesViewModel mensajesViewModel = new ViewModelProvider(requireActivity()).get(MensajesViewModel.class);

        mensajesViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Mensaje>() {
            @Override
            public void onChanged(Mensaje mensaje) {

                    binding.asunto.setText(mensaje.asunto);
                    binding.usuario.setText(mensaje.usuario);
                    binding.destinatario.setText(mensaje.destinario);
                    //Formato hora fecha
                    String horaFormateada = formatoFechaHora(mensaje.hora);
                    binding.fecha.setText(horaFormateada);

                    binding.contenido.setText(mensaje.contenido);

                    binding.papelera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.papelera.setImageResource(R.drawable.papleraabierta);
                            mensajesViewModel.eliminar(mensaje);
                        }
                    });
            }
        });
    }
}