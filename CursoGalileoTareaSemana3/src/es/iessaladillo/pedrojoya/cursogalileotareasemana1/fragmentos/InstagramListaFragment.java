package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.Aplicacion;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades.MainActivity;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.adaptadores.ImagenesInstagramAdapter;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.contratos.InstagramContrat;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos.ImagenInstagram;

public class InstagramListaFragment extends Fragment {

    // Vistas.
    private ListView lstFotos;

    // Propiedades.
    private View vRaiz;
    private ImagenesInstagramAdapter adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_instagram_lista, container,
                false);
        // Se obtienen las referencias a las vistas.
        getVistas(v);
        return v;
    }

    private void getVistas(View v) {
        vRaiz = v;
        lstFotos = (ListView) v.findViewById(R.id.lstFotos);
        // El propio fragmento responderá al hacer click sobre el botón o sobre
        // un elemento de la lista.
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se carga de datos la lista.
        cargarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void cargarLista() {
        // Se muestra el círculo de progreso.
        if (getActivity() != null) {
            ((MainActivity) getActivity()).mostrarProgreso(true);
        }
        // Se obtiene la URL de petición a Instagram.
        String url = InstagramContrat.getRecentMediaURL("algeciras");
        // Se crea el listener que recibirá la respuesta de la petición.
        Response.Listener<JSONObject> listenerRespuesta = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject respuesta) {
                // Se oculta el progreso.
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).mostrarProgreso(false);
                }
                // Se crea la lista de datos parseando la respuesta.
                ArrayList<ImagenInstagram> datosAdaptador = new ArrayList<ImagenInstagram>();
                parseRespuesta(respuesta, datosAdaptador);
                Log.d("pedro", respuesta.toString());
                // Se crea el adaptador y se asigna a la lista.
                adaptador = new ImagenesInstagramAdapter(getActivity(),
                        datosAdaptador);
                lstFotos.setAdapter(adaptador);
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
            ArrayList<ImagenInstagram> datosAdaptador) {
        try {
            // Se parsea la respuesta para obtener los datos deseados.
            // Se obtiene el valor de la clave "data", que correponde al
            // array de datos.

            JSONArray dataKeyJSONArray = respuesta
                    .getJSONArray(InstagramContrat.ARRAY_DATOS_KEY);
            // Por cada uno de los elementos del array de datos.
            for (int i = 0; i < dataKeyJSONArray.length(); i++) {
                // Se obtiene el elemento, que es un JSONObject.
                JSONObject elemento = dataKeyJSONArray.getJSONObject(i);
                // Si el tipo de elemento indica que es una imagen.
                if (elemento.getString(InstagramContrat.TIPO_ELEMENTO_KEY)
                        .equals(InstagramContrat.TIPO_ELEMENTO_IMAGEN)) {
                    // Se crea un objeto modelo.
                    ImagenInstagram imagenInstagram = new ImagenInstagram();
                    // Se obtiene el usuario.
                    JSONObject usuario = elemento
                            .getJSONObject(InstagramContrat.USUARIO_KEY);
                    // Se obtiene del usuario el nombre de usuario y se
                    // guarda en el objeto modelo.
                    imagenInstagram.setUsername(usuario
                            .getString(InstagramContrat.NOMBRE_USUARIO_KEY));
                    // Se obtiene la imagen.
                    JSONObject imagen = elemento
                            .getJSONObject(InstagramContrat.IMAGEN_KEY);
                    // Se obtiene la url de la imagen en resolución
                    // estándar y se guarda en el objeto modelo.
                    imagenInstagram.setUrl(imagen.getJSONObject(
                            InstagramContrat.RESOLUCION_ESTANDAR_KEY)
                            .getString(InstagramContrat.URL_KEY));
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

}
