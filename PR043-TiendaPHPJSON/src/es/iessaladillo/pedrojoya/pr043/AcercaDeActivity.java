package es.iessaladillo.pedrojoya.pr043;

import android.app.Activity;
import android.os.Bundle;

// Muestra la pantalla de Acerca de con un botón para finalizar la actividad.
public class AcercaDeActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acerca_de);
	}

	// Al pulsar el botón btnSalir.
	public void btnSalirOnClick() {
		finish();
	}
}