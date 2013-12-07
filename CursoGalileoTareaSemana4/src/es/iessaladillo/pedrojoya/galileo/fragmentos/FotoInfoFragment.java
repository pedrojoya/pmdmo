package es.iessaladillo.pedrojoya.galileo.fragmentos;

import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.BD;
import es.iessaladillo.pedrojoya.galileo.datos.Foto;

public class FotoInfoFragment extends Fragment {

    // Constantes.
    public static String EXTRA_FOTO = "foto";

    // Vistas.
    private ImageView imgFoto;
    private TextView lblDescripcion;
    private TextView lblFavoritos;

    // Propiedades.
    private ParseObject parseFoto;
    private Foto foto;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout correspondiente.
        View v = inflater
                .inflate(R.layout.fragment_foto_info, container, false);
        // Se obtienen e inicializan las vistas.
        getVistas(v);
        // Se retorna la vista que debe mostrar el fragmento.
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas(View v) {
        // Se obtienen las vistas.
        imgFoto = (ImageView) v.findViewById(R.id.imgFoto);
        lblDescripcion = (TextView) v.findViewById(R.id.lblDescripcion);
        lblFavoritos = (TextView) v.findViewById(R.id.lblFavoritos);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // El fragmento aportará ítems a la ActionBar.
        setHasOptionsMenu(true);
        // Se obtienen los argumentos con los que ha sido llamado el fragmento.
        foto = getArguments().getParcelable(EXTRA_FOTO);
        // Se escriben los datos.
        if (foto != null) {
            escribirVistas(foto);
        }
        super.onActivityCreated(savedInstanceState);
    }

    // Escribe los datos de una foto en las vistas.
    private void escribirVistas(Foto foto) {
        // Se escriben las propiedades de la foto en las vistas
        // correspondientes.
        Picasso.with(getActivity()).load(foto.getUrl()).into(imgFoto);
        lblDescripcion.setText(foto.getDescripcion());
        lblFavoritos.setText(getString(R.string.marcada_como_favorita) + " "
                + foto.getFavoritos() + " " + getString(R.string.veces));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú correspondiente.
        inflater.inflate(R.menu.fragment_foto_info, menu);
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
        // Se obtiene el objecto Parse correspondiente a la tienda.
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                BD.Foto.TABLE_NAME);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo(BD.Foto.OBJECTID, foto.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lista, ParseException e) {
                if (e == null) {
                    // Se incrementa el valor de favoritos.
                    parseFoto = lista.get(0);
                    parseFoto.increment(BD.Foto.FAVORITOS);
                    parseFoto.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            // Se actualiza el número de favoritos en la bd.
                            foto.setFavoritos(foto.getFavoritos() + 1);
                            Uri uri = Uri.withAppendedPath(BD.Foto.CONTENT_URI,
                                    foto.getId() + "");
                            ContentValues valores = foto.toContentValues();
                            String where = BD.Foto._ID + " = " + foto.getId();
                            int actualizados = getActivity()
                                    .getContentResolver().update(uri, valores,
                                            where, null);
                            if (getActivity() != null) {
                                escribirVistas(foto);
                            }
                        }
                    });
                }
            }
        });
    }

    // Envía un intent para compartir la foto mostrada.
    private void compartir() {
        // Se obtiene la uri de la foto a compartir (OJO no poner la extensión).
        Uri uri = Uri
                .parse("android.resource://" + getActivity().getPackageName()
                        + "/drawable/centro_comercial");
        // Se crea un intent con la acción de enviar, el tipo imagen en jpg y se
        // almacena en el extra EXTRA_STREAM la uri de la Foto.
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        // Se envía el intent a través de selector de aplicación.
        startActivity(Intent.createChooser(intent,
                getString(R.string.elija_aplicacion)));
    }

}
