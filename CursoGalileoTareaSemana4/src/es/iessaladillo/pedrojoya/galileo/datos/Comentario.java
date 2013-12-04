package es.iessaladillo.pedrojoya.galileo.datos;

import android.content.ContentValues;

import com.parse.ParseObject;

public class Comentario {

    // Propiedades.
    private String objectId;
    private String texto;
    private String parent;

    // Constructores.
    public Comentario(String texto) {
        this.texto = texto;
    }

    public Comentario(ParseObject objeto) {
        from(objeto);
    }

    public Comentario() {
    }

    // Getters and Setters.
    public String getObjectId() {
        return objectId;
    }

    public String getTexto() {
        return texto;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    // Getters and setters.
    public void setTexto(String texto) {
        this.texto = texto;
    }

    // Escribe en las propieades los datos de un ParseObject.
    public void from(ParseObject objeto) {
        objectId = objeto.getObjectId();
        texto = objeto.getString(BD.Comentario.TEXTO);
        parent = objeto.getString(BD.Comentario.PARENT);
    }

    // Retorna un ContentValues con los datos.
    public ContentValues toContentValues() {
        ContentValues objeto = new ContentValues();
        objeto.put(BD.Comentario.OBJECTID, objectId);
        objeto.put(BD.Comentario.TEXTO, texto);
        objeto.put(BD.Comentario.PARENT, parent);
        return objeto;
    }

}