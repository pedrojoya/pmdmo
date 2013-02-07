package es.iessaladillo.pedrojoya.imagencapturaescalamediastore;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// Constantes
	final static int RC_CAPTURA_FOTO = 0;

	// Variables a nivel de clase.
	Uri uriFoto;
	ImageView imgFoto;
	int anchuraVisor = 0;
	int alturaVisor = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imgFoto = (ImageView) findViewById(R.id.imgFoto);
		if (savedInstanceState != null) {
			// Obtengo del bundle la uri de la foto.
			uriFoto = Uri.parse(savedInstanceState.getString("uri"));
			anchuraVisor = savedInstanceState.getInt("anchura");
			alturaVisor = savedInstanceState.getInt("altura");
		}
	}

	public void btnCapturarOnClick(View v) {
		/*
		 * Creo un registro en el content provider de la MediaStore para la
		 * foto, indicando que se debe almacenar en almacenamiento externo y
		 * pasándole un objeto para almacenar los metadatos. Obtengo la uri en
		 * la que se almacenará el archivo.
		 */
		uriFoto = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,
				new ContentValues());

		/*
		 * Creo el intent para capturar una foto y le paso como dato extra la
		 * uri donde se debe almacenar la foto como archivo.
		 */
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uriFoto);
		// Envío el intent esperando respuesta.
		startActivityForResult(i, RC_CAPTURA_FOTO);
	}

	// Cuando se recibe la foto capturada.
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case RC_CAPTURA_FOTO:
				// Obtengo las dimensiones del visor y cargo la imagen.
				anchuraVisor = imgFoto.getMeasuredWidth();
				alturaVisor = imgFoto.getMeasuredHeight();
				cargarImagenEscalada();
				break;
			}
		}
	}

	// Escala y muestra la imagen en el visor.
	private void cargarImagenEscalada() {
		// Utilizo una tarea asíncrona para cargar la foto.
		// La ejecuto pasándole la anchura del visor, la altura y la uri donde
		// se ha almacenado la foto.
		CargaFoto carga = new CargaFoto();
		carga.execute(new String[] { String.valueOf(anchuraVisor),
				String.valueOf(alturaVisor), uriFoto.toString() });
		// Una vez realizada la tarea, elimino el objeto.
		carga = null;
	}

	// Tarea asíncrona que obtiene una foto a partir de una uri, la escala en
	// base a una anchura y altura y la muestra en un visor.
	private class CargaFoto extends AsyncTask<String, Void, Bitmap> {

		// Se ejecuta en un hilo trabajador.
		@Override
		protected Bitmap doInBackground(String... params) {
			// Obtengo los parámetros: anchura, altura y uri.
			int anchuraVisor = Integer.parseInt(params[0]);
			int alturaVisor = Integer.parseInt(params[1]);
			Uri uriFoto = Uri.parse(params[2]);
			// Obtengo las dimensiones de la imagen (inJustDecodeBounds = true
			// para simular)
			BitmapFactory.Options opcionesImagen = new BitmapFactory.Options();
			opcionesImagen.inJustDecodeBounds = true; // Se simula.
			Bitmap imagen;
			try {
				InputStream datos = getContentResolver().openInputStream(
						uriFoto);
				imagen = BitmapFactory
						.decodeStream(datos, null, opcionesImagen);
				try {
					datos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Obtengo la ratio de la imagen respecto al tamaño de la
				// pantalla.
				int ratioAltura = (int) Math.ceil(opcionesImagen.outHeight
						/ (float) alturaVisor);
				int ratioAnchura = (int) Math.ceil(opcionesImagen.outWidth
						/ (float) anchuraVisor);
				Log.v("Ratio de Altura", "" + ratioAltura);
				Log.v("Ratio de Anchura", "" + ratioAnchura);
				// Si ambos ratios son mayores que 1 es porque uno de los lados
				// de la imagen es más grande que la imagen.
				if (ratioAltura > 1 && ratioAnchura > 1) {
					// Escalo la imagen a la ratio mayor.
					if (ratioAltura > ratioAnchura) {
						opcionesImagen.inSampleSize = ratioAltura;
					} else {
						opcionesImagen.inSampleSize = ratioAnchura;
					}
				}
				// Hago el escalado real.
				opcionesImagen.inJustDecodeBounds = false;
				datos = getContentResolver().openInputStream(uriFoto);
				imagen = BitmapFactory
						.decodeStream(datos, null, opcionesImagen);
				// Retorno la imagen.
				return imagen;
			} catch (Exception e) {
				return null;
			}
		}

		// Una vez finalizado el hilo de trabajo. Se ejecuta en el hilo
		// principal.
		@Override
		protected void onPostExecute(Bitmap result) {
			// Muestro la foto
			if (result != null) {
				imgFoto.setImageBitmap(result);
			}
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Almaceno la uri de la foto en el bundle.
		outState.putString("uri", uriFoto.toString());
		outState.putInt("anchura", anchuraVisor);
		outState.putInt("altura", alturaVisor);
		super.onSaveInstanceState(outState);
	}

	public void btnMetadatosOnClick(View v) {
		// Creo el intent para mostrar la actividad MetadatosActivity.
		Intent i = new Intent(this, MetadatosActivity.class);
		i.setData(uriFoto);
		this.startActivity(i);
	}

	private void cargarImagen() {
		CargaFotoSinEscala carga = new CargaFotoSinEscala();
		carga.execute(uriFoto.toString());
		carga = null;
	}

	private class CargaFotoSinEscala extends AsyncTask<String, Void, Bitmap> {
	
		@Override
		protected Bitmap doInBackground(String... params) {
			Uri uriFoto = Uri.parse(params[0]);
			Bitmap imagen;
			try {
				InputStream datos = getContentResolver().openInputStream(
						uriFoto);
				imagen = BitmapFactory.decodeStream(datos);
				return imagen;
			} catch (Exception e) {
				Log.d("error raro", e.getMessage());
				return null;
			}
		}
	
		@Override
		protected void onPostExecute(Bitmap result) {
			// Muestro la foto
			if (result != null) {
				imgFoto.setImageBitmap(result);
			}
		}
	
	}

}
