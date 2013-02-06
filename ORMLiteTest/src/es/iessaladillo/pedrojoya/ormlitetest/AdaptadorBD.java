package es.iessaladillo.pedrojoya.ormlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class AdaptadorBD extends OrmLiteSqliteOpenHelper {

	// Constantes.
	private static final String DATABASE_NAME = "alumnado";	// Nombre de la BD.
	private static final int DATABASE_VERSION = 1;	// Versión de la BD.
	
	// Objetos DAO que usamos para acceder a la tabla alumnos
	private Dao<Alumno, Integer> alumnoDAO = null;
	private RuntimeExceptionDao<Alumno, Integer> alumnoRuntimeDAO = null;
	
	// Constructor.
	public AdaptadorBD(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Alumno.class);
		} catch (java.sql.SQLException e) {
			Log.e(AdaptadorBD.class.getName(), "No se puede crear la BD", e);
			throw new RuntimeException(e);
		}
	}	

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Alumno.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (java.sql.SQLException e) {
			Log.e(AdaptadorBD.class.getName(), "No puedo eliminar las tablas", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retorna el Database Access Object (DAO) para la clase Alumno.
	 * Si no existe lo creará.
	 * @throws java.sql.SQLException 
	 */
	public Dao<Alumno, Integer> getAlumnoDAO() throws java.sql.SQLException {
		if (alumnoDAO == null) {
			alumnoDAO = getDao(Alumno.class);
		}
		return alumnoDAO;
	}

	/**
	 * Returns la versión del objeto DAO de Alumno
	 * para usar sólo con RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Alumno, Integer> getAlumnoDataDAO() {
		if (alumnoRuntimeDAO == null) {
			alumnoRuntimeDAO = getRuntimeExceptionDao(Alumno.class);
		}
		return alumnoRuntimeDAO;
	}

	@Override
	public void close() {
		super.close();
		alumnoRuntimeDAO = null;
	}
	
}
