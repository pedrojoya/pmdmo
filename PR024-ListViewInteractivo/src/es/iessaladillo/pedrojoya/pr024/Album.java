package es.iessaladillo.pedrojoya.pr024;

// Clase modelo de datos de album musical.
public class Album {
	// Propiedades.
	private int fotoResId;
	private String nombre;
	private String anio;
	private float valoracion;

	// Constructor.
	public Album(int fotoResId, String nombre, String anio, float valoracion) {
		this.fotoResId = fotoResId;
		this.nombre = nombre;
		this.anio = anio;
		this.valoracion = valoracion;
	}

	protected int getFotoResId() {
		return fotoResId;
	}

	protected void setFotoResId(int fotoResId) {
		this.fotoResId = fotoResId;
	}

	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected String getAnio() {
		return anio;
	}

	protected void setAnio(String anio) {
		this.anio = anio;
	}

	protected float getValoracion() {
		return valoracion;
	}

	protected void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}
}
