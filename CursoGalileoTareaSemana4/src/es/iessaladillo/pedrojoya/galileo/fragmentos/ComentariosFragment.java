package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.adaptadores.ComentariosCursorAdapter;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Comentario;
import es.iessaladillo.pedrojoya.galileo.interfaces.MuestraProgreso;

public class ComentariosFragment extends Fragment implements OnClickListener,
        LoaderCallbacks<Cursor> {

    // Constantes.
    public static String EXTRA_PARENT = "parent";
    private static final int COMENTARIOS_LOADER = 4;

    // Vistas.
    private EditText txtComentario;
    private ImageView btnEnviar;
    private ListView lstComentarios;
    private ComentariosCursorAdapter adaptador;

    // Propiedades.
    private String objectIdParent;
    private LoaderManager gestor;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_comentarios, container,
                false);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        // Se retorna la vista.
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        lstComentarios = (ListView) v.findViewById(R.id.lstComentarios);
        txtComentario = (EditText) v.findViewById(R.id.txtComentario);
        btnEnviar = (ImageView) v.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
    }

    // Al terminar de crear la actividad.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se obtiene el argumento con el objectId del objeto parent.
        objectIdParent = getArguments().getString(EXTRA_PARENT);
        // Se obtiene el gestor de cargadores.
        gestor = getActivity().getSupportLoaderManager();
        // Se obtienen los datos de Parse.
        obtenerDatosBackend();
        // Se carga la lista.
        cargarLista();
        super.onActivityCreated(savedInstanceState);
    }

    // Carga la lista con los comentarios del objeto (tienda / foto).
    private void cargarLista() {
        // Se inicializa el cargador, actuando el fragmento como listener.
        gestor.initLoader(COMENTARIOS_LOADER, null, this);
        // Se crea el adaptador para la lista con un cursor inicial nulo (from y
        // to deben ser especificados por compatibilidad del adaptador con
        // SimpleCursorAdapter, aunque no sean usados después por el adaptador
        // personalizado).
        String[] from = new String[] { BD.Comentario.TEXTO };
        int[] to = new int[] { R.id.lblTexto };
        adaptador = new ComentariosCursorAdapter(this.getActivity(), null,
                from, to);
        // Se asigna el adaptador a la lista.
        lstComentarios.setAdapter(adaptador);

        // adaptador = new ParseQueryAdapter<ParseObject>(getActivity(),
        // new ParseQueryAdapter.QueryFactory<ParseObject>() {
        // public ParseQuery<ParseObject> create() {
        // // Se obtienen los comentarios del parent obtenido.
        // ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
        // BD.Comentario.TABLE_NAME);
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        // query.whereEqualTo(BD.Comentario.PARENT, objectIdParent);
        // return query;
        // }
        // }) {
        //
        // @Override
        // public View getItemView(ParseObject object, View v, ViewGroup parent)
        // {
        // // Si no se puede reciclar, inflo el layout.
        // if (v == null) {
        // v = View.inflate(getContext(),
        // R.layout.fragment_comentarios_item, null);
        // }
        // // Se deja que el ParseQueryAdapter haga el trabajo sucio.
        // return super.getItemView(object, v, parent);
        // }
        //
        // };
        // adaptador
        // .addOnQueryLoadListener(new OnQueryLoadListener<ParseObject>() {
        //
        // @Override
        // public void onLoaded(List<ParseObject> objects, Exception e) {
        // // Se oculta el círculo de progreso de la ActionBar.
        // if (getActivity() != null) {
        // ((MuestraProgreso) getActivity())
        // .mostrarProgreso(false);
        // }
        // }
        //
        // @Override
        // public void onLoading() {
        // // Se muestra el círculo de progreso en la ActionBar de
        // // la actividad.
        // if (getActivity() != null) {
        // ((MuestraProgreso) getActivity())
        // .mostrarProgreso(true);
        // }
        // }
        // });
        // adaptador.setTextKey(BD.Comentario.TEXTO);
        // lstComentarios.setAdapter(adaptador);
    }

    // Al hacer click sobre btnEnviar.
    @Override
    public void onClick(View v) {
        btnEnviarOnClick();
    }

    // Al pulsar sobre el botón btnEnviar.
    public void btnEnviarOnClick() {
        String texto = txtComentario.getText().toString();
        if (!TextUtils.isEmpty(texto)) {
            // Se salva un nuevo objeto Parse para el comentario.
            ParseObject comentarioParse = new ParseObject(
                    BD.Comentario.TABLE_NAME);
            comentarioParse.put(BD.Comentario.TEXTO, texto);
            comentarioParse.put(BD.Comentario.PARENT, objectIdParent);
            final Comentario comentario = new Comentario(comentarioParse);
            comentarioParse.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // Se agrega el comentario a la base de datos.
                        getActivity().getContentResolver().insert(
                                BD.Comentario.CONTENT_URI,
                                comentario.toContentValues());
                        // Se reinicia el cargador para que recargue el
                        // adaptador.
                        gestor.restartLoader(COMENTARIOS_LOADER, null,
                                ComentariosFragment.this);
                        // Se limpia el EditText.
                        txtComentario.setText("");
                        // Se informa.
                        Toast.makeText(getActivity(), "Comentario añadido",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
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
                BD.Comentario.TABLE_NAME);
        query.whereEqualTo(BD.Comentario.PARENT, objectIdParent);
        query.orderByAscending(BD.Comentario.UPDATEDAT);
        query.findInBackground(new FindCallback<ParseObject>() {

            // Cuando se recibe el resultado de la consulta.
            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                // Si se han obtenido datos.
                if (lista != null) {
                    // Se borran de la base de datos todos los comentarios de
                    // ese parent.
                    getActivity().getContentResolver().delete(
                            BD.Comentario.CONTENT_URI,
                            BD.Comentario.PARENT + " = '" + objectIdParent
                                    + "'", null);
                    // Se inserta un registro en la base de datos por cada
                    // tienda resultante de la consulta.
                    for (ParseObject elemento : lista) {
                        Comentario comentario = new Comentario(elemento);
                        getActivity().getContentResolver().insert(
                                BD.Comentario.CONTENT_URI,
                                comentario.toContentValues());
                    }
                    // Oculta el progreso en la ActionBar.
                    if (getActivity() != null) {
                        ((MuestraProgreso) getActivity())
                                .mostrarProgreso(false);
                    }
                    // Se reinicia el cargador para que recargue el adaptador.
                    gestor.restartLoader(COMENTARIOS_LOADER, null,
                            ComentariosFragment.this);
                }
            }
        });
    }

    // Cuando se crea el cargador de datos. Retorna el cargador del cursor.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Se retorna el cargador del cursor. Se le pasa el contexto, la uri en
        // la que consultar los datos y las columnas a obtener.
        return new CursorLoader(getActivity(), BD.Comentario.CONTENT_URI,
                BD.Comentario.ALL, BD.Comentario.PARENT + " = '"
                        + objectIdParent + "'", null, null);
    }

    // Cuando terminan de cargarse los datos en el cargador.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Se cambia el cursor del adaptador por el que tiene datos.
        if (adaptador != null) {
            adaptador.changeCursor(data);
        }
    }

    // Cuando se resetea el cargador.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Se vacía de datos el adaptador asignándole un curso nulo.
        if (adaptador != null) {
            adaptador.changeCursor(null);
        }
    }
}
