package es.iessaladillo.pedrojoya.mensaje;

import java.io.Serializable;

// Importante: Esta clase debe estar en el mismo nombre de paquete tanto 
// en el cliente como en el servidor para lectura/escritura correcta.

@SuppressWarnings("serial")
public class Mensaje implements Serializable {

    // Propiedades.
    private String texto; // Texto enviado.
    private boolean finalConexion = false; // Indicador de final de la conexión.

    // Constructor.
    public Mensaje(String texto, boolean finalConexion) {
        this.texto = texto;
        this.finalConexion = finalConexion;
    }

    // Getters and setters.
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isFinalConexion() {
        return finalConexion;
    }

    public void setFinalConexion(boolean finalConexion) {
        this.finalConexion = finalConexion;
    }

}