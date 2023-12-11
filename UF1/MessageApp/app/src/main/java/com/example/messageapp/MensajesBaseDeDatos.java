package com.example.messageapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Database(entities = {Mensaje.class}, version = 1, exportSchema = false)
public abstract class MensajesBaseDeDatos extends RoomDatabase {

    public abstract MensajesDao obtnerMensajesDao();

    @Dao
    interface MensajesDao {


        // Obtener todos los mensajes enviados
        @Query("SELECT * FROM Mensaje WHERE esRecibido = 0 AND esArchivado = 0 ORDER BY hora DESC")
        LiveData<List<Mensaje>> obtenerEnviados();

        //obtiene mensaje recibidos
        @Query("SELECT * FROM Mensaje WHERE esRecibido = 1 AND esArchivado = 0 ORDER BY hora DESC")
        LiveData<List<Mensaje>> obtenerRecibidos();

        //obtener mensajes favoritos
        @Query("SELECT * FROM Mensaje WHERE esFavorito = 1")
        LiveData<List<Mensaje>> obtenerFavoritos();

        //obtener mensajes archivados
        @Query("SELECT * FROM Mensaje WHERE esArchivado = 1")
        LiveData<List<Mensaje>> obtenerArchivados();

        //buscar mensajes
        @Query("SELECT * FROM Mensaje WHERE usuario LIKE '%' || :t || '%' OR asunto LIKE '%' || :t || '%' OR contenido LIKE '%' || :t || '%'")
        LiveData<List<Mensaje>> buscar(String t);

        @Insert
        void insertar(Mensaje mensaje);

        @Update
        void actualizar(Mensaje mensaje);

        @Delete
        void eliminar(Mensaje mensaje);

    }

    private static volatile MensajesBaseDeDatos INSTANCIA;

    static MensajesBaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (MensajesBaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                                    MensajesBaseDeDatos.class, "mensajes.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCIA;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Aquí es donde puedes agregar objetos predeterminados
            new InitialDataAsyncTask(INSTANCIA).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private MensajesDao mensajesDao;

        private InitialDataAsyncTask(MensajesBaseDeDatos db) {
            this.mensajesDao = db.obtnerMensajesDao();
        }

        private static long generarRandomTimestamp() {
            // Definir el rango de fechas (por ejemplo, últimos 365 días)
            long hoy = System.currentTimeMillis();
            long unAnyMillis = 365 * 24 * 60 * 60 * 1000L; // 365 días en milisegundos
            long fechaInicio = hoy - unAnyMillis;

            Random random = new Random();
            return fechaInicio + (long) (random.nextDouble() * (hoy - fechaInicio));
        }

        private static long generarRandomTimestampNavidaAnyNou() {
            // Establecer la fecha base al 20 de diciembre
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, 20);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

            // Agregar un número aleatorio de días entre 0 y 10
            Random random = new Random();
            int diasAAgregar = random.nextInt(11);
            calendar.add(Calendar.DAY_OF_MONTH, diasAAgregar);

            // Obtener el timestamp final
            return calendar.getTimeInMillis();
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            // Agrega tus objetos predeterminados aquí dentro de una transacción
            INSTANCIA.runInTransaction(new Runnable() {
                @Override
                public void run() {

                    long cumpleAnys = generarRandomTimestamp();

                    // Crear un objeto Calendar y establecer el tiempo actual
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(cumpleAnys);

                    // Agregar 3 horas al timestamp actual
                    int horasAAgregar = 3;
                    calendar.add(Calendar.HOUR_OF_DAY, horasAAgregar);


                    Mensaje mensaje1 = new Mensaje("Maria Salazar", "mí", "Año Nuevo", "Año nuevo, Que el año nuevo te traiga alegría, éxito y momentos inolvidables. ¡Feliz Año Nuevo!", generarRandomTimestampNavidaAnyNou());
                    Mensaje mensaje2 = new Mensaje("Jefa Departamento", "mí", "Navidad", "En esta Navidad, que la magia llene tu hogar de amor, paz y felicidad. ¡Felices Fiestas!", generarRandomTimestampNavidaAnyNou());
                    Mensaje mensaje3 = new Mensaje("Juan Armando", "mí","Continúa Así", "Enhorabuena por tus éxitos laborales. Que sigas alcanzando nuevas alturas y logros. ¡Bien hecho!", generarRandomTimestamp() );
                    Mensaje mensaje4 = new Mensaje("Manuel Rodríguez", "mí", "Año Nuevo", "Que el año que viene te traiga nuevas oportunidades y momentos emocionantes. ¡Feliz Año Nuevo!", generarRandomTimestampNavidaAnyNou());
                    Mensaje mensaje5 = new Mensaje("Andrea Llada", "mí", "Feliz Cumpleaños", "¡Feliz cumpleaños! Que este nuevo año de vida esté lleno de sorpresas, amor y logros increíbles.", cumpleAnys);
                    Mensaje mensaje6 = new Mensaje("Alberto Fernández", "mí", "Navidad", "En esta temporada navideña, que encuentres alegría en las pequeñas cosas y paz en cada momento. ¡Feliz Navidad!", generarRandomTimestampNavidaAnyNou());
                    Mensaje mensaje7 = new Mensaje("Julia Hernández", "mí", "Feliz Cumpleaños", "¡Feliz cumpleaños! Que este día esté lleno de risas, amor y los mejores recuerdos.", calendar.getTimeInMillis());
                    Mensaje mensaje8 = new Mensaje("Maria Sánchez", "mí", "Buen Trabajo", "Felicidades por tus logros profesionales. Que este éxito sea solo el comienzo de una carrera brillante.", generarRandomTimestamp());
                    Mensaje mensaje9 = new Mensaje("Roberto Alvares", "mí", "Feliz Año Nuevo", "Deseándote un año lleno de nuevas aventuras, oportunidades y momentos inolvidables. ¡Feliz Año Nuevo!", generarRandomTimestampNavidaAnyNou());
                    Mensaje mensaje10 = new Mensaje("Marcela Díaz", "mí", "Feliz Navidad", "Que esta Navidad esté llena de amor, paz y alegría compartida con aquellos que más aprecias. ¡Felices Fiestas!", generarRandomTimestampNavidaAnyNou());

                    mensaje1.esRecibido = true;
                    mensaje2.esRecibido = true;
                    mensaje3.esRecibido = true;
                    mensaje4.esRecibido = true;
                    mensaje5.esRecibido = true;
                    mensaje6.esRecibido = true;
                    mensaje7.esRecibido = true;
                    mensaje8.esRecibido = true;
                    mensaje9.esRecibido = true;
                    mensaje10.esRecibido = true;

                    mensajesDao.insertar(mensaje1);
                    mensajesDao.insertar(mensaje2);
                    mensajesDao.insertar(mensaje3);
                    mensajesDao.insertar(mensaje4);
                    mensajesDao.insertar(mensaje5);
                    mensajesDao.insertar(mensaje6);
                    mensajesDao.insertar(mensaje7);
                    mensajesDao.insertar(mensaje8);
                    mensajesDao.insertar(mensaje9);
                    mensajesDao.insertar(mensaje10);
                }
            });
            return null;
        }
    }
}
