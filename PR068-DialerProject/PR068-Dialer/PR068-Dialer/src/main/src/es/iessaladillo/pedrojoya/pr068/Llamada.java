package es.iessaladillo.pedrojoya.pr068;

import java.util.Date;

public class Llamada {

	// Constantes.
	public static final int LLAMADA_SALIENTE = 0;
	public static final int LLAMADA_ENTRANTE = 1;
	public static final int LLAMADA_PERDIDA = 2;

	// Propiedades.
	String numero;
	Date fecha;
	int tipo;

	// Constructor
	public Llamada(String numero, Date fecha, int tipo) {
		super();
		this.numero = numero;
		this.fecha = fecha;
		this.tipo = tipo;
	}

	// Getters and setters.
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
