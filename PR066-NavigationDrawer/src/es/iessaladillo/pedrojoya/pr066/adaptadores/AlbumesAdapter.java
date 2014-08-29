package es.iessaladillo.pedrojoya.pr066.adaptadores;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr066.R;
import es.iessaladillo.pedrojoya.pr066.modelos.Album;
import es.iessaladillo.pedrojoya.pr066.modelos.NavigationDrawerHeader;
import es.iessaladillo.pedrojoya.pr066.modelos.NavigationDrawerItem;

// Clase adaptador del grid.
public class AlbumesAdapter extends ArrayAdapter<NavigationDrawerItem> {

    // Variables miembro.
    private Context mContexto; // Actividad que lo usa.
    private ArrayList<NavigationDrawerItem> mAlbumes; // Array de datos.
    private LayoutInflater mInflador; // Inflador de layouts.

    // Clase interna para contener las vistas de un álbum.
    private class ContenedorVistasAlbum {
        ImageView imgFotoItem;
        TextView lblNombreItem;
    }

    // Clase interna para contener las vistas de un encabezado.
    private class ContenedorVistasHeader {
        TextView lblText;
    }

    public AlbumesAdapter(Context contexto,
            ArrayList<NavigationDrawerItem> albumes) {
        super(contexto, R.layout.panel_navegacion_list_item, albumes);
        // Hago una copia de los parámetros del constructor.
        this.mContexto = contexto;
        this.mAlbumes = albumes;
        // Obtengo un objeto inflador de layouts.
        mInflador = LayoutInflater.from(contexto);
    }

    // Cuando se va a pintar un elemento de la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        // Dependiendo del tipo de elemento se retorna una vista u otra.
        NavigationDrawerItem elemento = this.getItem(position);
        switch (elemento.getType()) {
        case NavigationDrawerItem.TYPE_HEADER:
            v = getHeaderView(convertView, parent, elemento);
            break;
        default:
            v = getAlbumView(convertView, parent, elemento);
            break;
        }
        return v;
    }

    @Override
    // Retorna el tipo de vista para una posición. Necesario para que la
    // reutilización de vistas de ítems se realice de acuerdo al tipo de vista.
    public int getItemViewType(int position) {
        return mAlbumes.get(position).getType();
    }

    // Retorna el número de tipos de ítems distintos existentes en la lista.
    // Necesario para la reutilización correcta de ítems.
    @Override
    public int getViewTypeCount() {
        return NavigationDrawerItem.NUM_TYPES;
    }

    // Retorna la vista a mostrar para un ítem que corresponde a un album.
    private View getAlbumView(View convertView, ViewGroup parent,
            NavigationDrawerItem elemento) {
        ContenedorVistasAlbum contenedor;
        // Si se puede reutilizar.
        if (convertView == null) {
            // Se infla la vista-fila a partir de la especificación XML.
            convertView = mInflador.inflate(
                    R.layout.panel_navegacion_list_item, parent, false);
            // Se crea un objeto contenedor con las referencias a las vistas
            // de la fila y se almacena en el Tag de la vista-fila.
            contenedor = new ContenedorVistasAlbum();
            contenedor.imgFotoItem = (ImageView) convertView
                    .findViewById(R.id.imgFotoItem);
            contenedor.lblNombreItem = (TextView) convertView
                    .findViewById(R.id.lblNombreItem);
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor desde la propiedad Tag de la
            // vista-fila.
            contenedor = (ContenedorVistasAlbum) convertView.getTag();
        }
        // Escribo lo valores correspondientes de las vistas de la fila.
        Album album = (Album) elemento;
        contenedor.imgFotoItem.setImageResource(album.getFotoResId());
        contenedor.lblNombreItem.setText(album.getNombre());
        // Retorno la vista-fila.
        return convertView;
    }

    // Retorna la vista a mostrar para un ítem que corresponde a un album.
    private View getHeaderView(View convertView, ViewGroup parent,
            NavigationDrawerItem elemento) {
        ContenedorVistasHeader contenedor;
        // Si se puede reutilizar.
        if (convertView == null) {
            // Se infla la vista-fila a partir de la especificación XML.
            convertView = mInflador.inflate(
                    R.layout.panel_navegacion_list_header, parent, false);
            // Se crea un objeto contenedor con las referencias a las vistas
            // de la fila y se almacena en el Tag de la vista-fila.
            contenedor = new ContenedorVistasHeader();
            contenedor.lblText = (TextView) convertView
                    .findViewById(R.id.lblText);
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor desde la propiedad Tag de la vista-fila.
            contenedor = (ContenedorVistasHeader) convertView.getTag();
        }
        // Se escriben lo valores correspondientes de las vistas de la fila.
        NavigationDrawerHeader header = (NavigationDrawerHeader) elemento;
        contenedor.lblText.setText(header.getText());
        // Se indica que no se haga nada al hacer click sobre la vista.
        convertView.setOnClickListener(null);
        convertView.setOnLongClickListener(null);
        convertView.setLongClickable(false);
        // Se retorna la vista-fila.
        return convertView;
    }

}
