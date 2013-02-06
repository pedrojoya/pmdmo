package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PrincipalActivity extends Activity implements OnClickListener {

	// Constantes.
	private static final int RC_ALUMNO = 1; // Código de llamada a actividad
											// Alumno.

	// Variables a nivel de clase.
	private String nombre = "";
	private int edad = 0;

	// Widgets.
	Button btnSolicitar;
	TextView lblDatos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al método onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout para la actividad.
		setContentView(R.layout.main);
		// Obtengo la referencia a los widgets.
		obtenerWidgets();
		// Hago que sea la propia actividad la que responda a los clicks sobre
		// el botón.
		btnSolicitar.setOnClickListener(this);
	}

	public void onClick(View vista) {
		// Dependiendo de sobre qué botón se ha hecho click.
		switch (vista.getId()) {
		case R.id.btnSolicitar:
			// Solicito los datos del alumno.
			solicitarDatos();
			break;
		}
	}

	/** Llama a la actividad para solicitar los datos del alumno */
	private void solicitarDatos() {
		// Creo el intent para llamar explícitamente a la actividad de
		// introducción de datos del alumno.
		// Utilizo el constructor que recibe paquete (this) y nombre de clase.
		Intent intencion = new Intent(this, AlumnoActivity.class);
		// Añado al intent los datos iniciales que se mostrarán en los TextView
		// de la actividad.
		intencion.putExtra("nombre", nombre);
		intencion.putExtra("edad", edad);
		// Inicio la actividad indicando que espero respuesta con un Request
		// Code concreto.
		this.startActivityForResult(intencion, RC_ALUMNO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Si la actividad llamada ha ido bien.
		if (resultCode == RESULT_OK) {
			// Depeniendo del código de petición (Request Code)
			switch (requestCode) {
			case RC_ALUMNO:
				// Obtengo los datos del intent de retorno.
				Bundle extras = data.getExtras();
				if (data.hasExtra("nombre")) {
					nombre = extras.getString("nombre");
				}
				if (data.hasExtra("edad")) {
					edad = extras.getInt("edad");
				}
				// Los muestro en el TextView.
				mostrarDatos();
			}
		}
	}

	// Obtiene la referencia a los widgets de la actividad.
	private void obtenerWidgets() {
		// Obtengo la referencia al botón.
		btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
		// Obtengo la referencia al TextView de datos.
		lblDatos = (TextView) this.findViewById(R.id.lblDatos);
	}

	// Muestra los datos en el TextView de datos.
	private void mostrarDatos() {
		String sDatos = this.getString(R.string.nombre) + ": " + nombre + "\n"
				+ this.getString(R.string.edad) + ": " + Integer.toString(edad);
		lblDatos.setText(sDatos);
	}

}