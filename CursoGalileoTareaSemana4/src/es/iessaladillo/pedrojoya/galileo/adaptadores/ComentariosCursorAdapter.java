package es.iessaladillo.pedrojoya.galileo.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.galileo.R;
import es.iessaladillo.pedrojoya.galileo.datos.BD;

public class ComentariosCursorAdapter extends SimpleCursorAdapter {

    private Context contexto;

    // Clase contenedora de vistas.
    private class ContenedorVistas {
        public TextView lblTexto;
    }

    // Constructor.
    public ComentariosCursorAdapter(Context contexto, Cursor c, String[] from,
            int[] to) {
        super(contexto, R.layout.fragment_comentarios_item, c, from, to, 0);
        this.contexto = contexto;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Se infla el layout.
        View v = LayoutInflater.from(contexto).inflate(
                R.layout.fragment_comentarios_item, parent, false);
        // Se obtienen las vistas de elemento.
        ContenedorVistas contenedor = new ContenedorVistas();
        contenedor.lblTexto = (TextView) v.findViewById(R.id.lblTexto);
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
        contenedor.lblTexto.setText(cursor.getString(cursor
                .getColumnIndex(BD.Comentario.TEXTO)));
    }
}
