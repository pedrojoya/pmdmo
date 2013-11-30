package es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos;

import com.parse.ParseObject;

public class Foto implements Comparable<Foto> {

    // Constantes.
    public static String TABLE_NAME = "Foto";
    public static String FLD_OBJECTID = "objectId";
    public static String FLD_URL = "url";
    public static String FLD_ARCHIVO = "archivo";
    public static String FLD_DESCRIPCION = "descripcion";
    public static String FLD_FAVORITOS = "favoritos";

    // Propiedades.
    private String objectId;
    String url;
    String descripcion;
    long favoritos;

    // Constructores.
    public Foto(ParseObject objeto) {
        from(objeto);
    }

    public Foto() {
    }

    // Getters and setters.
    public String getObjectId() {
        return objectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(long favoritos) {
        this.favoritos = favoritos;
    }

    // Escribe en las propieades los datos de un ParseObject.
    public void from(ParseObject objeto) {
        objectId = objeto.getObjectId();
        url = objeto.getParseFile(FLD_ARCHIVO).getUrl();
        descripcion = objeto.getString(FLD_DESCRIPCION);
        favoritos = objeto.getLong(FLD_FAVORITOS);
    }

    // Escribe en un ParseObject las propiedades.
    public void to(ParseObject objeto) {
        objeto.put(FLD_DESCRIPCION, descripcion);
        objeto.put(FLD_FAVORITOS, favoritos);
    }

    @Override
    public int compareTo(Foto another) {
        // Las fotos se ordenan descendientemente en función de su descripción.
        return (descripcion.compareTo(another.descripcion) * -1);
    }

}
