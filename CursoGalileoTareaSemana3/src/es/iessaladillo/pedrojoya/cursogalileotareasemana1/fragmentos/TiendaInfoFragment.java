package es.iessaladillo.pedrojoya.cursogalileotareasemana1.fragmentos;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.actividades.TiendaActivity;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos.Tienda;

public class TiendaInfoFragment extends Fragment {

    // Constantes.
    public static String EXTRA_TIENDA = "tienda";

    // Vistas.
    private ImageView btnLlamar;
    private TextView lblNombre;
    private TextView lblDireccion;
    private TextView lblTelefono;
    private TextView lblWebsite;
    private TextView lblEmail;
    private TextView lblFavoritos;

    // Propiedades.
    private String objectIdTienda;
    private ParseObject parseTienda;
    private Tienda tienda;
    private View vRaiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater.inflate(R.layout.fragment_tienda_info, container,
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
        lblNombre = (TextView) v.findViewById(R.id.lblNombre);
        lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
        lblTelefono = (TextView) v.findViewById(R.id.lblTelefono);
        lblWebsite = (TextView) v.findViewById(R.id.lblWebsite);
        lblEmail = (TextView) v.findViewById(R.id.lblEmail);
        lblFavoritos = (TextView) v.findViewById(R.id.lblFavoritos);
        btnLlamar = (ImageView) v.findViewById(R.id.btnLlamar);
        btnLlamar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnLlamarOnClick();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // El fragmento aportará ítems a la ActionBar.
        setHasOptionsMenu(true);
        // Se ocultan las vistas hasta que tengamos los datos.
        if (vRaiz != null) {
            vRaiz.setVisibility(View.INVISIBLE);
            if (getActivity() != null) {
                ((TiendaActivity) getActivity()).mostrarProgreso(true);
            }
        }
        // Se obtienen los argumentos con los que ha sido llamado el fragmento.
        objectIdTienda = getArguments().getString(EXTRA_TIENDA);
        // Se obtiene el objecto Parse correspondiente a la tienda.
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                Tienda.TABLE_NAME);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.whereEqualTo(Tienda.FLD_OBJECTID, objectIdTienda);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> lista, ParseException e) {
                if (e == null) {
                    // Se guarda el objeto.
                    parseTienda = lista.get(0);
                    tienda = new Tienda(parseTienda);
                    // Se escriben los datos en las vistas.
                    if (getActivity() != null) {
                        escribirVistas(tienda);
                    }
                    // Se hacen visibles las vistas.
                    if (vRaiz != null) {
                        vRaiz.setVisibility(View.VISIBLE);
                    }
                    if (getActivity() != null) {
                        ((TiendaActivity) getActivity()).mostrarProgreso(false);
                    }
                } else {
                    // something went wrong
                }
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    // Al hacer click sobre btnLlamar.
    private void btnLlamarOnClick() {
        // Se crea un intent implícito para mostrar el dial, que recibe como
        // data la URI con el número.
        String telefono = lblTelefono.getText().toString();
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + telefono));
        // Se envía el intent.
        startActivity(i);
    }

    // Escribe los datos de un tienda en las vistas.
    private void escribirVistas(Tienda tienda) {
        // Se escriben las propiedades de la tienda en las vistas
        // correspondientes.
        lblNombre.setText(tienda.getNombre());
        lblDireccion.setText(tienda.getDireccion());
        lblTelefono.setText(tienda.getTelefono());
        lblWebsite.setText(tienda.getWebsite());
        lblEmail.setText(tienda.getEmail());
        lblFavoritos.setText(getString(R.string.marcada_como_favorita) + " "
                + tienda.getFavoritos() + " " + getString(R.string.veces));
        // Algunos textos se muestran como enlaces.
        Linkify.addLinks(lblTelefono, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(lblWebsite, Linkify.WEB_URLS);
        Linkify.addLinks(lblEmail, Linkify.EMAIL_ADDRESSES);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú correspondiente.
        inflater.inflate(R.menu.fragment_tienda_info, menu);
        // Se indica que ya se ha procesado el evento.
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem del menú pulsado.
        switch (item.getItemId()) {
        case R.id.mnuFavorito:
            marcarComoFavorito();
            break;
        case R.id.mnuCompartir:
            // Se comparten los datos de la tienda.
            compartir();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void marcarComoFavorito() {
        // Se trae el objeto actualizado.
        parseTienda.refreshInBackground(new RefreshCallback() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // Se incrementa el valor de favoritos.
                    parseTienda = object;
                    parseTienda.put(Tienda.FLD_FAVORITOS,
                            parseTienda.getLong(Tienda.FLD_FAVORITOS) + 1);
                    parseTienda.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            // Se actualiza el número de favoritos.
                            if (getActivity() != null) {
                                tienda.from(parseTienda);
                                escribirVistas(tienda);
                            }
                        }
                    });
                }
            }
        });
    }

    // Envía un intent para compartir texto con los datos de la tienda.
    private void compartir() {
        // Se obtiene la cadena de recomendación.
        String recomendacion = getString(R.string.te_recomiendo_la_tienda)
                + lblNombre.getText().toString()
                + getString(R.string.del_centro_comercial);
        // Se crea un intent con la acción de enviar, el tipo texto plano y se
        // almacena en el extra EXTRA_TEXT la recomendación
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, recomendacion);
        // Se envía el intent creando un seleccionador de aplicación.
        startActivity(Intent.createChooser(intent,
                getString(R.string.compartir_recomendacion)));
    }

}
