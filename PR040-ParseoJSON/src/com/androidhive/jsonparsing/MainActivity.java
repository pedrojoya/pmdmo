package com.androidhive.jsonparsing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Constantes para los nombres de los nodos JSON.
	private static final String TAG_CONTACTOS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NOMBRE = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_DIRECCION = "address";
	private static final String TAG_GENERO = "gender";
	private static final String TAG_TELEFONO = "phone";
	private static final String TAG_TELEFONO_MOVIL = "mobile";
	private static final String TAG_TELEFONO_CASA = "home";
	private static final String TAG_TELEFONO_OFICINA = "office";

	// Variables miembro.
	private final String url = "http://api.androidhive.info/contacts/";
	private ListView lstLista;
	private RelativeLayout rlVacia;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lstLista = (ListView) this.findViewById(R.id.lstLista);
		rlVacia = (RelativeLayout) this.findViewById(R.id.rlVacia);
		lstLista.setEmptyView(rlVacia);
		// Cargo la lista a partir de los datos proporcionados en JSON por la
		// URL.
		CargarJSON tarea = new CargarJSON(this);
		tarea.execute(url);
	}

	private class CargarJSON extends
			AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

		Context contexto;
		ProgressDialog pd;
		Exception error = null;

		public CargarJSON(Context contexto) {
			super();
			this.contexto = contexto;
		}

		@Override
		protected void onPreExecute() {
			// Creo un diálogo de progreso.
			pd = ProgressDialog.show(contexto, "Carga de datos",
					"Iniciando...", true, false);
			super.onPreExecute();
		}

		@SuppressWarnings("unused")
		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... arg0) {

			// Variables.
			ArrayList<HashMap<String, String>> listaContactos = null;
			InputStream flujo = null;
			JSONObject jDatos = null;
			JSONArray jaContactos = null;
			String sRespuesta = "";

			// Obtengo la url a partir del array de parámetros del método.
			String url = arg0[0];
			// Realizo la petición POST por HTTP obteniendo como resultado un
			// flujo de datos con la respuesta.
			try {
				publishProgress("Realizando petición...");
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				flujo = httpEntity.getContent();
			} catch (Exception e) {
				error = e;
				return null;
			}
			// Leo del flujo de datos línea línea y lo almaceno en una cadena.
			try {
				publishProgress("Leyendo del flujo de entrada...");
				BufferedReader lector = new BufferedReader(
						new InputStreamReader(flujo, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String linea = null;
				while ((linea = lector.readLine()) != null) {
					sb.append(linea + "\n");
				}
				flujo.close();
				sRespuesta = sb.toString();
			} catch (Exception e) {
				error = e;
				return null;
			}
			// Convierto la cadena de respuesta en un objeto JSON y lo retorno.
			try {
				publishProgress("Analizando datos...");
				jDatos = new JSONObject(sRespuesta);
			} catch (JSONException e) {
				error = e;
				return null;
			}
			try {
				// Obtengo los contactos desde el objeto JSON.
				jaContactos = jDatos.getJSONArray(TAG_CONTACTOS);
				listaContactos = new ArrayList<HashMap<String, String>>();
				// Recorro el array de contactos.
				for (int i = 0; i < jaContactos.length(); i++) {
					// Obtengo un contacto y sus datos.
					JSONObject jContacto = jaContactos.getJSONObject(i);
					String id = jContacto.getString(TAG_ID);
					String nombre = jContacto.getString(TAG_NOMBRE);
					String email = jContacto.getString(TAG_EMAIL);
					String direccion = jContacto.getString(TAG_DIRECCION);
					String genero = jContacto.getString(TAG_GENERO);
					// Obtengo el array de teléfonos del contacto.
					JSONObject jaTelefonos = jContacto
							.getJSONObject(TAG_TELEFONO);
					String tlfMovil = jaTelefonos.getString(TAG_TELEFONO_MOVIL);
					String tlfCasa = jaTelefonos.getString(TAG_TELEFONO_CASA);
					String tlfOficina = jaTelefonos
							.getString(TAG_TELEFONO_OFICINA);
					// Creo y relleno la estructura de datos para el contacto y
					// la
					// añado a la lista.
					HashMap<String, String> mapContacto = new HashMap<String, String>();
					mapContacto.put(TAG_ID, id);
					mapContacto.put(TAG_NOMBRE, nombre);
					mapContacto.put(TAG_EMAIL, email);
					mapContacto.put(TAG_TELEFONO_MOVIL, tlfMovil);
					listaContactos.add(mapContacto);
				}
			} catch (JSONException e) {
				error = e;
				return null;
			}
			return listaContactos;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			// Cierro el diálogo de progreso.
			pd.dismiss();
			if (error == null) { 
				// Creo el adaptador para la lista.
				ListAdapter adaptador = new SimpleAdapter(contexto,
						result, R.layout.fila, new String[] {
								MainActivity.TAG_NOMBRE, MainActivity.TAG_EMAIL,
								MainActivity.TAG_TELEFONO_MOVIL }, new int[] {
								R.id.lblNombre, R.id.lblEmail, R.id.lblTlfMovil });
				lstLista.setAdapter(adaptador);
			}
			else {
				Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_LONG).show();
			}
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