package es.iessaladillo.pedrojoya.sqliteloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btnNuevoOnClick(View v) {
		// Creo un intent explícito con la acción NEW_STUDENT.
		Intent i = new Intent(this, CRUAlumnoActivity.class);
		i.setAction("es.iessaladillo.AGREGAR_ALUMNO");
		// Llamo a la actividad.
		startActivity(i);
	}

	public void btnListadoOnClick(View v) {
		// Creo un intent explícito.
		Intent i = new Intent(this, ListaAlumnosActivity.class);
		// Llamo a la actividad.
		startActivity(i);
	}

}
