package es.iessaladillo.pedrojoya.pr021;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Widgets.
	private Spinner spnAlbumes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo la onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que debe mostrar la actividad.
		setContentView(R.layout.main);
		// Obtengo e inicializo las vistas.
		getVistas();
	}

	private void getVistas() {
		spnAlbumes = (Spinner) findViewById(R.id.spnAlbum);
		// Creo el array de datos.
		ArrayList<Album> albumes = new ArrayList<Album>();
		albumes.add(new Album(R.drawable.veneno, "Veneno", "1977"));
		albumes.add(new Album(R.drawable.mecanico, "Seré mecánico por ti",
				"1981"));
		albumes.add(new Album(R.drawable.cantecito, "Échate un cantecito",
				"1992"));
		albumes.add(new Album(R.drawable.carinio,
				"Está muy bien eso del cariño", "1995"));
		albumes.add(new Album(R.drawable.paloma, "Punta Paloma", "1997"));
		albumes.add(new Album(R.drawable.puro, "Puro Veneno", "1998"));
		albumes.add(new Album(R.drawable.pollo, "La familia pollo", "2000"));
		albumes.add(new Album(R.drawable.ratito, "Un ratito de gloria", "2001"));
		albumes.add(new Album(R.drawable.hombre, "El hombre invisible", "2005"));
		// Creo el adaptador y se lo asigno al spinner.
		AdaptadorAlbumes adaptador = new AdaptadorAlbumes(this, albumes);
		spnAlbumes.setAdapter(adaptador);
		/*
		 * // Creo el array de datos. String[] albumes = new String[] {"Pepe",
		 * "Juan", "Antonio"}; ArrayAdapter<String> adaptador = new
		 * ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		 * albumes); adaptador.setDropDownViewResource(android.R.layout.
		 * simple_spinner_dropdown_item); spnAlbumes.setAdapter(adaptador);
		 */
	}

	// Al hacer click sobre btnMostrar.
	public void btnMostrarOnClick(View v) {

		// Obtengo el valor del spinner.
		Album album = (Album) spnAlbumes.getSelectedItem();
		// Informo al usuario.
		mostrarTostada(album.getNombre());
		/*
		 * // Obtengo el valor del spinner. String album = (String)
		 * spnAlbumes.getSelectedItem(); // Informo al usuario.
		 * mostrarTostada(album + spnAlbumes.getHeight());
		 */
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

}
