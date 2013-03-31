package es.iessaladillo.pedrojoya.pr047.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr047.R;
import es.iessaladillo.pedrojoya.pr047.modelos.Album;

public class DetalleFragment extends Fragment {

    private ImageView imgFoto;
    private TextView lblNombre;
    private TextView lblAnio;
    private RelativeLayout rlDatos;
    private TextView lblMensaje;
    private Album album;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Infla el layout y retorna la vista correspondiente.
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    // Muestra el detalle de un album en las vistas correspondientes.
    // Recibe el álbum.
    public void mostrarDetalle(Album album) {
        // Se guarda la copia local del album.
        this.album = album;
        // Se escriben los datos en las vistas correspondientes.
        getVistas();
        lblMensaje.setVisibility(View.GONE);
        rlDatos.setVisibility(View.VISIBLE);
        imgFoto.setImageResource(album.getFotoResId());
        lblNombre.setText(album.getNombre());
        lblAnio.setText(album.getAnio());
    }

    // Obtiene las referencias de las vistas.
    private void getVistas() {
        View v = this.getView();
        rlDatos = (RelativeLayout) v.findViewById(R.id.rlDatos);
        lblMensaje = (TextView) v.findViewById(R.id.lblMensaje);
        imgFoto = (ImageView) v.findViewById(R.id.imgFoto);
        lblNombre = (TextView) v.findViewById(R.id.lblNombre);
        lblAnio = (TextView) v.findViewById(R.id.lblAnio);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se guarda el album en el Bundle.
        outState.putParcelable("album", album);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se recupera el album y se muestra (si lo hay).
        if (savedInstanceState != null) {
            album = savedInstanceState.getParcelable("album");
            if (album != null) {
                mostrarDetalle(album);
            }
        }
        super.onActivityCreated(savedInstanceState);
    }
}
