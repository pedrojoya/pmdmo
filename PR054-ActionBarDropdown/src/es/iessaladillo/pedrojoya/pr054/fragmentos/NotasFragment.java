package es.iessaladillo.pedrojoya.pr054.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.pr054.R;

public class NotasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla y retorna el layout correspondiente.
        return inflater.inflate(R.layout.fragment_notas, container, false);
    }

}
