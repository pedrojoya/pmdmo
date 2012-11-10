package es.iessaladillo.pedrojoya.pr014;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Clase para Adaptador de lista de alumnos.
class AdaptadorAlumno extends ArrayAdapter<Alumno> {

	// Variables miembro.
	Activity contexto;			// Actividad de la lista.
	ArrayList<Alumno> alumnos;	// Alumnos (datos). ArrayList para poder eliminar.
	LayoutInflater inflador;	// Inflador de layouts.
	int colorMenorDeEdad;		// Color para menor de edad.
	int colorMayorDeEdad;		// Color para mayor de edad.
	
    // Clase interna privada contenedor de vistas.
    private class ContenedorVistas {
		// Variables miembro.
    	TextView lblNombre;
    	TextView lblCiclo;
    	TextView lblCurso;
    }

    // Constructor. Recibe la actividad y los datos.
    public AdaptadorAlumno(Activity contexto, ArrayList<Alumno> alumnos) {
    	// Llamo al constructor del ArrayList.
		super(contexto, R.layout.fila, alumnos);
		// Guardo una copia local de los parámetros del constructor.
		this.contexto = contexto;
		this.alumnos = alumnos;
		// Obtengo un  objeto inflador de layouts.
		inflador = contexto.getLayoutInflater();
		// Obtengo los colores para el nombre del alumno de entre los recursos.
		Resources recursos = contexto.getResources();
		colorMenorDeEdad = recursos.getColor(R.color.rojo);
		colorMayorDeEdad = recursos.getColor(R.color.negro);
	}
    
    // Cuando se debe pintar un elemento de la lista.
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ContenedorVistas contenedor;
		// Creo la vista-fila y le asigno la de reciclar.
		View fila = convertView;
		// Si no puedo reciclar.
		if (convertView == null) {
			// Inflo el layout y obtengo la vista-fila.
			fila = inflador.inflate(R.layout.fila, null);
			// Creo un nuevo contenedor de vistas.
			contenedor = new ContenedorVistas();
			// Guardo en el contenedor las referencias a las vistas 
			// de dentro de la vista-fila.
			contenedor.lblNombre = (TextView) fila.findViewById(R.id.lblNombre);
			contenedor.lblCiclo = (TextView) fila.findViewById(R.id.lblCiclo);
			contenedor.lblCurso = (TextView) fila.findViewById(R.id.lblCurso);
			// Guardo el contenedor en la propiedad Tag de la vista-fila.
			fila.setTag(contenedor);
		}
		else { // Si puedo reciclar.
			// Obtengo el contenedor desde la prop. Tag de la vista-fila.
			contenedor = (ContenedorVistas) fila.getTag();
		}
		// Escribo los datos en las vistas de la fila.
		contenedor.lblNombre.setText(alumnos.get(position).getNombre());
		contenedor.lblCiclo.setText(alumnos.get(position).getCiclo());
		contenedor.lblCurso.setText(alumnos.get(position).getCurso());
		if (alumnos.get(position).getEdad() < 18) {
			contenedor.lblNombre.setTextColor(colorMenorDeEdad);
		}
		else {
			contenedor.lblNombre.setTextColor(colorMayorDeEdad);
		}
		// Retorno la vista-fila.
		return fila;
	}
}
