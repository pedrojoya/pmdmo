package es.iessaladillo.pedrojoya.pr073;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr073.ListViewCompat.MultiChoiceModeListenerCompat;

public class MainActivity extends ActionBarActivity {

    private TextView txtAlumno;
    @SuppressWarnings("unused")
    private ActionMode modoIndividual;
    private ListViewCompat lstAsignaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configTxtAlumno();
        configLstAsignaturas();
    }

    private void configTxtAlumno() {
        // Se establece el listener para onLongClick sobre txtAlumno.
        txtAlumno = (TextView) this.findViewById(R.id.txtAlumno);
        txtAlumno.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Se activa el modo de acción contextual.
                activarModoContextualIndividual(v);
                // Se retorna que se ha procesado el evento.
                return true;
            }
        });
    }

    private void activarModoContextualIndividual(View v) {
        // Se inicia el modo de acción contextual pasándole el objeto listener
        // que atenderá a los eventos del modo de acción contextual, que creamos
        // de forma inline.
        modoIndividual = this.startSupportActionMode(new ActionMode.Callback() {
            // Al preparar el modo.
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // No se hace nada.
                return false;
            }

            // Al destruir el modo.
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Se destruye el modo de acción contextual.
                modoIndividual = null;
            }

            // Al crear el modo de acción.
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Se infla la especificación del menú contextual en el menú.
                mode.getMenuInflater().inflate(R.menu.txtalumnos_menu, menu);
                // Se retorna que se ha procesado la creación del modo de acción
                // contextual.
                return true;
            }

            // Al pulsar sobre un ítem de acción del modo contextual.
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Dependiendo del elemento pulsado.
                switch (item.getItemId()) {
                case R.id.mnuEditar:
                    Toast.makeText(getApplicationContext(), R.string.editar,
                            Toast.LENGTH_LONG).show();
                    break;
                case R.id.mnuEliminar:
                    Toast.makeText(getApplicationContext(), R.string.eliminar,
                            Toast.LENGTH_LONG).show();
                    break;
                }
                // Se retorna que se ha procesado el evento.
                return true;
            }
        });
        // Se indica que la vista ha sido seleccionada.
        v.setSelected(true);

    }

    private void configLstAsignaturas() {
        // Se establece el modo de selección múltiple modal.
        lstAsignaturas = (ListViewCompat) this
                .findViewById(R.id.lstAsignaturas);
        lstAsignaturas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Se carga la lista con datos.
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
                this, R.array.asignaturas,
                android.R.layout.simple_list_item_multiple_choice);
        lstAsignaturas.setAdapter(adaptador);
        // Se establece el listener para los eventos del modo de acción
        // contextual.
        lstAsignaturas
                .setMultiChoiceModeListener(new MultiChoiceModeListenerCompat() {
                    // Al prepararse el modo.
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode,
                            Menu menu) {
                        // No se hace nada.
                        return false;
                    }

                    // Al destruirse el modo.
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        // No se hace nada.
                    }

                    // Al crear el modo.
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        // Se infla la especificación del menú contextual en el
                        // menú.
                        mode.getMenuInflater().inflate(R.menu.txtalumnos_menu,
                                menu);
                        // Se retorna que ya se ha gestionado el evento.
                        return true;
                    }

                    // Al hacer click sobre un ítem de acción del modo
                    // contextual.
                    @Override
                    public boolean onActionItemClicked(ActionMode menu,
                            MenuItem item) {
                        // Dependiendo del elemento pulsado.
                        switch (item.getItemId()) {
                        case R.id.mnuEditar:
                            Toast.makeText(getApplicationContext(),
                                    R.string.editar, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.mnuEliminar:
                            Toast.makeText(getApplicationContext(),
                                    R.string.eliminar, Toast.LENGTH_LONG)
                                    .show();
                            break;
                        }
                        // Se retorna que se ha procesado el evento.
                        return true;
                    }

                    // Al cambiar el estado de selección de un elemento.
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode,
                            int position, long id, boolean checked) {
                        // Se actualiza el título de la action bar contextual.
                        mode.setTitle(lstAsignaturas.getCheckedItemCount()
                                + getString(R.string.de)
                                + lstAsignaturas.getCount());

                    }
                });
        lstAsignaturas.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adaptador, View v,
                    int position, long id) {
                // Se selecciona el elemento.
                lstAsignaturas.setItemChecked(position, true);

            }
        });

    }

}