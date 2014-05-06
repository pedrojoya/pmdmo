package es.iessaladillo.pedrojoya.pr012;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Variables a nivel de clase.
    private ListView lstAlumnos;

    // Clase interna privada contenedor de vistas.
    private class ContenedorVistas {
        // Variables miembro.
        TextView lblNombre;
        TextView lblCiclo;
        TextView lblCurso;
    }

    // Clase interna privada para Adaptador.
    private class AdaptadorAlumno extends ArrayAdapter<Alumno> {

        // Variables miembro.
        Alumno[] alumnos; // Alumnos (datos).
        LayoutInflater inflador; // Para inflar el layout de la fila.

        public AdaptadorAlumno(Context contexto, Alumno[] alumnos) {
            // Debo llamar al constructor de ArrayAdapter.
            super(contexto, R.layout.fila, alumnos);
            // Realizo una copia local de los datos pasados al constructor.
            this.alumnos = alumnos;
            // Obtengo el objeto inflador.
            inflador = LayoutInflater.from(contexto);
        }

        @Override
        public View getView(int posicion, View convertView, ViewGroup parent) {
            ContenedorVistas contenedor;
            // Creo la vista-fila y le asigno la de reciclar.
            View fila = convertView;
            // Si no puedo reciclar.
            if (convertView == null) {
                // Inflo el layout y obtengo la vista-fila.
                fila = inflador.inflate(R.layout.fila, parent, false);
                // Creo un nuevo contenedor de vistas.
                contenedor = new ContenedorVistas();
                // Guardo en el contenedor las referencias a las vistas
                // de dentro de la vista-fila.
                contenedor.lblNombre = (TextView) fila
                        .findViewById(R.id.lblNombre);
                contenedor.lblCiclo = (TextView) fila
                        .findViewById(R.id.lblCiclo);
                contenedor.lblCurso = (TextView) fila
                        .findViewById(R.id.lblCurso);
                // Guardo el contenedor en la propiedad Tag de la vista-fila.
                fila.setTag(contenedor);
            } else { // Si puedo reciclar.
                     // Obtengo el contenedor desde la prop. Tag de la
                     // vista-fila.
                contenedor = (ContenedorVistas) fila.getTag();
            }
            // Escribo los datos en las vistas de la fila.
            contenedor.lblNombre.setText(alumnos[posicion].getNombre());
            contenedor.lblCiclo.setText(alumnos[posicion].getCiclo());
            contenedor.lblCurso.setText(alumnos[posicion].getCurso());
            if (alumnos[posicion].getEdad() < 18) {
                contenedor.lblNombre.setTextColor(getResources().getColor(
                        R.color.rojo));
            } else {
                contenedor.lblNombre.setTextColor(getResources().getColor(
                        R.color.negro));
            }
            return fila;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Llamo al onCreate del padre.
        super.onCreate(savedInstanceState);
        // Establezco el layout que mostrará la actividad.
        setContentView(R.layout.main);
        // Obtengo las vistas.
        getVistas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void getVistas() {
        lstAlumnos = (ListView) this.findViewById(R.id.lstAlumnos);
        Alumno[] alumnos = new Alumno[] {
                new Alumno("Pedro", 22, "CFGS", "2º"),
                new Alumno("Antonio", 16, "CFGM", "1º") };
        lstAlumnos.setAdapter(new AdaptadorAlumno(this, alumnos));
        lstAlumnos.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> lst, View v, int posicion,
                    long id) {
                // Obtengo el alumno sobre el que se ha pulsado.
                Alumno alumno = (Alumno) lst.getItemAtPosition(posicion);
                // Informo al usuario.
                mostrarTostada(alumno.getNombre() + ", " + alumno.getCurso()
                        + alumno.getCiclo());
            }
        });

    }

    private void mostrarTostada(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
                .show();
    }

}
