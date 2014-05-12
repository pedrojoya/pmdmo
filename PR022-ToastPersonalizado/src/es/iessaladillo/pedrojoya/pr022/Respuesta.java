package es.iessaladillo.pedrojoya.pr022;

public class Respuesta {

    // Propiedades.
    private String texto;
    private int resIdImagen;

    // Constructor.
    public Respuesta(String texto, int resIdImagen) {
        this.texto = texto;
        this.resIdImagen = resIdImagen;
    }

    // Getters y Setters.
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getResIdImagen() {
        return resIdImagen;
    }

    public void setResIdImagen(int resIdImagen) {
        this.resIdImagen = resIdImagen;
    }

}
