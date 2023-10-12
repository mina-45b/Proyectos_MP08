package com.example.calculanotas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculanotas.databinding.FragmentCalculaNotasBinding;

public class CalculaNotasFragment extends Fragment {

    private FragmentCalculaNotasBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCalculaNotasBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CalcularNotasViewModel calcularNotasViewModel = new ViewModelProvider(this).get(CalcularNotasViewModel.class);

       binding.media.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               boolean error = false;

               double nota1 = 0;
               double nota2 = 0;
               double nota3 = 0;

               try {
                   nota1 = Double.parseDouble(binding.nota1.getText().toString());
               } catch (Exception e) {
                   binding.nota1.setError("Introduzca un número");
                   error = true;
               }

               try {
                   nota2 = Double.parseDouble(binding.nota2.getText().toString());
               } catch (Exception e) {
                   binding.nota2.setError("Introduzca un número");
                   error = true;
               }

               try {
                   nota3 = Double.parseDouble(binding.nota3.getText().toString());
               } catch (Exception e) {
                   binding.nota3.setError("Introduzca un número");
                   error = true;
               }

               if(!error) {
                   calcularNotasViewModel.calcular(nota1, nota2, nota3);
               }

           }
       });

       calcularNotasViewModel.mediaResultado.observe(getViewLifecycleOwner(), new Observer<Double>() {
           @Override
           public void onChanged(Double mediaResultado) {
               if (mediaResultado >= 5) {
                   binding.mediaResultado.setText(String.format("Ha aprovado el curso con %.1f", mediaResultado));
               } else {
                   binding.mediaResultado.setText(String.format("Ha reprovado el curso con %.1f", mediaResultado));
               }
           }
       }
       );

//llamar cada error y printar cada erro dependiendo del campo
        calcularNotasViewModel.errorNotaMinimaCampo1.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double notaMinima) {
                if (notaMinima != null) {
                    binding.nota1.setError("Nota inferior a " + notaMinima);
                } else {
                    binding.nota1.setError(null);
                }
            }
        });

        calcularNotasViewModel.errorNotaMaximaCampo1.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double notaMaxima) {
                if (notaMaxima != null) {
                    binding.nota1.setError("Nota superior a " + notaMaxima);
                } else {
                    binding.nota1.setError(null);
                }
            }
        });

        calcularNotasViewModel.errorNotaMinimaCampo2.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double notaMinima) {
                if (notaMinima != null) {
                    binding.nota2.setError("Nota inferior a " + notaMinima);
                } else {
                    binding.nota2.setError(null);
                }
            }
        });

        calcularNotasViewModel.errorNotaMaximaCampo2.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double notaMaxima) {
                if (notaMaxima != null) {
                    binding.nota2.setError("Nota superior a " + notaMaxima);
                } else {
                    binding.nota2.setError(null);
                }
            }
        });

        calcularNotasViewModel.errorNotaMinimaCampo3.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double notaMinima) {
                if (notaMinima != null) {
                    binding.nota3.setError("Nota inferior a " + notaMinima);
                } else {
                    binding.nota3.setError(null);
                }
            }
        });

        calcularNotasViewModel.errorNotaMaximaCampo3.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double notaMaxima) {
                if (notaMaxima!= null) {
                    binding.nota3.setError("Nota superior a " + notaMaxima);
                } else {
                    binding.nota3.setError(null);
                }
            }
        });


       calcularNotasViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean calculando) {
               if (calculando) {
                   binding.progressBar.setVisibility(View.VISIBLE);
                   binding.mediaResultado.setVisibility(View.GONE);
               } else {
                   binding.progressBar.setVisibility(View.GONE);
                   binding.mediaResultado.setVisibility(View.VISIBLE);
               }

           }
       });

    }
}