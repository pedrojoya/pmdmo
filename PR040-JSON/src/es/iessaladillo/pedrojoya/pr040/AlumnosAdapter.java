package es.iessaladillo.pedrojoya.pr040;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    // Variables.
    private Context contexto;
    private List<Alumno> alumnos;

    // Clase interna contenedora de vistas.
    private class Contenedor {
        TextView lblNombre;
        TextView lblTelefono;
        TextView lblDireccion;
        TextView lblCurso;

        public Contenedor(TextView lblNombre, TextView lblTelefono,
                TextView lblDireccion, TextView lblCurso) {
            this.lblNombre = lblNombre;
            this.lblTelefono = lblTelefono;
            this.lblDireccion = lblDireccion;
            this.lblCurso = lblCurso;
        }

    }

    // Constructor.
    public AlumnosAdapter(Context contexto, List<Alumno> alumnos) {
        // Se llama al constructor del padre.
        super(contexto, R.layout.activity_main_item, alumnos);
        // Se hace copia local de los parámetros.
        this.contexto = contexto;
        this.alumnos = alumnos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contenedor contenedor;
        // Si no podemos reciclar.
        if (convertView == null) {
            // Se infla el layout.
            convertView = LayoutInflater.from(contexto).inflate(
                    R.layout.activity_main_item, parent, false);
            // Se obtienen las vistas.
            TextView lblNombre = (TextView) convertView
                    .findViewById(R.id.lblNombre);
            TextView lblDireccion = (TextView) convertView
                    .findViewById(R.id.lblDireccion);
            TextView lblTelefono = (TextView) convertView
                    .findViewById(R.id.lblTelefono);
            TextView lblCurso = (TextView) convertView
                    .findViewById(R.id.lblCurso);
            // Se crea un contenedor de vistas.
            contenedor = new Contenedor(lblNombre, lblTelefono, lblDireccion,
                    lblCurso);
            // Se almacena el contenedor en la propiedad tag de la vista.
            convertView.setTag(contenedor);
        } else {
            // Se obtiene el contenedor de la propiedad tag de la vista.
            contenedor = (Contenedor) convertView.getTag();
        }
        // Se escriben los datos en las vistas.
        Alumno alumno = alumnos.get(position);
        contenedor.lblNombre.setText(alumno.getNombre());
        contenedor.lblTelefono.setText(alumno.getTelefono());
        contenedor.lblDireccion.setText(alumno.getDireccion());
        contenedor.lblCurso.setText(alumno.getCurso());
        // Se retorna la vista que debe mostrarse.
        return convertView;
    }
}
