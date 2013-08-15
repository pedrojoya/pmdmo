package es.iessaladillo.pedrojoya.pr043;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class PedidoActivity extends Activity {

	// Variables miembro.
	private EditText txtNombre;
	private EditText txtDireccion;
	private EditText txtEmail;
	private EditText txtTelefono;
	private EditText txtObservaciones;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedido_activity);
		// Activo el botón de Home en la Action Bar (si la versión lo permite).
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		getVistas();
	}

	private void getVistas() {
		txtNombre = (EditText) findViewById(R.id.txtNombre);
		txtDireccion = (EditText) findViewById(R.id.txtDireccion);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtTelefono = (EditText) findViewById(R.id.txtTelefono);
		txtObservaciones = (EditText) findViewById(R.id.txtObservaciones);
	}

	// Al mostrar el menú de opciones.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_pedido_activity, menu);
		return true;
	}

	// Al seleccionar una opción del menú de opciones.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
			case R.id.mnuEnviarPedido:
				enviarPedido();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void enviarPedido() {
		// Si hay conexión a Internet.
		if (ConexionServidor.isOnline(this)) {
			// Agrego a la cadena los datos del pedido.
			StringBuilder b = new StringBuilder();
			String sNombrePedido = txtNombre.getText().toString();
			b.append("Nombre: " + sNombrePedido + "\n");
			b.append("Dirección: " + txtDireccion.getText().toString() + "\n");
			b.append("e-mail: " + txtEmail.getText().toString() + "\n");
			b.append("Teléfono: " + txtTelefono.getText().toString() + "\n");
			b.append("Observaciones: " + txtObservaciones.getText().toString()
					+ "\n");
			b.append("Productos pedidos:\n");
			// Consulto a través del content provider los registros con unidades
			// a
			// pedir.
			Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos");
			CursorLoader cLoader = new CursorLoader(this, uri,
					DAO.PRO_TODOS, DAO.FLD_PRO_VEN + " > 0", null,
					null);
			Cursor c = cLoader.loadInBackground();
			if (c != null) {
				c.moveToFirst();
				while (!c.isAfterLast()) {
					b.append(c.getString(c.getColumnIndex(DAO.FLD_PRO_NOM))
							+ " - "
							+ c.getString(c
									.getColumnIndex(DAO.FLD_PRO_VEN))
							+ " unidades\n");
					c.moveToNext();
				}
				c.close();
			}
			// Obtengo la cadena final.
			String sTextoPedido = b.toString();
			// Creo la tarea asíncrona para enviar el pedido.
			EnviarPedido tarea = new EnviarPedido(this);
			tarea.execute(sNombrePedido, sTextoPedido);
		}
		else {
			Toast.makeText(this, R.string.sin_conexion_a_internet,
					Toast.LENGTH_LONG).show();
		}
	}

}
