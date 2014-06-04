package es.iessaladillo.pedrojoya.pr012;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

    // Vistas.
    private ListView lstAlumnos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        lstAlumnos = (ListView) this.findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView((TextView) findViewById(R.id.lblEmpty));
        // Se crea el ArrayList de datos, el adaptador y se asigna a la lista.
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        alumnos.add(new Alumno(R.drawable.foto1, "Dolores Fuertes Barriga", 22,
                "CFGS DAM", "2ºA", false, 7, 8));
        alumnos.add(new Alumno(R.drawable.foto2, "Baldomero LLégate Ligero",
                16, "CFGM SMR", "1ºA", true, 4, 6));
        alumnos.add(new Alumno(R.drawable.foto3, "Jorge Jiménez Jaén", 20,
                "CFGM DAM", "1ºA", false, 8, 9));
        lstAlumnos.setAdapter(new AdaptadorAlumno(this, alumnos));
        lstAlumnos.setOnItemClickListener(this);
    }

    // Al pulsar sobre un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se muestra el alumno sobre el que se ha pulsado.
        Alumno alumno = (Alumno) lst.getItemAtPosition(position);
        Toast.makeText(this, alumno.getNombre(), Toast.LENGTH_SHORT).show();
    }

    // Clase interna privada para Adaptador.
    private class AdaptadorAlumno extends ArrayAdapter<Alumno> {

        // Clase interna privada contenedor de vistas.
        private class ContenedorVistas {
            // Variables miembro.
            ImageView imgFoto;
            TextView lblNombre;
            TextView lblCiclo;
            TextView lblCurso;
            TextView lblEdad;
            TextView lblRepetidor;
            TextView lblNotaAndroid;
            TextView lblNotaMultihilo;
        }

        // Variables miembro.
        ArrayList<Alumno> alumnos;
        LayoutInflater inflador;

        // Constructor.
        public AdaptadorAlumno(Context contexto, ArrayList<Alumno> alumnos) {
            super(contexto, R.layout.tarjeta, alumnos);
            this.alumnos = alumnos;
            // Se obtiene el objeto inflador de layouts.
            inflador = LayoutInflater.from(contexto);
        }

        // Antes de "pintar" cada fila.
        @Override
        public View getView(int posicion, View convertView, ViewGroup parent) {
            ContenedorVistas contenedor;
            // Si no se puede reciclar.
            if (convertView == null) {
                // Se infla el layout.
                convertView = inflador.inflate(R.layout.tarjeta, parent, false);
                // Se crea el contenedor de vistas.
                contenedor = new ContenedorVistas();
                // Se obtienen las vistas y se guardan en el contenedor.
                contenedor.imgFoto = (ImageView) convertView
                        .findViewById(R.id.imgFoto);
                contenedor.lblNombre = (TextView) convertView
                        .findViewById(R.id.lblNombre);
                contenedor.lblCiclo = (TextView) convertView
                        .findViewById(R.id.lblCiclo);
                contenedor.lblCurso = (TextView) convertView
                        .findViewById(R.id.lblCurso);
                contenedor.lblEdad = (TextView) convertView
                        .findViewById(R.id.lblEdad);
                contenedor.lblRepetidor = (TextView) convertView
                        .findViewById(R.id.lblRepetidor);
                contenedor.lblNotaAndroid = (TextView) convertView
                        .findViewById(R.id.lblNotaAndroid);
                contenedor.lblNotaMultihilo = (TextView) convertView
                        .findViewById(R.id.lblNotaMultihilo);
                convertView.setTag(contenedor);
            }
            // Si se puede reciclar.
            else {
                // Se obtiene el contenedor de vistas desde la vista reciclada.
                contenedor = (ContenedorVistas) convertView.getTag();
            }
            // Se obtiene el alumno que debe mostrar el elemento.
            Alumno alumno = alumnos.get(posicion);
            // Se configuran las vista según los datos del alumno.
            contenedor.imgFoto.setImageResource(alumno.getFoto());
            contenedor.lblNombre.setText(alumno.getNombre());
            contenedor.lblCiclo.setText(alumno.getCiclo());
            contenedor.lblCurso.setText(alumno.getCurso());
            contenedor.lblEdad.setText(alumno.getEdad() + "");
            contenedor.lblNotaAndroid.setText(alumno.getNotaAndroid() + "");
            contenedor.lblNotaMultihilo.setText(alumno.getNotaMultihilo() + "");
            if (alumno.getEdad() < 18) {
                contenedor.lblEdad
                        .setBackgroundResource(R.drawable.edad_fondo_menor);
            } else {
                contenedor.lblEdad
                        .setBackgroundResource(R.drawable.edad_fondo_mayor);
            }
            if (alumno.isRepetidor()) {
                contenedor.lblRepetidor.setVisibility(View.VISIBLE);
            } else {
                contenedor.lblRepetidor.setVisibility(View.INVISIBLE);
            }
            // Se retorna la vista-fila.
            return convertView;
        }
    }

}
