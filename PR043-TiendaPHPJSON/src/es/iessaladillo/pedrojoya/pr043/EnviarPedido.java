package es.iessaladillo.pedrojoya.pr043;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

// Tarea asíncrona para enviar el pedido al el servidor.
class EnviarPedido extends AsyncTask<String, String, Boolean> {

	// Variables miembro.
	Activity contexto;
	ProgressDialog pd;

	public EnviarPedido(Activity contexto) {
		super();
		this.contexto = contexto;
	}

	@Override
	protected void onPreExecute() {
		// Creo un diálogo de progreso.
		pd = ProgressDialog.show(contexto,
				contexto.getString(R.string.EnviarPedido),
				contexto.getString(R.string.iniciando), true, false);
		super.onPreExecute();
	}

	// Recibe el nombre y el texto del pedido.
	@Override
	protected Boolean doInBackground(String... params) {
		boolean recibido = false;
		// Obtengo los datos que se van a mandar por POST.
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("nombre", params[0]);
		parametros.put("texto", params[1]);
		try {
			// Envío los datos al servidor llamando a exporta.php
			// sin parámetros.
			ConexionServidor conexion = new ConexionServidor();
			String respuesta = conexion.getDatosPost("guarda.php", parametros);
			// Informo de que se ha recibido la respuesta.
			// Si la respuesta no está vacía.
			if (respuesta != null && respuesta.trim() != "") {
				publishProgress(contexto
						.getString(R.string.procesando_respuesta));
				recibido = procesarRespuesta(respuesta.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// El hilo trabajador retorna si se ha recibido el pedido o no.
		return recibido;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// Cierro el diálogo de progreso.
		pd.dismiss();
		// Informo de si el pedido se ha realizado satisfactoriamente.
		if (result) {
			Toast.makeText(
					contexto,
					contexto.getString(R.string.el_pedido_se_ha_realizado_correctamente),
					Toast.LENGTH_LONG).show();
			// Vacío el carrito.
			vaciarCarrito();
		}
		else {
			Toast.makeText(
					contexto,
					contexto.getString(R.string.no_se_ha_podido_realizar_el_pedido),
					Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		// Escribo el mensaje de progreso en el diálogo.
		pd.setMessage(values[0]);
		super.onProgressUpdate(values);
	}

	// Analiza la cadena de respuesta que indica si el pedido se ha recibido
	// satisfactoriamente.
	private boolean procesarRespuesta(String respuesta) {
		return (respuesta.equals("Insertado"));
	}

	// Actualiza la BD para que no haya productos en el carrito.
	private void vaciarCarrito() {
		// Realizo el update en la base de datos a través del content provider.
		Uri uri = Uri.parse("content://es.iessaladillo.tienda/productos/");
		ContentValues valores = new ContentValues();
		valores.put(GestorBD.FLD_PRO_VEN, 0);
		contexto.getContentResolver().update(uri, valores,
				GestorBD.FLD_PRO_VEN + " > 0", null);
	}

}