package es.iessaladillo.pedrojoya.pr088mensaje;

import java.io.Serializable;
import java.util.Date;

// MUY IMPORTANTE: LA CLASE MENSAJE TIENE QUE ESTAR EN EL MISMO PAQUETE EN 
// AMBOS PROYECTOS PARA QUE LA SERIALIZACIÓN FUNCIONE CORRECTAMENTE.

// Mensaje del chat. Debe implementar la interfaz Serializable.
@SuppressWarnings("serial")
public class Mensaje implements Serializable {

    // Propiedades.
    private String texto;
    private String autor;
    private Date fecha;
    private boolean fin = false;

    // Constructor.
    public Mensaje(String texto, String autor, Date fecha, boolean fin) {
        this.texto = texto;
        this.autor = autor;
        this.fecha = fecha;
        this.fin = fin;
    }

    // Getters and setters.
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

}