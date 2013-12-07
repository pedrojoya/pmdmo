package es.iessaladillo.pedrojoya.galileo.datos;

import android.content.ContentValues;

public class Imagen {

    private String url;
    private String username;
    private String thumbnail;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    // Retorna un ContentValues con los datos.
    public ContentValues toContentValues() {
        ContentValues objeto = new ContentValues();
        objeto.put(BD.Imagen.URL, url);
        objeto.put(BD.Imagen.USERNAME, username);
        objeto.put(BD.Imagen.THUMBNAIL, thumbnail);
        return objeto;
    }

}
