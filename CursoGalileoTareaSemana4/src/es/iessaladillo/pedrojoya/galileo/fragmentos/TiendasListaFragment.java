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
        OnRefreshListener, LoaderCallbacks<Cursor>, OnItemClickListener {

    // Constantes.
    private static final int TIENDAS_LOADER = 0;

    // Vistas.
    private RelativeLayout rlListaTiendasVacia;
    private ListView lstTiendas;
    private PullToRefreshLayout ptrLayout;

    // Variables a nivel de clase.
    private LoaderManager gestor;
    private TiendasCursorAdapter adaptador;

    // Retorna la vista que debe mostrar el fragmento.
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

    // Cuando la actividad se ha creado completamente.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se configura el pulltorefresh.
        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
                .listener(this).setup(ptrLayout);
        // Se obtiene el gestor de cargadores.
        gestor = getActivity().getSupportLoaderManager();
        // Se carga de datos la lista.
        cargarLista();
        // cargarLista();
        super.onActivityCreated(savedInstanceState);
    }

    // Cuando el fragmento pasa a primer plano.
    @Override
    public void onResume() {
        // Se reinicia el cargador de datos correspondiente.
        gestor.restartLoader(TIENDAS_LOADER, null, this);
        super.onResume();
    }

    // Cuando se crea el cargador de datos. Retorna el cargador del cursor.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
            // Se visualiza o oculta el relative layout de lista vacía
            // dependiendo de si el adaptador tiene datos o no.
            rlListaTiendasVacia
                    .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vacía de datos el adaptador asignándole un curso nulo.
        if (adaptador != null) {
            adaptador.changeCursor(null);
            // Se visualiza o oculta el relative layout de lista vacía.
            rlListaTiendasVacia
                    .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    }

    // Cuando se pulsa sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // Se obtiene del adaptador de la lista el elemento
        // pulsado.
        Cursor cursor = (Cursor) lstTiendas.getItemAtPosition(position);
        Tienda tienda = new Tienda(cursor);
        // Se muestra la actividad de detalle de la tienda.
        mostrarDetalleTienda(tienda);
    }

    // Cuando se realiza PullToRefesh.
    @Override
    public void onRefreshStarted(View view) {
        // Se obtienen los datos desde backend (Parse).
        obtenerDatosBackend();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        lstTiendas = (ListView) v.findViewById(R.id.lstTiendas);
        rlListaTiendasVacia = (RelativeLayout) v
                .findViewById(R.id.rlListaTiendasVacia);
        ptrLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
        // El propio fragmento actuará como listener cuando se pulse un elemento
        // de la lista.
        lstTiendas.setOnItemClickListener(this);
    }

    // Muestra la actividad de detalle de la tienda.
    private void mostrarDetalleTienda(Tienda tienda) {
        // Se crea el intent para llamar a la actividad de detalle de tienda, y
        // se le pasa la tienda como dato extra.
        Intent i = new Intent(getActivity(), TiendaActivity.class);
        i.putExtra(TiendaActivity.EXTRA_TIENDA, tienda);
        // Se envía el intent.
        startActivity(i);
    }

    // Carga la lista con los datos obtenidos de la base de datos.
    private void cargarLista() {
        // Se inicializa el cargador, actuando el fragmento como listener.
        gestor.initLoader(TIENDAS_LOADER, null, this);
        // Se crea el adaptador para la lista con un cursor inicial nulo (from y
        // to deben ser especificados por compatibilidad del adaptador con
        // SimpleCursorAdapter, aunque no sean usados después por el adaptador
        // personalizado).
        String[] from = new String[] { BD.Tienda.NOMBRE, BD.Tienda.URL_LOGO };
        int[] to = new int[] { R.id.lblNombre, R.id.imgLogo };
        adaptador = new TiendasCursorAdapter(this.getActivity(), null, from, to);
        // Se visualiza o oculta el relative layout de lista vacía dependiendo
        // de si el adaptador tiene datos.
        rlListaTiendasVacia
                .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                        : View.VISIBLE);
        // Se asigna el adaptador a la lista.
        lstTiendas.setAdapter(adaptador);
    }

    // Obtiene los datos correspondientes del Backend y los almacena en la base
    // de datos.
    private void obtenerDatosBackend() {
        // Muestra el progreso en la ActionBar.
        if (getActivity() != null) {
            ((MuestraProgreso) getActivity()).mostrarProgreso(true);
        }
        // Se consultan en Parse todas las tiendas.
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                BD.Tienda.TABLE_NAME);
        query.orderByAscending(BD.Tienda.NOMBRE);
        query.findInBackground(new FindCallback<ParseObject>() {

            // Cuando se recibe el resultado de la consulta.
            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                // Si se han obtenido datos.
                if (lista != null) {
                    // Se borran de la base de datos todos las tiendas.
                    getActivity().getContentResolver().delete(
                            BD.Tienda.CONTENT_URI, null, null);
                    // Se inserta un registro en la base de datos por cada
                    // tienda resultante de la consulta.
                    for (ParseObject elemento : lista) {
                        Tienda tienda = new Tienda(elemento);
                        getActivity().getContentResolver()
                                .insert(BD.Tienda.CONTENT_URI,
                                        tienda.toContentValues());
                    }
                    // Oculta el progreso en la ActionBar.
                    if (getActivity() != null) {
                        ((MuestraProgreso) getActivity())
                                .mostrarProgreso(false);
                    }
                    // Se reinicia el cargador para que recargue el adaptador.
                    gestor.restartLoader(TIENDAS_LOADER, null,
                            TiendasListaFragment.this);
                    // Se indica que el PullToRefresh ha concluido.
                    ptrLayout.setRefreshComplete();
                }
            }
        });
    }
}
