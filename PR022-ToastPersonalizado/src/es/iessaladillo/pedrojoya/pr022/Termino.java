package es.iessaladillo.pedrojoya.pr022;

public class Termino {

    // Propiedades.
    private String pregunta;
    private Respuesta respuesta;

    public Termino(String pregunta, Respuesta respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
}
