package es.iessaladillo.pedrojoya.pr028.proveedores;

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
import es.iessaladillo.pedrojoya.pr028.bd.DAO;
import es.iessaladillo.pedrojoya.pr028.bd.BD;

public class AlumnosContentProvider extends ContentProvider {

    // Constantes.
    private static final String AUTORIDAD = "es.iessaladillo.institutoprovider";
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

    // Retorna el tipo de uri recibida.
    @Override
    public String getType(Uri uri) {
        // Dependiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIPO_URI_TODOS:
            return "vnd.android.cursor.dir/vnd.es.iessaladillo.instituto.alumnos";
        case TIPO_URI_ID:
            return "vnd.android.cursor.item/vnd.es.iessaladillo.instituto.alumnos";
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
    }

    // Retorna si todo ha ido bien.
    @Override
    public boolean onCreate() {
        // Se obtiene el objeto DAO y se abre la base de datos.
        bd = new DAO(getContext()).open();
        // Se retorna si se ha realizado la apertura.
        return (bd != null && bd.isOpen());
    }

    // Consulta de alumnos. Retorna el cursor con el resultado de la consulta.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        // Se compueba si el llamador ha solicitado una columna que no existe.
        comprobarColumnas(projection);
        // Se inicializa la parte del where.
        String where = selection;
        // Dependiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIPO_URI_TODOS:
            break;
        case TIPO_URI_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Alumno._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se realiza la consulta.
        Cursor cursor = bd.query(BD.Alumno.TABLA, projection, where,
                selectionArgs, null, null, sortOrder);
        // Se notifica a los escuchadores del content provider.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Eliminación de alumnos. Retorna el número de registros eliminados.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int filasBorradas = 0;
        // Se inicializae la parte del where.
        String where = selection;
        // Dependiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIPO_URI_TODOS:
            break;
        case TIPO_URI_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Alumno._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se quieren borrar todos los alumnos.
        filasBorradas = bd.delete(BD.Alumno.TABLA, where, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return filasBorradas;
    }

    // Inserción de un alumno. Retorna la uri del nuevo alumno.
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        // Sólo es válida la inserción en URI global de Alumnos.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIPO_URI_TODOS:
            id = bd.insert(BD.Alumno.TABLA, null, values);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica a los escuchadores del content provider.
        getContext().getContentResolver().notifyChange(uri, null);
        // Se retorna la URI del alumno insertado.
        return ContentUris.withAppendedId(URI_BASE, id);
        // return Uri.parse("content://" + AUTORIDAD + "/" + RUTA_BASE + "/" +
        // id);
    }

    // Actualización de alumnos. Retorna el número de registros actualizados.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int filasActualizadas = 0;
        // Se inicializa la parte del where.
        String where = selection;
        // Depndiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIPO_URI_TODOS:
            break;
        case TIPO_URI_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Alumno._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se quieren borrar todos los alumnos.
        filasActualizadas = bd.update(BD.Alumno.TABLA, values, where,
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return filasActualizadas;
    }

    private void comprobarColumnas(String[] columnas) {
        String[] disponibles = BD.Alumno.TODOS;
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
