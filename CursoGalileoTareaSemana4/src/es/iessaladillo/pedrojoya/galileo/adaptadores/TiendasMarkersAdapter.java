package es.iessaladillo.pedrojoya.galileo.adaptadores;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.Tienda;

// Adaptador para ventana de información de marcadores.
public class TiendasMarkersAdapter implements InfoWindowAdapter {

    // Variables miembro.
    private LayoutInflater inflador;
    private Context contexto;
    private HashMap<Marker, Tienda> datos;

    // Constructor
    public TiendasMarkersAdapter(Context contexto, HashMap<Marker, Tienda> datos) {
        this.contexto = contexto;
        this.datos = datos;
        // Inflo el layout y obtengo la vista de datos.
        inflador = LayoutInflater.from(contexto);
    }

    // Cuando se va a mostrar la ventana de información sobre el marcador.
    public View getInfoContents(Marker marcador) {
        // Se obtienen las vistas.
        View v = inflador.inflate(R.layout.fragment_tiendas_mapa_marker_info,
                null, false);
        ImageView imgInfo = (ImageView) v.findViewById(R.id.imgInfo);
        TextView lblTitulo = (TextView) v.findViewById(R.id.lblTitulo);
        TextView lblInfo = (TextView) v.findViewById(R.id.lblInfo);
        // Se obtiene la tienda correspondiente.
        Tienda tienda = datos.get(marcador);
        // Le asigno los datos que deben mostrar.
        if (tienda != null) {
            lblTitulo.setText(tienda.getNombre());
            lblInfo.setText(tienda.getDireccion());
            Picasso.with(contexto).load(tienda.getUrlLogo()).into(imgInfo);
        }
        // Retorno la vista de datos.
        return v;
    }

    // Si retorna null llama a getInfoContents, y si �ste retorna null,
    // llama a la ventana de info por defecto.
    public View getInfoWindow(Marker arg0) {
        return null;
    }

}
