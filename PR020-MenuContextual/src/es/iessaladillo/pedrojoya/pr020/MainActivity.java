package es.iessaladillo.pedrojoya.pr020;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables a nivel de clase.
	private ListView lstAlumnos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que mostrará la actividad.
		setContentView(R.layout.main);
		// Obtengo la referencia a las vistas.
		getVistas();
	}

	private void getVistas() {
		lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
		// Obtengo los datos para el adaptador de la lista.
		String[] alumnos = getResources().getStringArray(R.array.alumnos);
		// Creo el adaptador que usará dichos datos y un layout estándar.
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, alumnos);
		lstAlumnos.setAdapter(adaptador);
		// Creo el listener para cuando se hace click en un item de la lista.
		lstAlumnos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> lst, View vistafila,
					int posicion, long id) {
				// Informo al usuario sobre que alumno ha pulsado.
				mostrarTostada(getString(R.string.ha_pulsado_sobre)
						+ lst.getItemAtPosition(posicion));
			}
		});
		// Registro el ListView para que tenga menú contextual.
		registerForContextMenu(lstAlumnos);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// Si se ha hecho LongClick sobre la lista.
		if (v.getId() == R.id.lstAlumnos) {
			// Obtengo la posición de la lista que se ha pulsado
			int position = ((AdapterContextMenuInfo) menuInfo).position;
			// Inflo el menú.
			this.getMenuInflater().inflate(R.menu.lstalumnos, menu);
			// Cambio el título de los menús para incluir el nombre del alumno.
			menu.findItem(R.id.mnuEditar).setTitle(
					getString(R.string.editar)
							+ lstAlumnos.getItemAtPosition(position));
			menu.findItem(R.id.mnuEliminar).setTitle(
					getString(R.string.eliminar)
							+ lstAlumnos.getItemAtPosition(position));
			// Establezco el título que se muestra en el encabezado del menú.
			menu.setHeaderTitle(R.string.elija_una_opcion);
		}
		// Llamo al OnCreateContextMenu del padre por si quiere
		// añadir algún elemento.
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Obtengo la posición de la lista que se ha pulsado
		int position = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
		// Dependiendo del menú sobre el que se ha pulsado informo al usuario.
		switch (item.getItemId()) {
			case R.id.mnuEditar:
				mostrarTostada(getString(R.string.editar)
						+ lstAlumnos.getItemAtPosition(position));
				break;
			case R.id.mnuEliminar:
				mostrarTostada(getString(R.string.eliminar)
						+ lstAlumnos.getItemAtPosition(position));
				break;
			default:
				// Retorno lo que retorne el padre.
				return super.onContextItemSelected(item);
		}
		// Retorno que he gestionado yo el evento.
		return true;
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

}
