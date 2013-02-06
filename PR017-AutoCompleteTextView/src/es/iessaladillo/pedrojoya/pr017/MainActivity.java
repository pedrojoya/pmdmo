package es.iessaladillo.pedrojoya.pr017;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables a nivel de clase.
	private AutoCompleteTextView txtAlbum;

	// Clase privada para adaptador de albums
	// Extendemos de ArrayAdapter porque éste hereda de ListAdapter
	// y además implementa la intefaz Filterable,
	// como requiere el AutoCompleteTextView.
	private class AdaptadorAlbumes extends ArrayAdapter<Album> {

		// Variables miembro.
		private ArrayList<Album> albumes; // Se verá modificado por el filtro.
		private ArrayList<Album> todos; // Todos los álbumes manejados.
		private ArrayList<Album> sugerencias; // Sugerencias para el filtro.

		// Clase privada para contenedor de vistas.
		private class ContenedorVistas {
			// Variables miembro.
			ImageView imgFoto;
			TextView lblNombre;
			TextView lblAnio;
		}

		@SuppressWarnings("unchecked")
		public AdaptadorAlbumes(Context contexto, ArrayList<Album> albumes) {
			// Llamo al constructor del padre. El segundo parámetro es exigido
			// aunque no sirve para nada porque se sobrecarga getView().
			super(contexto, R.layout.fila, R.id.lblAlbum, albumes);
			// Hago las copias locales.
			this.albumes = albumes;
			this.todos = (ArrayList<Album>) this.albumes.clone(); // Copia del
																	// original.
			this.sugerencias = new ArrayList<Album>();
		}

		// Obtiene el objeto Filter que va a filtrar el adaptador.
		@Override
		public Filter getFilter() {
			// Retorno un nuevo filtro subclase de Filter (inline).
			return new Filter() {
				// Se ejecuta cuando se debe filtrar. Recibe la cadena ya
				// introducida.
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					// Si ya se ha introducido texto.
					if (constraint != null) {
						// Vacío las sugerencias.
						sugerencias.clear();
						// Compruebo uno a uno todos los álbumes por si debemos
						// incluirlo en las sugerencias.
						// OJO compruebo en el ArrayList con TODOS los álbumes.
						for (Album album : todos) {
							// Si comienza igual.
							if (album
									.getNombre()
									.toLowerCase(Locale.getDefault())
									.contains(
											constraint.toString().toLowerCase(
													Locale.getDefault()))) {
								sugerencias.add(album);
							}
						}
						// Creo el objeto resultado del filtro y lo retorno.
						FilterResults filterResults = new FilterResults();
						filterResults.values = sugerencias; // Valores de las
															// sugerencias.
						filterResults.count = sugerencias.size(); // Número de
																	// sugerencias.
						return filterResults;
					} else {
						// Si no se ha introducido texto retorno un resultado
						// vacío.
						return new FilterResults();
					}
				}

				// Se encarga de publicar el resultado seleccionado en el
				// widget.
				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					// Si hay sugerencias.
					if (results != null && results.count > 0) {
						// Las obtengo.
						@SuppressWarnings("unchecked")
						ArrayList<Album> albumesFiltrados = (ArrayList<Album>) results.values;
						// Lleno el array de álbumes con la lista de álbumes
						// filtrados.
						// OJO! Se debe usar clear() y add() del adaptador, para
						// modificar
						// el array de datos que maneja el adaptador.
						clear();
						for (Album album : albumesFiltrados) {
							add(album);
						}
						// Notifico que se han producido cambios en el conjunto
						// de datos
						// para que la vistas se repinten. (¿obligatorio en este
						// caso?)
						notifyDataSetChanged();
					}

				}

				// Retorna la cadena que debe escribirse en el widget. Recibe el
				// objeto seleccionado
				@Override
				public CharSequence convertResultToString(Object resultValue) {
					// Se escribirá el nombre del álbum y el anio.
					Album album = (Album) resultValue;
					return album.getNombre() + " (" + album.getAnio() + ")";
				}

			};
		}

		// Obtiene la vista-fila que se va a mostrar para un elemento dada su
		// posición.
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Contenedor de vistas.
			ContenedorVistas contenedor;
			// Intento reciclar.
			View fila = convertView;
			if (fila == null) {
				// Inflo el layout.
				fila = getLayoutInflater().inflate(R.layout.fila, null);
				// Creo el contenedor de vistas.
				contenedor = new ContenedorVistas();
				contenedor.imgFoto = (ImageView) fila
						.findViewById(R.id.imgFoto);
				contenedor.lblNombre = (TextView) fila
						.findViewById(R.id.lblNombre);
				contenedor.lblAnio = (TextView) fila.findViewById(R.id.lblAnio);
				fila.setTag(contenedor);
			} else {
				// Obtengo el contenedor.
				contenedor = (ContenedorVistas) fila.getTag();
			}
			// Escribo los datos en las vistas
			// OJO! usar getItem del Adaptador para obtener el album
			// correspondiente
			// ya que el array de datos del adaptador se está viendo modificado
			// por el filtro.
			Album album = getItem(position);
			contenedor.imgFoto.setImageResource(album.getFotoResId());
			contenedor.lblNombre.setText(album.getNombre());
			contenedor.lblAnio.setText(album.getAnio());
			// Retorno la vista-fila.
			return fila;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezo el layout que mostrará la actividad.
		setContentView(R.layout.main);
		// Obtengo e inicializo las vistas.
		getVistas();
	}

	// Obtiene e inicializa las vistas de la actividad.
	private void getVistas() {
		txtAlbum = (AutoCompleteTextView) findViewById(R.id.txtAlbum);
		// Creo el array de datos.
		ArrayList<Album> albumes = new ArrayList<Album>();
		albumes.add(new Album(R.drawable.veneno, "Veneno", "1977"));
		albumes.add(new Album(R.drawable.mecanico, "Seré mecánico por ti",
				"1981"));
		albumes.add(new Album(R.drawable.cantecito, "Echate un cantecito",
				"1992"));
		albumes.add(new Album(R.drawable.carinio,
				"Está muy bien eso del cariño", "1995"));
		albumes.add(new Album(R.drawable.paloma, "Punta Paloma", "1997"));
		albumes.add(new Album(R.drawable.puro, "Puro Veneno", "1998"));
		albumes.add(new Album(R.drawable.pollo, "La familia pollo", "2000"));
		albumes.add(new Album(R.drawable.ratito, "Un ratito de gloria", "2001"));
		albumes.add(new Album(R.drawable.hombre, "El hombre invisible", "2005"));
		// Establezco el adaptador para el AutoCompleteTextView.
		AdaptadorAlbumes adaptador = new AdaptadorAlbumes(this, albumes);
		txtAlbum.setAdapter(adaptador);
	}

	// Al hacer click sobre btnMostrar.
	public void btnMostrarOnClick(View v) {
		// Informo al usuario.
		mostrarTostada(txtAlbum.getText().toString());
	}

	// Muestra un Toast.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
