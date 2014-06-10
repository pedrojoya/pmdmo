package es.iessaladillo.pedrojoya.pr013;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Variables a nivel de clase.
    private ListView lstAlumnos;

    // Clase interna privada para Adaptador.
    private class AdaptadorAlumno extends ArrayAdapter<Alumno> {

        // Variables miembro.
        Alumno[] alumnos; // Alumnos (datos).
        LayoutInflater inflador; // Inflador de layout para la fila.

        // Clase interna privada contenedor de vistas.
        private class ContenedorVistas {
            // Variables miembro.
            TextView lblNombre;
            TextView lblCiclo;
            TextView lblCurso;
        }

        // Constructor.
        public AdaptadorAlumno(Context contexto, Alumno[] alumnos) {
            // Llamo al constructor del padre (obligatorio).
            super(contexto, R.layout.activity_main_item, alumnos);
            // Realizo la copia local de los datos.
            this.alumnos = alumnos;
            // Obtengo un objeto inflador de layouts.
            inflador = LayoutInflater.from(contexto);
        }

        @Override
        public View getView(final int posicion, View convertView,
                ViewGroup parent) {
            ContenedorVistas contenedor;
            // Creo la vista-fila y le asigno la de reciclar.
            View fila = convertView;
            // Si no puedo reciclar.
            if (convertView == null) {
                // Inflo el layout y obtengo la vista-fila.
                fila = inflador.inflate(R.layout.activity_main_item, parent,
                        false);
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
                // Obtengo el contenedor desde la prop. Tag de la vista-fila.
                contenedor = (ContenedorVistas) fila.getTag();
            }
            // Escribo los datos en las vistas de la fila.
            Alumno alumno = alumnos[posicion];
            contenedor.lblNombre.setText(alumno.getNombre());
            contenedor.lblCiclo.setText(alumno.getCiclo());
            contenedor.lblCurso.setText(alumno.getCurso());
            if (alumno.getEdad() < 18) {
                contenedor.lblNombre.setTextColor(getResources().getColor(
                        R.color.rojo));
            } else {
                contenedor.lblNombre.setTextColor(getResources().getColor(
                        R.color.negro));
            }
            // Establezco el fondo de la vista-fila dependiendo de si dicho
            // elemento está
            // seleccionado o no.
            // if (lstAlumnos.isItemChecked(posicion)) {
            // fila.setBackgroundColor(getResources().getColor(
            // R.color.seleccionado));
            // } else {
            // fila.setBackgroundColor(getResources().getColor(
            // R.color.desseleccionado));
            // }
            // Hago que el elemento cambie el estado de selección cuando se
            // pulsa en la fila.
            // lstAlumnos.setItemChecked(posicion, true);
            // fila.setOnClickListener(new OnClickListener() {
            // public void onClick(View v) {
            // lstAlumnos.setItemChecked(posicion,
            // !lstAlumnos.isItemChecked(posicion));
            // // if (lstAlumnos.isItemChecked(posicion)) {
            // // // Si ya estaba seleccionado lo desselecciono y cambio
            // // // el fondo.
            // // v.setBackgroundColor(getResources().getColor(
            // // R.color.desseleccionado));
            // // lstAlumnos.setItemChecked(posicion, false);
            // // } else {
            // // // Si no estaba seleccionado lo selecciono y cambio el
            // // // fondo.
            // // v.setBackgroundColor(getResources().getColor(
            // // R.color.seleccionado));
            // // lstAlumnos.setItemChecked(posicion, true);
            // // }
            // }
            // });
            // Retorno la vista-fila
            return fila;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Llamo al onCreate del padre.
        super.onCreate(savedInstanceState);
        // Establezco el layout que mostrará la actividad.
        setContentView(R.layout.activity_main);
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
        // Establezco el adaptador de la lista con datos que creo.
        Alumno[] alumnos = new Alumno[] {
                new Alumno("Pedro", 22, "CFGS", "2º"),
                new Alumno("Baldomero", 16, "CFGM", "2º"),
                new Alumno("Sergio", 27, "CFGM", "1º"),
                new Alumno("Pablo", 22, "CFGS", "2º"),
                new Alumno("Rodolfo", 21, "CFGS", "1º"),
                new Alumno("Atanasio", 17, "CFGM", "1º"),
                new Alumno("Gervasio", 24, "CFGS", "2º"),
                new Alumno("Prudencia", 20, "CFGS", "2º"),
                new Alumno("Oswaldo", 26, "CFGM", "1º"),
                new Alumno("Gumersindo", 17, "CFGS", "2º"),
                new Alumno("Gerardo", 18, "CFGS", "1º"),
                new Alumno("Rodrigo", 22, "CFGM", "2º"),
                new Alumno("Óscar", 21, "CFGS", "2º"),
                new Alumno("Antonio", 16, "CFGM", "1º") };
        lstAlumnos.setAdapter(new AdaptadorAlumno(this, alumnos));
        // Indico que la lista tenga el modo de selección múltiple.
        // (es importante hacerlo después de establecer el adaptador).
        lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // Inicializo todos los elementos de la lista para que no estén
        // seleccionados inicialmente (OJO! si no no funciona misteriosamente).
        // for (int i = 0; i < lstAlumnos.getCount(); i++) {
        // lstAlumnos.setItemChecked(i, false);
        // }
    }

    // Al hacer click en el botón btnMostrar.
    public void btnMostrarOnClick(View v) {
        // Alumno alumno = (Alumno) lstAlumnos.getItemAtPosition(lstAlumnos
        // .getCheckedItemPosition());
        // mostrarTostada(alumno.getNombre());
        // Cadena con los nombres de los alumnos seleccionados.

        StringBuilder sAlumnos = new StringBuilder();
        sAlumnos.append("");
        // Obtengo los alumnos seleccionados en un array de booleanos.
        ArrayList<Alumno> seleccionados = getElementosSeleccionados(lstAlumnos,
                false);

        // Recorro el array.
        for (Alumno alumno : seleccionados) {
            // Añado su nombre a la cadena.
            sAlumnos.append(alumno.getNombre() + " ");
        }
        // Informo al usuario.
        String sMensaje = sAlumnos.toString();
        if (sMensaje.equals("")) {
            sMensaje = getString(R.string.no_ha_seleccionado_ningun_alumno);
        }
        mostrarTostada(sMensaje);
    }

    // Retorna la lista de elementos seleccionados. Recibe el ListView
    // y si se les debe quitar la selección una vez procesados.
    private ArrayList<Alumno> getElementosSeleccionados(ListView lst,
            boolean uncheck) {
        ArrayList<Alumno> datos = new ArrayList<Alumno>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                // Se obtiene la posición del elemento.
                int position = selec.keyAt(i);
                // Si se ha indicado, se le quita la selección al elemento.
                if (uncheck) {
                    lst.setItemChecked(position, false);
                }
                // Se añade el elemento a la lista a retornar.
                datos.add((Alumno) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        return datos;
    }

    // Muestra un Toast.
    private void mostrarTostada(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
                .show();
    }
}