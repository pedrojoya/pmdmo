package es.iessaladillo.pedrojoya.pr066.adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr066.R;
import es.iessaladillo.pedrojoya.pr066.modelos.Album;

// Clase adaptador del grid.
public class AlbumesAdapter extends BaseAdapter {

    // Variables miembro.
    private Activity contexto; // Actividad que lo usa.
    private ArrayList<Album> albumes; // Array de datos.
    private LayoutInflater inflador; // Inflador de layouts.

    public AlbumesAdapter(Activity contexto, ArrayList<Album> albumes) {
        // Hago una copia de los parámetros del constructor.
        this.contexto = contexto;
        this.albumes = albumes;
        // Obtengo un objeto inflador de layouts.
        inflador = this.contexto.getLayoutInflater();
    }

    // Clase interna para contener las vistas.
    private class ContenedorVistas {
        ImageView imgFotoItem;
        TextView lblNombreItem;
    }

    // Retorna cuántos elementos de datos maneja el adaptador.
    @Override
    public int getCount() {
        // Retorno el número de elementos del ArrayList.
        return albumes.size();
    }

    // Obtiene un dato.
    @Override
    public Object getItem(int position) {
        // Retorno el id de la imagen solicitada.
        return albumes.get(position);
    }

    // Obtiene el identificador de un dato.
    @Override
    public long getItemId(int position) {
        // No gestionamos ids.
        return 0;
    }

    // Cuando se va a pintar un elemento de la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Variables locales.
        ContenedorVistas contenedor; // Contenedor de vistas.
        // Intento reutilizar.
        View fila = convertView;
        if (fila == null) {
            // Inflo la vista-fila a partir de la especificación XML.
            fila = inflador.inflate(R.layout.panel_navegacion_list_item, parent, false);
            // Creo un objeto contenedor con las referencias a las vistas
            // de la fila y lo almaceno en el Tag de la vista-fila.
            contenedor = new ContenedorVistas();
            contenedor.imgFotoItem = (ImageView) fila
                    .findViewById(R.id.imgFotoItem);
            contenedor.lblNombreItem = (TextView) fila
                    .findViewById(R.id.lblNombreItem);
            fila.setTag(contenedor);
        } else {
            // Obtengo el contenedor desde la propiedad Tag de la vista-fila.
            contenedor = (ContenedorVistas) fila.getTag();
        }
        // Escribo lo valores correspondientes de las vistas de la fila.
        Album album = albumes.get(position);
        contenedor.imgFotoItem.setImageResource(album.getFotoResId());
        contenedor.lblNombreItem.setText(album.getNombre());
        // Retorno la vista-fila.
        return fila;
    }

}
