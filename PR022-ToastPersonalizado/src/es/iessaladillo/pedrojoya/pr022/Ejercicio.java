package es.iessaladillo.pedrojoya.pr022;

import java.util.ArrayList;
import java.util.Collections;

public class Ejercicio {

    public static final int NUM_RESPUESTAS = 4;

    // Propiedades.
    private String pregunta;
    private ArrayList<Respuesta> respuestas;
    private int correcta;

    // Constructor.
    public Ejercicio(String pregunta, ArrayList<Respuesta> respuestas,
            int correcta) {
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
        return respuestas.get(i);
    }

    public int getCorrecta() {
        return correcta;
    }

    public void shuffleRespuestas() {
        Respuesta respuestaCorrecta = respuestas.get(correcta);
        Collections.shuffle(respuestas);
        correcta = respuestas.indexOf(respuestaCorrecta);
    }
}