package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.List;

import android.content.ContentValues;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Tienda;

public class TiendaInfoFragment extends Fragment implements OnClickListener {

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
    private ParseObject parseTienda;
    private Tienda tienda;

    // Retorna la vista que debe mostrar el fragmento.
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

    // Cuando la actividad se ha creado completamente.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se indica que el fragmento aportará ítems a la ActionBar.
        setHasOptionsMenu(true);
        // Se obtiene la tienda pasada como argumento.
        tienda = getArguments().getParcelable(EXTRA_TIENDA);
        // Se muestran los datos de la tienda en las vistas correspondientes.
        if (tienda != null) {
            escribirVistas(tienda);
        }
        super.onActivityCreated(savedInstanceState);
    }

    // Al crearse el menú de opciones.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú correspondiente.
        inflater.inflate(R.menu.fragment_tienda_info, menu);
        // Se indica que ya se ha procesado el evento.
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Al seleccionar un ítem de menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del ítem del menú pulsado.
        switch (item.getItemId()) {
        case R.id.mnuFavorito:
            // Se incrementa el número de favoritos.
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

    // Cuando se pulsa en botón de llamar.
    @Override
    public void onClick(View v) {
        btnLlamarOnClick();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        lblNombre = (TextView) v.findViewById(R.id.lblNombre);
        lblDireccion = (TextView) v.findViewById(R.id.lblDireccion);
        lblTelefono = (TextView) v.findViewById(R.id.lblTelefono);
        lblWebsite = (TextView) v.findViewById(R.id.lblWebsite);
        lblEmail = (TextView) v.findViewById(R.id.lblEmail);
        lblFavoritos = (TextView) v.findViewById(R.id.lblFavoritos);
        btnLlamar = (ImageView) v.findViewById(R.id.btnLlamar);
        // El propio fragmento actúa de listener cuando se pulse el botón de
        // llamar.
        btnLlamar.setOnClickListener(this);
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

    private void marcarComoFavorito() {
        // Se obtiene el objecto Parse correspondiente a la tienda.
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                BD.Tienda.TABLE_NAME);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo(BD.Tienda.OBJECTID, tienda.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                if (e == null) {
                    // Se incrementa el valor de favoritos.
                    parseTienda = lista.get(0);
                    parseTienda.increment(BD.Tienda.FAVORITOS);
                    parseTienda.saveEventually(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            // Se actualiza el número de favoritos en la bd.
                            Tienda t = new Tienda(parseTienda);
                            Uri uri = Uri.withAppendedPath(
                                    BD.Tienda.CONTENT_URI, tienda.getId() + "");
                            ContentValues valores = t.toContentValues();
                            String where = BD.Tienda._ID + " = "
                                    + tienda.getId();
                            int actualizados = getActivity()
                                    .getContentResolver().update(uri, valores,
                                            where, null);
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
