package es.iessaladillo.pedrojoya.pr047.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.iessaladillo.pedrojoya.pr047.R;

public class ListaFragment extends Fragment {

    private ListView lstAlbumes;

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
        adaptador = new ArrayAdapter<T>(context, textViewResourceId)
    }
}
