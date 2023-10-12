package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int contador;
    TextView contadorDeClics;
    Button augmentarElcontador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contadorDeClics = findViewById(R.id.contadorDeClics);
        augmentarElcontador = findViewById(R.id.augmentarElContador);

        augmentarElcontador.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                contador++;
                contadorDeClics.setText("Has clicado " +  contador + " veces");

            }
        });
    }
}