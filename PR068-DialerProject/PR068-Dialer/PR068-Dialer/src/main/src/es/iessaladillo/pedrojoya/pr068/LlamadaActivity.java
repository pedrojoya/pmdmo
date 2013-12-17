package es.iessaladillo.pedrojoya.pr068;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// Actividad que simula la realización de una llamada telefónica.
public class LlamadaActivity extends Activity {

	// Constantes.
	public static final String EXTRA_NUMERO = "numero";

	// Variables.
	private TextView lblNumero;

	// Al crear la actividad.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_llamada);
		// Se obtienen las vistas.
		getVistas();
	}

	// Obtiene e inicializa las vistas.
	private void getVistas() {
		// Se obtienen las vistas.
		lblNumero = (TextView) findViewById(R.id.lblNumero);
		// Se obtiene el extra con el que ha sido llamada la actividad y se
		// escribe.
		lblNumero.setText(getIntent().getStringExtra(EXTRA_NUMERO));
	}

	// Al hacer click sobre btnFinalizar.
	public void btnFinalizarOnClick(View v) {
		// Se añade la llamada al registro.
		Llamada llamada = new Llamada(lblNumero.getText().toString(),
				new Date(), Llamada.LLAMADA_SALIENTE);
		Registro.registrar(llamada);
		// Se cierra la actividad.
		finish();
	}

}