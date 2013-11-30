package es.iessaladillo.pedrojoya.galileo.datos;

public class Comentario {

    // Propiedades.
    private String texto;

    // Constructores.
    public Comentario(String texto) {
        this.texto = texto;
    }

    public Comentario() {
    }

    public String getTexto() {
        return texto;
    }

    // Getters and setters.
    public void setTexto(String texto) {
        this.texto = texto;
    }

}