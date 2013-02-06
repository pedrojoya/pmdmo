package es.iessaladillo.pedrojoya.vibracion;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

public class MainActivity extends Activity {

	// Variables a nivel de clase.
	private Vibrator vibracion;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Obtengo del sistema el servicio de vibración.
        vibracion = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
    }
	
	public void btnMedioOnClick(View v) {
		// Vibro durante medio segundo.
		vibracion.vibrate(500);
	}

	public void btnCancelarOnClick(View v) {
		// Cancelo una vibración repetitiva.
		vibracion.cancel();
	}

	public void btnPatronOnClick(View v) {
		// Creo el patrón de vibración.
		long[] patron = {
				50, // Silencio antes de empezar.
				100, // Vibrar.
				100, // No vibrar.
				1000, // Vibrar.
				1000 // No vibrar.
		};
		
		// Vibro siguiendo el patrón y se repetirá desde el primer valor.
		vibracion.vibrate(patron, 0);
	}
	
	
}