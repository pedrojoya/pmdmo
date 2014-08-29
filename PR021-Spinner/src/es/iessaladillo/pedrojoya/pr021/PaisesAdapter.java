package es.iessaladillo.pedrojoya.pr021;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class PaisesAdapter extends BaseAdapter {

    // Variables.
    private LayoutInflater mInflador;
    private ArrayList<Pais> mPaises;
    private Context mContexto;

    // Constructor.
    public PaisesAdapter(Context contexto, ArrayList<Pais> paises) {
        mContexto = contexto;
        mPaises = paises;
        // Se obtiene un objeto inflador de layouts.
        mInflador = LayoutInflater.from(contexto);
    }

    // Clase interna para contener las vistas.
    private class ContenedorVistas {
        ImageView imgBandera;
        TextView lblNombre;
    }

    // Retorna cuántos elementos de datos maneja el adaptador.
    @Override
    public int getCount() {
        return mPaises.size();
    }

    // Obtiene un dato.
    @Override
    public Object getItem(int position) {
        return mPaises.get(position);
    }

    // Obtiene el identificador de un dato.
    @Override
    public long getItemId(int position) {
        // No gestionamos ids.
        return 0;
    }

    // Cuando se va a pintar el elemento seleccionado colapsado.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = mInflador.inflate(
                    R.layout.activity_main_pais_collapsed, parent, false);
            // Se obtienen las vistas y se almacenan en el contenedor.
            contenedor = new ContenedorVistas();
            contenedor.imgBandera = (ImageView) convertView
                    .findViewById(R.id.imgBandera);
            contenedor.lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            // Se almacena el contenedor en el tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Si se recicla, se obtiene el contenedor desde el tag de la vista.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los datos en las vistas.
        Pais pais = mPaises.get(position);
        contenedor.imgBandera.setImageResource(pais.banderaResId);
        contenedor.lblNombre.setText(pais.getNombre());
        // Se retorna la vista.
        return convertView;
    }

    // Cuando se de va a pintar la lista de elementos expandida.
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ContenedorVistas contenedor;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = mInflador.inflate(
                    R.layout.activity_main_pais_expanded, parent, false);
            // Se obtienen las vistas y se almacenan en el contenedor.
            contenedor = new ContenedorVistas();
            contenedor.imgBandera = (ImageView) convertView
                    .findViewById(R.id.imgBandera);
            contenedor.lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            // Se almacena el contenedor en el tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Si se recicla se obtiene el contenedor del tag de la vista.
            contenedor = (ContenedorVistas) convertView.getTag();
        }
        // Se escriben los valores en las vistas.
        Pais pais = mPaises.get(position);
        contenedor.imgBandera.setImageResource(pais.getBanderaResId());
        contenedor.lblNombre.setText(pais.getNombre());
        // Se retorna la vista.
        return convertView;
    }

}
