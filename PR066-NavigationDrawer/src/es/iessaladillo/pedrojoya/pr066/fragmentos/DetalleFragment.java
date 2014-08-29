package es.iessaladillo.pedrojoya.pr066.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr066.R;
import es.iessaladillo.pedrojoya.pr066.modelos.Album;

public class DetalleFragment extends Fragment {

    // Constantes.
    public static final String ARG_ALBUM = "argAlbum";

    // Vistas.
    private ImageView imgFoto;
    private TextView lblNombre;
    private TextView lblAnio;

    // Retorna una instancia del fragmento ya configurada.
    // Recibe el album que debe mostrar el fragmento.
    public static DetalleFragment newInstance(Album album) {
        DetalleFragment frgDetalle = new DetalleFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ALBUM, album);
        frgDetalle.setArguments(args);
        return frgDetalle;
    }

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

    // Cuando la actividad ha sido creada completamente.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se obtiene el álbum desde el bundle de parámetros.
        Album album = this.getArguments().getParcelable(ARG_ALBUM);
        // Si hay álbum, se muestra.
        if (album != null) {
            mostrarDetalle(album);
        }
        super.onActivityCreated(savedInstanceState);
    }

    // Muestra el detalle de un album en las vistas correspondientes.
    public void mostrarDetalle(Album album) {
        // Escribo los datos en las vistas.
        imgFoto.setImageResource(album.getFotoResId());
        lblNombre.setText(album.getNombre());
        lblAnio.setText(album.getAnio());
    }
}
