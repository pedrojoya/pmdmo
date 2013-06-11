package es.iessaladillo.pedrojoya.pr027.bd;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.R.string;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;

/**
 * Clase adaptadora a través de la que podremos acceder a la base de datos de alumnos.
 * Utiliza un objeto de un clase privada interna para gestionar realmente la base de datos,
 * creando hacia el exterior un wrapper (una envoltura) que permita a otros objetos interactuar
 * con la base de datos si hacer uso de sentencias SQL ni conocer detalles interno de ella.
 */
public class AdaptadorBD {

	// CONSTANTES DE LA BASE DE DATOS.
	
	// Generales.
	public static final String DATABASE_NAME = "gestionCentros";	// Nombre de la BD
	private static final int DATABASE_VERSION = 2;	// Versión de la BD.
	private static final String TAG_LOG = "AdaptadorBD";	// Tag usado para los logs.

	// Tabla Alumno.
	private static final String TBL_ALUMNO = "Alumno";
	public static final String FLD_ALU_ID = "_id";	// Id (clava primaria autonumérica).
	public static final String FLD_ALU_NOM = "nombre";	// Nombre.
	public static final String FLD_ALU_CUR = "curso";	// Curso.	
	public static final String FLD_ALU_TEL = "telefono";	// Teléfono.
	public static final String FLD_ALU_DIR = "direccion"; // Dirección.
	private String[] aluAllFields =new String[] {FLD_ALU_ID, FLD_ALU_NOM, FLD_ALU_CUR, FLD_ALU_TEL, FLD_ALU_DIR};

	// SQL.
	private static final String TBL_ALUMNO_CREATE =
	"create table " + TBL_ALUMNO +
	"(" + FLD_ALU_ID + " integer primary key autoincrement, "
	+ FLD_ALU_NOM + " text not null, "
	+ FLD_ALU_CUR + " text not null, "
	+ FLD_ALU_TEL + " text not null, "
	+ FLD_ALU_DIR + " text);";
	private static final String TBL_ALUMNO_ALTER_V2 = 
			"alter table " + TBL_ALUMNO + 
			" add column " + FLD_ALU_DIR + " text;";
	
	// CLASE INTERNA PRIVADA DE GESTIÓN DE LA BASE DE DATOS	
	
	/**
	 * Gestiona las operaciones de creación, upgrade, apertura y cierre de la BD.
	 * Se sobrescriben onCreate y onUpgrade para personalizar la clase SQLiteOpenHelper
	 * adaptándola a las características propias de la BD que se quiere gestionar
	 */
	private static class GestorBD extends SQLiteOpenHelper {
	
		/**
		 * Constructor
		 * @param ctx Contexto. Necesita recibirlo para llamar al constructor de
		 * su clase padre SQLiteOpenHelper.
		 */
		GestorBD(Context ctx) {
			// Llamo al constructor del padre, que es quien realmente crea o
			// actualiza la versión de BD si es necesario.
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
	
		// Método de callback para cuando se ha creado la BD.
		@Override
		public void onCreate(SQLiteDatabase db)	{
			// Ejecuto las sentencias SQL de creación de las tablas de la BD. 
			db.execSQL(TBL_ALUMNO_CREATE);
		}
		
		// Método de callback para cuando la BD debe se actualizada de versión
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Guardo un log de que se va a actualizar la versión de la BD.
			final String MENSAJE = 
			  "Actualizando base de datos de la versión %1$d a la %2$d";
			String cadena = String.format(MENSAJE, oldVersion, newVersion);
			Log.w(TAG_LOG, cadena);
			// Ejecuto las sentencias SQL de actualizacion de tablas de la BD.
			switch (oldVersion) {
			case 1:				
				db.execSQL(TBL_ALUMNO_ALTER_V2);
				break;
			}
		}
	}

	// Variables a nivel de clase.
	private final Context contexto;	// Contexto.
	private GestorBD gestorBD;	// Gestor real de la base de datos.
	private SQLiteDatabase bdSQL;	// Base de datos que manejará el adaptador.

	/**
	 * Constructor de la clase
	 * @param ctx Contexto
	 */
	public AdaptadorBD(Context ctx) {
		// Hacemos copia local del contexto.
		this.contexto = ctx;
		// Obtenemos el objeto gestor de la BD.
		gestorBD = new GestorBD(contexto);
	}

	// OPERACIONES BÁSICAS CON LA BASE DE DATOS.
	
	/**
	 * Abre la base de datos
	 * @return El objeto sobre el que se ha ejecutado el método open (él mismo).
	 * @throws SQLException
	 */
	public AdaptadorBD open() throws SQLException {
		// Utilizamos el gestor para obtener un objeto BD actualizable.
		bdSQL = gestorBD.getWritableDatabase();
		// Nos retornamos a nosotros mismos, es decir, al objeto 
		// adaptador de BD sobre el que se ha ejecutado el método open.
		return this;
	}

