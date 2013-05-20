package es.iessaladillo.pedrojoya.pr067.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr067.R;
import es.iessaladillo.pedrojoya.pr067.modelos.Album;

public class DetalleFragment extends Fragment {

    public static final String ARG_ALBUM = "argAlbum";

    private ImageView imgFoto;
    private TextView lblNombre;
    private TextView lblAnio;
    private Album album;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Infla el layout, obtiene la referencia a las vistas y lo retorna.
        View v = inflater.inflate(R.layout.fragment_detalle, container, false);
        imgFoto = (ImageView) v.findViewById(R.id.imgFoto);
        lblNombre = (TextView) v.findViewById(R.id.lblNombre);
        lblAnio = (TextView) v.findViewById(R.id.lblAnio);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se obtiene el álbum desde el bundle de parámetros.
        album = this.getArguments().getParcelable(ARG_ALBUM);
        // Si hay álbum, se muestra.
        if (album != null) {
            mostrarDetalle();
        }
        super.onActivityCreated(savedInstanceState);
    }

    // Muestra el detalle de un album en las vistas correspondientes.
    public void mostrarDetalle() {
        // Escribo los datos en las vistas.
        imgFoto.setImageResource(album.getFotoResId());
        lblNombre.setText(album.getNombre());
        lblAnio.setText(album.getAnio());
    }
}
