package com.example.catlife;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Life {

    interface LifeListener {
        void cuandoSeMuestre(String orden);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> viendo;

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarVida(new LifeListener() {
                @Override
                public void cuandoSeMuestre(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            paraVer();
        }
    };

    void iniciarVida(LifeListener lifeListener) {
        if(viendo == null || viendo.isCancelled()) {
            viendo = scheduler.scheduleAtFixedRate(new Runnable() {
                int vista = 0;
                int months = 0;

                @Override
                public void run() {
                    if (months % 5 == 0) {
                        vista++;
                        if (vista == 7) {
                            vista = 1;
                            months = 0;
                        }
                    }

                  lifeListener.cuandoSeMuestre("cat" + vista + ":" + (months % 5 == 0 ? "cycle1" : months));
                    months++;
                }
            }, 0, 1, SECONDS);
        }
    }

    void paraVer() {
        if (viendo != null) {
            viendo.cancel(true);
        }
    }

}
