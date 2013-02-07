package es.iessaladillo.pedrojoya.sqliteloader;

public class Alumno {

	// Propiedades.
	long id;
	String nombre;
	String telefono;
	String curso;
	String direccion;

	// Constructores

	/**
	 * Constructor de Alumno (con id)
	 * 
	 * @param id
	 *            Id del alumno.
	 * @param nombre
	 *            Nombre del alumno.
	 * @param telefono
	 *            Teléfono del alumno.
	 * @param curso
	 *            Curso del alumno.
	 */
	Alumno(long id, String nombre, String telefono, String curso,
			String direccion) {
		// Establezo los valores iniciales para las propiedades
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.curso = curso;
		this.direccion = direccion;
	}

	/**
	 * Constructor de Alumno (sin id)
	 * 
	 * @param nombre
	 *            Nombre del alumno.
	 * @param telefono
	 *            Teléfono del alumno.
	 * @param curso
	 *            Curso del alumno.
	 */
	Alumno(String nombre, String telefono, String curso, String direccion) {
		// Establezo los valores iniciales para las propiedades
		this.id = 0;
		this.nombre = nombre;
		this.telefono = telefono;
		this.curso = curso;
		this.direccion = direccion;
	}

	/**
	 * Constructor de Alumno (sin datos)
	 */
	Alumno() {
		// Establezo los valores iniciales para las propiedades
		this.id = 0;
		this.nombre = null;
		this.telefono = null;
		this.curso = null;
		this.direccion = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	// Getters y Setters de las propiedades.
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
