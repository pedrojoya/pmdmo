package es.iessaladillo.pedrojoya.pr027.actividades;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.fragmentos.AlumnoFragment;
import es.iessaladillo.pedrojoya.pr027.fragmentos.ListaAlumnosFragment;
import es.iessaladillo.pedrojoya.pr027.fragmentos.ListaAlumnosFragment.OnListaAlumnosFragmentListener;
import es.iessaladillo.pedrojoya.pr027.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr027.fragmentos.SiNoDialogFragment.SiNoDialogListener;

public class MainActivity extends Activity implements
		OnListaAlumnosFragmentListener, SiNoDialogListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.mnuAlumnoAgregar:
			onAgregarAlumno();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAgregarAlumno() {
		// Se muestra la actividad para agregar alumno.
		Intent i = new Intent(this, AlumnoActivity.class);
		i.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_AGREGAR);
		this.startActivity(i);
	}

	@Override
	public void onEditarAlumno(long id) {
		// Se muestra la actividad para editar alumno
		Intent i = new Intent(this, AlumnoActivity.class);
		i.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_EDITAR);
		i.putExtra(AlumnoFragment.EXTRA_ID, id);
		this.startActivity(i);
	}

	@Override
	public void onConfirmarEliminarAlumnos() {
		SiNoDialogFragment frgDialogo = new SiNoDialogFragment();
		frgDialogo.show(this.getFragmentManager(), "SiNoDialogFragment");
	}

	@Override
	public void onPositiveButtonClick(DialogFragment dialog) {
		// Se confirma que deben eliminarse los alumnos.
		ListaAlumnosFragment frgListaAlumnos = (ListaAlumnosFragment) getFragmentManager()
				.findFragmentById(R.id.frgListaAlumnos);
		frgListaAlumnos.eliminarAlumnos();
	}

	@Override
	public void onNegativeButtonClick(DialogFragment dialog) {
	}

}
