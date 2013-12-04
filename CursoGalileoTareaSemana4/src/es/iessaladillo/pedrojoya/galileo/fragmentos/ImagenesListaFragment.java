package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import es.iessaladillo.pedrojoya.galileo.Aplicacion;
import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.actividades.MainActivity;
import es.iessaladillo.pedrojoya.galileo.adaptadores.ImagenesCursorAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Imagen;
import es.iessaladillo.pedrojoya.galileo.datos.Instagram;

public class ImagenesListaFragment extends Fragment implements
        OnRefreshListener, LoaderCallbacks<Cursor> {

    private static final int IMAGENES_LOADER = 2;

    // Vistas.
    private ListView lstFotos;

    // Propiedades.
    private ImagenesCursorAdapter adaptador;

    private PullToRefreshLayout ptrLayout;

    private RelativeLayout rlListaFotosVacia;

    private LoaderManager gestor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_imagenes_lista, container,
                false);
        // Se obtienen las referencias a las vistas.
        getVistas(v);
        return v;
    }

    private void getVistas(View v) {
        // Se obtienen las vistas.
        lstFotos = (ListView) v.findViewById(R.id.lstFotos);
        rlListaFotosVacia = (RelativeLayout) v
                .findViewById(R.id.rlListaFotosVacia);
        ptrLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se indica que el fragmento aportará ítems a la ActionBar.
        setHasOptionsMenu(true);
        // Se configura el pulltorefresh.
        ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
                .listener(this).setup(ptrLayout);
        // Se obtiene el gestor de cargadores.
        gestor = getActivity().getSupportLoaderManager();
        cargarListaDesdeBD();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú correspondiente.
        inflater.inflate(R.menu.fragment_imagenes_lista, menu);
        // Se indica que ya se ha procesado el evento.
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Al seleccionar un ítem de menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem del menú pulsado.
        switch (item.getItemId()) {
        case R.id.mnuActualizar:
            // Se cargan los datos desde el backend.
            obtenerDatos();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void obtenerDatos() {
        // Se muestra el círculo de progreso.
        if (getActivity() != null) {
            ((MainActivity) getActivity()).mostrarProgreso(true);
        }
        // Se obtiene la URL de petición a Instagram.
        String url = Instagram.getRecentMediaURL("algeciras");
        // Se crea el listener que recibirá la respuesta de la petición.
        Response.Listener<JSONObject> listenerRespuesta = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject respuesta) {
                // Se oculta el progreso.
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).mostrarProgreso(false);
                }
                // Se crea la lista de datos parseando la respuesta.
                ArrayList<Imagen> lista = new ArrayList<Imagen>();
                parseRespuesta(respuesta, lista);
                // Se recorre la lista y se añade cada elemento a la BD.
                if (lista != null) {
                    getActivity().getContentResolver().delete(
                            BD.Imagen.CONTENT_URI, null, null);
                    for (Imagen elemento : lista) {
                        getActivity().getContentResolver().insert(
                                BD.Imagen.CONTENT_URI,
                                elemento.toContentValues());
                    }
                    // Se reinicia el cargador.
                    gestor.restartLoader(IMAGENES_LOADER, null,
                            ImagenesListaFragment.this);
                }
                // Se indica que el PullToRefresh ha concluido.
                ptrLayout.setRefreshComplete();
            }

        };
        // Se crea la petición JSON.
        JsonObjectRequest peticion = new JsonObjectRequest(Method.GET, url,
                null, listenerRespuesta, null);
        // Se añada la petición a la cola de peticiones.
        Aplicacion.colaPeticiones.add(peticion);
        // Se crea el adaptador y se asigna a la lista.
        // TODO
        // lstFotos.setAdapter(adaptador);
    }

    private void parseRespuesta(JSONObject respuesta,
            ArrayList<Imagen> datosAdaptador) {
        try {
            // Se parsea la respuesta para obtener los datos deseados.
            // Se obtiene el valor de la clave "data", que correponde al
            // array de datos.

            JSONArray dataKeyJSONArray = respuesta
                    .getJSONArray(Instagram.ARRAY_DATOS_KEY);
            // Por cada uno de los elementos del array de datos.
            for (int i = 0; i < dataKeyJSONArray.length(); i++) {
                // Se obtiene el elemento, que es un JSONObject.
                JSONObject elemento = dataKeyJSONArray.getJSONObject(i);
                // Si el tipo de elemento indica que es una imagen.
                if (elemento.getString(Instagram.TIPO_ELEMENTO_KEY).equals(
                        Instagram.TIPO_ELEMENTO_IMAGEN)) {
                    // Se crea un objeto modelo.
                    Imagen imagenInstagram = new Imagen();
                    // Se obtiene el usuario.
                    JSONObject usuario = elemento
                            .getJSONObject(Instagram.USUARIO_KEY);
                    // Se obtiene del usuario el nombre de usuario y se
                    // guarda en el objeto modelo.
                    imagenInstagram.setUsername(usuario
                            .getString(Instagram.NOMBRE_USUARIO_KEY));
                    // Se obtiene la imagen.
                    JSONObject imagen = elemento
                            .getJSONObject(Instagram.IMAGEN_KEY);
                    // Se obtiene la url de la imagen en resolución
                    // estándar y se guarda en el objeto modelo.
                    imagenInstagram.setUrl(imagen.getJSONObject(
                            Instagram.RESOLUCION_ESTANDAR_KEY).getString(
                            Instagram.URL_KEY));
                    // Se añade el objeto modelo a la lista de datos
                    // para el adaptador.
                    datosAdaptador.add(imagenInstagram);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void cargarListaDesdeBD() {
        // Se inicializa el cargador.
        gestor.initLoader(IMAGENES_LOADER, null, this);
        // Se crea un adaptador inicial con el cursor nulo.
        String[] from = new String[] { BD.Imagen.USERNAME, BD.Imagen.URL };
        int[] to = new int[] { R.id.lblUsuario, R.id.imgFoto };
        adaptador = new ImagenesCursorAdapter(this.getActivity(), null, from,
                to);
        // Se visualiza o oculta el relative layout de lista vacía.
        rlListaFotosVacia
                .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                        : View.VISIBLE);
        lstFotos.setAdapter(adaptador);
    }

    // Cuando se crea el cargador. Retorna el cargador del cursor.
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // Se retorna el cargador del cursor. Se le pasa el contexto, la uri en
        // la que consultar los datos y las columnas a obtener.
        return new CursorLoader(getActivity(), BD.Imagen.CONTENT_URI,
                BD.Imagen.ALL, null, null, null);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se cambia el cursor del adaptador por el que tiene datos.
        if (adaptador != null) {
            adaptador.changeCursor(data);
            // Se visualiza o oculta el relative layout de lista vacía.
            rlListaFotosVacia
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
            rlListaFotosVacia
                    .setVisibility((adaptador.getCount() > 0) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        gestor.restartLoader(IMAGENES_LOADER, null, this);
        super.onResume();
    }

    @Override
    public void onRefreshStarted(View view) {
        // Se obtienen los datos desde Parse.
        obtenerDatos();
    }
}
