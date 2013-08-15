package es.iessaladillo.pedrojoya.pr027.actividades;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.fragmentos.AlumnoFragment;
import es.iessaladillo.pedrojoya.pr027.fragmentos.ListaAlumnosFragment;
import es.iessaladillo.pedrojoya.pr027.fragmentos.ListaAlumnosFragment.OnListaAlumnosFragmentListener;
import es.iessaladillo.pedrojoya.pr027.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr027.fragmentos.SiNoDialogFragment.SiNoDialogListener;

public class MainActivity extends Activity implements
        OnListaAlumnosFragmentListener, SiNoDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se carga el layout que incluye de forma estática el fragmento.
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo de la opción de menú seleccionada.
        switch (item.getItemId()) {
        case android.R.id.home:
            // Se vuelve atrás.
            onBackPressed();
            break;
        case R.id.mnuAlumnoAgregar:
            // Se quiere agregar un alumno.
            onAgregarAlumno();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Muestra la actividad de alumno en "modo agregar".
    @Override
    public void onAgregarAlumno() {
        Intent i = new Intent(this, AlumnoActivity.class);
        // Se establece un extra para indicar que querremos que la actividad
        // de alumno funcione en "modo agregar".
        i.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_AGREGAR);
        this.startActivity(i);
    }

    // Muestra la actividad de alumno en "modo editar".
    @Override
    public void onEditarAlumno(long id) {
        Intent i = new Intent(this, AlumnoActivity.class);
        // Se establece un extra para indicar que querremos que la actividad
        // de alumno funcione en "modo editar".
        i.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_EDITAR);
        // Se pasa el ID del alumno que queremos editar.
        i.putExtra(AlumnoFragment.EXTRA_ID, id);
        this.startActivity(i);
    }

    // Muestra el fragmento de diálogo de confirmación de eliminación.
    @Override
    public void onConfirmarEliminarAlumnos() {
        SiNoDialogFragment frgDialogo = new SiNoDialogFragment();
        frgDialogo.show(this.getFragmentManager(), "SiNoDialogFragment");
    }

    // Se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        // Se llama al método del fragmento para eliminar los alumnos
        // seleccionados.
        ListaAlumnosFragment frgListaAlumnos = (ListaAlumnosFragment) getFragmentManager()
                .findFragmentById(R.id.frgListaAlumnos);
        frgListaAlumnos.eliminarAlumnos();
    }

    // No se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        // Método requerido por la interfaz SiNoDialogListener.
    }

}
