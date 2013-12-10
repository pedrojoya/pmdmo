package es.iessaladillo.pedrojoya.pr068;

import java.util.ArrayList;
import java.util.Date;

public class Registro {

	// Propiedades.
	private static ArrayList<Llamada> lista = new ArrayList<Llamada>();

	static {
		lista.add(0, new Llamada("678678678", new Date(),
				Llamada.LLAMADA_ENTRANTE));
		lista.add(0, new Llamada("656793482", new Date(),
				Llamada.LLAMADA_PERDIDA));
		lista.add(0, new Llamada("612654919", new Date(),
				Llamada.LLAMADA_SALIENTE));
	}

	// Constructor privado para que no se pueda instanciar.
	private Registro() {
	}

	// Métodos
	public static void registrar(Llamada llamada) {
		lista.add(0, llamada);
	}

	public static ArrayList<Llamada> getLlamadas() {
		return lista;
	}

}
