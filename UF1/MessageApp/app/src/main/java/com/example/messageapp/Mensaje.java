package com.example.messageapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mensaje {

    @PrimaryKey(autoGenerate = true)
            int id;

    String usuario;
    String destinario;
    String asunto;
    String contenido;

    long hora;

    boolean esRecibido; // Si no es recibido se entiende que es enviado
    boolean esFavorito;
    boolean esArchivado;

    public Mensaje(String usuario, String destinario, String asunto, String contenido, long hora) {
        this.usuario = usuario;
        this.destinario = destinario;
        this.asunto = asunto;
        this.contenido = contenido;

        this.hora = hora;

        esRecibido = false;
        esFavorito = false;
        esArchivado = false;
    }
}
