package es.iessaladillo.pedrojoya.galileo.datos;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BD {

    // Constantes generales de la BD.
    public static final String DB_NAME = "galileo";
    public static final int DB_VERSION = 1;
    public static final String AUTHORITY = "es.iessaladillo.pedrojoya.galileo.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Tabla Tienda.
    public static abstract class Tienda implements BaseColumns {
        // Campos.
        public static final String TABLE_NAME = "Tienda";
        public static final String OBJECTID = "objectId";
        public static final String NOMBRE = "nombre";
        public static final String DIRECCION = "direccion";
        public static final String TELEFONO = "telefono";
        public static final String HORARIOS = "horarios";
        public static final String WEBSITE = "website";
        public static final String EMAIL = "email";
        public static final String FAVORITOS = "favoritos";
        public static final String UBICACION = "ubicacion";
        public static final String ARCHIVO_LOGO = "logo"; // Para Parse.
        public static final String URL_LOGO = "urlLogo";
        // SQL.
        public static final String[] ALL = new String[] { _ID, OBJECTID,
                NOMBRE, DIRECCION, TELEFONO, HORARIOS, WEBSITE, EMAIL,
                FAVORITOS, UBICACION, URL_LOGO };
        public static final String CREATE = "CREATE TABLE "
                + BD.Tienda.TABLE_NAME + " (" + BD.Tienda._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BD.Tienda.OBJECTID
                + " TEXT, " + BD.Tienda.NOMBRE + " TEXT, "
                + BD.Tienda.DIRECCION + " TEXT, " + BD.Tienda.TELEFONO
                + " TEXT, " + BD.Tienda.HORARIOS + " TEXT, "
                + BD.Tienda.WEBSITE + " TEXT, " + BD.Tienda.EMAIL + " TEXT, "
                + BD.Tienda.FAVORITOS + " INTEGER, " + BD.Tienda.UBICACION
                + " TEXT, " + BD.Tienda.URL_LOGO + " TEXT " + " )";
        public static final String DROP = "DROP TABLE IF EXISTS "
                + BD.Tienda.TABLE_NAME;
        // Content Provider.
        public static final String BASE_PATH = "tiendas";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BD.CONTENT_URI, BASE_PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.tiendas";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.tienda";
        public static final String SORT_ORDER_DEFAULT = NOMBRE + " ASC";
    }

    // Tabla Comentario.
    public static abstract class Comentario implements BaseColumns {
        // Campos.
        public static final String TABLE_NAME = "Comentario";
        public static final String OBJECTID = "objectId";
        public static final String TEXTO = "texto";
        public static final String PARENT = "parent";
        // SQL.
        public static final String[] ALL = new String[] { _ID, OBJECTID, TEXTO,
                PARENT };
        public static final String CREATE = "CREATE TABLE "
                + BD.Comentario.TABLE_NAME + " (" + BD.Comentario._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BD.Comentario.OBJECTID + " TEXT, " + BD.Comentario.TEXTO
                + " TEXT, " + BD.Comentario.PARENT + " TEXT " + " )";
        public static final String DROP = "DROP TABLE IF EXISTS "
                + BD.Comentario.TABLE_NAME;
        // Content Provider.
        public static final String BASE_PATH = "comentarios";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BD.CONTENT_URI, BASE_PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.comentarios";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.comentario";
        public static final String SORT_ORDER_DEFAULT = OBJECTID + " DESC";
    }

    // Tabla Foto.
    public static abstract class Foto implements BaseColumns {
        // Campos.
        public static final String TABLE_NAME = "Foto";
        public static final String OBJECTID = "objectId";
        public static final String URL = "url";
        public static final String ARCHIVO = "archivo"; // Para Parse.
        public static final String DESCRIPCION = "descripcion";
        public static final String FAVORITOS = "favoritos";
        // SQL.
        public static final String[] ALL = new String[] { _ID, OBJECTID, URL,
                DESCRIPCION, FAVORITOS };
        public static final String CREATE = "CREATE TABLE "
                + BD.Foto.TABLE_NAME + " (" + BD.Foto._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BD.Foto.OBJECTID
                + " TEXT, " + BD.Foto.URL + " TEXT, " + BD.Foto.DESCRIPCION
                + " TEXT, " + BD.Tienda.FAVORITOS + " INTEGER " + " )";
        public static final String DROP = "DROP TABLE IF EXISTS "
                + BD.Foto.TABLE_NAME;
        // Content Provider.
        public static final String BASE_PATH = "fotos";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BD.CONTENT_URI, BASE_PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.fotos";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.foto";
        public static final String SORT_ORDER_DEFAULT = DESCRIPCION + " DESC";
    }

    // Tabla ImagenInstagram.
    public static abstract class Imagen implements BaseColumns {
        // Campos.
        public static final String TABLE_NAME = "Imagen";
        public static final String URL = "url";
        public static final String USERNAME = "username";
        // SQL.
        public static final String[] ALL = new String[] { _ID, URL, USERNAME };
        public static final String CREATE = "CREATE TABLE "
                + BD.Imagen.TABLE_NAME + " (" + BD.Imagen._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BD.Imagen.URL
                + " TEXT, " + BD.Imagen.USERNAME + " TEXT " + " )";
        public static final String DROP = "DROP TABLE IF EXISTS "
                + BD.Imagen.TABLE_NAME;
        // Content Provider.
        public static final String BASE_PATH = "imagenes";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                BD.CONTENT_URI, BASE_PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.imagenes";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.es.iessaladillo.galileo.imagen";
        public static final String SORT_ORDER_DEFAULT = _ID + " DESC";
    }

    // Constructor privado para que NO pueda instanciarse.
    private BD() {
    }

    // Comprueba si todas las columnas están entre las disponibles.
    public static void checkColumns(String[] disponibles, String[] columnas) {
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
