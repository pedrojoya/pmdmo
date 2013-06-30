package es.iessaladillo.pedrojoya.pr027.adaptadores;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.modelos.Alumno;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Clase interna privada para Adaptador.
public class ListaAlumnosAdapter extends ArrayAdapter<Alumno> {

	// Variables miembro.
	ArrayList<Alumno> alumnos; // Alumnos (datos).
	LayoutInflater inflador; // Inflador de layout para la fila.

	// Clase interna privada contenedor de vistas.
	private class ContenedorVistas {
		// Variables miembro.
		TextView lblNombre;
		TextView lblTelefono;
		TextView lblCurso;
		TextView lblDireccion;
	}

	// Constructor.
	public ListaAlumnosAdapter(Context contexto, ArrayList<Alumno> alumnos) {
		// Llamo al constructor del padre (obligatorio).
		super(contexto, R.layout.fragment_lista_alumnos_item, alumnos);
		// Realizo la copia local de los datos.
		this.alumnos = alumnos;
		// Obtengo un objeto inflador de layouts.
		inflador = LayoutInflater.from(contexto);
	}

	@Override
	public View getView(final int posicion, View convertView,
			ViewGroup parent) {
		ContenedorVistas contenedor;
		// Creo la vista-fila y le asigno la de reciclar.
		View fila = convertView;
		// Si no puedo reciclar.
		if (convertView == null) {
			// Inflo el layout y obtengo la vista-fila.
			fila = inflador.inflate(R.layout.fragment_lista_alumnos_item, parent, false);
			// Creo un nuevo contenedor de vistas.
			contenedor = new ContenedorVistas();
			// Guardo en el contenedor las referencias a las vistas
			// de dentro de la vista-fila.
			contenedor.lblNombre = (TextView) fila
					.findViewById(R.id.lblNombre);
			contenedor.lblTelefono = (TextView) fila
					.findViewById(R.id.lblTelefono);
			contenedor.lblCurso = (TextView) fila
					.findViewById(R.id.lblCurso);
			contenedor.lblDireccion = (TextView) fila
					.findViewById(R.id.lblDireccion);
			// Guardo el contenedor en la propiedad Tag de la vista-fila.
			fila.setTag(contenedor);
		} else { // Si puedo reciclar.
			// Obtengo el contenedor desde la prop. Tag de la vista-fila.
			contenedor = (ContenedorVistas) fila.getTag();
		}
		// Escribo los datos en las vistas de la fila.
		Alumno alumno = alumnos.get(posicion);
		contenedor.lblNombre.setText(alumno.getNombre());
		contenedor.lblTelefono.setText(alumno.getTelefono());
		contenedor.lblCurso.setText(alumno.getCurso());
		contenedor.lblDireccion.setText(alumno.getDireccion());
		// Retorno la vista-fila
		return fila;
	}
}