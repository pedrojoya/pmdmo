package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.galileo.actividades.TiendaActivity;
import es.iessaladillo.pedrojoya.galileo.adaptadores.TiendasAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Tienda;
import es.iessaladillo.pedrojoya.galileo.interfaces.MuestraProgreso;

public class TiendasListaFragment extends Fragment {

    // Vistas.
    private RelativeLayout rlListaTiendasVacia;
    private ListView lstTiendas;
    // private ArrayAdapter<String> adaptador;
    private TiendasAdapter adaptador;
    private View vRaiz;
    private ArrayList<Tienda> datosAdaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente..
        View v = inflater.inflate(R.layout.fragment_tiendas_lista, container,
                false);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        vRaiz = v;
        lstTiendas = (ListView) v.findViewById(R.id.lstTiendas);
        rlListaTiendasVacia = (RelativeLayout) v
                .findViewById(R.id.rlListaTiendasVacia);
        // Se establece la vista a mostrar cuando la lista esté vacía.
        lstTiendas.setEmptyView(rlListaTiendasVacia);
    }

    // Muestra la actividad de detalle de la tienda.
    private void mostrarDetalleTienda(String objectIdTienda) {
        // Se crea el intent para llamar a la actividad de detalle de tienda, y
        // se le pasa la tienda como dato extra.
        Intent i = new Intent(getActivity(), TiendaActivity.class);
        i.putExtra(TiendaActivity.EXTRA_TIENDA, objectIdTienda);
        // Se envía el intent.
        startActivity(i);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se carga de datos la lista.
        cargarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void cargarLista() {
        // Se crea el adaptador para la lista, que usará para mostrar cada
        // elemento uno de los layouts predefinidos de Android y como fuente de
        // datos un array de cadenas de caracteres con los nombres de las
        // tiendas.
        // String[] tiendas = getResources().getStringArray(R.array.tiendas);
        // adaptador = new ArrayAdapter<String>(getActivity(),
        // android.R.layout.simple_list_item_1, tiendas);
        if (getActivity() != null) {
            ((MuestraProgreso) getActivity()).mostrarProgreso(true);
        }

        /*
         * adaptador = new ParseQueryAdapter<ParseObject>(getActivity(), new
         * ParseQueryAdapter.QueryFactory<ParseObject>() { public
         * ParseQuery<ParseObject> create() { // Se obtienen los comentarios del
         * parent obtenido. ParseQuery<ParseObject> query = new
         * ParseQuery<ParseObject>( Tienda.TABLE_NAME);
         * query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
         * query.orderByAscending(Tienda.FLD_NOMBRE); return query; } }) {
         * 
         * @Override public View getItemView(ParseObject object, View v,
         * ViewGroup parent) { // Si no se puede reciclar, inflo el layout. if
         * (v == null) { v = View.inflate(getContext(),
         * R.layout.fragment_tiendas_lista_item, null); } // Se deja que el
         * ParseQueryAdapter haga el trabajo sucio. return
         * super.getItemView(object, v, parent); }
         * 
         * }; adaptador .addOnQueryLoadListener(new
         * OnQueryLoadListener<ParseObject>() {
         * 
         * @Override public void onLoaded(List<ParseObject> objects, Exception
         * e) { // Se hacen visibles las vistas. if (vRaiz != null) {
         * vRaiz.setVisibility(View.VISIBLE); } if (getActivity() != null) {
         * ((MainActivity) getActivity()) .mostrarProgreso(false); } }
         * 
         * @Override public void onLoading() { // TODO Auto-generated method
         * stub
         * 
         * } }); adaptador.setTextKey(Tienda.FLD_NOMBRE);
         * adaptador.setImageKey(Tienda.FLD_LOGO);
         * lstTiendas.setAdapter(adaptador);
         */

        // Se obtienen las tiendas.
        datosAdaptador = new ArrayList<Tienda>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                BD.Tienda.TABLE_NAME);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.orderByAscending(BD.Tienda.NOMBRE);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                if (lista != null) {
                    datosAdaptador.clear();
                    for (ParseObject elemento : lista) {
                        Tienda tienda = new Tienda(elemento);
                        datosAdaptador.add(tienda);
                    }
                    adaptador = new TiendasAdapter(getActivity(),
                            datosAdaptador);
                    lstTiendas.setAdapter(adaptador);
                    // Se hacen visibles las vistas.
                    if (vRaiz != null) {
                        vRaiz.setVisibility(View.VISIBLE);
                    }
                    if (getActivity() != null) {
                        ((MuestraProgreso) getActivity())
                                .mostrarProgreso(false);
                    }
                }
            }
        });

        // Al pulsar sobre una tienda se debe mostrar la actividad de detalle de
        // tienda.
        lstTiendas.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // Se obtiene del adaptador de la lista los datos del elemento
                // pulsado.
                String objectIdTienda = ((Tienda) lstTiendas
                        .getItemAtPosition(position)).getObjectId();
                // Se muestra la actividad de detalle de la tienda.
                mostrarDetalleTienda(objectIdTienda);
            }

        });
    }
}
