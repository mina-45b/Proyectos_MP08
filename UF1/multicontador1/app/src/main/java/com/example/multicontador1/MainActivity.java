package com.example.multicontador1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int contador1, contador2, contador3, contador4, contador_general;
    TextView contadorClics1, contadorClics2, contadorClics3, contadorClics4, contadorClics_general;

    Button aumentaContador, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contadorClics1 = findViewById(R.id.contador2);
        contadorClics2 = findViewById(R.id.contador3);
        contadorClics3 = findViewById(R.id.contador4);
        contadorClics4 = findViewById(R.id.contador5);
        contadorClics_general = findViewById(R.id.contador1);

        reset = findViewById(R.id.reset_all);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador1 = 0;
                contador2 = 0;
                contador3 = 0;
                contador4 = 0;
                contador_general = 0;

                contadorClics1.setText(contador1 +"");
                contadorClics2.setText(contador2 +"");
                contadorClics3.setText(contador3 +"");
                contadorClics4.setText(contador4 +"");

                contadorClics_general.setText(contador_general +"");
            }
        });

        //boton 1
        aumentaContador = findViewById(R.id.boton1);
        aumentaContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador1++;
                contador_general++;
                contadorClics1.setText(contador1 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        reset = findViewById(R.id.reset1);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador_general = contador_general - contador1;
                contador1 = 0;
                contadorClics1.setText(contador1 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        //boton 2
        aumentaContador = findViewById(R.id.boton2);
        aumentaContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador2++;
                contador_general++;
                contadorClics2.setText(contador2 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        reset = findViewById(R.id.reset2);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador_general = contador_general - contador2;
                contador2 = 0;
                contadorClics2.setText(contador2 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        //boton 3
        aumentaContador = findViewById(R.id.boton3);
        aumentaContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador3++;
                contador_general++;
                contadorClics3.setText(contador3 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        reset = findViewById(R.id.reset3);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador_general = contador_general - contador3;
                contador3 = 0;
                contadorClics3.setText(contador3 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        //boton 4
        aumentaContador = findViewById(R.id.boton4);
        aumentaContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador4++;
                contador_general++;
                contadorClics4.setText(contador4 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

        reset = findViewById(R.id.reset4);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador_general = contador_general - contador4;
                contador4 = 0;
                contadorClics4.setText(contador4 +"");
                contadorClics_general.setText(contador_general +"");
            }
        });

    }
}


















