package es.iessaladillo.pedrojoya.pr050;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables miembro.
	private EditText txtAlumno;

	// Cuando se crea la actividad.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Se llama al constructor del padre.
		super.onCreate(savedInstanceState);
		// Se establece el layout que mostrará la actividad.
		setContentView(R.layout.activity_main);
		// Se muestra el icono de navegación junto al icono de la aplicación.
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// Activo el ítem de overflow en dispositivos con botón físico de menú.
		activarOverflow();
		// Obtengo las vistas.
		getVistas();
	}

	// Activa el ítem de overflow en dispositivos con botón físico de menú.
	private void activarOverflow() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}
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

	// Cuando se pulsa un elemento del menú.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo del item pulsado realizo la acción deseada.
		switch (item.getItemId()) {
		case android.R.id.home:
			mostrarTostada(getString(R.string.ir_a_la_actividad_superior));
			break;
		case R.id.mnuAgregar:
			mostrarTostada(getString(R.string.agregar));
			break;
		case R.id.mnuCargar:
			mostrarTostada(getString(R.string.cargar));
			break;
		case R.id.mnuEditar:
			mostrarTostada(item.getTitle().toString());
			break;
		case R.id.mnuEliminar:
			mostrarTostada(getString(R.string.eliminar));
			break;
		case R.id.mnuBuscar:
			mostrarTostada(getString(R.string.buscar));
			break;
		case R.id.mnuCompartir:
			mostrarTostada(getString(R.string.compartir));
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
