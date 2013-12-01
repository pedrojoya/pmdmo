package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseQueryAdapter.OnQueryLoadListener;
import com.parse.SaveCallback;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.interfaces.MuestraProgreso;

public class ComentariosFragment extends Fragment {

    // Constantes.
    public static String EXTRA_PARENT = "parent";

    // Vistas.
    private EditText txtComentario;
    private ImageView btnEnviar;
    private ListView lstComentarios;
    private ParseQueryAdapter<ParseObject> adaptador;
    private String objectIdTienda;

    private View vRaiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_comentarios, container,
                false);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        return v;
    }

    // Al pulsar sobre el botón btnEnviar.
    public void btnEnviarOnClick() {
        String texto = txtComentario.getText().toString();
        if (!TextUtils.isEmpty(texto)) {
            // Se salva un nuevo objeto Parse para el comentario.
            ParseObject comentario = new ParseObject(BD.Comentario.TABLE_NAME);
            comentario.put(BD.Comentario.TEXTO, texto);
            comentario.put(BD.Comentario.PARENT, objectIdTienda);
            comentario.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    // Se carga la lista de nuevo.
                    cargarLista();
                    // Se limpia el EditText.
                    txtComentario.setText("");
                    // Se informa.
                    Toast.makeText(getActivity(), "Comentario añadido",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        vRaiz = v;
        lstComentarios = (ListView) v.findViewById(R.id.lstComentarios);
        txtComentario = (EditText) v.findViewById(R.id.txtComentario);
        // txtComentario.clearFocus();
        btnEnviar = (ImageView) v.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                btnEnviarOnClick();
            }
        });
    }

    private void cargarLista() {
        adaptador = new ParseQueryAdapter<ParseObject>(getActivity(),
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery<ParseObject> create() {
                        // Se obtienen los comentarios del parent obtenido.
                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                                BD.Comentario.TABLE_NAME);
                        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                        query.whereEqualTo(BD.Comentario.PARENT, objectIdTienda);
                        return query;
                    }
                }) {

            @Override
            public View getItemView(ParseObject object, View v, ViewGroup parent) {
                // Si no se puede reciclar, inflo el layout.
                if (v == null) {
                    v = View.inflate(getContext(),
                            R.layout.fragment_comentarios_item, null);
                }
                // Se deja que el ParseQueryAdapter haga el trabajo sucio.
                return super.getItemView(object, v, parent);
            }

        };
        adaptador
                .addOnQueryLoadListener(new OnQueryLoadListener<ParseObject>() {

                    @Override
                    public void onLoaded(List<ParseObject> objects, Exception e) {
                        // Se muestran las vistas una vez que tenemos los datos.
                        vRaiz.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoading() {

                    }
                });
        adaptador.setTextKey(BD.Comentario.TEXTO);
        lstComentarios.setAdapter(adaptador);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se ocultan las vistas hasta que tengamos los datos.
        vRaiz.setVisibility(View.INVISIBLE);
        if (getActivity() != null) {
            ((MuestraProgreso) getActivity()).mostrarProgreso(true);
        }

        // Se obtiene el argumento con el objectId del objeto parent.
        objectIdTienda = getArguments().getString(EXTRA_PARENT);
        cargarLista();
        super.onActivityCreated(savedInstanceState);
    }
}