	/**
	 * Cierra la base de datos.
	 */
	public void close() {
		// Utilizamos el gestor de BD para cerrar la BD.
		gestorBD.close();
	}

	// CRUD (Create-Read-Update-Delete) de la tabla Alumno
	
	/**
	 * Inserta en la BD un alumno.
	 * @param nombre Nombre del alumno
	 * @param curso Curso del alumno
	 * @param telefono Teléfono del alumno
	 * @param direccion Dirección del alumno
	 * @return _id del alumno una vez insertado o -1 si se ha producido un error.
	 */
	public long insertAlumno(String nombre, String curso, String telefono, String direccion) {
		// Creamos un la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(FLD_ALU_NOM, nombre);
		valores.put(FLD_ALU_CUR, curso);
		valores.put(FLD_ALU_TEL, telefono);
		valores.put(FLD_ALU_DIR, direccion);		
		// Realizamos el insert SQL sobr la BD,
		// retornando el _id del alumno insertado o -1 si error.
		return bdSQL.insert(TBL_ALUMNO, null, valores);
	}
	
	/**
	 * Inserta en la BD un alumno.
	 * @param alumno Objeto Alumno correspondiente al alumno que se quiere insertar.
	 * @return _id del alumno una vez insertado o -1 si se ha producido un error.
	 */
	public long insertAlumno(Alumno alumno) {
		// Creamos un la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(FLD_ALU_NOM, alumno.getNombre());
		valores.put(FLD_ALU_CUR, alumno.getCurso());
		valores.put(FLD_ALU_TEL, alumno.getTelefono());
		valores.put(FLD_ALU_DIR, alumno.getDireccion());		
		// Realizamos el insert SQL sobre la BD,
		// retornando el _id del alumno insertado o -1 si error.
		return bdSQL.insert(TBL_ALUMNO, null, valores);
	}
	
	/**
	 * Borra de la BD un alumno.
	 * @param id _id del alumno que se quiere eliminar de la BD.
	 * @return Si se ha realizado la eliminación con éxito.
	 */
	public boolean deleteAlumno(long id) {
		// Realizamos el delete SQL sobre la BD, retornando si ha ido bien o no.
		return bdSQL.delete(TBL_ALUMNO, FLD_ALU_ID + " = " + id, null) > 0;
	}
	
	/**
	 * Actualiza en la BD los datos de un alumno.
	 * @param id _id del alumno que se quiere actualizar.
	 * @param nombre Nuevo nombre para el alumno.
	 * @param curso Nuevo curso para el alumno
	 * @param telefono Nuevo teléfono para el alumno
	 * @param direccion Nueva dirección para el alumno
	 * @return Si se ha realizado la actualización con éxito.
	 */
	public boolean updateAlumno(long id, String nombre, String curso, String telefono, String direccion) {
		// Creamos un la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(FLD_ALU_NOM, nombre);
		valores.put(FLD_ALU_CUR, curso);
		valores.put(FLD_ALU_TEL, telefono);
		valores.put(FLD_ALU_DIR, direccion);
		// Realizamos el update SQL sobre la BD, retornando si ha ido bien o no.		
		return bdSQL.update(TBL_ALUMNO, valores, FLD_ALU_ID + " = " + id, null) > 0;
	}

	/**
	 * Actualiza en la BD los datos de un alumno.
	 * @param alumno Objeto Alumno que contiene los datos del alumno.
	 * @return Si se ha realizado la actualización con éxito.
	 */
	public boolean updateAlumno(Alumno alumno) {
		// Creamos un la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(FLD_ALU_NOM, alumno.getNombre());
		valores.put(FLD_ALU_CUR, alumno.getCurso());
		valores.put(FLD_ALU_TEL, alumno.getTelefono());
		valores.put(FLD_ALU_DIR, alumno.getDireccion());
		// Realizamos el update SQL sobre la BD, retornando si ha bien o no.		
		return bdSQL.update(TBL_ALUMNO, valores, 
				FLD_ALU_ID + " = " + alumno.getId(), null) > 0;
	}

	/**
	 * Consulta en la BD todos los alumnos
	 * @return Cursor con todos los alumnos (puede ser null si no hay alumnos).
	 */
	public Cursor queryAllAlumnos() {
		// Realizamos la query SQL sobre la BD, retornando el cursor resultado.
		return bdSQL.query(TBL_ALUMNO, aluAllFields, null, null, null, null, FLD_ALU_NOM);
	}
	
