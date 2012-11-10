package es.iessaladillo.pedrojoya.pr012;

// Clase para modelar el alumno.
public class Alumno {
	// Variables miembro.
	private String nombre;
	private int edad;
	private String ciclo;
	private String curso;
	// Constructor.
	public Alumno(String nombre, int edad, String ciclo, String curso) {
		this.nombre = nombre;
		this.edad = edad;
		this.ciclo = ciclo;
		this.curso = curso;
	}
	// Getters y Setters.
	protected String getNombre() {
		return nombre;
	}
	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}
	protected int getEdad() {
		return edad;
	}
	protected void setEdad(int edad) {
		this.edad = edad;
	}
	protected String getCiclo() {
		return ciclo;
	}
	protected void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	protected String getCurso() {
		return curso;
	}
	protected void setCurso(String curso) {
		this.curso = curso;
	}
}
