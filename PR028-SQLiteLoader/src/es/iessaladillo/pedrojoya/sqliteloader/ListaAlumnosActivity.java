package es.iessaladillo.pedrojoya.sqliteloader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListaAlumnosActivity extends FragmentActivity implements
		OnClickListener, OnItemClickListener,
		LoaderManager.LoaderCallbacks<Cursor> {

	long idAlumno;
	private ListView lstAlumnos;
	SimpleCursorAdapter adaptador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que utilizará la actividad.
		setContentView(R.layout.activity_listaalumnos);
		// Obtengo las vistas.
		lstAlumnos = (ListView) this.findViewById(R.id.lstAlumnos);
		this.registerForContextMenu(lstAlumnos);
		// Cargo la lista.
		cargarLista();
		// Registro la lista para menú contextual
		lstAlumnos.setOnItemClickListener(this);		
	}

	// Obtiene los datos de la BD y los carga en la lista.
	private void cargarLista() {
		// Inicializo el cargador.
	    getSupportLoaderManager().initLoader(0, null, this);
		// Creo un adaptador para la lista (con el cursor nulo inicialmente).
		adaptador = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null,
				new String[] { GestorBD.FLD_ALU_NOM },
				new int[] { android.R.id.text1 });
		// Asigno el adaptador a la ListActivity.
		lstAlumnos.setAdapter(adaptador);

//		MÉTODO SIMPLE SIN QUE LA ACTIVIDAD CONTROLE LOS CURSOR LOADER.
//		
//		// Consulto todos los alumnos a través del content provider.
//		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos");
//		CursorLoader cLoader = new CursorLoader(this, uri, GestorBD.ALU_TODOS,
//				null, null, null);
//		Cursor cursor = cLoader.loadInBackground();
//		// Indico que el sistema gestione el cursor respecto al ciclo de vida de
//		// la actividad.
//		startManagingCursor(cursor);
//		// Creo un adaptador para la lista.
//		SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
//				android.R.layout.simple_list_item_1, cursor,
//				new String[] { GestorBD.FLD_ALU_NOM },
//				new int[] { android.R.id.text1 });
//		// Asigno el adaptador a la ListActivity.
//		lstAlumnos.setAdapter(adaptador);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// Llamo al padre por si quiere añadir alguna opción de menú.
		super.onCreateContextMenu(menu, v, menuInfo);
		// Inflo el menú en el parámetro de salida, gracias a un MenuInflater
		// (inflador).
		getMenuInflater().inflate(R.menu.menu_lista, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Obtengo la info del item de menú seleccionado
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		// Obtengo el registro correspondiente al alumno seleccionado en forma
		// de cursor.
		Cursor cursor = (Cursor) lstAlumnos.getItemAtPosition(info.position);
		// Obtengo el id del alumno seleccionado.
		idAlumno = cursor.getLong(cursor.getColumnIndex(GestorBD.FLD_ALU_ID));
		String telefono = cursor.getString(cursor
				.getColumnIndex(GestorBD.FLD_ALU_TEL));
		String direccion = cursor.getString(cursor
				.getColumnIndex(GestorBD.FLD_ALU_DIR));
		// Cierro el cursor.
		cursor.close();
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
		// Opción de menú Ver alumno.
		case R.id.mnuVer:
			verAlumno(idAlumno);
			break;
		// Opción de menú Editar alumno.
		case R.id.mnuEditar:
			editarAlumno(idAlumno);
			break;
		case R.id.mnuEliminar:
			eliminarAlumno(idAlumno);
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

	// Envía el intent necesario para llamar a la actividad para editar el
	// alumno.
	private void verAlumno(long id) {
		// Creo un intent explícito con la acción EDIT_STUDENT.
		Intent i = new Intent(this, CRUAlumnoActivity.class);
		i.setAction("es.iessaladillo.VER_ALUMNO");
		// Le añado como extra el id del alumno que queremos editar.
		i.putExtra("id", id);
		// Llamo a la actividad.
		startActivity(i);
	}

	// Envía el intent necesario para llamar a la actividad para editar el
	// alumno.
	private void editarAlumno(long id) {
		// Creo un intent explícito con la acción EDIT_STUDENT.
		Intent i = new Intent(this, CRUAlumnoActivity.class);
		i.setAction("es.iessaladillo.EDITAR_ALUMNO");
		// Le añado como extra el id del alumno que queremos editar.
		i.putExtra("id", id);
		// Llamo a la actividad.
		startActivity(i);
	}

	// Elimina de la BD el alumno con dicho id.
	private void eliminarAlumno(long id) {
		// Borro el alumno a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos/"
				+ idAlumno);
		if (getContentResolver().delete(uri, null, null) > 0) {
			Toast.makeText(getApplicationContext(),
					R.string.eliminacion_correcta, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					R.string.eliminacion_incorrecta, Toast.LENGTH_LONG).show();
		}
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	// Envía el intent necesario para llamar por teléfono al alumno.
	private void llamarAlumno(String telefono) {
		/*
		 * Creo un intent implícito para llamar al teléfono del alumno
		 * (convetido a URI) y llamo a la actividad.
		 */
		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ telefono)));
	}

	// Envía el intent necesario para ver la dirección del alumno en un mapa.
	private void verMapaAlumno(String direccion) {
		/*
		 * Creo un intent implícito para ver la dirección del alumo en un mapa y
		 * llamo a la actividad.
		 */
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
				+ direccion)));
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// Creo un AlertDialog para confirmar la eliminación
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setMessage(R.string.confirmar_eliminar);
		b.setCancelable(false);
		b.setNegativeButton(R.string.no, null);
		b.setPositiveButton(R.string.si, this);
		return b.create();
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// Borro el alumno a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos/"
				+ idAlumno);
		if (getContentResolver().delete(uri, null, null) > 0) {
			Toast.makeText(getApplicationContext(),
					R.string.eliminacion_correcta, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					R.string.eliminacion_incorrecta, Toast.LENGTH_LONG).show();
		}
		cargarLista();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// Obtengo el registro correspondiente al alumno seleccionado en forma
		// de cursor.
		Cursor cursor = (Cursor) lstAlumnos.getItemAtPosition(position);
		// Obtengo el id del alumno seleccionado.
		idAlumno = cursor.getLong(cursor.getColumnIndex(GestorBD.FLD_ALU_ID));
		// Cierro el cursor.
		cursor.close();
		// Envío el intent correspondiente.
		editarAlumno(idAlumno);
	}

	// Crea el cursor.
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Consulto todos los alumnos a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.alumnos/alumnos");
		CursorLoader cLoader = new CursorLoader(this, uri, GestorBD.ALU_TODOS,
				null, null, null);
	    return cLoader;
	}

	// Cuando el cursor ha terminado de cargar los datos.
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Cambio el cursor del adaptador por el que tiene datos.
		adaptador.changeCursor(data);		
	}

	// Cuando se resetea el cargador.
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Cambio el cursor del adaptador por el que tiene datos.
		adaptador.changeCursor(null);				
	}

	@Override
	protected void onResume() {
		getSupportLoaderManager().restartLoader(0, null, this);
		super.onResume();
	}
	
}
