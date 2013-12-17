package es.iessaladillo.pedrojoya.pr068;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// Actividad que muestra el registro de llamadas.
public class RegistroActivity extends Activity implements OnItemClickListener {

	// Vistas.
	private ListView lstLlamadas;

	// Variables a nivel de clase.
	private LlamadasAdapter adaptador;

	// Al crear la actividad.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		// Se obtienen las vistas.
		getVistas();
		// Se carga la lista.
		cargarLista();
	}

	// Cuando la actividad pasa a primer plano.
	@Override
	protected void onResume() {
		// Se repinta la lista.
		adaptador.notifyDataSetChanged();
		super.onResume();
	}

	// Carga el ListView con la lista de llamadas.
	private void cargarLista() {
		// Se crea el adaptador para la lista.
		adaptador = new LlamadasAdapter(getApplicationContext(),
				Registro.getLlamadas());
		lstLlamadas.setAdapter(adaptador);

	}

	// Obtiene e inicializa las vistas.
	private void getVistas() {
		lstLlamadas = (ListView) findViewById(R.id.lstLlamadas);
		// La propia actividad actuará de listener cuando se pulse sobre un
		// elemento de la lista.
		lstLlamadas.setOnItemClickListener(this);
	}

	// Al pulsar sobre un elemento de la lista.
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position,
			long id) {
		// Se simula la llamada al número correspondiente, mostrando la
		// actividad LlamadaActivity.
		Llamada llamada = (Llamada) lstLlamadas.getItemAtPosition(position);
		Intent intent = new Intent(getApplicationContext(),
				LlamadaActivity.class);
		intent.putExtra(LlamadaActivity.EXTRA_NUMERO, llamada.getNumero());
		startActivity(intent);
	}

}
