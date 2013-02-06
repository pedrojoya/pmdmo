package es.iessaladillo.pedrojoya.ormlitetest;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AlumnoListAdapter extends android.widget.BaseAdapter {

	// Propiedades.
	private Activity contexto;	// Necesito contexto Activity para obtener inflador. 
	private List<Alumno> lista;	// Lista de datos que manejará el adaptador.
	
	static class ContenedorVistas {
		public TextView text1;
	}
	
	// Constructor.
	public AlumnoListAdapter(Activity contexto, List<Alumno> lista) {
		// Copio los valores a las propiedades.
		this.contexto = contexto;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		// Retorno el número de elementos de la lista.
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		// Retorno el objeto correspondiente a dicha posición en la lista.
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		// Retorno el id del alumno situado en la lista en dicha posición.
		return lista.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContenedorVistas contenedor;
		// Intento reutilizar una vista-fila existente.
		View fila = convertView;
		// Si tengo que crear una nueva.
		if (fila == null) {
			// Inflo el layout obteniendo la vista que representa la fila.
			LayoutInflater inflador = contexto.getLayoutInflater();
			fila = inflador.inflate(android.R.layout.simple_list_item_1, null);
			// Creo un nuevo contenedor de vistas, guardo en él las referencias de 
			// las vistas de la fila y lo almaceno en la propiedad Tag de la vista-fila.
			contenedor = new ContenedorVistas();
			contenedor.text1 = (TextView) fila.findViewById(android.R.id.text1);
			fila.setTag(contenedor);
		}
		else {
			// Obtengo el contenedor de vista desde la propiedad Tag de
			// la vista-fila reutilizada.
			contenedor = (ContenedorVistas) fila.getTag();			
		}
		// Escribo el nombre del alumno correspondiente a dicha fila del ListView
		// en el TextView de la vista-fila.
		String nombre = lista.get(position).getNombre();
		contenedor.text1.setText(nombre);
		// Retorno la vista-fila.
		return fila;
	}
}
