package test.Droidlogin.library;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;

// Clase para el envío de peticiones HTTP POST y procesamiento de la respuesta JSON.
public class PeticionWeb {

	// Miembros de clase.
	InputStream entrada = null;	// Flujo de entrada de la respuesta a la petición.

	// Obtiene los datos de una petición HTTP Post a la url indicada con los parámetros indicados.
	// Retorna un array JSON o null si se ha producido algún error.
	public JSONArray getDatosServidor(ArrayList<NameValuePair> parametros, String urlServidor) {
		// Realiza la petición HTTP Post, obteniendo el flujo de entrada correspondiente a la respuesta.
		InputStream entrada = enviarPost(parametros, urlServidor);
		// Si se obtuvo flujo de entrada de la entidad de respuesta a la petición Post.
		if (entrada != null) {
			// Obtengo los datos de respuesta desde el flujo de entrada.
			String texto = getTextoRespuesta(entrada);
				return getJSONArray(texto);
		}
		else {
			return null;
		}
	}

	// Realiza una petición HTTP POST a la url del servidor con los datos suministrados.
	private InputStream enviarPost(ArrayList<NameValuePair> parametros, String urlServidor){
		try {
			// Creo un cliente HTTP.
			HttpClient clienteHTTP = new DefaultHttpClient();
			// Creo un objeto de petición HTTP Post indicando la url del servidor. 
			HttpPost postHTTP = new HttpPost(urlServidor);
			// Creo la entidad del post a partir de los parámetros.
			postHTTP.setEntity(new UrlEncodedFormEntity(parametros));
			// Realizo la petición HTTP Post en el cliente HTTP y obtengo la respuesta.
			HttpResponse respuestaHTTP = clienteHTTP.execute(postHTTP); 
			// Obtengo la entidad de la respuesta.
			HttpEntity entidadRespuesta = respuestaHTTP.getEntity();
			// Retorno el flujo de entrada de la entidad de la respuesta.
			return entidadRespuesta.getContent();
		}catch(Exception e){
			return null;
		}
	}

	// Obtiene el texto correspondiente a la respuesta desde el flujo de entrada indicado.
	// Retorna el texto o null si se ha producido un error.
	public String getTextoRespuesta(InputStream entrada){
		try{
			// Creo un buffer de entrada de 8 caracteres para el flujo de entrada de la respuesta.
			BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(entrada,"iso-8859-1"),8);
			// Leo línea a línea de la entrada y la concateno al texto.
			StringBuilder texto = new StringBuilder();
			String linea = null;
			while ((linea = bufferEntrada.readLine()) != null) {
				texto.append(linea + "\n");
			}
			entrada.close();
			// Retorno el texto.
			return texto.toString();
		}catch(Exception e){
			// Retorno null.
			return null;
		}
	}

	// Obtiene un array JSON a partir del texto indicado. Retorna el JSON Array o null si error.
	public JSONArray getJSONArray(String texto){
		// parse json data
		try{
			JSONArray jArray = new JSONArray(texto);

			return jArray;
		}
		catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
			return null;
		}

	}


}	





