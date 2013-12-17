package es.iessaladillo.pedrojoya.pr068;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

// Actividad principal. Muestra un dial para marcar número de teléfono.
public class MainActivity extends Activity implements OnLongClickListener {

	// Vistas.
	private EditText txtNumero;
	private ImageButton btnBorrar;

	// Al crear la actividad.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Se obtienen las vistas.
		getVistas();
	}

	// Obtiene e inicializa las vistas.
	private void getVistas() {
		txtNumero = (EditText) this.findViewById(R.id.txtNumero);
		btnBorrar = (ImageButton) this.findViewById(R.id.btnBorrar);
		// La propia actividad actuará como listener cuando se haga click largo
		// sobre btnBorrar.
		btnBorrar.setOnLongClickListener(this);

	}

	// Al pulsar sobre un botón correspondiente a un número.
	public void btnNumeroOnClick(View v) {
		// Concatena el número del botón al TextView
		txtNumero.append(((Button) v).getText());
	}

	// Al pulsar sobre el botón btnBorrar.
	public void btnBorrarOnClick(View v) {
		// Quita el último carácter del EditText.
		String texto = txtNumero.getText().toString().trim();
		if (texto.length() != 0) {
			texto = texto.substring(0, texto.length() - 1);
			txtNumero.setText(texto);
		}
	}

	// Al pulsar sobre el botón btnRegistro.
	public void btnRegistroOnClick(View v) {
		// Se muestra la actividad de registro de llamadas.
		Intent intent = new Intent(getApplicationContext(),
				RegistroActivity.class);
		startActivity(intent);
	}

	// Al pulsar sobre el botón btnLlamar.
	public void btnLlamarOnClick(View v) {
		// Si se ha introducido un número.
		if (!TextUtils.isEmpty(txtNumero.getText().toString())) {
			// Se muestra la actividad de llamada.
			Intent intent = new Intent(getApplicationContext(),
					LlamadaActivity.class);
			intent.putExtra(LlamadaActivity.EXTRA_NUMERO, txtNumero.getText()
					.toString());
			startActivity(intent);
		}
	}

	// Al hacer click largo sobre btnBorrar.
	@Override
	public boolean onLongClick(View v) {
		// Dependiendo del botón pulsado.
		switch (v.getId()) {
		case R.id.btnBorrar:
			btnBorrarOnLongClick();
			break;
		default:
			break;
		}
		return true;
	}

	// Cuando se ha hecho click largo sobre btnBorrar.
	private void btnBorrarOnLongClick() {
		// Se borra todo el contenido de texto.
		txtNumero.setText("");
	}

	// Al crear el menú de opciones.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Se infla el menú correspondiente.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// Al seleccionar un ítem de menú.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnuBuscar:
			buscarNumero();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Busca en Internet el número de teléfono introducido.
	private void buscarNumero() {
		// Si se ha introducido un número.
		String numero = txtNumero.getText().toString();
		if (!TextUtils.isEmpty(numero)) {
			// Se lanza un intent implícito para que el navegador busque el
			// número de Internet.
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, numero);
			startActivity(intent);
		}
	}

}
