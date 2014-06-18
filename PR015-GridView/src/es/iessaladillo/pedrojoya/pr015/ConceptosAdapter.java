package es.iessaladillo.pedrojoya.pr015;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ConceptosAdapter extends BaseAdapter {

    // Variables miembro.
    private Context contexto; // Actividad que lo usa.
    private ArrayList<Concepto> datos; // Array de datos.
    private LayoutInflater inflador; // Inflador de layouts.

    // Constructor.
    public ConceptosAdapter(Context contexto, ArrayList<Concepto> datos) {
        // Hago una copia de los parámetros del constructor.
        this.contexto = contexto;
        this.datos = datos;
        // Obtengo un objeto inflador de layouts.
        inflador = LayoutInflater.from(contexto);
    }

    // Clase interna para contener las vistas.
    private class ContenedorVistas {
        ImageView imgFoto;
        TextView lblEnglish;
        TextView lblSpanish;
    }

    // Retorna cuántos elementos de datos maneja el adaptador.
    @Override
    public int getCount() {
        // Retorno el número de elementos del ArrayList.
        return datos.size();
    }

    // Obtiene un dato.
    @Override
    public Object getItem(int position) {
        // Retorno el id de la imagen solicitada.
        return datos.get(position);
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
        // Si no se puede reutilizar.
        if (convertView == null) {
            // Se infla la especificación XML y se obtiene la vista.
            convertView = inflador.inflate(R.layout.activity_main_celda,
                    parent, false);
            // Se obtienen las vistas y se almacenan en el contenedor.
            contenedor = new ContenedorVistas();
            contenedor.imgFoto = (ImageView) convertView
                    .findViewById(R.id.imgFoto);
            contenedor.lblEnglish = (TextView) convertView
                    .findViewById(R.id.lblEnglish);
            contenedor.lblSpanish = (TextView) convertView
                    .findViewById(R.id.lblSpanish);
            // Se almacena el contendor en el tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor desde el tag de la vista.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los datos en los widgets.
        Concepto concepto = datos.get(position);
        contenedor.imgFoto.setImageResource(concepto.getFotoResId());
        contenedor.lblEnglish.setText(concepto.getEnglish());
        contenedor.lblSpanish.setText(concepto.getSpanish());
        // Se retorna la vista.
        return convertView;
    }

}
