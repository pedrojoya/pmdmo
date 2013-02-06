package es.iessaladillo.pedrojoya.pr019;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Constantes para ids de los menús.
	private static final int MNU_AGREGAR = 1;
	private static final int MNU_REFRESCAR = 2;
	private static final int MNU_REFRESCAR_COMPLETO = 3;
	private static final int MNU_REFRESCAR_PARCIAL = 4;
	private static final int MNU_CARGAR = 5;
	private static final int MNU_EDITAR = 6;
	private static final int MNUGRP_ALUMNO = 1;
	private static final int MNU_ELIMINAR = 7;
	private static final int MNU_BUSCAR = 8;
	private static final int MNU_COMPARTIR = 9;

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
		// Creo dinámicamente el menú.
		// Ítem Agregar
		menu.add(Menu.NONE, MNU_AGREGAR, Menu.NONE, R.string.agregar).setIcon(
				R.drawable.ic_menu_add);
		// Añado el subemenú y lo guardo para poder añadirle los items.
		SubMenu mnuRefrescar = menu.addSubMenu(Menu.NONE, MNU_REFRESCAR,
				Menu.NONE, R.string.refrescar).setIcon(
				R.drawable.ic_menu_refresh);
		// Subítem Refrescar Completamente.
		mnuRefrescar.add(Menu.NONE, MNU_REFRESCAR_COMPLETO, Menu.NONE,
				R.string.refrescar_completamente);
		// Subítem Refrescar Parcialmente.
		mnuRefrescar.add(Menu.NONE, MNU_REFRESCAR_PARCIAL, Menu.NONE,
				R.string.refrescar_parcialmente);
		// Ítem Cargar
		menu.add(Menu.NONE, MNU_CARGAR, Menu.NONE, R.string.cargar).setIcon(
				R.drawable.ic_menu_upload);
		// Ítem Editar
		menu.add(MNUGRP_ALUMNO, MNU_EDITAR, Menu.NONE, R.string.editar)
				.setIcon(R.drawable.ic_menu_edit);
		// Ítem Eliminar
		menu.add(MNUGRP_ALUMNO, MNU_ELIMINAR, Menu.NONE, R.string.eliminar)
				.setIcon(R.drawable.ic_menu_delete);
		// Ítem Buscar
		menu.add(Menu.NONE, MNU_BUSCAR, Menu.NONE, R.string.buscar).setIcon(
				R.drawable.ic_menu_search);
		// Ítem Cargar
		menu.add(Menu.NONE, MNU_COMPARTIR, Menu.NONE, R.string.compartir)
				.setIcon(R.drawable.ic_menu_share);
		// Retorno lo que devuelva la actividad.
		return super.onCreateOptionsMenu(menu);
	}

	// Antes de mostrar el menú.
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// Obtengo el nombre del alumno.
		String alumno = txtAlumno.getText().toString();
		// Obtengo los menús cuyo título quiero modificar.
		MenuItem item = menu.findItem(MNU_EDITAR);
		// Si se ha introducido un alumno.
		if (!alumno.equals("")) {
			// Activo el grupo de alumno.
			menu.setGroupEnabled(MNUGRP_ALUMNO, true);
			item.setTitle(getResources().getString(R.string.editar) + " "
					+ alumno);
		} else {
			// Desctivo el grupo de alumno.
			menu.setGroupEnabled(MNUGRP_ALUMNO, false);
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
		case MNU_AGREGAR:
			mostrarTostada(getResources().getString(R.string.agregar));
			break;
		case MNU_REFRESCAR_COMPLETO:
			mostrarTostada(getResources().getString(
					R.string.refrescar_completamente));
			break;
		case MNU_REFRESCAR_PARCIAL:
			mostrarTostada(getResources().getString(
					R.string.refrescar_parcialmente));
			break;
		case MNU_CARGAR:
			mostrarTostada(getResources().getString(R.string.cargar));
			break;
		case MNU_EDITAR:
			mostrarTostada(item.getTitle().toString());
			break;
		case MNU_ELIMINAR:
			mostrarTostada(getResources().getString(R.string.eliminar));
			break;
		case MNU_BUSCAR:
			mostrarTostada(getResources().getString(R.string.buscar));
			break;
		case MNU_COMPARTIR:
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
