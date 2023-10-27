package com.example.catlife;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import kotlin.jvm.functions.Function1;

public class LifeCatViewModel extends AndroidViewModel {
    Life life;

    LiveData<Integer> lifeLiveData;
    LiveData<String> monthsLiveData;

    public LifeCatViewModel(@NonNull Application application) {
        super(application);

        life = new Life();


        lifeLiveData = Transformations.switchMap(life.ordenLiveData, new Function1<String, LiveData<Integer>>() {
            String gatoAnterior;

            @Override
            public LiveData<Integer> invoke(String orden) {

                String ciclo = orden.split(":")[0];

                if (!ciclo.equals(gatoAnterior)) {
                    gatoAnterior = ciclo;
                    int imagen;
                    switch (ciclo) {
                        case "cat1":
                        default:
                            imagen = R.drawable.b0;
                            break;
                        case "cat2":
                            imagen = R.drawable.b1;
                            break;
                        case "cat3":
                            imagen = R.drawable.b2;
                            break;
                        case "cat4":
                            imagen = R.drawable.b3;
                            break;
                        case "cat5":
                            imagen = R.drawable.b4;
                            break;
                        case "cat6":
                            imagen = R.drawable.b5;
                            break;
                    }

                   return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });

        monthsLiveData = Transformations.switchMap(life.ordenLiveData, new Function1<String, LiveData<String>>() {
            @Override
            public LiveData<String> invoke(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });


    }

    LiveData<Integer> obtenerCat() {
        return lifeLiveData;
    }

    LiveData<String> obtenerMonths() {
        return monthsLiveData;
    }




}
