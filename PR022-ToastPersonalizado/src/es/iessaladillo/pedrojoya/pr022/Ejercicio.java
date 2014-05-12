package es.iessaladillo.pedrojoya.pr022;

public class Ejercicio {

    public static final int NUM_RESPUESTAS = 4;

    // Propiedades.
    private String pregunta;
    private Respuesta[] respuestas;
    private int correcta;

    // Constructor.
    public Ejercicio(String pregunta, Respuesta[] respuestas, int correcta) {
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.correcta = correcta;
    }

    // Getters and setters.
    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Respuesta getRespuesta(int i) {
        return respuestas[i];
    }

    public void setRespuesta(Respuesta respuesta, int i) {
        this.respuestas[i] = respuesta;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

}