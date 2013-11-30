package es.iessaladillo.pedrojoya.cursogalileotareasemana1.adaptadores;

import java.util.ArrayList;
import java.util.Comparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos.Foto;

public class FotosAdapter extends ArrayAdapter<Foto> {

    private Context contexto;
    private ArrayList<Foto> datos;
    // private ImageLoader cargadorImagenes;
    private final int anchoFoto;
    private final int altoFoto;

    // Clase contenedora de vistas.
    private class ContenedorVistas {
        public ImageView imgFoto;
        public TextView lblDescripcion;
    }

    public FotosAdapter(Context contexto, ArrayList<Foto> datos) {
        super(contexto, R.layout.fragment_instagram_lista_item, datos);
        this.contexto = contexto;
        this.datos = datos;
        // cargadorImagenes = new ImageLoader(Aplicacion.colaPeticiones,
        // new CacheImagenes());
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
                    R.layout.fragment_fotos_lista_item, parent, false);
            // Se obtienen las vistas.
            contenedor = new ContenedorVistas();
            contenedor.imgFoto = (ImageView) item.findViewById(R.id.imgFoto);
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
        Foto imagen = datos.get(position);
        // contenedor.imgFoto.setImageUrl(imagen.getUrl(), cargadorImagenes);
        Picasso.with(contexto).load(imagen.getUrl())
                .resize(anchoFoto, altoFoto).into(contenedor.imgFoto);
        contenedor.lblDescripcion.setText(imagen.getDescripcion());
        return item;
    }

    @Override
    public void notifyDataSetChanged() {
        // Se deshabilita temporalmente para que el sort funcione bien.
        setNotifyOnChange(false);
        // Se ordena el array de datos del adaptador (descendientemente según su
        // descripción).
        sort(new Comparator<Foto>() {
            @Override
            public int compare(Foto foto1, Foto foto2) {
                return foto1.compareTo(foto2);
            }
        });
        super.notifyDataSetChanged();
    }
}