	/**
	 * Consulta en la BD los datos de un alumno.
	 * @param id Identificador del alumno cuyos datos se quieren consultar.
	 * @return Cursor con el registro del alumno.
	 */
	public Cursor queryAlumno(long id) {
		// Realizo la query SQL sobre la BD.
		Cursor cursor = bdSQL.query(true, TBL_ALUMNO, aluAllFields, 
				FLD_ALU_ID + " = " + id, null, null, null, null, null);
		// Me muevo al primer registro del cursor.
		if (cursor != null)  cursor.moveToFirst();
		// Retorno el cursor.
		return cursor;
	}
	
	/**
	 * Crea un objeto Alumno a partir del registro actual de un cursor.
	 * @param cursorAlumno Cursor que contiene el registro de alumno
	 * @return objeto Alumno cargado con los datos del registro actual del cursor.
	 */
	public Alumno cursorToAlumno(Cursor cursorAlumno) { 
		// Creo un objeto Alumno y guardo los valores provenientes
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

	/**
	 * Obtiene una lista de objetos Alumno correspondientes a los
	 * alumnos de la BD.
	 * @return ArrayList de objetos Alumno
	 */
	public List<Alumno> getAlumnosList() {
		// Creo un ArrayList de alumnos.
		List<Alumno> lista = new ArrayList<Alumno>();
		// Consulto todos los alumnos en la BD y obtengo un cursor.
		Cursor cursor = this.queryAllAlumnos();
		// LLeno la lista convirtiendo cada registro del cursor 
		// en un elemento de la lista.
		cursor.moveToFirst(); 		
		while (!cursor.isAfterLast()) {
			Alumno comment = cursorToAlumno(cursor); 
			lista.add(comment); 
			cursor.moveToNext();
		}
		// Cierro el cursor (IMPORTANTE).
		cursor.close();
		// Retorno la lista.
		return lista;
	}

	/**
	 * Consulta en la BD los datos de un alumno y crea una descripción textual con ellos.
	 * @param id Identificador del alumno cuyos datos se quieren consultar.
	 * @return Descripción textual del alumno (vacía si el alumno no existe)
	 */
	public String queryAlumnoToString(long id) {
		String cadena = null;	// Cadena de alumno.
		// Realizo la consulta para obtener el cursor con el registro del alumno.
		// Ojo, puede lanzar la excepción SQLException, que propagamos.
		Cursor cursor = queryAlumno(id);
		// Me coloco en el primer registro del curso.
		// Si el curso no está vacío (moveToFirst retorna true), creo la cadena.
		if (cursor.moveToFirst()) {
			cadena =  contexto.getString(R.string.id_cab) + 
					cursor.getString(cursor.getColumnIndex(FLD_ALU_ID)) + "\n" +
					contexto.getString(R.string.alumno_cab) + 
					cursor.getString(cursor.getColumnIndex(FLD_ALU_NOM)) + "\n" +
					contexto.getString(R.string.curso_cab) + 
					cursor.getString(cursor.getColumnIndex(FLD_ALU_CUR)) + "\n" + 
					contexto.getString(R.string.telefono_cab) + 
					cursor.getString(cursor.getColumnIndex(FLD_ALU_TEL)) + "\n" + 
					contexto.getString(R.string.direccion_cab) + 
					cursor.getString(cursor.getColumnIndex(FLD_ALU_DIR));
		}
		// Retorno la cadena.
		return cadena;
	}
	
	/**
	 * Crea la descripción textual de un alumno.
	 * @param cursor Cursor cuyo registro actual corresonde al alumno
	 * @return Descripción textual del alumno (vacía si el alumno no existe)
	 * @throws IllegalArgumentException
	 */
	public String cursorToAlumnoString(Cursor cursor) throws IllegalArgumentException {
		String cadena = null;	// Cadena de alumno.
		// Creo la cadena a partir de los campos del registro actual del cursor.
		cadena =  contexto.getString(R.string.id_cab) + 
				cursor.getString(cursor.getColumnIndexOrThrow(FLD_ALU_ID)) + "\n" +
				contexto.getString(R.string.alumno_cab) + 
				cursor.getString(cursor.getColumnIndexOrThrow(FLD_ALU_NOM)) + "\n" +
				contexto.getString(R.string.curso_cab) + 
				cursor.getString(cursor.getColumnIndexOrThrow(FLD_ALU_CUR)) + "\n" + 
				contexto.getString(R.string.telefono_cab) + 
				cursor.getString(cursor.getColumnIndexOrThrow(FLD_ALU_TEL)) + "\n" + 
				contexto.getString(R.string.direccion_cab) + 
				cursor.getString(cursor.getColumnIndexOrThrow(FLD_ALU_DIR));
		// Retorno la cadena.
		return cadena;
	}
	
}
