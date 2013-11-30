package es.iessaladillo.pedrojoya.galileo.datos;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class BDContentProvider extends ContentProvider {

    // Constantes.
    private static final int TIENDA_LIST = 1;
    private static final int TIENDA_ID = 2;
    private static final int COMENTARIO_LIST = 3;
    private static final int COMENTARIO_ID = 4;
    private static final int FOTO_LIST = 5;
    private static final int FOTO_ID = 6;
    private static final int IMAGEN_LIST = 7;
    private static final int IMAGEN_ID = 8;

    private static final UriMatcher validadorURIs = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        validadorURIs.addURI(BD.AUTHORITY, BD.Tienda.BASE_PATH, TIENDA_LIST);
        validadorURIs.addURI(BD.AUTHORITY, BD.Tienda.BASE_PATH + "/#",
                TIENDA_ID);
        validadorURIs.addURI(BD.AUTHORITY, BD.Comentario.BASE_PATH,
                COMENTARIO_LIST);
        validadorURIs.addURI(BD.AUTHORITY, BD.Comentario.BASE_PATH + "/#",
                COMENTARIO_ID);
        validadorURIs.addURI(BD.AUTHORITY, BD.Foto.BASE_PATH, FOTO_LIST);
        validadorURIs.addURI(BD.AUTHORITY, BD.Foto.BASE_PATH + "/#", FOTO_ID);
        validadorURIs.addURI(BD.AUTHORITY, BD.Imagen.BASE_PATH, IMAGEN_LIST);
        validadorURIs.addURI(BD.AUTHORITY, BD.Imagen.BASE_PATH + "/#",
                IMAGEN_ID);
    }

    // Variables miembro.
    private BDHelper helper;

    // Retorna el tipo de uri recibida.
    @Override
    public String getType(Uri uri) {
        // Dependiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIENDA_LIST:
            return BD.Tienda.CONTENT_TYPE;
        case TIENDA_ID:
            return BD.Tienda.CONTENT_ITEM_TYPE;
        case COMENTARIO_LIST:
            return BD.Comentario.CONTENT_TYPE;
        case COMENTARIO_ID:
            return BD.Comentario.CONTENT_ITEM_TYPE;
        case FOTO_LIST:
            return BD.Foto.CONTENT_TYPE;
        case FOTO_ID:
            return BD.Foto.CONTENT_ITEM_TYPE;
        case IMAGEN_LIST:
            return BD.Imagen.CONTENT_TYPE;
        case IMAGEN_ID:
            return BD.Imagen.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
    }

    // Retorna si todo ha ido bien.
    @Override
    public boolean onCreate() {
        // Se crea el helper para el acceso a la bd.
        helper = new BDHelper(getContext());
        return true;
    }

    // Consulta. Retorna el cursor con el resultado.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        // Se abre la base de datos.
        SQLiteDatabase bd = helper.getReadableDatabase();
        // Se crea un constructor de consultas.
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // Dependiendo de la uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIENDA_LIST:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Tienda.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Tienda.TABLE_NAME);
            // Si no se ha especificado orden se usa el por defecto.
            if (TextUtils.isEmpty(sortOrder)) {
                sortOrder = BD.Tienda.SORT_ORDER_DEFAULT;
            }
            break;
        case TIENDA_ID:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Tienda.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Tienda.TABLE_NAME);
            // Se agrega al where la selección de ese alumno.
            builder.appendWhere(BD.Tienda._ID + " = "
                    + uri.getLastPathSegment());
            break;
        case COMENTARIO_LIST:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Comentario.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Comentario.TABLE_NAME);
            // Si no se ha especificado orden se usa el por defecto.
            if (TextUtils.isEmpty(sortOrder)) {
                sortOrder = BD.Comentario.SORT_ORDER_DEFAULT;
            }
            break;
        case COMENTARIO_ID:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Comentario.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Comentario.TABLE_NAME);
            // Se agrega al where la selección de ese alumno.
            builder.appendWhere(BD.Comentario._ID + " = "
                    + uri.getLastPathSegment());
            break;
        case FOTO_LIST:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Foto.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Foto.TABLE_NAME);
            // Si no se ha especificado orden se usa el por defecto.
            if (TextUtils.isEmpty(sortOrder)) {
                sortOrder = BD.Foto.SORT_ORDER_DEFAULT;
            }
            break;
        case FOTO_ID:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Foto.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Foto.TABLE_NAME);
            // Se agrega al where la selección de ese alumno.
            builder.appendWhere(BD.Foto._ID + " = " + uri.getLastPathSegment());
            break;
        case IMAGEN_LIST:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Imagen.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Imagen.TABLE_NAME);
            // Si no se ha especificado orden se usa el por defecto.
            if (TextUtils.isEmpty(sortOrder)) {
                sortOrder = BD.Imagen.SORT_ORDER_DEFAULT;
            }
            break;
        case IMAGEN_ID:
            // Se compueba si el llamador ha solicitado una columna que no
            // existe.
            BD.checkColumns(BD.Imagen.ALL, projection);
            // Se establece la tabla para la consulta.
            builder.setTables(BD.Imagen.TABLE_NAME);
            // Se agrega al where la selección de ese alumno.
            builder.appendWhere(BD.Imagen._ID + " = "
                    + uri.getLastPathSegment());
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se realiza la consulta.
        Cursor cursor = builder.query(bd, projection, selection, selectionArgs,
                null, null, sortOrder);
        // Se notifica a los escuchadores del content provider.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Eliminación. Retorna el número de registros eliminados.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int filasBorradas = 0;
        // Se inicializa la selección.
        String where = selection;
        // Se obtiene la base de datos.
        SQLiteDatabase bd = helper.getWritableDatabase();
        // Dependiendo de la uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIENDA_LIST:
            filasBorradas = bd.delete(BD.Tienda.TABLE_NAME, where,
                    selectionArgs);
            break;
        case TIENDA_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Tienda._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasBorradas = bd.delete(BD.Tienda.TABLE_NAME, where,
                    selectionArgs);
            break;
        case COMENTARIO_LIST:
            filasBorradas = bd.delete(BD.Comentario.TABLE_NAME, where,
                    selectionArgs);
            break;
        case COMENTARIO_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Comentario._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasBorradas = bd.delete(BD.Comentario.TABLE_NAME, where,
                    selectionArgs);
            break;
        case FOTO_LIST:
            filasBorradas = bd.delete(BD.Foto.TABLE_NAME, where, selectionArgs);
            break;
        case FOTO_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Foto._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasBorradas = bd.delete(BD.Foto.TABLE_NAME, where, selectionArgs);
            break;
        case IMAGEN_LIST:
            filasBorradas = bd.delete(BD.Imagen.TABLE_NAME, where,
                    selectionArgs);
            break;
        case IMAGEN_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Imagen._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasBorradas = bd.delete(BD.Imagen.TABLE_NAME, where,
                    selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica de los cambios a todos los listener.
        if (filasBorradas > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return filasBorradas;
    }

    // Inserción. Retorna la uri del nuevo registro.
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        // Se obtiene la base de datos.
        SQLiteDatabase bd = helper.getWritableDatabase();
        // Dependiendo de la uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIENDA_LIST:
            id = bd.insert(BD.Tienda.TABLE_NAME, null, values);
            break;
        case COMENTARIO_LIST:
            id = bd.insert(BD.Comentario.TABLE_NAME, null, values);
            break;
        case FOTO_LIST:
            id = bd.insert(BD.Foto.TABLE_NAME, null, values);
            break;
        case IMAGEN_LIST:
            id = bd.insert(BD.Imagen.TABLE_NAME, null, values);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica a los escuchadores del content provider.
        getContext().getContentResolver().notifyChange(uri, null);
        // Se retorna la URI del registro insertado.
        return ContentUris.withAppendedId(uri, id);
    }

    // Actualización. Retorna el número de registros actualizados.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        int filasActualizadas = 0;
        // Se inicializa la parte del where.
        String where = selection;
        // Se obtiene la base de datos.
        SQLiteDatabase bd = helper.getWritableDatabase();
        // Depndiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
        case TIENDA_LIST:
            filasActualizadas = bd.update(BD.Tienda.TABLE_NAME, values, where,
                    selectionArgs);
            break;
        case TIENDA_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Tienda._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasActualizadas = bd.update(BD.Tienda.TABLE_NAME, values, where,
                    selectionArgs);
            break;
        case COMENTARIO_LIST:
            filasActualizadas = bd.update(BD.Comentario.TABLE_NAME, values,
                    where, selectionArgs);
            break;
        case COMENTARIO_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Comentario._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasActualizadas = bd.update(BD.Comentario.TABLE_NAME, values,
                    where, selectionArgs);
            break;
        case FOTO_LIST:
            filasActualizadas = bd.update(BD.Foto.TABLE_NAME, values, where,
                    selectionArgs);
            break;
        case FOTO_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Foto._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasActualizadas = bd.update(BD.Foto.TABLE_NAME, values, where,
                    selectionArgs);
            break;
        case IMAGEN_LIST:
            filasActualizadas = bd.update(BD.Imagen.TABLE_NAME, values, where,
                    selectionArgs);
            break;
        case IMAGEN_ID:
            // Se agrega al where la selección de ese alumno.
            where = BD.Imagen._ID + "=" + uri.getLastPathSegment()
                    + (TextUtils.isEmpty(selection) ? "" : " and " + where);
            filasActualizadas = bd.update(BD.Imagen.TABLE_NAME, values, where,
                    selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica a los listeners.
        if (filasActualizadas > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Se retorna el número de registros actualizados.
        return filasActualizadas;
    }

}
