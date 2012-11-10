package es.iessaladillo.pedrojoya.pr006;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PrincipalActivity extends Activity {
 
	// Variables a nivel de clase.
	StringBuilder listado = new StringBuilder();	// Constructor de cadenas.
	TextView lblListado;	// TextView donde se mostrará el listado de métodos.
	
	// Envía un mensaje de depuración
	private void escribirLog (String metodo) {
		// Envío el log de depuración.
		Log.d("Proyecto Ciclo de Vida", metodo);
		// Añado a la cadena el nuevo método.
		listado.append(metodo);
		listado.append('\n');
		// Muestro el nuevo listado en el TextView.
		lblListado.setText(listado.toString());
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// Llamo al método del padre.
    	super.onCreate(savedInstanceState);
    	// Muestro el layout.
        setContentView(R.layout.main);
        // Obtengo la referencia al TextView.
        lblListado = (TextView) this.findViewById(R.id.lblListado);
        // Envío el mensaje de depuración.
        escribirLog("onCreate");
    }

	@Override
	protected void onDestroy() {
		// Llamo al método del padre.
		super.onDestroy();
        // Envío el mensaje de depuración.
        escribirLog("onDestroy");
	}

	@Override
	protected void onPause() {
		// Llamo al método del padre.
		super.onPause();
        // Envío el mensaje de depuración.
        escribirLog("onPause");		
	}

	@Override
	protected void onRestart() {
		// Llamo al método del padre.
		super.onRestart();
	    // Envío el mensaje de depuración.
        escribirLog("onRestart");		
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// Llamo al método del padre.
		super.onRestoreInstanceState(savedInstanceState);
	    // Envío el mensaje de depuración.
        escribirLog("onRestoreInstanceState");		
	}

	@Override
	protected void onResume() {
		// Llamo al método del padre.
		super.onResume();
	    // Envío el mensaje de depuración.
        escribirLog("onResume");		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Llamo al método del padre.
		super.onSaveInstanceState(outState);
	    // Envío el mensaje de depuración.
        escribirLog("onSaveInstanceState");		
	}

	@Override
	protected void onStart() {
		// Llamo al método del padre.
		super.onStart();
	    // Envío el mensaje de depuración.
        escribirLog("onStart");		
	}

	@Override
	protected void onStop() {
		// Llamo al método del padre.
		super.onStop();
	    // Envío el mensaje de depuración.
        escribirLog("onStop");		
	}
    
    
}