package es.iessaladillo.pedrojoya.pr084;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class FotosAdapter extends ArrayAdapter<Foto> {

    private Context contexto;
    private ArrayList<Foto> datos;
    private ImageLoader cargadorImagenes;
    private final int anchoFoto;
    private final int altoFoto;
    private RequestQueue colaPeticiones;

    // Clase contenedora de vistas.
    private class ContenedorVistas {
        public NetworkImageView imgFoto;
        public TextView lblDescripcion;
    }

    public FotosAdapter(Context contexto, ArrayList<Foto> datos) {
        super(contexto, R.layout.activity_main_item, datos);
        this.contexto = contexto;
        this.datos = datos;
        colaPeticiones = App.getRequestQueue();
        int max_cache_size = 1000000;
        cargadorImagenes = new ImageLoader(colaPeticiones, new BitmapLruCache(
                max_cache_size));
        anchoFoto = contexto.getResources().getDimensionPixelSize(
                R.dimen.ancho_foto);
        altoFoto = contexto.getResources().getDimensionPixelSize(
                R.dimen.alto_foto);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor;
        View item = convertView;
        // Si no se puede reciclar.
        if (item == null) {
            // Se infla el layout.
            item = LayoutInflater.from(contexto).inflate(
                    R.layout.activity_main_item, parent, false);
            // Se obtienen las vistas.
            contenedor = new ContenedorVistas();
            contenedor.imgFoto = (NetworkImageView) item
                    .findViewById(R.id.imgFoto);
            contenedor.lblDescripcion = (TextView) item
                    .findViewById(R.id.lblDescripcion);
            // Se guarda el contenedor en la propiedad tag del item.
            item.setTag(contenedor);
        } else {
            // Si se puede reciclar, obtengo el contenedor de la vista
            // reciclada.
            contenedor = (ContenedorVistas) item.getTag();
        }
        // Se escriben los datos correspondientes en las vistas.
        Foto foto = datos.get(position);
        contenedor.imgFoto.setImageUrl(foto.getUrl(), cargadorImagenes);
        contenedor.lblDescripcion.setText(foto.getDescripcion());
        return item;
    }

}
