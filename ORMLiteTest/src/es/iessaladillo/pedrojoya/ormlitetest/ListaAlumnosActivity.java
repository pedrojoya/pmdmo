package es.iessaladillo.pedrojoya.ormlitetest;

import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.Dao;

public class ListaAlumnosActivity extends OrmLiteBaseListActivity<AdaptadorBD> {

	// Variables a nivel de clase.
	private AdaptadorBD bd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que utilizará la actividad.
		setContentView(R.layout.lista);
		// Registro la lista para menú contextual
		this.registerForContextMenu(this.getListView());
		// Obtengo el adaptador de la BD y la abro.
		bd = this.getHelper();
	}

	// Obtiene los datos de la BD y los carga en la lista.
	private void cargarLista() {
		// Consulto en la BD los datos del alumno.
		try {
			// Consulto todos los alumnos.
			Dao<Alumno, Integer> dao = bd.getAlumnoDAO();
			List<Alumno> alumnos = dao.queryForAll();
			// Creo un adaptador para la lista.
			AlumnoListAdapter adaptador = new AlumnoListAdapter(this, alumnos);
			// Asigno el adaptador a la ListActivity.
			this.setListAdapter(adaptador);
		} catch (java.sql.SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Envío el intent correspondiente.
		editarAlumno(id);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// Llamo al padre por si quiere añadir alguna opción de menú.
		super.onCreateContextMenu(menu, v, menuInfo);
		// Inflo el menú en el parámetro de salida, gracias a un MenuInflater (inflador).
		getMenuInflater().inflate(R.menu.menu_lista, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Obtengo la info del item de menú seleccionado
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    // Obtengo el registro correspondiente al alumno seleccionado en forma de cursor.
		Alumno alumno = (Alumno) getListView().getItemAtPosition(info.position);
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
		// Opción de menú Ver alumno.
		case R.id.mnuVer:
			verAlumno(alumno.getId());
			break;
		// Opción de menú Editar alumno.
		case R.id.mnuEditar:
			editarAlumno(alumno.getId());
			break;
		case R.id.mnuEliminar:
			eliminarAlumno(alumno.getId());
			break;
		case R.id.mnuLlamar:
			llamarAlumno(alumno.getTelefono());
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	// Envía el intent necesario para llamar a la actividad para editar el alumno.
	private void verAlumno(long id) {
		// Creo un intent explícito con la acción EDIT_STUDENT.
	    Intent i = new Intent(this, AddAlumnos.class);
	    i.setAction("es.uma.SQL.VIEW_STUDENT");
	    // Le añado como extra el id del alumno que queremos editar.
	    i.putExtra("id", new Long(id).intValue());
	    // Llamo a la actividad.
	    startActivity(i);		
	}

	// Envía el intent necesario para llamar a la actividad para editar el alumno.
	private void editarAlumno(long id) {
		// Creo un intent explícito con la acción EDIT_STUDENT.
	    Intent i = new Intent(this, AddAlumnos.class);
	    i.setAction("es.uma.SQL.EDIT_STUDENT");
	    // Le añado como extra el id del alumno que queremos editar.
	    i.putExtra("id", new Long(id).intValue());
	    // Llamo a la actividad.
	    startActivity(i);		
	}

	// Elimina de la BD el alumno con dicho id.
	private void eliminarAlumno(final long id) {
		// Copio el id en la variable a nivel d
		// Creo un AlertDialog para confirmar la eliminación
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setMessage(R.string.confirmar_eliminar);
		b.setCancelable(false);
		b.setNegativeButton(R.string.no, null);
		b.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Dao<Alumno, Integer> dao = bd.getAlumnoDAO();
					if (dao.deleteById(new Integer((int) id)) > 0) {
						Toast.makeText(getApplicationContext(), R.string.eliminacion_correcta, Toast.LENGTH_LONG).show();
						// Recargo la lista.
						cargarLista();
					}
					else {
						Toast.makeText(getApplicationContext(), R.string.eliminacion_incorrecta, Toast.LENGTH_LONG).show();
					}
				} catch (java.sql.SQLException e) {
					throw new RuntimeException(e);
				}		
			}
		});
		b.create().show();
	}

	// Envía el intent necesario para llamar por teléfono al alumno.
	private void llamarAlumno(String telefono) {
		/* Creo un intent implícito para llamar al teléfono del alumno 
		 * (convetido a URI) y llamo a la actividad. */
	    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono)));		
	}

	@Override
	protected void onResume() {
		// Consulto los datos.
		cargarLista();
		super.onResume();
	}

}
