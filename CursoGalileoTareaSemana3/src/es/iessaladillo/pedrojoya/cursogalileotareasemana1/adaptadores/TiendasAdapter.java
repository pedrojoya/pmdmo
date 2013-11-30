package es.iessaladillo.pedrojoya.cursogalileotareasemana1.adaptadores;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.iessaladillo.pedrojoya.cursogalileotareasemana1.R;
import es.iessaladillo.pedrojoya.cursogalileotareasemana1.modelos.Tienda;

public class TiendasAdapter extends ArrayAdapter<Tienda> {

    private Context contexto;
    private ArrayList<Tienda> datos;
    // private ImageLoader cargadorImagenes;
    private final int anchoLogo;
    private final int altoLogo;

    // Clase contenedora de vistas.
    private class ContenedorVistas {
        public ImageView imgLogo;
        public TextView lblNombre;
    }

    // Constructor.
    public TiendasAdapter(Context contexto, ArrayList<Tienda> datos) {
        super(contexto, R.layout.fragment_tiendas_lista_item, datos);
        this.contexto = contexto;
        this.datos = datos;
        // cargadorImagenes = new ImageLoader(Aplicacion.colaPeticiones,
        // new CacheImagenes());
        // Se obtiene el ancho y alto con el que deben guardarse la imágenes en
        // la caché.
        anchoLogo = contexto.getResources().getDimensionPixelSize(
                R.dimen.ancho_logo_tienda);
        altoLogo = contexto.getResources().getDimensionPixelSize(
                R.dimen.alto_logo_tienda);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = LayoutInflater.from(contexto).inflate(
                    R.layout.fragment_tiendas_lista_item, parent, false);
            // Se obtienen las vistas de elemento.
            contenedor = new ContenedorVistas();
            contenedor.imgLogo = (ImageView) convertView
                    .findViewById(R.id.imgLogo);
            contenedor.lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            // Se guarda el contenedor en la propiedad tag de la vista del
            // elemento.
            convertView.setTag(contenedor);
        } else {
            // Si se puede reciclar, obtengo el contenedor de la vista
            // reciclada del elemento.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los datos correspondientes en las vistas.
        Tienda tienda = datos.get(position);
        // contenedor.imgFoto.setImageUrl(imagen.getUrl(), cargadorImagenes);
        // Se muestra la imagen en el imageView con la librería Picasso, que
        // almacenará la imagen en caché de memoria y de disco con el tamaño
        // indicado.
        Picasso.with(contexto).load(tienda.getUrlLogo())
                .resize(anchoLogo, altoLogo).into(contenedor.imgLogo);
        contenedor.lblNombre.setText(tienda.getNombre());
        // Se retorna la vista del elemento para que se pinte.
        return convertView;
    }
}
