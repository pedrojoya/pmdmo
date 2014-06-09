package es.iessaladillo.pedrojoya.encuesta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHelper extends SQLiteOpenHelper {

    // Sentencia SQL para crear la tabla de Alumnos.
    private static final String SQL_CREATE_CUMPLIMENTACION = "CREATE TABLE "
            + BDContract.Cumplimentacion.TABLA + " ("
            + BDContract.Cumplimentacion._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BDContract.Cumplimentacion.PREGUNTA1 + " INTEGER, "
            + BDContract.Cumplimentacion.PREGUNTA2 + " INTEGER, "
            + BDContract.Cumplimentacion.PREGUNTA3 + " INTEGER" + " )";

    // Constructor.
    public BDHelper(Context contexto, String nombreBD, CursorFactory factory,
            int versionBD) {
        // Se llama al constuctor del padre.
        super(contexto, nombreBD, factory, versionBD);
    }

    // Se llama automáticamente al intentar abrir una base de datos que
    // no exista aún.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecutan las sentencias SQL de creación de las tablas.
        db.execSQL(SQL_CREATE_CUMPLIMENTACION);
    }

    // Se llama automáticamente al intentar abrir una base de datos con una
    // versión distinta a la que tiene actualmente.
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior,
            int versionNueva) {
        // Por simplicidad, se eliminan las tablas existentes y se vuelven a
        // crear,
        db.execSQL("DROP TABLE IF EXISTS " + BDContract.Cumplimentacion.TABLA);
        db.execSQL(SQL_CREATE_CUMPLIMENTACION);
    }

}