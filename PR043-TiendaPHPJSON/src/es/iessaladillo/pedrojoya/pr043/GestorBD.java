package es.iessaladillo.pedrojoya.pr043;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Crea, actualiza y permite el acceso a la base de datos.
public class GestorBD extends SQLiteOpenHelper {

	// Constantes.
	static final String BD_NOMBRE = "Tienda";
	static final int BD_VERSION = 1;
	static final String TBL_PRODUCTOS = "PRODUCTOS";
	static final String FLD_PRO_ID = "PRO_ID";
	static final String FLD_PRO_NOMBRE = "PRO_NOM";
	static final String FLD_PRO_DESCRIPCION = "PRO_DES";
	static final String FLD_PRO_IMAGEN = "PRO_IMA";
	static final String FLD_PRO_VENDIDAS = "PRO_VEN";
	private final String CREATE_T_PRODUCTOS = "CREATE TABLE PRODUCTOS (PRO_ID INTEGER, PRO_NOM TEXT, "
			+ "PRO_DES TEXT, PRO_IMA TEXT, PRO_VEN INTEGER);";
	private final String DROP_T_PRODUCTOS = "DROP TABLE IF EXISTS PRODUCTOS";

	// Constructor. Recibe el contexto.
	public GestorBD(Context contexto) {
		super(contexto, BD_NOMBRE, null, BD_VERSION);
	}

	// Cuando se debe crear la BD.
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Se ejecuta la sentencia SQL de creación de la tabla
		db.execSQL(CREATE_T_PRODUCTOS);
	}

	// Cuando se debe actualizar la BD.
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior,
			int versionNueva) {
		// Se elimina la versión anterior de la tabla
		db.execSQL(DROP_T_PRODUCTOS);
		// Se crea la nueva version de la tabla
		db.execSQL(CREATE_T_PRODUCTOS);
	}
}