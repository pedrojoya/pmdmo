package es.iessaladillo.pedrojoya.pr047.adaptadores;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr047.R;
import es.iessaladillo.pedrojoya.pr047.modelos.Album;

// Adaptador ArrayAdapter personalizado con ArrayList
public class AlbumesAdapter extends ArrayAdapter<Album> {

	// Variables miembro.
	Context contexto;
	ArrayList<Album> datos;
	LayoutInflater inflador;

	// Clase interna privada contenedor de vistas.
	private class ContenedorVistas {
		// TO-DO Definici�n de vistas de la fila.
		TextView lblNombre;
	}

	// Constructor. Recibe la actividad y los datos.
	public AlbumesAdapter(Context contexto, ArrayList<Album> datos) {
		// Llamo al constructor del ArrayList.
		super(contexto, R.layout.fragment_lista_item, datos);
		// Guardo una copia local de los par�metros del constructor.
		this.contexto = contexto;
		this.datos = datos;
		// Obtengo un objeto inflador de layouts.
		inflador = LayoutInflater.from(contexto);
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
			fila = inflador.inflate(R.layout.fragment_lista_item, null);
			// Creo un nuevo contenedor de vistas.
			contenedor = new ContenedorVistas();
			// TO-DO Guardo en el contenedor las referencias a las vistas
			// de dentro de la vista-fila.
			contenedor.lblNombre = (TextView) fila.findViewById(R.id.lblNombre);
			// Guardo el contenedor en la propiedad Tag de la vista-fila.
			fila.setTag(contenedor);
		} else { // Si puedo reciclar.
					// Obtengo el contenedor desde la prop. Tag de la
					// vista-fila.
			contenedor = (ContenedorVistas) fila.getTag();
		}
		// TO-DO Escribo los datos en las vistas de la fila.
		contenedor.lblNombre.setText(datos.get(position).getNombre());
		// Retorno la vista-fila.
		return fila;
	}
}