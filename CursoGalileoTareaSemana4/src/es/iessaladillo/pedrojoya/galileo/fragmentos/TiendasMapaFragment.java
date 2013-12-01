package es.iessaladillo.pedrojoya.galileo.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.galileo.R;

public class TiendasMapaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_tiendas_mapa, container,
                false);
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

}
