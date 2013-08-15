package es.iessaladillo.pedrojoya.pr043;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

// Tarea asíncrona para cargar el catálogo desde el servidor.
class ImportarCatalogo extends AsyncTask<Void, String, Integer> {

	// Variables miembro.
	Activity contexto;
	ProgressDialog pd;

	public ImportarCatalogo(Activity contexto) {
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
				contexto.getString(R.string.catalogo_actualizado_con) + result
						+ " " + contexto.getString(R.string.productos),
				Toast.LENGTH_LONG).show();

		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// Escribo el mensaje de progreso en el diálogo.
		pd.setMessage(values[0]);
		super.onProgressUpdate(values);
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
		contexto.getContentResolver().delete(uri, null, null);
		// Recorro el array de productos.
		ConexionServidor c = new ConexionServidor();
		for (int i = 0; i < jaProductos.length(); i++) {
			// Obtengo un producto y sus datos y los almaceno en un
			// ContentValues.
			JSONObject jProducto = jaProductos.getJSONObject(i);
			ContentValues valores = new ContentValues();
			valores.put(DAO.FLD_PRO_ID,
					jProducto.getString(ConexionServidor.TAG_PRO_ID));
			valores.put(DAO.FLD_PRO_NOM,
					jProducto.getString(ConexionServidor.TAG_PRO_NOMBRE));
			valores.put(DAO.FLD_PRO_DES,
					jProducto.getString(ConexionServidor.TAG_PRO_DESCRIPCION));
			String sProImagen = jProducto
					.getString(ConexionServidor.TAG_PRO_IMAGEN);
			valores.put(DAO.FLD_PRO_IMA, sProImagen);
			// Inserto el producto en la BD.
			Uri uriInsercion = Uri
					.parse("content://es.iessaladillo.tienda/productos");
			contexto.getContentResolver().insert(uriInsercion, valores);
			// Obtengo la foto desde el servidor y hago una copia en la SD.
			if (sProImagen != null && !sProImagen.equals("")) {
				c.getImagen(jProducto
						.getString(ConexionServidor.TAG_PRO_IMAGEN));
			}
		}
		return jaProductos.length();
	}

}