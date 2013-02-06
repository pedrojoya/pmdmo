package test.Sockettest;

import java.io.Serializable;

// Clase modelo de datos del mensaje que se enviará a través del socket.
// Debe implementar la interfaz Serializable.
public class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 9178463713495654837L;
    public String texto;					// Texto enviado.
    public boolean finalConexion = false;	// Indicador de final de la conexiónn.
        
}
