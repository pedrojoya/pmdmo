package es.iessaladillo.pedrojoya.pr012;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
        // Se crea el ArrayList de datos.
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        // Primer alumno.
        HashMap<String, Integer> notas = new HashMap<String, Integer>();
        notas.put("PMDMO", 7);
        notas.put("PSPRO", 6);
        notas.put("DI", 5);
        notas.put("ACCDAT", 9);
        notas.put("HLC", 4);
        notas.put("SGE", 3);
        notas.put("EMPIE", 9);
        Alumno alumno = new Alumno(R.drawable.foto1,
                "Dolores Fuertes de Barriga", 22, "CFGS DAM", "2º", false,
                notas);
        alumnos.add(alumno);
        // Segundo alumno.
        notas = new HashMap<String, Integer>();
        notas.put("APLWEB", 4);
        notas.put("SERVRED", 7);
        notas.put("SOR", 8);
        alumno = new Alumno(R.drawable.foto2, "Baldomero LLégate Ligero", 17,
                "CFGm SMR", "2º", true, notas);
        alumnos.add(alumno);
        // Tercer alumno.
        notas = new HashMap<String, Integer>();
        notas.put("PROGR", 8);
        notas.put("ENTDES", 3);
        notas.put("BD", 1);
        notas.put("LENMAR", 4);
        alumno = new Alumno(R.drawable.foto3, "Jorge Javier Jiménez Jaén", 36,
                "CFGS DAM", "1º", false, notas);
        alumnos.add(alumno);
        // Se crea el adaptador y se asigna a la lista.
        lstAlumnos.setAdapter(new AdaptadorAlumno(this, alumnos));
        // La actividad actuará como listener cuando se pulse un elemento.
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
            TextView[] lblModulos = new TextView[8];
            TextView[] lblNotas = new TextView[8];
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
                contenedor.lblModulos[0] = (TextView) convertView
                        .findViewById(R.id.lblModulo1);
                contenedor.lblNotas[0] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo1);
                contenedor.lblModulos[1] = (TextView) convertView
                        .findViewById(R.id.lblModulo2);
                contenedor.lblNotas[1] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo2);
                contenedor.lblModulos[2] = (TextView) convertView
                        .findViewById(R.id.lblModulo3);
                contenedor.lblNotas[2] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo3);
                contenedor.lblModulos[3] = (TextView) convertView
                        .findViewById(R.id.lblModulo4);
                contenedor.lblNotas[3] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo4);
                contenedor.lblModulos[4] = (TextView) convertView
                        .findViewById(R.id.lblModulo5);
                contenedor.lblNotas[4] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo5);
                contenedor.lblModulos[5] = (TextView) convertView
                        .findViewById(R.id.lblModulo6);
                contenedor.lblNotas[5] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo6);
                contenedor.lblModulos[6] = (TextView) convertView
                        .findViewById(R.id.lblModulo7);
                contenedor.lblNotas[6] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo7);
                contenedor.lblModulos[7] = (TextView) convertView
                        .findViewById(R.id.lblModulo8);
                contenedor.lblNotas[7] = (TextView) convertView
                        .findViewById(R.id.lblNotaModulo8);
                convertView.setTag(contenedor);
            }
            // Si se puede reciclar.
            else {
                // Se obtiene el contenedor de vistas desde la vista reciclada.
                contenedor = (ContenedorVistas) convertView.getTag();
            }
            // Se obtiene el alumno que debe mostrar el elemento.
            Alumno alumno = alumnos.get(posicion);
            // Se escriben los datos del alumno en las vistas.
            contenedor.imgFoto.setImageResource(alumno.getFoto());
            contenedor.lblNombre.setText(alumno.getNombre());
            contenedor.lblCiclo.setText(alumno.getCiclo());
            contenedor.lblCurso.setText(alumno.getCurso());
            contenedor.lblEdad.setText(alumno.getEdad() + "");
            //
            Set<Entry<String, Integer>> notas = alumno.getNotas().entrySet();

            int i = 0;
            for (Map.Entry<String, Integer> elemento : notas) {
                contenedor.lblModulos[i].setText(elemento.getKey());
                contenedor.lblNotas[i].setText(elemento.getValue().toString());
                contenedor.lblModulos[i].setVisibility(View.VISIBLE);
                contenedor.lblNotas[i].setVisibility(View.VISIBLE);
                i++;
                if (i >= 8)
                    break;
            }
            for (; i < 8; i++) {
                contenedor.lblModulos[i].setVisibility(View.GONE);
                contenedor.lblNotas[i].setVisibility(View.GONE);
            }
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
