package com.example.messageapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import kotlin.jvm.functions.Function1;

public class MensajesViewModel extends AndroidViewModel {
    MensajesRepositorio mensajesRepositorio;

    MutableLiveData<Mensaje> mensajeSeleccionado = new MutableLiveData<>();

    MutableLiveData<String> terminoBusqueda = new MutableLiveData<>();

    LiveData<List<Mensaje>> resultadoBusqueda = Transformations.switchMap(terminoBusqueda, new Function1<String, LiveData<List<Mensaje>>>() {
        @Override
        public LiveData<List<Mensaje>> invoke(String input) {
            return mensajesRepositorio.buscar(input);
        }
    });

    public MensajesViewModel(@NonNull Application application) {
        super(application);

        mensajesRepositorio = new MensajesRepositorio(application);

    }


    LiveData<List<Mensaje>> obtenerMensajesEnviados() {return mensajesRepositorio.obtenerMensajesEnviados();}
    LiveData<List<Mensaje>> obtenerMensajesRecibidos() {return mensajesRepositorio.obtenerMensajesRecibidos();}
    LiveData<List<Mensaje>> obtenerFavoritos() {return mensajesRepositorio.obtenerFavoritos();}
    LiveData<List<Mensaje>> obtenerArchivados() {return mensajesRepositorio.obtenerArchivados();}


    //Interaccion del usuario con los datos

    void insertar(Mensaje mensaje) {mensajesRepositorio.insertar(mensaje);}
    void eliminar(Mensaje mensaje) {mensajesRepositorio.eliminar(mensaje);}
    void actualizar(Mensaje mensaje, boolean esFavorito, boolean esArchivado ){
        mensajesRepositorio.actualizar(mensaje, esFavorito, esArchivado);
    }


    void seleccionar(Mensaje mensaje){
        mensajeSeleccionado.setValue(mensaje);
    }

    MutableLiveData<Mensaje> seleccionado(){
        return mensajeSeleccionado;
    }

    LiveData<List<Mensaje>> buscar(){
        return resultadoBusqueda;
    }

    void establecerTerminoBusqueda(String t){
        terminoBusqueda.setValue(t);
    }

}
