package es.iessaladillo.pedrojoya.galileo.datos;

import com.parse.ParseObject;

public class Foto implements Comparable<Foto> {

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
        url = objeto.getParseFile(BD.Foto.ARCHIVO).getUrl();
        descripcion = objeto.getString(BD.Foto.DESCRIPCION);
        favoritos = objeto.getLong(BD.Foto.FAVORITOS);
    }

    // Escribe en un ParseObject las propiedades.
    public void to(ParseObject objeto) {
        objeto.put(BD.Foto.DESCRIPCION, descripcion);
        objeto.put(BD.Foto.FAVORITOS, favoritos);
    }

    @Override
    public int compareTo(Foto another) {
        // Las fotos se ordenan descendientemente en función de su descripción.
        return (descripcion.compareTo(another.descripcion) * -1);
    }

}
