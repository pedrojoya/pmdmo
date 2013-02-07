package es.iessaladillo.pedrojoya.sqliteloader;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class AlumnosContentProvider extends ContentProvider {

	// Constantes.
	private static final String AUTORIDAD = "es.iessaladillo.alumnos";
	private static final String RUTA_BASE = "alumnos";
	public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD + "/"
			+ RUTA_BASE);
	public static final String TIPO_CONTENIDO = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/alumnos";
	public static final String TIPO_ITEM_CONTENIDO = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/alumno";
	private static final int TIPO_URI_TODOS = 10;
	private static final int TIPO_URI_ID = 20;
	private static final UriMatcher validadorURIs = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		validadorURIs.addURI(AUTORIDAD, RUTA_BASE, TIPO_URI_TODOS);
		validadorURIs.addURI(AUTORIDAD, RUTA_BASE + "/#", TIPO_URI_ID);
	}

	// Variables miembro.
	private SQLiteDatabase bd;

	// Retorna si todo ha ido bien.
	@Override
	public boolean onCreate() {
		// Obtengo la base de datos.
		GestorBD gestor = new GestorBD(getContext());
		bd = gestor.getWritableDatabase();
		// Retorno si se ha realizado la apertura.
		return bd != null && bd.isOpen();
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// Compruebo si el llamador ha solicitado una columna que no existe.
		comprobarColumnas(projection);
		// Inicializo la parte del where.
		String where = selection;
		// Dependiendo del tipo de uri solicitada.
		int tipoURI = validadorURIs.match(uri);
		switch (tipoURI) {
		case TIPO_URI_TODOS:
			break;
		case TIPO_URI_ID:
			// Agrego al where la selección de ese alumno.
			where = GestorBD.FLD_ALU_ID + "=" + uri.getLastPathSegment()
					+ (TextUtils.isEmpty(selection) ? "" : " and " + where);
			break;
		default:
			throw new IllegalArgumentException("URI desconoida: " + uri);
		}
		// Realizo la consulta.
		Cursor cursor = bd.query(GestorBD.TBL_ALUMNO, projection, where,
				selectionArgs, null, null, sortOrder);
		// Notifico a los escuchadores del content provider.
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int filasBorradas = 0;
		// Inicializo la parte del where.
		String where = selection;
		// Depndiendo del tipo de uri solicitada.
		int tipoURI = validadorURIs.match(uri);
		switch (tipoURI) {
		case TIPO_URI_TODOS:
			break;
		case TIPO_URI_ID:
			// Agrego al where la selección de ese alumno.
			where = GestorBD.FLD_ALU_ID + "=" + uri.getLastPathSegment()
					+ (TextUtils.isEmpty(selection) ? "" : " and " + where);
			break;
		default:
			throw new IllegalArgumentException("URI desconocida: " + uri);
		}
		// Se quieren borrar todos los alumnos.
		filasBorradas = bd.delete(GestorBD.TBL_ALUMNO, where, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return filasBorradas;
	}

	@Override
	public String getType(Uri uri) {
		// Dependiendo del tipo de uri solicitada.
		int tipoURI = validadorURIs.match(uri);
		switch (tipoURI) {
		case TIPO_URI_TODOS:
			return "vnd.android.cursor.dir/vnd.es.iessaladillo.alumnos";
		case TIPO_URI_ID:
			return "vnd.android.cursor.item/vnd.es.iessaladillo.alumnos";
		default:
			throw new IllegalArgumentException("URI desconocida: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = 0;
		// Sólo es válida la inserción en URI global de Alumnos.
		int tipoURI = validadorURIs.match(uri);
		switch (tipoURI) {
		case TIPO_URI_TODOS:
			id = bd.insert(GestorBD.TBL_ALUMNO, null, values);
			break;
		default:
			throw new IllegalArgumentException("URI desconocida: " + uri);
		}
		// Notifico a los escuchadores del content provider.
		getContext().getContentResolver().notifyChange(uri, null);
		// Retorno la URI del alumno insertado.
		return ContentUris.withAppendedId(URI_BASE, id);
//		return Uri.parse("content://" + AUTORIDAD + "/" + RUTA_BASE + "/" + id);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int filasActualizadas = 0;
		// Inicializo la parte del where.
		String where = selection;
		// Depndiendo del tipo de uri solicitada.
		int tipoURI = validadorURIs.match(uri);
		switch (tipoURI) {
		case TIPO_URI_TODOS:
			break;
		case TIPO_URI_ID:
			// Agrego al where la selección de ese alumno.
			where = GestorBD.FLD_ALU_ID + "=" + uri.getLastPathSegment()
					+ (TextUtils.isEmpty(selection) ? "" : " and " + where);
			break;
		default:
			throw new IllegalArgumentException("URI desconocida: " + uri);
		}
		// Se quieren borrar todos los alumnos.
		filasActualizadas = bd.update(GestorBD.TBL_ALUMNO, values, where, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return filasActualizadas;
	}

	private void comprobarColumnas(String[] columnas) {
		String[] disponibles = { GestorBD.FLD_ALU_NOM, GestorBD.FLD_ALU_TEL,
				GestorBD.FLD_ALU_DIR, GestorBD.FLD_ALU_CUR, GestorBD.FLD_ALU_ID };
		if (columnas != null) {
			HashSet<String> columnasSolicitadas = new HashSet<String>(
					Arrays.asList(columnas));
			HashSet<String> columnasDisponibles = new HashSet<String>(
					Arrays.asList(disponibles));
			// Si hay alguna solicitada no disponible se lanza excepción.
			if (!columnasDisponibles.containsAll(columnasSolicitadas)) {
				throw new IllegalArgumentException(
						"Se ha solicitado un campo desconocido");
			}
		}
	}
}
