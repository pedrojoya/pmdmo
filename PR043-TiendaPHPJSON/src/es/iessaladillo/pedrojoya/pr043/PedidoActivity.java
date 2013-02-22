package es.iessaladillo.pedrojoya.pr043;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PedidoActivity extends Activity {
	private Button bEnviarPedido;
	private String detallePedido;
	private EditText cNombre;
	private EditText cEmail;
	private EditText cTelefono;
	private EditText cObservaciones;
	private Button bAnadirMasProductos;
	private GestorBD usdbh = new GestorBD(this);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_activity);

		bAnadirMasProductos = (Button) findViewById(R.id.anadirMasProductos);
		bAnadirMasProductos.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarListadoProductos();
			}
		});

		bEnviarPedido = (Button) findViewById(R.id.enviarCorreo);
		bEnviarPedido.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarEnvioPedido();
			}
		});
	}

	public void lanzarEnvioPedido() {

		cNombre = (EditText) findViewById(R.id.nombre);
		cEmail = (EditText) findViewById(R.id.email);
		cTelefono = (EditText) findViewById(R.id.telefono);
		cObservaciones = (EditText) findViewById(R.id.observaciones);

		detallePedido = "";
		detallePedido = detallePedido + "Nombre: "
				+ cNombre.getText().toString() + "\n";
		detallePedido = detallePedido + "Email: " + cEmail.getText().toString()
				+ "\n";
		detallePedido = detallePedido + "Teléfono: "
				+ cTelefono.getText().toString() + "\n";
		detallePedido = detallePedido + "Observaciones: "
				+ cObservaciones.getText().toString() + "\n";
		detallePedido = detallePedido + "Productos Seleccionados:\n";

		SQLiteDatabase db = usdbh.getWritableDatabase();

		String sql = "SELECT pro_nombre, pro_unidades_sel FROM t_producto WHERE pro_unidades_sel>0";
		Cursor c = db.rawQuery(sql, null);

		int numRegistros = c.getCount();

		if (numRegistros > 0) {
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					detallePedido += c.getString(0) + " - " + c.getString(1)
							+ " unidades\n";
				} while (c.moveToNext());
			}
		}

		ArrayList parametros = new ArrayList();
		parametros.add("clave_acceso");
		parametros.add("alri23aklLL");
		parametros.add("nombre");
		parametros.add(cNombre.getText().toString());
		parametros.add("texto");
		parametros.add(detallePedido);

		// Llamada a Servidor Web PHP
		try {
			Post post = new Post();
			String datos = post.getServerData(parametros,
					"http://www.jrcj.com/android/guarda.php");
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.PedidoInsertado),
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(),
					this.getString(R.string.ErrorConectaServidor),
					Toast.LENGTH_SHORT).show();
		}
		// FIN Llamada a Servidor Web PHP

		Intent i = new Intent(this, MainActivity.class);
		startActivityForResult(i, 1234);
	}

	public void lanzarListadoProductos() {
		Intent i = new Intent(this, CatalogoActivity.class);
		startActivityForResult(i, 1234);
	}
}
