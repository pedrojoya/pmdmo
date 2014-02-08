package es.iessaladillo.pedrojoya.pr088mensaje;

import java.io.Serializable;

// MUY IMPORTANTE: LA CLASE MENSAJE TIENE QUE ESTAR EN EL MISMO PAQUETE EN 
// AMBOS PROYECTOS PARA QUE LA SERIALIZACIÓN FUNCIONE CORRECTAMENTE.

// Clase modelo de datos del mensaje que se enviará a través del socket.
// Debe implementar la interfaz Serializable.
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