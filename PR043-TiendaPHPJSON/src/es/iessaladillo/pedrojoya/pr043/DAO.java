package es.iessaladillo.pedrojoya.pr043;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Crea, actualiza y permite el acceso a la base de datos.
public class DAO extends SQLiteOpenHelper {

	// Constantes.
	static final String BD_NOMBRE = "Tienda";
	static final int BD_VERSION = 1;
	static final String TBL_PRODUCTOS = "PRODUCTOS";
	static final String FLD_PRO_ID = "_id"; // MUY IMPORTANTE QUE SE LLAME ASÍ.
	static final String FLD_PRO_NOM = "pro_nom";
	static final String FLD_PRO_DES = "pro_des";
	static final String FLD_PRO_IMA = "pro_ima";
	static final String FLD_PRO_VEN = "pro_ven";
	private final String CREATE_T_PRODUCTOS = "CREATE TABLE PRODUCTOS ("
			+ FLD_PRO_ID + " INTEGER, " + FLD_PRO_NOM + " TEXT, " + FLD_PRO_DES
			+ " TEXT, " + FLD_PRO_IMA + " TEXT, " + FLD_PRO_VEN + " INTEGER);";
	public static final String[] PRO_TODOS = new String[] { FLD_PRO_ID,
			FLD_PRO_NOM, FLD_PRO_DES, FLD_PRO_IMA, FLD_PRO_VEN };
	private final String DROP_T_PRODUCTOS = "DROP TABLE IF EXISTS PRODUCTOS";

	// Constructor. Recibe el contexto.
	public DAO(Context contexto) {
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
		// Se elimina la versiÃ³n anterior de la tabla
		db.execSQL(DROP_T_PRODUCTOS);
		// Se crea la nueva version de la tabla
		db.execSQL(CREATE_T_PRODUCTOS);
	}
}