package es.iessaladillo.pedrojoya.pr088client;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import es.iessaladillo.pedrojoya.pr088.R;
import es.iessaladillo.pedrojoya.pr088mensaje.Mensaje;

public class MensajesAdapter extends ArrayAdapter<Mensaje> {

    // Propieadades.
    private Context context;
    private List<Mensaje> datos;
    private String autor;
    private SimpleDateFormat formateador;

    // Constructor.
    public MensajesAdapter(Context context, List<Mensaje> datos, String autor) {
        super(context, R.layout.activity_main_item, datos);
        this.context = context;
        this.datos = datos;
        this.autor = autor;
        formateador = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    // Clase privada para contener las vistas del elemento.
    private class Contenedor {
        RelativeLayout rlElemento;
        TextView lblAutor;
        TextView lblFecha;
        TextView lblTexto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contenedor contenedor;
        // Si se puede reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.activity_main_item, parent, false);
            // Se crea el contenedor de vistas.
            contenedor = new Contenedor();
            // Se obtienen las referencias a las vistas y se almacenan en el
            // contenedor.
            contenedor.rlElemento = (RelativeLayout) convertView
                    .findViewById(R.id.rlElemento);
            contenedor.lblAutor = (TextView) convertView
                    .findViewById(R.id.lblAutor);
            contenedor.lblFecha = (TextView) convertView
                    .findViewById(R.id.lblFecha);
            contenedor.lblTexto = (TextView) convertView
                    .findViewById(R.id.lblTexto);
            // Se almacena el contenedor en el tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor desde el tag de la vista.
            contenedor = (Contenedor) convertView.getTag();
        }
        // Se obtiene el mensaje a mostrar.
        Mensaje mensaje = datos.get(position);
        // Se obtienen los parámetros de posicionamiento del rlElemento.
        LayoutParams lp = (LayoutParams) contenedor.rlElemento
                .getLayoutParams();
        // Se configura el fondo y la posición del elemento dependiendo de si el
        // mensaje es propio o no.
        if (mensaje.getAutor().equals(autor)) {
            contenedor.rlElemento
                    .setBackgroundResource(R.drawable.mensaje_propio);
            lp.gravity = Gravity.RIGHT;
            contenedor.lblAutor.setText("");
        } else {
            contenedor.rlElemento
                    .setBackgroundResource(R.drawable.mensaje_ajeno);
            lp.gravity = Gravity.LEFT;
            contenedor.lblAutor.setText(mensaje.getAutor());
        }
        contenedor.rlElemento.setLayoutParams(lp);
        // Se escriben los datos del mensaje en las vistas.
        contenedor.lblFecha.setText(formateador.format(mensaje.getFecha()));
        contenedor.lblTexto.setText(mensaje.getTexto());
        // Se retorna la vista configurada.
        return convertView;
    }
}
