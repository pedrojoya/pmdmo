package es.iessaladillo.pedrojoya.pr047.fragmentos;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import es.iessaladillo.pedrojoya.pr047.R;
import es.iessaladillo.pedrojoya.pr047.adaptadores.AlbumesAdapter;
import es.iessaladillo.pedrojoya.pr047.modelos.Album;

public class ListaFragment extends Fragment {

	private ListView lstAlbumes;
	private AlbumesAdapter adaptador;

	// Retorna la vista que mostará el fragmento.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflo el layout del fragmento y retorno la vista correspondiente.
		return inflater.inflate(R.layout.fragment_lista, container, false);
	}

	// Cuando se ha terminado de crear la actividad completa.
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// Ya puedo crear el adaptador y asignárselo al ListView.
		lstAlbumes = (ListView) this.getView().findViewById(R.id.lstAlbumes);
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
		adaptador = new AlbumesAdapter(this.getActivity(), albumes);
		lstAlbumes.setAdapter(adaptador);
	}
}
