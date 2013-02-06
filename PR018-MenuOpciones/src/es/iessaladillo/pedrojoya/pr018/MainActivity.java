package es.iessaladillo.pedrojoya.pr018;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables miembro.
	private EditText txtAlumno;

	// Cuando se crea la actividad.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al constructor del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que mostrará la actividad.
		setContentView(R.layout.main);
		// Obtengo las vistas.
		getVistas();
	}

	// Obtiene e inicializa las vistas.
	private void getVistas() {
		txtAlumno = (EditText) this.findViewById(R.id.txtAlumno);
	}

	// Al crear la primera vez el menú.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflo el menú a partir del XML.
		getMenuInflater().inflate(R.menu.main, menu);
		// Retorno lo que devuelva la actividad.
		return super.onCreateOptionsMenu(menu);
	}

	// Antes de mostrar el menú.
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// Obtengo el nombre del alumno.
		String alumno = txtAlumno.getText().toString();
		// Obtengo los menús cuyo título quiero modificar.
		MenuItem item = menu.findItem(R.id.mnuEditar);
		// Si se ha introducido un alumno.
		if (!alumno.equals("")) {
			// Activo el grupo de alumno.
			menu.setGroupEnabled(R.id.mnugrpAlumno, true);
			item.setTitle(getResources().getString(R.string.editar) + " "
					+ alumno);
		} else {
			// Desctivo el grupo de alumno.
			menu.setGroupEnabled(R.id.mnugrpAlumno, false);
			item.setTitle(R.string.editar);
		}
		// Retorno lo que devuelva la actividad.
		return super.onPrepareOptionsMenu(menu);
	}

	// Cuando se pulsa un elemento del menú.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo del item pulsado realizo la acción deseada.
		switch (item.getItemId()) {
		case R.id.mnuAgregar:
			mostrarTostada(getResources().getString(R.string.agregar));
			break;
		case R.id.mnuRefrescarCompleto:
			mostrarTostada(getResources().getString(
					R.string.refrescar_completamente));
			break;
		case R.id.mnuRefrescarParcial:
			mostrarTostada(getResources().getString(
					R.string.refrescar_parcialmente));
			break;
		case R.id.mnuCargar:
			mostrarTostada(getResources().getString(R.string.cargar));
			break;
		case R.id.mnuEditar:
			mostrarTostada(item.getTitle().toString());
			break;
		case R.id.mnuEliminar:
			mostrarTostada(getResources().getString(R.string.eliminar));
			break;
		case R.id.mnuBuscar:
			mostrarTostada(getResources().getString(R.string.buscar));
			break;
		case R.id.mnuCompartir:
			mostrarTostada(getResources().getString(R.string.compartir));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		// Retorno que lo he gestionado yo.
		return true;
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

}
