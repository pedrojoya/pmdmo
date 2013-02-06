package es.iessaladillo.pedrojoya.pr021;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// Clase adaptador del grid.
class AdaptadorAlbumes extends BaseAdapter {

	// Variables miembro.
	private Activity contexto; // Actividad que lo usa.
	private ArrayList<Album> albumes; // Array de datos.
	private LayoutInflater inflador; // Inflador de layouts.

	public AdaptadorAlbumes(Activity contexto, ArrayList<Album> albumes) {
		// Hago una copia de los parámetros del constructor.
		this.contexto = contexto;
		this.albumes = albumes;
		// Obtengo un objeto inflador de layouts.
		inflador = this.contexto.getLayoutInflater();
	}

	// Clase interna para contener las vistas.
	private class ContenedorVistas {
		ImageView imgFoto;
		TextView lblNombre;
		TextView lblAnio;
	}

	// Retorna cuántos elementos de datos maneja el adaptador.
	@Override
	public int getCount() {
		// Retorno el número de elementos del ArrayList.
		return albumes.size();
	}

	// Obtiene un dato.
	@Override
	public Object getItem(int position) {
		// Retorno el id de la imagen solicitada.
		return albumes.get(position);
	}

	// Obtiene el identificador de un dato.
	@Override
	public long getItemId(int position) {
		// No gestionamos ids.
		return 0;
	}

	// Cuando se va a pintar un elemento de la lista.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContenedorVistas contenedor; // Contenedor de vistas.
		// Intento reutilizar.
		View fila = convertView;
		if (fila == null) {
			// Inflo la vista-fila a partir de la especificación XML estándar de
			// Android.
			fila = inflador.inflate(R.layout.caja, null);
			// Creo un objeto contenedor con las referencias a las vistas
			// de la fila y lo almaceno en el Tag de la vista-fila.
			contenedor = new ContenedorVistas();
			contenedor.imgFoto = (ImageView) fila.findViewById(R.id.imgFoto);
			contenedor.lblNombre = (TextView) fila.findViewById(R.id.lblNombre);
			fila.setTag(contenedor);
		} else {
			// Obtengo el contenedor desde la propiedad Tag de la vista-fila.
			contenedor = (ContenedorVistas) fila.getTag();
		}
		// Escribo lo valores correspondientes de las vistas de la fila.
		Album album = albumes.get(position);
		contenedor.imgFoto.setImageResource(album.fotoResId);
		contenedor.lblNombre.setText(album.getNombre() + " (" + album.getAnio()
				+ ")");
		// Retorno la vista-fila.
		return fila;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ContenedorVistas contenedor; // Contenedor de vistas.
		// Intento reutilizar.
		View fila = convertView;
		if (fila == null) {
			// Inflo la vista-fila a partir de la especificación XML.
			fila = inflador.inflate(R.layout.fila, null);
			// Creo un objeto contenedor con las referencias a las vistas
			// de la fila y lo almaceno en el Tag de la vista-fila.
			contenedor = new ContenedorVistas();
			contenedor.imgFoto = (ImageView) fila.findViewById(R.id.imgFoto);
			contenedor.lblNombre = (TextView) fila.findViewById(R.id.lblNombre);
			contenedor.lblAnio = (TextView) fila.findViewById(R.id.lblAnio);
			fila.setTag(contenedor);
		} else {
			// Obtengo el contenedor desde la propiedad Tag de la vista-fila.
			contenedor = (ContenedorVistas) fila.getTag();
		}
		// Escribo lo valores correspondientes de las vistas de la fila.
		Album album = albumes.get(position);
		contenedor.imgFoto.setImageResource(album.getFotoResId());
		contenedor.lblNombre.setText(album.getNombre());
		contenedor.lblAnio.setText(album.getAnio());
		// Retorno la vista-fila.
		return fila;
	}

}
