package es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos;

public class Comentario {

    // Constantes.
    public static String TABLE_NAME = "Comentario";
    public static String FLD_TEXTO = "texto";
    public static String FLD_PARENT = "parent";

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