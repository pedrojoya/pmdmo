package es.iessaladillo.pedrojoya.pr027.actividades;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.fragmentos.AlumnoFragment;

public class AlumnoActivity extends Activity {

	AlumnoFragment frgAlumno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Se activa el icono de la aplicación.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// Cargo el fragmento de detalle en el FrameLayout.
		FragmentManager gestorFragmentos = this.getFragmentManager();
		FragmentTransaction transaccion = gestorFragmentos.beginTransaction();
		frgAlumno = new AlumnoFragment();
		Bundle argumentos = new Bundle();
		argumentos.putString(AlumnoFragment.EXTRA_MODO, getIntent().getExtras()
				.getString(AlumnoFragment.EXTRA_MODO));
		argumentos.putLong(AlumnoFragment.EXTRA_ID, getIntent().getExtras()
				.getLong(AlumnoFragment.EXTRA_ID));
		frgAlumno.setArguments(argumentos);
		transaccion.add(android.R.id.content, frgAlumno);
		transaccion.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_alumno, menu);
		return true;
	}

	// Al seleccionar una opción del menú de opciones.

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.mnuAlumnoGuardar:
			frgAlumno.guardarAlumno();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
