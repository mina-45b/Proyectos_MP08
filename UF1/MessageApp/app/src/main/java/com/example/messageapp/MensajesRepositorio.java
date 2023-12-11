package com.example.messageapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MensajesRepositorio {
    MensajesBaseDeDatos.MensajesDao mensajesDao;
    Executor executor = Executors.newSingleThreadExecutor();

    public MensajesRepositorio(Application application) {
        mensajesDao = MensajesBaseDeDatos.obtenerInstancia(application).obtnerMensajesDao();
    }

    //Como obtengo mis datos

    LiveData<List<Mensaje>> obtenerMensajesRecibidos() {return mensajesDao.obtenerRecibidos();}

    LiveData<List<Mensaje>> obtenerMensajesEnviados() {return mensajesDao.obtenerEnviados();}

    LiveData<List<Mensaje>> obtenerFavoritos() {return mensajesDao.obtenerFavoritos();}

    LiveData<List<Mensaje>> obtenerArchivados() {return mensajesDao.obtenerArchivados();}

    LiveData<List<Mensaje>> buscar(String t) {return mensajesDao.buscar(t);}

    void insertar(Mensaje mensaje) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mensajesDao.insertar(mensaje);
            }
        });
    }

    void eliminar(Mensaje mensaje) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mensajesDao.eliminar(mensaje);
            }
        });
    }

    void actualizar(Mensaje mensaje, boolean esFavorito, boolean esArchivado) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mensaje.esFavorito = esFavorito;
                mensaje.esArchivado = esArchivado;
                mensajesDao.actualizar(mensaje);
            }
        });
    }
}
