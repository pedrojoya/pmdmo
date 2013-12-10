package es.iessaladillo.pedrojoya.pr068;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RegistroActivity extends Activity implements OnItemClickListener {

	// Variables.
	private ListView lstLlamadas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		getVistas();
	}

	@Override
	protected void onResume() {
		// Se carga la lista.
		cargarLista();
		super.onResume();
	}

	private void cargarLista() {
		// Se crea el adaptador para la lista.
		LlamadasAdapter adaptador = new LlamadasAdapter(
				getApplicationContext(), Registro.getLlamadas());
		lstLlamadas.setAdapter(adaptador);

	}

	private void getVistas() {
		lstLlamadas = (ListView) findViewById(R.id.lstLlamadas);
		lstLlamadas.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position,
			long id) {
		// Se simula la llamada al número.
		Llamada llamada = (Llamada) lstLlamadas.getItemAtPosition(position);
		Intent intent = new Intent(getApplicationContext(),
				LlamadaActivity.class);
		intent.putExtra(LlamadaActivity.EXTRA_NUMERO, llamada.getNumero());
		startActivity(intent);
	}

}
