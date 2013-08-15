package es.iessaladillo.pedrojoya.pr069.bd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import es.iessaladillo.pedrojoya.pr069.modelos.Alumno;

/**
 * Clase de acceso a los datos de la base de datps. Utiliza un objeto de un
 * clase privada interna para gestionar realmente la base de datos, creando
 * hacia el exterior un wrapper (una envoltura) que permita a otros objetos
 * interactuar con la base de datos si hacer uso de sentencias SQL ni conocer
 * detalles internos de ella.
 */
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

	// CRUD (Create-Read-Update-Delete) de la tabla alumnos

	// Inserta un alumno en la tabla de alumnmos.
	// Recibe el objeto Alumno a insertar.
	// Retorna el _id del alumna una vez insertado o -1 si se ha producido un
	// error.
	public long createAlumno(Alumno alumno) {
		// Se crea la lista de pares campo-valor para realizar la inserción.
		ContentValues valores = new ContentValues();
		valores.put(Instituto.Alumno.NOMBRE, alumno.getNombre());
		valores.put(Instituto.Alumno.CURSO, alumno.getCurso());
		valores.put(Instituto.Alumno.TELEFONO, alumno.getTelefono());
		valores.put(Instituto.Alumno.DIRECCION, alumno.getDireccion());
		// Se realiza el insert retornando el _id del alumno insertado o -1 si
		// error.
		return bd.insert(Instituto.Alumno.TABLA, null, valores);
	}

	// Borra de la BD un alumno. Recibe el _id del alumno a borrar. Retorna true
	// si se ha realizado la eliminación con éxito.
	public boolean deleteAlumno(long id) {
		// Se realiza el delete, retornando si ha ido bien o no.
		return bd.delete(Instituto.Alumno.TABLA, Instituto.Alumno._ID + " = "
				+ id, null) > 0;
	}

	// Actualiza en la BD los datos de un alumno. Recibe el alumno. Retorna true
	// si la actualización se ha realizado con éxito.
	public boolean updateAlumno(Alumno alumno) {
		// Se crea la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(Instituto.Alumno.NOMBRE, alumno.getNombre());
		valores.put(Instituto.Alumno.CURSO, alumno.getCurso());
		valores.put(Instituto.Alumno.TELEFONO, alumno.getTelefono());
		valores.put(Instituto.Alumno.DIRECCION, alumno.getDireccion());
		// Se realiza el update, retornando si ha ido bien o no.
		return bd.update(Instituto.Alumno.TABLA, valores, Instituto.Alumno._ID
				+ " = " + alumno.getId(), null) > 0;
	}

	// Consulta en la BD los datos de un alumno. Recibe el _id del alumno a
	// consultar. Retorna el objeto Alumno o null si no existe.
	public Alumno queryAlumno(long id) {
		// Se realiza la query SQL sobre la BD.
		Cursor cursor = bd.query(true, Instituto.Alumno.TABLA,
				Instituto.Alumno.TODOS, Instituto.Alumno._ID + " = " + id,
				null, null, null, null, null);
		// Se mueve al primer registro del cursor.
		if (cursor != null) {
			cursor.moveToFirst();
			// Retorno el objeto Alumno correspondiente.
			return cursorToAlumno(cursor);
		} else {
			return null;
		}
	}

	// Consulta en la BD todos los alumnos. Retorna el cursor resultado de la
	// consulta (puede ser null si no hay alumnos), ordenados alfabéticamente
	// por nombre.
	public Cursor queryAllAlumnos() {
		// Se realiza la consulta, retornando el cursor resultado.
		return bd.query(Instituto.Alumno.TABLA, Instituto.Alumno.TODOS, null,
				null, null, null, Instituto.Alumno.NOMBRE);
	}

	// Consulta en la VD todos los alumnos. Retorna una lista de objeto Alumno,
	// ordenados alfabéticamente por nombre.
	public List<Alumno> getAllAlumnos() {
		// Crea un ArrayList de alumnos.
		List<Alumno> lista = new ArrayList<Alumno>();
		// Consulta todos los alumnos en la BD y obtiene un cursor.
		Cursor cursor = this.queryAllAlumnos();
		// LLena la lista convirtiendo cada registro del cursor
		// en un elemento de la lista.
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Alumno alumno = cursorToAlumno(cursor);
			lista.add(alumno);
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
	public static Alumno cursorToAlumno(Cursor cursorAlumno) {
		// Crea un objeto Alumno y guarda los valores provenientes
		// del registro actual del cursor.
		Alumno alumno = new Alumno();
		alumno.setId(cursorAlumno.getLong(0));
		alumno.setNombre(cursorAlumno.getString(1));
		alumno.setCurso(cursorAlumno.getString(2));
		alumno.setTelefono(cursorAlumno.getString(3));
		alumno.setDireccion(cursorAlumno.getString(4));
		// Retorno el objeto Alumno.
		return alumno;
	}

}
