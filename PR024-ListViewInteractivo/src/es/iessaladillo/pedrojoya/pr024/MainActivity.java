package es.iessaladillo.pedrojoya.pr024;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Creo el array de datos.
		ArrayList<Album> albumes = new ArrayList<Album>();
		albumes.add(new Album(R.drawable.veneno, "Veneno", "1977", 3));
		albumes.add(new Album(R.drawable.mecanico, "Seré mecánico por ti",
				"1981", 2));
		albumes.add(new Album(R.drawable.cantecito, "Échate un cantecito",
				"1992", 4));
		albumes.add(new Album(R.drawable.carinio,
				"Está muy bien eso del cariño", "1995", 5));
		albumes.add(new Album(R.drawable.paloma, "Punta Paloma", "1997", 1));
		albumes.add(new Album(R.drawable.puro, "Puro Veneno", "1998", 0));
		albumes.add(new Album(R.drawable.pollo, "La familia pollo", "2000", 2));
		albumes.add(new Album(R.drawable.ratito, "Un ratito de gloria", "2001",
				3));
		albumes.add(new Album(R.drawable.hombre, "El hombre invisible", "2005",
				3));
		// Establezco el adaptador que usará el GridView.
		this.setListAdapter(new AdaptadorAlbumes(this, albumes));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Obtengo el album correspondiente.
		Album album = (Album) l.getItemAtPosition(position);
		// Informo al usuario.
		mostrarTostada(album.getNombre() + " (" + album.getAnio() + ") - "
				+ (int) album.getValoracion() + " estrellas");
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

}
