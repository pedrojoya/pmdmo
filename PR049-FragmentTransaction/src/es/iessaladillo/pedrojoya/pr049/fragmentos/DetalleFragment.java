package es.iessaladillo.pedrojoya.pr049.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.actividades.MainActivity;
import es.iessaladillo.pedrojoya.pr049.modelos.Album;

public class DetalleFragment extends Fragment {

    private ImageView imgFoto;
    private TextView lblNombre;
    private TextView lblAnio;
    private Album album;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Infla el layout.
        View v = inflater.inflate(R.layout.fragment_detalle, container, false);
        // Obtiene el álbum.
        album = this.getArguments().getParcelable(MainActivity.EXTRA_ALBUM);
        // Escribe los datos.
        escribirDatos(v);
        // Retorna la vista correspondiente al layout.
        return v;
    }

    // Muestra el detalle de un album en las vistas correspondientes.
    // Recibe el álbum.
    public void mostrarDetalle(Album album) {
        // Se guarda la copia local del album.
        this.album = album;
        View v = this.getView();
        escribirDatos(v);
    }

    private void escribirDatos(View v) {
        // Obtengo la referencia a las vistas.
        imgFoto = (ImageView) v.findViewById(R.id.imgFoto);
        lblNombre = (TextView) v.findViewById(R.id.lblNombre);
        lblAnio = (TextView) v.findViewById(R.id.lblAnio);
        // Escribo los datos en las vistas.
        imgFoto.setImageResource(album.getFotoResId());
        lblNombre.setText(album.getNombre());
        lblAnio.setText(album.getAnio());
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        // Se guarda el album en el Bundle.
//        outState.putParcelable("album", album);
//    }
//
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se recupera el album y se muestra (si lo hay).
        if (savedInstanceState != null) {
            album = savedInstanceState.getParcelable(MainActivity.EXTRA_ALBUM);
            if (album != null) {
                mostrarDetalle(album);
            }
        }
        super.onActivityCreated(savedInstanceState);
    }
}
