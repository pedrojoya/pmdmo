package es.iessaladillo.pedrojoya.pr043;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	// Al hacer click sobre btnAcceder
	public void btnAccederOnClick(View v) {
		// Creo el intent explícito y muestro la actividad CatalogoActivity.
		Intent i = new Intent(this, CatalogoActivity.class);
		startActivity(i);
	}

	// Al mostrar el menú de opciones.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	// Al seleccionar una opción del menú de opciones.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Dependiendo de la opción de menú seleccionada.
		switch (item.getItemId()) {
			case R.id.mnuAcercaDe:
				mostrarAcercaDe();
				break;
			case R.id.mnuActualizarCatalogo:
				actualizarCatalogo();
				break;
		}
		return true;

	}

	// Muestra la actividad AcercaDeActivity
	private void mostrarAcercaDe() {
		// Creo el intent explícito e inicio la actividad AcercaDeActivity.
		Intent i = new Intent(this, AcercaDeActivity.class);
		startActivity(i);
	}

	private void actualizarCatalogo() {
		// Si hay conexión a Internet.
		if (isOnline()) {
			// Creo la tarea asíncrona para importar los datos.
			ImportarCatalogo tarea = new ImportarCatalogo(this);
			tarea.execute();
		}
		else {
			Toast.makeText(this, R.string.sin_conexion_a_internet,
					Toast.LENGTH_LONG).show();
		}
	}

	// Retorna si el dispositivo tiene conexión a Internet.
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	// Analiza la cadena de respuesta obteniendo los objeto JSON y realiza las
	// operaciones necesarias en la base de datos.
	private int procesarRespuesta(String respuesta) throws Exception {
		// Convierto la cadena de respuesta en un objeto JSON.
		JSONObject jDatos = new JSONObject(respuesta);
		// Obtengo los productos desde el objeto JSON.
		JSONArray jaProductos = jDatos
				.getJSONArray(ConexionServidor.TAG_PRODUCTOS);
		// Borramos todos los registros de la tabla de productos.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos");
		getContentResolver().delete(uri, null, null);
		// Recorro el array de productos.
		ConexionServidor c = new ConexionServidor();
		for (int i = 0; i < jaProductos.length(); i++) {
			// Obtengo un producto y sus datos y los almaceno en un
			// ContentValues.
			JSONObject jProducto = jaProductos.getJSONObject(i);
			ContentValues valores = new ContentValues();
			valores.put(GestorBD.FLD_PRO_ID,
					jProducto.getString(ConexionServidor.TAG_PRO_ID));
			valores.put(GestorBD.FLD_PRO_NOMBRE,
					jProducto.getString(ConexionServidor.TAG_PRO_NOMBRE));
			valores.put(GestorBD.FLD_PRO_DESCRIPCION,
					jProducto.getString(ConexionServidor.TAG_PRO_DESCRIPCION));
			String sProImagen = jProducto
					.getString(ConexionServidor.TAG_PRO_IMAGEN);
			valores.put(GestorBD.FLD_PRO_IMAGEN, sProImagen);
			// Inserto el producto en la BD.
			Uri uriInsercion = Uri
					.parse("content://es.iessaladillo.tienda/productos");
			getContentResolver().insert(uriInsercion, valores);
			// Obtengo la foto desde el servidor y hago una copia en la SD.
			if (sProImagen != null && !sProImagen.equals("")) {
				c.getImagen(jProducto
						.getString(ConexionServidor.TAG_PRO_IMAGEN));
			}
		}
		return jaProductos.length();
	}

	// Tarea asíncrona para cargar el catálogo desde el servidor.
	private class ImportarCatalogo extends AsyncTask<Void, String, Integer> {

		// Variables miembro.
		Context contexto;
		ProgressDialog pd;

		public ImportarCatalogo(Context contexto) {
			super();
			this.contexto = contexto;
		}

		@Override
		protected void onPreExecute() {
			// Creo un diálogo de progreso.
			pd = ProgressDialog.show(contexto,
					contexto.getString(R.string.importar_catalogo),
					contexto.getString(R.string.iniciando), true, false);
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int cuantos = 0;
			try {
				// Obtengo los datos desde el servidor llamando a exporta.php
				// sin parámetros.
				ConexionServidor conexion = new ConexionServidor();
				String respuesta = conexion.getDatosPost("exporta.php", null);
				// Informo de que se ha recibido la respuesta.
				// Si la respuesta no está vacía.
				if (respuesta != null && respuesta.trim() != "") {
					publishProgress(contexto
							.getString(R.string.procesando_respuesta));
					cuantos = procesarRespuesta(respuesta);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// El hilo trabajador no retorna nada.
			return cuantos;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// Cierro el diálogo de progreso.
			pd.dismiss();
			// Informo del número de productos insertados.
			Toast.makeText(
					contexto,
					getString(R.string.catalogo_actualizado_con) + result + " "
							+ getString(R.string.productos), Toast.LENGTH_LONG)
					.show();

			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// Escribo el mensaje de progreso en el diálogo.
			pd.setMessage(values[0]);
			super.onProgressUpdate(values);
		}

	}

}
