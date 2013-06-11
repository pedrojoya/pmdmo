package es.iessaladillo.pedrojoya.pr027.actividades;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.R.id;
import es.iessaladillo.pedrojoya.pr027.R.layout;
import es.iessaladillo.pedrojoya.pr027.R.menu;
import es.iessaladillo.pedrojoya.pr027.R.string;
import es.iessaladillo.pedrojoya.pr027.bd.AdaptadorBD;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListadoActivity extends ListActivity {

	// Variables a nivel de clase.
	private AdaptadorBD bd;	// Base de datos.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que utilizará la actividad.
		setContentView(R.layout.lista);
		// Registro la lista para menú contextual
		this.registerForContextMenu(this.getListView());
		// Creo un objeto AdaptadorBD.
		bd = new AdaptadorBD(this);
		bd.open();
	}

	// Obtiene los datos de la BD y los carga en la lista.
	private void cargarLista() {
		// Consulto todos los alumnos.
		Cursor cursor = bd.queryAllAlumnos();
		// Indico que el sistema gestione el cursor respecto al ciclo de vida de la actividad.
		startManagingCursor(cursor);
		// Creo un adaptador para la lista.
		SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_1, 
				cursor, new String[] {AdaptadorBD.FLD_ALU_NOM}, new int[] {android.R.id.text1});
		// Asigno el adaptador a la ListActivity.
		this.setListAdapter(adaptador);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Obtengo el registro correspondiente al alumno seleccionado en forma de cursor.
		Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
		// Obtengo el id del alumno seleccionado.
		long idAlumno = cursor.getLong(cursor.getColumnIndex(AdaptadorBD.FLD_ALU_ID));
		// Cierro el cursor.
		cursor.close();
		// Envío el intent correspondiente.
		editarAlumno(idAlumno);
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
		Cursor cursor = (Cursor) getListView().getItemAtPosition(info.position);
		// Obtengo el id del alumno seleccionado.
		long id = cursor.getLong(cursor.getColumnIndex(AdaptadorBD.FLD_ALU_ID));
		String telefono = cursor.getString(cursor.getColumnIndex(AdaptadorBD.FLD_ALU_TEL));
		String direccion = cursor.getString(cursor.getColumnIndex(AdaptadorBD.FLD_ALU_DIR));
		// Cierro el cursor.
		cursor.close();
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
		// Opción de menú Ver alumno.
		case R.id.mnuVer:
			verAlumno(id);
			break;
		// Opción de menú Editar alumno.
		case R.id.mnuEditar:
			editarAlumno(id);
			break;
		case R.id.mnuEliminar:
			eliminarAlumno(id);
			break;
		case R.id.mnuLlamar:
			llamarAlumno(telefono);
			break;
		case R.id.mnuMapa:
			verMapaAlumno(direccion);
			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	// Envía el intent necesario para llamar a la actividad para editar el alumno.
	private void verAlumno(long id) {
		// Creo un intent explícito con la acción EDIT_STUDENT.
	    Intent i = new Intent(this, AlumnoActivity.class);
	    i.setAction("es.uma.SQL.VIEW_STUDENT");
	    // Le añado como extra el id del alumno que queremos editar.
	    i.putExtra("id", id);
	    // Llamo a la actividad.
	    startActivity(i);		
	}

	// Envía el intent necesario para llamar a la actividad para editar el alumno.
	private void editarAlumno(long id) {
		// Creo un intent explícito con la acción EDIT_STUDENT.
	    Intent i = new Intent(this, AlumnoActivity.class);
	    i.setAction("es.uma.SQL.EDIT_STUDENT");
	    // Le añado como extra el id del alumno que queremos editar.
	    i.putExtra("id", id);
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
				if (bd.deleteAlumno(id)) {
					Toast.makeText(getApplicationContext(), R.string.eliminacion_correcta, Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getApplicationContext(), R.string.eliminacion_incorrecta, Toast.LENGTH_LONG).show();
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

	// Envía el intent necesario para ver la dirección del alumno en un mapa.
	private void verMapaAlumno(String direccion) {
		/* Creo un intent implícito para ver la dirección del alumo en un mapa 
		 * y llamo a la actividad. */
	    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + direccion)));		
	}
	
	@Override
	protected void onResume() {
		// Consulto los datos.
		cargarLista();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (bd != null) {
			bd.close();
		}
		super.onDestroy();
		
	}

}
