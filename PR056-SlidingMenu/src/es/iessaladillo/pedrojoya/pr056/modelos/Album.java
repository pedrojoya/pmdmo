package es.iessaladillo.pedrojoya.pr056.modelos;

// Clase modelo de datos de álbum
public class Album {

    // Propiedades.
    int fotoResId;
    String nombre;
    String anio;

    // Constructores.
    public Album(int fotoResId, String nombre, String anio) {
        this.fotoResId = fotoResId;
        this.nombre = nombre;
        this.anio = anio;
    }

    public Album() {
    }

    // Getters y Setters.
    public int getFotoResId() {
        return fotoResId;
    }

    public void setFotoResId(int fotoResId) {
        this.fotoResId = fotoResId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

}
