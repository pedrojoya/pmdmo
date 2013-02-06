package es.iessaladillo.pedrojoya.sqliteloader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Gestiona las operaciones de creación, upgrade, apertura y cierre de la BD. Se
 * sobrescriben onCreate y onUpgrade para personalizar la clase SQLiteOpenHelper
 * adaptándola a las características propias de la BD que se quiere gestionar
 */
class GestorBD extends SQLiteOpenHelper {

	// CONSTANTES DE LA BASE DE DATOS.

	// Generales.
	public static final String DATABASE_NAME = "gestionCentros"; // Nombre BD.
	private static final int DATABASE_VERSION = 1; // Versión de la BD.

	// Tabla Alumno.
	public static final String TBL_ALUMNO = "Alumno";
	public static final String FLD_ALU_ID = "_id"; // Autonumérico.
	public static final String FLD_ALU_NOM = "nombre";
	public static final String FLD_ALU_CUR = "curso";
	public static final String FLD_ALU_TEL = "telefono";
	public static final String FLD_ALU_DIR = "direccion";
	public static final String[] ALU_TODOS = new String[] { FLD_ALU_ID, FLD_ALU_NOM,
			FLD_ALU_CUR, FLD_ALU_TEL, FLD_ALU_DIR };

	// SQL.
	private static final String TBL_ALUMNO_CREATE = "create table "
			+ TBL_ALUMNO + "(" + FLD_ALU_ID
			+ " integer primary key autoincrement, " + FLD_ALU_NOM
			+ " text not null, " + FLD_ALU_CUR + " text not null, "
			+ FLD_ALU_TEL + " text not null, " + FLD_ALU_DIR + " text);";

	// Constructor. Recibe el contexto.
	GestorBD(Context ctx) {
		// Llamo al constructor del padre, que es quien realmente crea o
		// actualiza la versión de BD si es necesario.
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Método de callback para cuando se ha creado la BD.
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Ejecuto las sentencias SQL de creación de las tablas de la BD.
		db.execSQL(TBL_ALUMNO_CREATE);
	}

	// Método de callback para cuando la BD debe se actualizada de versión
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public static Alumno cursorToAlumno(Cursor cursorAlumno) {
		// Creo un objeto Alumno y guardo los valores provenientes
		// del registro actual del cursor.
		Alumno alumno = new Alumno();
		alumno.setId(cursorAlumno.getLong(cursorAlumno.getColumnIndexOrThrow(FLD_ALU_ID)));
		alumno.setNombre(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(FLD_ALU_NOM)));
		alumno.setCurso(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(FLD_ALU_CUR)));
		alumno.setTelefono(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(FLD_ALU_TEL)));
		alumno.setDireccion(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(FLD_ALU_DIR)));
		// Retorno el objeto Alumno.
		return alumno;
		
	}
	
	public static ContentValues alumnoToContentValues(Alumno alumno) {
		// Creamos un la lista de pares clave-valor con cada campo-valor.
		ContentValues valores = new ContentValues();
		valores.put(FLD_ALU_NOM, alumno.getNombre());
		valores.put(FLD_ALU_CUR, alumno.getCurso());
		valores.put(FLD_ALU_TEL, alumno.getTelefono());
		valores.put(FLD_ALU_DIR, alumno.getDireccion());
		return valores;
	}
	
}
