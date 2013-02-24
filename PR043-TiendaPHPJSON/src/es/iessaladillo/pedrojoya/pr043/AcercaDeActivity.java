package es.iessaladillo.pedrojoya.pr043;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

// Muestra la pantalla de Acerca de con un botón para finalizar la actividad.
public class AcercaDeActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acerca_de);
		// Activo el botón de Home en la Action Bar (si la versión lo permite).
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	// Al seleccionar una opción del menú de opciones.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}