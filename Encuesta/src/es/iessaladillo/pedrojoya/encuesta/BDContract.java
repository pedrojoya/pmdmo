package es.iessaladillo.pedrojoya.encuesta;

import android.provider.BaseColumns;

public class BDContract {
    // Constantes generales de la BD.
    public static final String BD_NOMBRE = "encuesta";
    public static final int BD_VERSION = 1;

    // Tabla Alumno.
    public static abstract class Cumplimentacion implements BaseColumns {
        public static final String TABLA = "cumplimentaciones";
        public static final String PREGUNTA1 = "pregunta1";
        public static final String PREGUNTA2 = "pregunta2";
        public static final String PREGUNTA3 = "pregunta3";
        public static final String[] TODOS = new String[] { _ID, PREGUNTA1,
                PREGUNTA2, PREGUNTA3 };
    }

    // Constructor privado para que NO pueda instanciarse.
    private BDContract() {
    }
}
