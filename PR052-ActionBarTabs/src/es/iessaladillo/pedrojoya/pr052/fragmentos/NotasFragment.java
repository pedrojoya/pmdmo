package es.iessaladillo.pedrojoya.pr052.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr052.R;

public class NotasFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se establece que el fragmento reciba callbacks de eventos de menú.
        this.setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú a partir del XML.
        inflater.inflate(R.menu.fragment_notas, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado se realiza la acción deseada.
        switch (item.getItemId()) {
        case R.id.mnuCompartir:
            Toast.makeText(getActivity(), R.string.compartir,
                    Toast.LENGTH_SHORT).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla y retorna el layout correspondiente.
        return inflater.inflate(R.layout.fragment_notas, container, false);
    }

}
