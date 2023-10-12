package com.example.calculanotas;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.calculanotas.SimuladorCalcular;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CalcularNotasViewModel extends AndroidViewModel {

    Executor executor;

    SimuladorCalcular simulador;

    MutableLiveData<Double> mediaResultado = new MutableLiveData<>();
    MutableLiveData<Double> errorNotaMinimaCampo1 = new MutableLiveData();
    MutableLiveData<Double> errorNotaMinimaCampo2 = new MutableLiveData();
    MutableLiveData<Double> errorNotaMinimaCampo3 = new MutableLiveData();

    MutableLiveData<Double> errorNotaMaximaCampo1 = new MutableLiveData<>();
    MutableLiveData<Double> errorNotaMaximaCampo2 = new MutableLiveData<>();
    MutableLiveData<Double> errorNotaMaximaCampo3 = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();


    public CalcularNotasViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorCalcular();
    }

    public void calcular(double nota1, double nota2, double nota3){

        final SimuladorCalcular.Solicitud solicitud = new SimuladorCalcular.Solicitud(nota1, nota2, nota3);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(solicitud, new SimuladorCalcular.Callback() {

                    @Override
                    public void cuandoEsteCalculandoLaMedia(double media) {
                        errorNotaMinimaCampo1.postValue(null);
                        errorNotaMinimaCampo2.postValue(null);
                        errorNotaMinimaCampo3.postValue(null);
                        errorNotaMaximaCampo1.postValue(null);
                        errorNotaMaximaCampo2.postValue(null);
                        errorNotaMaximaCampo3.postValue(null);
                        mediaResultado.postValue(media);
                    }
//crear un error dependiendo del id, y llamar con una serie de if los errores
                    @Override
                    public void errorNotaInferiorAlMinimo(double notaMinima, int idNota) {
                        if (idNota == 1) {
                            errorNotaMinimaCampo1.postValue(notaMinima);
                        } else if (idNota == 2) {
                            errorNotaMinimaCampo2.postValue(notaMinima);
                        } else if (idNota == 3) {
                            errorNotaMinimaCampo3.postValue(notaMinima);
                        }
                    }

                    @Override
                    public void errorNotaSuperiorAlMaximo(double notaMaxima, int idNota) {
                        if (idNota == 1) {
                            errorNotaMaximaCampo1.postValue(notaMaxima);
                        } else if (idNota == 2) {
                            errorNotaMaximaCampo2.postValue(notaMaxima);
                        } else if (idNota == 3) {
                            errorNotaMaximaCampo3.postValue(notaMaxima);
                        }
                    }

                    @Override
                    public void cuandoEmpiceElCalculo() {
                        calculando.postValue(true);
                    }
                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }
                });
            }
        });
    }




}
