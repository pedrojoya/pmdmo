package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import es.iessaladillo.pedrojoya.galileo.Aplicacion;
import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.adaptadores.ImagenesCursorAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Imagen;
import es.iessaladillo.pedrojoya.galileo.datos.Instagram;
import es.iessaladillo.pedrojoya.galileo.widgets.EndlessGridView;

public class ImagenesListaFragment extends Fragment implements
        OnRefreshListener, LoaderCallbacks<Cursor>, EndlessGridView.LoadAgent {

    private static final int IMAGENES_LOADER = 2;
    private static final String EXTRA_URL = "url";

    // Vistas.
    private EndlessGridView lstFotos;
    private PullToRefreshLayout ptrLayout;
    private RelativeLayout rlListaFotosVacia;

    // Propiedades.
    private ImagenesCursorAdapter adaptador;
    private LoaderManager gestor;
    private boolean cargando;
    private MenuItem mnuActualizar;
    private String siguientePeticionURL;

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
        lstFotos = (EndlessGridView) v.findViewById(R.id.lstFotos);
        // El propio fragmento hará de listener cuando el grid solicite la carga
        // de más datos.
        lstFotos.setLoadAgent(this);
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
        if (savedInstanceState == null) {
            siguientePeticionURL = Instagram.getRecentMediaURL("algeciras");
        } else {
            siguientePeticionURL = savedInstanceState.getString(EXTRA_URL);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú correspondiente.
        menu.clear(); // Para que no repita el menú de Tienda.
        inflater.inflate(R.menu.fragment_imagenes_lista, menu);
        mnuActualizar = menu.findItem(R.id.mnuActualizar);
        // Se indica que ya se ha procesado el evento.
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (cargando) {
            MenuItemCompat.setActionView(mnuActualizar,
                    R.layout.actionview_progreso);
        } else {
            MenuItemCompat.setActionView(mnuActualizar, null);
        }
        super.onPrepareOptionsMenu(menu);
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

    // Muestra el círculo de progreso en la ActionBar.
    private void mostrarProgreso(boolean mostrar) {
        cargando = mostrar;
        getActivity().invalidateOptionsMenu();
    }

    private void obtenerDatos() {
        // Se muestra el círculo de progreso.
        mostrarProgreso(true);
        // Se obtiene la URL de petición a Instagram.
        String url = siguientePeticionURL;
        // Se crea el listener que recibirá la respuesta de la petición.
        Response.Listener<JSONObject> listenerRespuesta = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject respuesta) {
                // Se crea la lista de datos parseando la respuesta.
                ArrayList<Imagen> lista = new ArrayList<Imagen>();
                // Se obtiene un indicador de si se trata de la carga inicial.
                boolean peticionInicial = siguientePeticionURL.equals(Instagram
                        .getRecentMediaURL("algeciras"));
                parseRespuesta(respuesta, lista);
                // Se recorre la lista y se añade cada elemento a la BD.
                if (lista != null) {
                    // Si es una petición inicial se borran los registros
                    // existentes.
                    if (peticionInicial) {
                        getActivity().getContentResolver().delete(
                                BD.Imagen.CONTENT_URI, null, null);
                    }
                    for (Imagen elemento : lista) {
                        getActivity().getContentResolver().insert(
                                BD.Imagen.CONTENT_URI,
                                elemento.toContentValues());
                    }
                    // Se reinicia el cargador.
                    gestor.restartLoader(IMAGENES_LOADER, null,
                            ImagenesListaFragment.this);
                }
                // Se oculta el progreso.
                mostrarProgreso(false);
                // Se indica que el PullToRefresh ha concluido.
                ptrLayout.setRefreshComplete();
                lstFotos.setLoaded();
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
            // Se obtiene cual debe ser la próxima petición para paginación.
            JSONObject paginationKeyJSONObject = respuesta
                    .getJSONObject(Instagram.PAGINACION_KEY);
            siguientePeticionURL = paginationKeyJSONObject
                    .getString(Instagram.SIGUIENTE_PETICION_KEY);
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
                    // Se obtiene la url de la miniatura de la imagen y se
                    // guarda en el objeto modelo.
                    imagenInstagram.setThumbnail(imagen.getJSONObject(
                            Instagram.RESOLUCION_MINIATURA_KEY).getString(
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
        String[] from = new String[] { BD.Imagen.USERNAME, BD.Imagen.THUMBNAIL };
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
        siguientePeticionURL = Instagram.getRecentMediaURL("algeciras");
        obtenerDatos();
    }

    @Override
    public void onDestroyView() {
        // Se "quitan" los menus del fragmento. OJO: si no se
        // hace se muestran repetidos al cambiar orientación.
        setHasOptionsMenu(false);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Se guarda la proxima url a consultar.
        outState.putString(EXTRA_URL, siguientePeticionURL);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void loadData() {
        obtenerDatos();
    }

}
