package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;

public class ComunidadFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout.
        View v = inflater
                .inflate(R.layout.fragment_comunidad, container, false);
        return v;
    }

}
