package es.iessaladillo.pedrojoya.encuesta;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Clase de acceso a los datos de la base de datps. Utiliza un objeto de un
 * clase privada interna para gestionar realmente la base de datos, creando
 * hacia el exterior un wrapper (una envoltura) que permita a otros objetos
 * interactuar con la base de datos si hacer uso de sentencias SQL ni conocer
 * detalles internos de ella.
 */
public class DAO {

    // Variables a nivel de clase.
    private BDHelper helper; // Ayudante para la creación y gestión de la BD.
    private SQLiteDatabase bd; // BD que manejará el DAO.

    // Constructor. Recibe el contexto.
    public DAO(Context contexto) {
        // Se obtiene el helper.
        helper = new BDHelper(contexto, BDContract.BD_NOMBRE, null,
                BDContract.BD_VERSION);
    }

    // Abre la BD. Se retorna a sí mismo.
    public DAO open() throws SQLException {
        // Se obtiene una BD actualizable a través del helper.
        bd = helper.getWritableDatabase();
        // Nos retornamos a nosotros mismos, es decir, al objeto
        // adaptador de BD sobre el que se ha ejecutado el método open.
        return this;
    }

    // Cierra la BD.
    public void close() {
        // Se cierra la BD a través del helper.
        helper.close();
    }

    // Inserta una cumplimentación en la tabla de Cumplimentaciones.
    // Recibe el objeto Cumplimentacion a insertar.
    // Retorna el _id de la cumplimentacion una vez insertada o -1 si se ha
    // producido un
    // error.
    public long createCumplimentacion(Cumplimentacion cumplimentacion) {
        // Se crea la lista de pares campo-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(BDContract.Cumplimentacion.PREGUNTA1,
                cumplimentacion.getPregunta1());
        valores.put(BDContract.Cumplimentacion.PREGUNTA2,
                cumplimentacion.getPregunta2());
        valores.put(BDContract.Cumplimentacion.PREGUNTA3,
                cumplimentacion.getPregunta3());
        // Se realiza el insert retornando el _id del alumno insertado o -1 si
        // error.
        return bd.insert(BDContract.Cumplimentacion.TABLA, null, valores);
    }

    // Consulta en la BD todos las cumplimentaciones. Retorna el cursor
    // resultado de la
    // consulta (puede ser null si no hay cumplimentaciones), ordenados por su
    // id.
    public Cursor queryAllCumplimentaciones() {
        // Se realiza la consulta, retornando el cursor resultado.
        return bd.query(BDContract.Cumplimentacion.TABLA,
                BDContract.Cumplimentacion.TODOS, null, null, null, null,
                BDContract.Cumplimentacion._ID);
    }

    // Consulta en la BD todas las cumplimentaciones. Retorna una lista de
    // objeto Cumplimentacion,
    // ordenados por su id.
    public List<Cumplimentacion> getAllCumplimentaciones() {
        // Crea un ArrayList de cumplimentaciones.
        List<Cumplimentacion> lista = new ArrayList<Cumplimentacion>();
        // Consulta todos las cumplimentaciones en la BD y obtiene un cursor.
        Cursor cursor = this.queryAllCumplimentaciones();
        // LLena la lista convirtiendo cada registro del cursor
        // en un elemento de la lista.
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cumplimentacion cumplimentacion = cursorToCumplimentacion(cursor);
            lista.add(cumplimentacion);
            cursor.moveToNext();
        }
        // Cierro el cursor (IMPORTANTE).
        cursor.close();
        // Retorno la lista.
        return lista;
    }

    // Crea un objeto Alumno a partir del registro actual de un cursor. Recibe
    // el cursor y retorna un nuevo objeto Alumno cargado con los datos del
    // registro actual del cursor.
    public Cumplimentacion cursorToCumplimentacion(Cursor cursor) {
        // Crea un objeto Cumplimentacion y guarda los valores provenientes
        // del registro actual del cursor.
        Cumplimentacion cumplimentacion = new Cumplimentacion();
        cumplimentacion.setId(cursor.getLong(0));
        cumplimentacion.setPregunta1(cursor.getString(1));
        cumplimentacion.setPregunta2(cursor.getString(2));
        cumplimentacion.setPregunta3(cursor.getString(3));
        // Retorno el objeto Cumplimentacion.
        return cumplimentacion;
    }

}