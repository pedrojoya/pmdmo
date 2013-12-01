package es.iessaladillo.pedrojoya.galileo.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.BD;

public class FotosCursorAdapter extends SimpleCursorAdapter {

    private Context contexto;
    private final int anchoFoto;
    private final int altoFoto;

    // Clase contenedora de vistas.
    private class ContenedorVistas {
        public ImageView imgFoto;
        public TextView lblDescripcion;
    }

    // Constructor.
    public FotosCursorAdapter(Context contexto, Cursor c, String[] from,
            int[] to) {
        super(contexto, R.layout.fragment_fotos_lista_item, c, from, to, 0);
        this.contexto = contexto;
        // Se obtiene el ancho y alto con el que deben guardarse la imágenes en
        // la caché.
        anchoFoto = contexto.getResources().getDimensionPixelSize(
                R.dimen.ancho_foto);
        altoFoto = contexto.getResources().getDimensionPixelSize(
                R.dimen.alto_foto);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Se infla el layout.
        View v = LayoutInflater.from(contexto).inflate(
                R.layout.fragment_fotos_lista_item, parent, false);
        // Se obtienen las vistas de elemento.
        ContenedorVistas contenedor = new ContenedorVistas();
        contenedor.imgFoto = (ImageView) v.findViewById(R.id.imgFoto);
        contenedor.lblDescripcion = (TextView) v
                .findViewById(R.id.lblDescripcion);
        // Se guarda el contenedor en la propiedad tag de la vista del
        // elemento.
        v.setTag(contenedor);
        bindView(v, context, getCursor());
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Se obtiene el contenedor de la vista reciclada del elemento.
        ContenedorVistas contenedor = (ContenedorVistas) view.getTag();
        Picasso.with(contexto)
                .load(cursor.getString(cursor.getColumnIndex(BD.Foto.URL)))
                .resize(anchoFoto, altoFoto).into(contenedor.imgFoto);
        contenedor.lblDescripcion.setText(cursor.getString(cursor
                .getColumnIndex(BD.Foto.DESCRIPCION)));
    }
}
