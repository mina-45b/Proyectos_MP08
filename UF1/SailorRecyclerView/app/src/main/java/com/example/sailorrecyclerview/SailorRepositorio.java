package com.example.sailorrecyclerview;

import java.util.ArrayList;
import java.util.List;

public class SailorRepositorio {

    List<Sailor> sailors = new ArrayList<>();

    interface Callback {
        void cuandoFinalice(List<Sailor> sailors);
    }

    SailorRepositorio() {
        Sailor sailorMoon = new Sailor("Sailor Moon", "«Soy una Sailor Scout que lucha por el amor y la justicia. ¡Soy Sailor Moon, y te castigaré, en el nombre de la Luna!»\n" +
                "—Frase de presentación eventual.");
        Sailor sailorMercury = new Sailor("Sailor Mercury", "«Soy una Sailor Scout que lucha por el amor y el conocimiento. ¡Soy Sailor Mercury, y te castigaré en el nombre de Mercurio!»\n" +
                "—Su frase de presentación habitual.");
        Sailor sailorMars = new Sailor("Sailor Mars", "«Soy una Sailor Scout que lucha por el amor y la pasión. ¡Soy Sailor Mars, y te castigaré en el nombre de Marte!»\n" +
                "—Su frase de presentación eventual.");
        Sailor sailorJupiter = new Sailor("Sailor Jupiter", "«Soy una Sailor Scout que lucha por el amor y la fuerza. ¡Soy Sailor Jupiter, y te castigaré en el nombre de Jupiter!»\n" +
                "—Su frase de presentación en el anime.");
        Sailor sailorVenus = new Sailor("Sailor Venus", "«Soy una Sailor Scout que lucha por el amor y la justicia. ¡Soy Sailor Venus, y te castigaré en el nombre de Venus!»\n" +
                "—Su frase de presentación en el anime.");

        sailorMoon.image = R.drawable.a;
        sailorMercury.image = R.drawable.b;
        sailorMars.image = R.drawable.c;
        sailorJupiter.image = R.drawable.d;
        sailorVenus.image = R.drawable.e;

        sailors.add(sailorMoon);
        sailors.add(sailorMercury);
        sailors.add(sailorMars);
        sailors.add(sailorJupiter);
        sailors.add(sailorVenus);

    }

    List<Sailor> obtener() {
        return sailors;
    }


    void insertar(Sailor sailor, Callback callback){
        sailor.image = R.drawable.z;
        sailors.add(sailor);
        callback.cuandoFinalice(sailors);
    }

    void eliminar(Sailor sailor, Callback callback) {
        sailors.remove(sailor);
        callback.cuandoFinalice(sailors);
    }

    void actualizar(Callback callback) {
        callback.cuandoFinalice(sailors);
    }

}
