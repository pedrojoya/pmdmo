package es.iessaladillo.pedrojoya.pr043;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

class ConexionServidor {

	// Constantes.
	private final String URL_BASE = "http://informaticasaladillo.es/android/tienda/";
	private final String URL_IMAGENES = URL_BASE + "imagenes/";
	private final String P_CLAVE = "clave_acceso";
	private final String CLAVE = "alri23aklLL";
	// Tags para JSON.
	public static final String TAG_PRODUCTOS = "productos";
	public static final String TAG_PRO_ID = "pro_id";
	public static final String TAG_PRO_NOMBRE = "pro_nombre";
	public static final String TAG_PRO_DESCRIPCION = "pro_descripcion";
	public static final String TAG_PRO_IMAGEN = "pro_imagen";

	// Realiza una conexión con el servidor. Recibe la página a visitar y los
	// parámetros para POST.
	String getDatosPost(String pagina, Map<String, String> parametros)
			throws Exception {
		// Cadena que será retornada.
		String sRespuesta = null;
		// URL del servidor.
		URL url = new URL(URL_BASE + pagina);
		// Abrimos la conexión con dicha URL.
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		// Indico que voy a utilizar el método POST y se van a enviar
		// parámetros.
		conexion.setRequestMethod("POST");
		conexion.setDoOutput(true);
		// Obtengo un escritor para el flujo de salida.
		OutputStreamWriter escritor = new OutputStreamWriter(
				conexion.getOutputStream());
		// Escribo el parámetro de clave en el flujo de salida.
		escritor.write(P_CLAVE + "=" + URLEncoder.encode(CLAVE, "UTF-8"));
		// Escribo el resto de parámetros en el flujo de salida.
		if (parametros != null) {
			for (Map.Entry<String, String> p : parametros.entrySet()) {
				escritor.write(URLEncoder.encode(p.getKey(), "UTF-8") + "="
						+ URLEncoder.encode(p.getValue(), "UTF-8"));
			}
		}
		// Envío los datos escritos.
		escritor.flush();
		// Obtengo la respuesta.
		if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
			// Leo del flujo de entrada la respuesta y la convierto en
			// cadena.
			InputStream flujoEntrada = conexion.getInputStream();
			BufferedReader lector = new BufferedReader(new InputStreamReader(
					conexion.getInputStream(), "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String linea = null;
			while ((linea = lector.readLine()) != null) {
				sb.append(linea + "\n");
			}
			flujoEntrada.close();
			sRespuesta = sb.toString();
		}
		conexion.disconnect();
		// Retorno la cadena correspondiente a la respuesta.
		return sRespuesta;
	}

	void getImagen(String sNombreImagen) {
		try {
			// URL de la imagen.
			URL url = new URL(URL_IMAGENES + sNombreImagen);
			// Abrimos la conexión con dicha URL.
			HttpURLConnection conexion = (HttpURLConnection) url
					.openConnection();
			conexion.setRequestMethod("GET");
			conexion.connect();
			// Si todo ha ido bien.
			if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Obtengo el bitmap correspondiente a la imagen.
				BitmapFactory.Options opcionesImagen = new BitmapFactory.Options();
				opcionesImagen.inJustDecodeBounds = false;
				opcionesImagen.inSampleSize = 4;
				Bitmap imagen = BitmapFactory.decodeStream(
						conexion.getInputStream(), null, opcionesImagen);
				// Guardo el bitmap en la tarjeta SD.
				File dirRootSD = Environment.getExternalStorageDirectory();
				File dirImagenes = new File(dirRootSD, "tienda");
				// Si el directorio para las imágenes no existe lo creo.
				if (!dirImagenes.exists()) {
					dirImagenes.mkdirs();
				}
				// Creo el fichero para la imagen.
				File archivo = new File(dirImagenes, sNombreImagen);
				if (archivo.exists())
					archivo.delete();
				FileOutputStream salida = new FileOutputStream(archivo);
				imagen.compress(Bitmap.CompressFormat.PNG, 100, salida);
				salida.flush();
				salida.close();
			}
			conexion.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
