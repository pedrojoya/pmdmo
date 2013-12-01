package es.iessaladillo.pedrojoya.galileo.adaptadores;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.Imagen;

public class ImagenesAdapter extends ArrayAdapter<Imagen> {

    private Context contexto;
    private ArrayList<Imagen> datos;
    // private ImageLoader cargadorImagenes;
    private final int anchoFoto;
    private final int altoFoto;

    // Clase contenedora de vistas.
    private class ContenedorVistas {
        public ImageView imgFoto;
        public TextView lblUsuario;
    }

    // Constructor.
    public ImagenesAdapter(Context contexto,
            ArrayList<Imagen> datos) {
        super(contexto, R.layout.fragment_imagenes_lista_item, datos);
        this.contexto = contexto;
        this.datos = datos;
        // cargadorImagenes = new ImageLoader(Aplicacion.colaPeticiones,
        // new CacheImagenes());
        // Se obtiene el ancho y alto con el que deben guardarse la imágenes en
        // la caché.
        anchoFoto = contexto.getResources().getDimensionPixelSize(
                R.dimen.ancho_foto_instagram);
        altoFoto = contexto.getResources().getDimensionPixelSize(
                R.dimen.alto_foto_instagram);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = LayoutInflater.from(contexto).inflate(
                    R.layout.fragment_imagenes_lista_item, parent, false);
            // Se obtienen las vistas de elemento.
            contenedor = new ContenedorVistas();
            contenedor.imgFoto = (ImageView) convertView
                    .findViewById(R.id.imgFoto);
            contenedor.lblUsuario = (TextView) convertView
                    .findViewById(R.id.lblUsuario);
            // Se guarda el contenedor en la propiedad tag de la vista del
            // elemento.
            convertView.setTag(contenedor);
        } else {
            // Si se puede reciclar, obtengo el contenedor de la vista
            // reciclada del elemento.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los datos correspondientes en las vistas.
        Imagen imagen = datos.get(position);
        // contenedor.imgFoto.setImageUrl(imagen.getUrl(), cargadorImagenes);
        // Se muestra la imagen en el imageView con la librería Picasso, que
        // almacenará la imagen en caché de memoria y de disco con el tamaño
        // indicado.
        Picasso.with(contexto).load(imagen.getUrl())
                .resize(anchoFoto, altoFoto).into(contenedor.imgFoto);
        contenedor.lblUsuario.setText(imagen.getUsername());
        // Se retorna la vista del elemento para que se pinte.
        return convertView;
    }
}
