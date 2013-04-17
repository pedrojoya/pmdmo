package es.iessaladillo.pedrojoya.pr056.fragmentos;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.pr056.R;
import es.iessaladillo.pedrojoya.pr056.adaptadores.AdaptadorAlbumes;
import es.iessaladillo.pedrojoya.pr056.modelos.Album;

public class MenuFragment extends ListFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se retorna la vista que debe utilizar el fragmento, que corresponde
        // al layout con la lista de opciones.
        return inflater.inflate(R.layout.menu_list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se crea el adaptador con los datos correspondientes y se le asigna a
        // la lista.
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
        AdaptadorAlbumes adaptador = new AdaptadorAlbumes(this.getActivity(),
                albumes);
        setListAdapter(adaptador);
    }

}
