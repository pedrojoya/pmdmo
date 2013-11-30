package es.iessaladillo.pedrojoya.cursogalileotareasemana1.contratos;

public class InstagramContrat {

    // Constantes.
    public final static String CLIENT_ID = "c432d0e158dd46f7873950a19a582102";
    public final static String BASE_URL = "https://api.instagram.com/v1";
    public final static String ARRAY_DATOS_KEY = "data";
    public final static String TIPO_ELEMENTO_KEY = "type";
    public final static String TIPO_ELEMENTO_IMAGEN = "image";
    public final static String USUARIO_KEY = "user";
    public final static String NOMBRE_USUARIO_KEY = "username";
    public final static String IMAGEN_KEY = "images";
    public final static String RESOLUCION_ESTANDAR_KEY = "standard_resolution";
    public final static String URL_KEY = "url";

    public static String getRecentMediaURL(String tag) {
        return BASE_URL + "/tags/" + tag + "/media/recent?client_id="
                + CLIENT_ID;
    }
}
