package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades.DetalleTiendaActivity;

public class ListaTiendasFragment extends Fragment {

    // Vistas.
    private RelativeLayout rlListaTiendasVacia;
    private ListView lstTiendas;
    private ArrayAdapter<String> adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente..
        View v = inflater.inflate(R.layout.fragment_lista_tiendas, container,
                false);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        lstTiendas = (ListView) v.findViewById(R.id.lstTiendas);
        rlListaTiendasVacia = (RelativeLayout) v
                .findViewById(R.id.rlListaTiendasVacia);
        // Se establece la vista a mostrar cuando la lista esté vacía.
        lstTiendas.setEmptyView(rlListaTiendasVacia);
        // Se crea el adaptador para la lista, que usará para mostrar cada
        // elemento uno de los layouts predefinidos de Android y como fuente de
        // datos un array de cadenas de caracteres con los nombres de las
        // tiendas.
        String[] tiendas = getResources().getStringArray(R.array.tiendas);
        adaptador = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, tiendas);
        lstTiendas.setAdapter(adaptador);
        // Al pulsar sobre una tienda se debe mostrar la actividad de detalle de
        // tienda.
        lstTiendas.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // Se obtiene del adaptador de la lista los datos del elemento
                // pulsado.
                String tienda = (String) lstTiendas.getItemAtPosition(position);
                // Se muestra la actividad de detalle de la tienda.
                mostrarDetalleTienda(tienda);
            }

        });
    }

    // Muestra la actividad de detalle de la tienda.
    private void mostrarDetalleTienda(String tienda) {
        // Se crea el intent para llamar a la actividad de detalle de tienda, y
        // se le pasa la tienda como dato extra.
        Intent i = new Intent(getActivity(), DetalleTiendaActivity.class);
        i.putExtra(DetalleTiendaActivity.EXTRA_TIENDA, tienda);
        // Se envía el intent.
        startActivity(i);
    }

}
