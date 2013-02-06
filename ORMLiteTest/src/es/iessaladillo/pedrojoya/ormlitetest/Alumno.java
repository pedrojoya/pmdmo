package es.iessaladillo.pedrojoya.ormlitetest;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="alumno")
public class Alumno {

	// Campos.
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    String nombre;
    @DatabaseField
    String telefono;
    @DatabaseField
    String curso;
	
	// Constructor por defecto usado por ORMLite.
	Alumno(){
    }
	
	// Constructor.
	Alumno (String nombre, String telefono, String curso) {
		// Establezo los valores iniciales para las propiedades
		this.nombre = nombre;
		this.telefono = telefono;
		this.curso = curso;
	}

	// Getters y Setters de las propiedades.

	public int getId (){
		return id;
	}

	public void setId (int id){
		this.id = id;
	}

	public String getNombre(){
		return nombre;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public String  getTelefono (){
		return telefono;
	}

	public void setTelefono (String telefono){
		this.telefono = telefono;
	}
	
	public String getCurso(){
		return curso;
	}

	public void setCurso(String curso){
		this.curso = curso;
	}
}
