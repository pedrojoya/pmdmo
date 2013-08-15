package es.iessaladillo.pedrojoya.pr071.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr071.R;

public class AlumnoFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se establece que el fragmento reciba callbacks de eventos de menú.
        this.setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú a partir del XML.
        inflater.inflate(R.menu.fragment_alumno, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado se realiza la acción deseada.
        switch (item.getItemId()) {
        case R.id.mnuAgregar:
            Toast.makeText(getActivity(), R.string.agregar, Toast.LENGTH_SHORT)
                    .show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla y retorna el layout correspondiente.
        return inflater.inflate(R.layout.fragment_alumno, container, false);
    }

}
