package es.iessaladillo.pedrojoya.pr043;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}

	// Al hacer click sobre btnCatalogo
	public void btnCatalogoOnClick(View v) {
		// Creo el intent explícito y muestro la actividad CatalogoActivity.
		Intent i = new Intent(this, CatalogoActivity.class);
		startActivity(i);
	}

	// Al hacer click sobre btnCarrito
	public void btnCarritoOnClick(View v) {
		// Creo el intent explícito y muestro la actividad CarritoActivity.
		Intent i = new Intent(this, CarritoActivity.class);
		startActivity(i);
	}

	// Muestra la actividad AcercaDeActivity
	public void btnAcercaDeOnClick(View v) {
		// Creo el intent explícito e inicio la actividad AcercaDeActivity.
		Intent i = new Intent(this, AcercaDeActivity.class);
		startActivity(i);
	}

	public void btnActualizarCatalogoOnClick(View v) {
		// Si hay conexión a Internet.
		if (ConexionServidor.isOnline(this)) {
			// Creo la tarea asíncrona para importar los datos.
			ImportarCatalogo tarea = new ImportarCatalogo(this);
			tarea.execute();
		}
		else {
			Toast.makeText(this, R.string.sin_conexion_a_internet,
					Toast.LENGTH_LONG).show();
		}
	}

}
