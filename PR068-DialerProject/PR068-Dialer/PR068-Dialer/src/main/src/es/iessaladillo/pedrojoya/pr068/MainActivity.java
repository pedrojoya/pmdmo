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

public class MainActivity extends Activity implements OnLongClickListener {

	private EditText txtNumero;
	private ImageButton btnBorrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getVistas();
	}

	private void getVistas() {
		txtNumero = (EditText) this.findViewById(R.id.txtNumero);
		btnBorrar = (ImageButton) this.findViewById(R.id.btnBorrar);
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
		if (!TextUtils.isEmpty(txtNumero.getText().toString())) {
			// Se muestra la actividad de llamada.
			Intent intent = new Intent(getApplicationContext(),
					LlamadaActivity.class);
			intent.putExtra(LlamadaActivity.EXTRA_NUMERO, txtNumero.getText()
					.toString());
			startActivity(intent);
		}
	}

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

	private void btnBorrarOnLongClick() {
		// Se borra todo el contenido de texto.
		txtNumero.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

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

	private void buscarNumero() {
		String numero = txtNumero.getText().toString();
		if (!TextUtils.isEmpty(numero)) {
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, numero);
			startActivity(intent);
		}
	}

}
