package es.iessaladillo.pedrojoya.pr069.fragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import es.iessaladillo.pedrojoya.pr069.R;
import es.iessaladillo.pedrojoya.pr069.bd.DAO;
import es.iessaladillo.pedrojoya.pr069.bd.Instituto;
import es.iessaladillo.pedrojoya.pr069.modelos.Alumno;

public class ListaAlumnosFragment extends Fragment {

    // Interfaz de comunicación con la actividad.
    public interface OnListaAlumnosFragmentListener {
        public void onAgregarAlumno();

        public void onEditarAlumno(long id);

        public void onConfirmarEliminarAlumnos();
    }

    // Variables miembro.
    private ListView lstAlumnos;
    private RelativeLayout rlListaVacia;
    private OnListaAlumnosFragmentListener listener;
    private ActionMode modoContextual;
    private DAO dao;

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Se infla el layout que debe mostrar el fragmento.
        View v = inflater.inflate(R.layout.fragment_lista_alumnos, container,
                false);
        // Se configuran las vistas.
        lstAlumnos = (ListView) v.findViewById(R.id.lstAlumnos);
        rlListaVacia = (RelativeLayout) v.findViewById(R.id.rlListaVacia);
        // Si la lista está vacía se muestra un icono y un texto para que al
        // pulsarlo se agregue un alumno.
        rlListaVacia.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Se informa al listener (actividad).
                listener.onAgregarAlumno();
            }
        });
        lstAlumnos.setEmptyView(rlListaVacia);
        // Al hacer click sobre un elemento de la lista.
        lstAlumnos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // Se obtiene el alumno sobre el que se ha pulsado.
                Cursor c = (Cursor) lstAlumnos.getItemAtPosition(position);
                Alumno alumno = DAO.cursorToAlumno(c);
                // Se da la orden de editar.
                listener.onEditarAlumno(alumno.getId());
            }

        });
        // Se establece que se puedan seleccionar varios elementos de la lista.
        lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstAlumnos.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode arg0) {
            }

            // Al crear el modo de acción contextual.
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Se infla la especificación del menú contextual en el
                // menú.
                mode.getMenuInflater().inflate(R.menu.fragment_lista_alumnos,
                        menu);
                // Se retorna que ya se ha gestionado el evento.
                return true;
            }

            // Al pulsar sobre un ítem del modo de acción contextual.
            @Override
            public boolean onActionItemClicked(ActionMode modo, MenuItem item) {
                // Dependiendo del elemento pulsado.
                switch (item.getItemId()) {
                case R.id.mnuAlumnoEliminar:
                    // Si hay elementos seleccionados se pide confirmación.
                    if (lstAlumnos.getCheckedItemPositions().size() > 0) {
                        // Se almacena el modo contextual para poder cerrarlo
                        // una vez eliminados.
                        modoContextual = modo;
                        // Se pide confirmación.
                        listener.onConfirmarEliminarAlumnos();
                    }
                    break;
                }
                // Se retorna que se ha procesado el evento.
                return true;
            }

            // Al seleccionar un elemento de la lista.
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                    int position, long id, boolean checked) {
                // Se actualiza el título de la action bar contextual.
                mode.setTitle(lstAlumnos.getCheckedItemCount() + "");
            }
        });
        // Se retorna la vista a mostrar.
        return v;
    }

    // Al mostrar el fragmento.
    @Override
    public void onResume() {
        // Se abre la base de datos.
        dao = new DAO(this.getActivity()).open();
        // Se cargan la lista de alumnos.
        cargarAlumnos();
        super.onResume();
    }

    @SuppressWarnings("deprecation")
    private void cargarAlumnos() {
        // Se obtienen los datos de los alumnos a través del DAO.
        Cursor cursor = dao.queryAllAlumnos();
        // Se establece un SimpleCursorAdapter como adaptador para la lista.
        String[] from = { Instituto.Alumno.NOMBRE, Instituto.Alumno.CURSO,
                Instituto.Alumno.TELEFONO, Instituto.Alumno.DIRECCION };
        int[] to = { R.id.lblNombre, R.id.lblCurso, R.id.lblTelefono,
                R.id.lblDireccion };
        lstAlumnos.setAdapter(new SimpleCursorAdapter(this.getActivity(),
                R.layout.fragment_lista_alumnos_item, cursor, from, to));
        // Inicialmente todos los elementos de la lista NO están seleccionados.
        for (int i = 0; i < lstAlumnos.getCount(); i++) {
            lstAlumnos.setItemChecked(i, false);
        }
    }

    // Cuando el fragmento es cargado en la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Establece la actividad como objeto listener.
            listener = (OnListaAlumnosFragmentListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(activity.toString()
                    + " debe implementar OnElementoSeleccionadoListener");
        }
    }

    @Override
    public void onPause() {
        // Se cierra la base de datos.
        dao.close();
        super.onPause();
    }

    // Elimina de la base de datos los alumnos seleccionados, actualiza el
    // adaptador y cierra el modo de acción conextual.
    public void eliminarAlumnos() {
        // Se obtiene el array con las posiciones seleccionadas.
        SparseBooleanArray seleccionados = lstAlumnos.getCheckedItemPositions();
        // Por cada selección.
        for (int i = 0; i < seleccionados.size(); i++) {
            // Se obtiene la posición del elemento en el
            // adaptador.
            int position = seleccionados.keyAt(i);
            // Si ha sido seleccionado
            if (seleccionados.valueAt(i)) {
                // Se obtiene el alumo.
                Cursor cursor = (Cursor) lstAlumnos.getItemAtPosition(position);
                Alumno alu = DAO.cursorToAlumno(cursor);
                // Se borra de la base de datos.
                dao.deleteAlumno(alu.getId());
            }
        }
        // Se finaliza el modo contextual.
        modoContextual.finish();
        // Se obtiene el cursor actualizado.
        Cursor cursor = dao.queryAllAlumnos();
        // Se actualiza el cursor del adaptador.
        SimpleCursorAdapter adaptador = ((SimpleCursorAdapter) lstAlumnos
                .getAdapter());
        adaptador.changeCursor(cursor);
        // Se informa al adaptador de que ha habido cambios en sus datos.
        adaptador.notifyDataSetChanged();
    }
}
