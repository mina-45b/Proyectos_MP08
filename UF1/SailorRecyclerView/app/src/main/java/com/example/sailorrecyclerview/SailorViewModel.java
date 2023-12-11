package com.example.sailorrecyclerview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class SailorViewModel extends AndroidViewModel {

    MutableLiveData<Sailor> sailorSeleccionada = new MutableLiveData<>();

    SailorRepositorio sailorsRepositorio;

    MutableLiveData<List<Sailor>> listSailorsMutableLiveData = new MutableLiveData<>();

    public SailorViewModel(@NonNull Application application) {
        super(application);

        sailorsRepositorio = new SailorRepositorio();

        listSailorsMutableLiveData.setValue(sailorsRepositorio.obtener());
    }

    MutableLiveData<List<Sailor>> obtener(){
        return listSailorsMutableLiveData;
    }

    void insertar(Sailor sailor){
        sailorsRepositorio.insertar(sailor, new SailorRepositorio.Callback(){
            @Override
            public void cuandoFinalice(List<Sailor> sailors) {
                listSailorsMutableLiveData.setValue(sailors);
            }
        });
    }

    void eliminar(Sailor elemento){
        sailorsRepositorio.eliminar(elemento, new SailorRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Sailor> sailors) {
                listSailorsMutableLiveData.setValue(sailors);
            }
        });
    }

    void actualizar(){
        sailorsRepositorio.actualizar(new SailorRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<Sailor> sailors) {
                listSailorsMutableLiveData.setValue(sailors);
            }
        });
    }

    void seleccionar(Sailor sailor){
        sailorSeleccionada.setValue(sailor);
    }

    MutableLiveData<Sailor> seleccionado(){
        return sailorSeleccionada;
    }
}
