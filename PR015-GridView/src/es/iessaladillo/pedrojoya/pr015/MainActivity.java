package es.iessaladillo.pedrojoya.pr015;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables miembro.
	private GridView grdCuadricula;

	// Al crear la actividad.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que mostrará la actividad.
		setContentView(R.layout.main);
		// Obtengo e inicializa las vistas.
		getVistas();
	}

	// Obtiene e inicializa las vistas.
	private void getVistas() {
		grdCuadricula = (GridView) this.findViewById(R.id.grdCuadricula);
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
		// Establezco el adaptador que usará el GridView.
		grdCuadricula.setAdapter(new AdaptadorAlbumes(this, albumes));
		// Establezco el listener para el onClick sobre la cuadrícula.
		grdCuadricula.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> cuadricula, View v,
					int position, long id) {
				// Obtengo el album correspondiente.
				Album album = (Album) cuadricula.getItemAtPosition(position);
				// Informo al usuario.
				mostrarTostada(album.nombre + " (" + album.anio + ")");
			}
		});
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

}
