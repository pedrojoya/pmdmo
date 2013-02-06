package es.iessaladillo.pedrojoya.pr011;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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
		// Obtengo e inicializo las vistas.
		getVistas();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
				Toast.makeText(
						lst.getContext(),
						getResources().getString(R.string.ha_pulsado_sobre)
								+ lst.getItemAtPosition(posicion),
						Toast.LENGTH_LONG).show();
			}
		});
	}

}
