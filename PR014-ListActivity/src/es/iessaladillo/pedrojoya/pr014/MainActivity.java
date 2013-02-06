package es.iessaladillo.pedrojoya.pr014;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	// Variables a nivel de clase.
	private ListView lstAlumnos;
	private ArrayList<Alumno> alumnos;

	// Al crear la actividad.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que debe mostrar la actividad.
		setContentView(R.layout.main);
		// Inicializo las vistas.
		getVistas();
	}

	// Obtiene la referencia a las vistas y las incializa.
	@SuppressWarnings("unchecked")
	private void getVistas() {
		lstAlumnos = this.getListView();
		// Obtengo los datos de la recreación.
		alumnos = (ArrayList<Alumno>) getLastNonConfigurationInstance();
		// Si no tenía datos los creo.
		if (alumnos == null) {
			alumnos = new ArrayList<Alumno>();
			alumnos.add(new Alumno("Pedro", 22, "CFGS", "2º"));
			alumnos.add(new Alumno("Baldomero", 16, "CFGM", "2º"));
			alumnos.add(new Alumno("Sergio", 27, "CFGM", "1º"));
			alumnos.add(new Alumno("Pablo", 22, "CFGS", "2º"));
			alumnos.add(new Alumno("Rodolfo", 21, "CFGS", "1º"));
			alumnos.add(new Alumno("Atanasio", 17, "CFGM", "1º"));
			alumnos.add(new Alumno("Gervasio", 24, "CFGS", "2º"));
			alumnos.add(new Alumno("Prudencia", 20, "CFGS", "2º"));
			alumnos.add(new Alumno("Oswaldo", 26, "CFGM", "1º"));
			alumnos.add(new Alumno("Gumersindo", 17, "CFGS", "2º"));
			alumnos.add(new Alumno("Gerardo", 18, "CFGS", "1º"));
			alumnos.add(new Alumno("Rodrigo", 22, "CFGM", "2º"));
			alumnos.add(new Alumno("Óscar", 21, "CFGS", "2º"));
			alumnos.add(new Alumno("Antonio", 16, "CFGM", "1º"));
		}
		// Establezco el adaptador de la lista con datos.
		lstAlumnos.setAdapter(new AdaptadorAlumno(this, alumnos));
	}

	// Cuando se pulsa sobre un elemento de la lista.
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Obtengo el alumno sobre el que se ha pulsado.
		Alumno pulsado = (Alumno) l.getItemAtPosition(position);
		// Se elimina el alumno de la lista.
		((AdaptadorAlumno) l.getAdapter()).remove(pulsado);
		// Informo al usuario.
		mostrarTostada(getString(R.string.se_ha_eliminado)
				+ pulsado.getNombre());
	}

	// Cuando se cambia la configuración.
	@Override
	public Object onRetainNonConfigurationInstance() {
		// Retorno el ArrayList de datos tal y como está
		// para que se mantenga al destruir la actividad.
		return alumnos;
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
	}

}
