package es.iessaladillo.pedrojoya.pr028.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import es.iessaladillo.pedrojoya.pr028.modelos.Alumno;

// Clase de acceso a los datos de la base de datos.
public class DAO {

    // Variables a nivel de clase.
    private Helper helper; // Ayudante para la creación y gestión de la BD.
    private SQLiteDatabase bd; // BD que manejará el DAO.

    // Constructor. Recibe el contexto.
    public DAO(Context contexto) {
        // Se obtiene el helper.
        helper = new Helper(contexto);
    }

    // Abre la BD. Se retorna a sí mismo.
    public SQLiteDatabase open() throws SQLException {
        // Se obtiene una BD actualizable a través del helper y se retorna.
        bd = helper.getWritableDatabase();
        return bd;
    }

    // Cierra la BD.
    public void close() {
        // Se cierra la BD a través del helper.
        helper.close();
    }

    // Crea un objeto Alumno a partir del registro actual de un cursor. Recibe
    // el cursor y retorna un nuevo objeto Alumno cargado con los datos del
    // registro actual del cursor.
    public static Alumno cursorToAlumno(Cursor cursorAlumno) {
        // Crea un objeto Alumno y guarda los valores provenientes
        // del registro actual del cursor.
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getLong(cursorAlumno
                .getColumnIndex(Instituto.Alumno._ID)));
        alumno.setNombre(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.NOMBRE)));
        alumno.setCurso(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.CURSO)));
        alumno.setTelefono(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.TELEFONO)));
        alumno.setDireccion(cursorAlumno.getString(cursorAlumno
                .getColumnIndex(Instituto.Alumno.DIRECCION)));
        // Retorno el objeto Alumno.
        return alumno;
    }

    // Crea un objeto ContentValues a partir de un objeto Alumo y lo retorna.
    public static ContentValues alumnoToContentValues(Alumno alumno) {
        // Creamos un la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(Instituto.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Instituto.Alumno.CURSO, alumno.getCurso());
        valores.put(Instituto.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Instituto.Alumno.DIRECCION, alumno.getDireccion());
        return valores;
    }

}
