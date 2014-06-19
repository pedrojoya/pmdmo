package es.iessaladillo.pedrojoya.pr016;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Variables a nivel de clase.
	private ExpandableListView lstAlumnos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que debe mostrar la actividad.
		setContentView(R.layout.activity_main);
		// Obtengo e inicializo las vistas.
		getVistas();
	}

	private void getVistas() {
		lstAlumnos = (ExpandableListView) this.findViewById(R.id.lstAlumnos);
		// Indico que NO se usen los indicadores por defecto para grupos e
		// hijos.
		lstAlumnos.setGroupIndicator(null);
		lstAlumnos.setChildIndicator(null);
		// Creo los datos.
		ArrayList<String> grupos = new ArrayList<String>();
		ArrayList<ArrayList<Alumno>> alumnos = new ArrayList<ArrayList<Alumno>>();
		ArrayList<Alumno> grupoActual;
		// Primer grupo.
		grupos.add("CFGM Sistemas Microinformáticos y Redes");
		grupoActual = new ArrayList<Alumno>();
		grupoActual.add(new Alumno("Baldomero", 16, "CFGM", "2º"));
		grupoActual.add(new Alumno("Sergio", 27, "CFGM", "1º"));
		grupoActual.add(new Alumno("Atanasio", 17, "CFGM", "1º"));
		grupoActual.add(new Alumno("Oswaldo", 26, "CFGM", "1º"));
		grupoActual.add(new Alumno("Rodrigo", 22, "CFGM", "2º"));
		grupoActual.add(new Alumno("Antonio", 16, "CFGM", "1º"));
		alumnos.add(grupoActual);
		// Segundo grupo.
		grupos.add("CFGS Desarrollo de Aplicaciones Multiplataforma");
		grupoActual = new ArrayList<Alumno>();
		grupoActual.add(new Alumno("Pedro", 22, "CFGS", "2º"));
		grupoActual.add(new Alumno("Pablo", 22, "CFGS", "2º"));
		grupoActual.add(new Alumno("Rodolfo", 21, "CFGS", "1º"));
		grupoActual.add(new Alumno("Gervasio", 24, "CFGS", "2º"));
		grupoActual.add(new Alumno("Prudencia", 20, "CFGS", "2º"));
		grupoActual.add(new Alumno("Gumersindo", 17, "CFGS", "2º"));
		grupoActual.add(new Alumno("Gerardo", 18, "CFGS", "1º"));
		grupoActual.add(new Alumno("Óscar", 21, "CFGS", "2º"));
		alumnos.add(grupoActual);
		// Creo el adaptador para la lista.
		AdaptadorAlumnos adaptador = new AdaptadorAlumnos(this, lstAlumnos,
				grupos, alumnos);
		// Establezco el adaptador de la lista.
		lstAlumnos.setAdapter(adaptador);
		// // Si quiero que inicialmente todos los grupos aparezcan expandidos.
		// for (int i = 0; i < adaptador.getGroupCount(); i++) {
		// lstAlumnos.expandGroup(i);
		// }
		// Establezco el onClick sobre un elemento de la lista.
		lstAlumnos.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// Informo al usuario.
				// OJO usar getExpandableListAdapter(), no getAdapter().
				AdaptadorAlumnos adaptador = (AdaptadorAlumnos) parent
						.getExpandableListAdapter();
				Alumno alumno = adaptador
						.getChild(groupPosition, childPosition);
				mostrarTostada(alumno.getNombre() + " (" + alumno.getCurso()
						+ " " + alumno.getCiclo() + ")");
				// Retorno que lo he gestionado yo.
				return true;
			}
		});
	}

	// Muestra una tostada.
	private void mostrarTostada(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT)
				.show();
	}

}
