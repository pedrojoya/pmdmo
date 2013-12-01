package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.actividades.TiendaActivity;
import es.iessaladillo.pedrojoya.galileo.adaptadores.TiendasCursorAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Tienda;
import es.iessaladillo.pedrojoya.galileo.interfaces.MuestraProgreso;

public class TiendasListaFragment extends Fragment implements
        OnRefreshListener, LoaderCallbacks<Cursor> {

    private static final int TIENDAS_LOADER = 0;
    // Vistas.
    private RelativeLayout rlListaTiendasVacia;
    private ListView lstTiendas;
    // private ArrayAdapter<String> adaptador;
    // private TiendasAdapter adaptador;
    private View vRaiz;
    private PullToRefreshLayout ptrLayout;
    private LoaderManager gestor;
    private TiendasCursorAdapter adaptador;

    // private ArrayList<Tienda> datosAdaptador;

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
        ptrLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
        // Al pulsar sobre una tienda se debe mostrar la actividad de detalle de
        // tienda.
        lstTiendas.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // Se obtiene del adaptador de la lista los datos del elemento
                // pulsado.
                Cursor cursor = (Cursor) lstTiendas.getItemAtPosition(position);
                String objectIdTienda = cursor.getString(cursor
                        .getColumnIndex(BD.Tienda.OBJECTID));
                // Se muestra la actividad de detalle de la tienda.
                mostrarDetalleTienda(objectIdTienda);
            }

        });
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
        // Se configura el pulltorefresh.
        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
                .listener(this).setup(ptrLayout);
        // Se obtiene el gestor de cargadores.
        gestor = getActivity().getSupportLoaderManager();
        // Se carga de datos la lista.
        cargarListaDesdeBD();
        // cargarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void cargarListaDesdeBD() {
        // Se inicializa el cargador.
        gestor.initLoader(TIENDAS_LOADER, null, this);
        // Se crea un adaptador inicial con el cursor nulo.
        String[] from = new String[] { BD.Tienda.NOMBRE, BD.Tienda.URL_LOGO };
        int[] to = new int[] { R.id.lblNombre, R.id.imgLogo };
        adaptador = new TiendasCursorAdapter(this.getActivity(), null, from, to);
        // Se visualiza o oculta el relative layout de lista vacía.
        rlListaTiendasVacia
                .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                        : View.VISIBLE);
        lstTiendas.setAdapter(adaptador);
    }

    private void obtenerDatos() {
        if (getActivity() != null) {
            ((MuestraProgreso) getActivity()).mostrarProgreso(true);
        }
        // Se obtienen las tiendas.
        // datosAdaptador = new ArrayList<Tienda>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                BD.Tienda.TABLE_NAME);
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.orderByAscending(BD.Tienda.NOMBRE);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                if (lista != null) {
                    // datosAdaptador.clear();
                    getActivity().getContentResolver().delete(
                            BD.Tienda.CONTENT_URI, null, null);
                    for (ParseObject elemento : lista) {
                        Tienda tienda = new Tienda(elemento);
                        // datosAdaptador.add(tienda);
                        getActivity().getContentResolver()
                                .insert(BD.Tienda.CONTENT_URI,
                                        tienda.toContentValues());
                    }
                    // adaptador = new TiendasAdapter(getActivity(),
                    // datosAdaptador);
                    // lstTiendas.setAdapter(adaptador);
                    // Se hacen visibles las vistas.
                    if (vRaiz != null) {
                        vRaiz.setVisibility(View.VISIBLE);
                    }
                    if (getActivity() != null) {
                        ((MuestraProgreso) getActivity())
                                .mostrarProgreso(false);
                    }
                    // Se reinicia el cargador.
                    gestor.restartLoader(TIENDAS_LOADER, null,
                            TiendasListaFragment.this);
                    // Se indica que el PullToRefresh ha concluido.
                    ptrLayout.setRefreshComplete();
                }
            }
        });
    }

    @Override
    public void onRefreshStarted(View view) {
        // Se obtienen los datos desde Parse.
        obtenerDatos();
    }

    // Cuando se crea el cargador. Retorna el cargador del cursor.
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // Se retorna el cargador del cursor. Se le pasa el contexto, la uri en
        // la que consultar los datos y las columnas a obtener.
        return new CursorLoader(getActivity(), BD.Tienda.CONTENT_URI,
                BD.Tienda.ALL, null, null, null);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se cambia el cursor del adaptador por el que tiene datos.
        if (adaptador != null) {
            adaptador.changeCursor(data);
            // Se visualiza o oculta el relative layout de lista vacía.
            rlListaTiendasVacia
                    .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vacía de datos el adaptador.
        if (adaptador != null) {
            adaptador.changeCursor(null);
            // Se visualiza o oculta el relative layout de lista vacía.
            rlListaTiendasVacia
                    .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        gestor.restartLoader(TIENDAS_LOADER, null, this);
        super.onResume();
    }
}
