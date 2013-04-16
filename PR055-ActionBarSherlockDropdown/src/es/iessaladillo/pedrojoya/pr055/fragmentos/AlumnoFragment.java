package es.iessaladillo.pedrojoya.pr055.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.iessaladillo.pedrojoya.pr055.R;

public class AlumnoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla y retorna el layout correspondiente.
        return inflater.inflate(R.layout.fragment_alumno, container, false);
    }

}
