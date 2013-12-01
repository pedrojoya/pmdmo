package es.iessaladillo.pedrojoya.galileo.datos;

import android.content.ContentValues;

public class Imagen {

    private String url;
    private String username;

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

    // Retorna un ContentValues con los datos.
    public ContentValues toContentValues() {
        ContentValues objeto = new ContentValues();
        objeto.put(BD.Imagen.URL, url);
        objeto.put(BD.Imagen.USERNAME, username);
        return objeto;
    }

}
