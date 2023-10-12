package com.example.calculanotas;

public class SimuladorCalcular {

    public static class Solicitud {

        public double nota1;
        public double nota2;
        public double nota3;

        public Solicitud(double nota1, double nota2, double nota3) {
            this.nota1 = nota1;
            this.nota2 = nota2;
            this.nota3 = nota3;
        }
    }

    interface Callback {


        void cuandoEsteCalculandoLaMedia(double media);
        void errorNotaInferiorAlMinimo(double notaMinima, int idNota);
        void errorNotaSuperiorAlMaximo(double notaMaxima, int idNota);
        void cuandoEmpiceElCalculo();

        void cuandoFinaliceElCalculo();

    }

    public void calcular (Solicitud solicitud, Callback callback) {

        callback.cuandoEmpiceElCalculo();

        int numActividades = 0;
        int idNota = 0;

        double notaMinima = 0;
        double notaMaxima = 10;

        boolean error = false;


        if (solicitud.nota1 < notaMinima) {
            idNota = 1;
            callback.errorNotaInferiorAlMinimo(notaMinima, idNota);
            error = true;
        } else if (solicitud.nota1 > notaMaxima) {
            idNota = 1;
            callback.errorNotaSuperiorAlMaximo(notaMaxima, idNota);
            error = true;
        }

        if (solicitud.nota2 < notaMinima) {
            idNota = 2;
            callback.errorNotaInferiorAlMinimo(notaMinima, idNota);
            error = true;
        } else  if (solicitud.nota2 > notaMaxima) {
            idNota = 2;
            callback.errorNotaSuperiorAlMaximo(notaMaxima, idNota);
            error = true;
        }

        if (solicitud.nota3 < notaMinima) {
            idNota = 3;
            callback.errorNotaInferiorAlMinimo(notaMinima, idNota);
            error = true;
        } else  if (solicitud.nota3 > notaMaxima) {
            idNota = 3;
            callback.errorNotaSuperiorAlMaximo(notaMaxima, idNota);
            error = true;
        }

        if (!error) {
        try {

            Thread.sleep(5000); //5s
            numActividades = 3;

        } catch (InterruptedException e) {

        }
            callback.cuandoEsteCalculandoLaMedia((solicitud.nota1 + solicitud.nota2 + solicitud.nota3) / numActividades);
        }

        callback.cuandoFinaliceElCalculo();
    }
}
