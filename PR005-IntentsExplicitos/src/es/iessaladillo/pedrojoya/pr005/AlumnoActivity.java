package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlumnoActivity extends Activity {

	// Widgets.
	Button btnAceptar;
	EditText txtNombre;
	EditText txtEdad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout de la actividad.
		this.setContentView(R.layout.alumno);
		// Obtengo la referencia a los widgets.
		btnAceptar = (Button) this.findViewById(R.id.btnAceptar);
		txtNombre = (EditText) this.findViewById(R.id.txtNombre);
		txtEdad = (EditText) this.findViewById(R.id.txtEdad);
		// Obtengo los datos del intent con el que me han llamado y los escribo en los EditText.
		Bundle datos = this.getIntent().getExtras();
		if (datos != null) {
			String nombre = datos.getString("nombre");
			if (nombre != null) {
				txtNombre.setText(nombre);
			}
			int edad = datos.getInt("edad", 0);
			if (edad != 0) {
				txtEdad.setText(Integer.toString(edad));
			}
		}
		// Establezco el listener del click del botón.
		btnAceptar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				retornar();
			}
		});
	}

	// Empaqueta los datos de retorno y finaliza la actividad.
	private void retornar() {
		// Creo un nuevo intent sin acción ni destinatario y le agrego los datos.
		Intent datos = new Intent();
		datos.putExtra("nombre", txtNombre.getText().toString());
		try {
			datos.putExtra("edad", Integer.parseInt(txtEdad.getText().toString()));
		}
		catch (NumberFormatException e) {
			datos.putExtra("edad", 0);
		}
		// Establezco que todo ha ido bien.
		this.setResult(RESULT_OK, datos);
		// Finalizo la actividad.
		this.finish();
	}
	
}
