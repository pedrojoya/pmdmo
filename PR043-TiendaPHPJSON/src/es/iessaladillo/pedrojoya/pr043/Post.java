package es.iessaladillo.pedrojoya.pr043;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

class Post {
	private InputStream is = null;
	private String respuesta = "";

	private void conectaPost(ArrayList parametros, String URL) {
		ArrayList nameValuePairs;
		try {

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(URL);
			nameValuePairs = new ArrayList();

			if (parametros != null) {
				for (int i = 0; i < parametros.size() - 1; i += 2) {
					nameValuePairs.add(new BasicNameValuePair(
							(String) parametros.get(i), (String) parametros
									.get(i + 1)));
				}
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());

		} finally {

		}
	}

	private void getRespuestaPost() {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			respuesta = sb.toString();
			Log.e("log_tag", "Cadena JSon " + respuesta);
		} catch (Exception e) {

			Log.e("log_tag", "Error converting result " + e.toString());

		}
	}


	public String getServerData(ArrayList parametros, String URL) {
		conectaPost(parametros, URL);
		if (is != null) {
			getRespuestaPost();
		}
		if (respuesta != null && respuesta.trim() != "") {
			//return getJsonArray();
			return respuesta;
		} else {
			return null;
		}
	}
}
