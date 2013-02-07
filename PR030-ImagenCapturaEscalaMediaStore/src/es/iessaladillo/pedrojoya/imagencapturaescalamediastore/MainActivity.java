package es.iessaladillo.pedrojoya.imagencapturaescalamediastore;

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
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// Constantes.
	final static int RC_CAPTURA_FOTO = 0;

	// Variables a nivel de clase.
	Uri uriFoto;
	ImageView imgFoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imgFoto = (ImageView) findViewById(R.id.imgFoto);
		// Si ya tengo una foto capturada.
		if (savedInstanceState != null) {
			// Obtengo del bundle la uri de la foto desde el Bundle.
			uriFoto = Uri.parse(savedInstanceState.getString("uri"));
		}
	}

	// Al hacer click sobre btnCapturar.
	public void btnCapturarOnClick(View v) {
		// Creo un registro en el content provider de la MediaStore para la
		// foto, indicando que se debe almacenar en almacenamiento externo y
		// pasándole un objeto para almacenar los metadatos. Obtengo la uri en
		// la que se almacenará el archivo.
		uriFoto = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,
				new ContentValues());
		// Creo el intent para capturar una foto y le paso como dato extra la
		// uri
		// donde se debe almacenar la foto como archivo.
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
				// Cargo la imagen.
				cargarImagenEscalada();
				break;
			}
		}
	}

	// Escala y muestra la imagen en el visor.
	private void cargarImagenEscalada() {
		// Utilizo una tarea asíncrona para cargar la foto. La ejecuto pasándole
		// la uri donde se ha almacenado la foto.
		CargaFoto carga = new CargaFoto();
		carga.execute(uriFoto.toString());
		// Una vez realizada la tarea, elimino el objeto.
		carga = null;
	}

	// Tarea asíncrona que obtiene una foto a partir de una uri y la muestra en
	// un visor.
	private class CargaFoto extends AsyncTask<String, Void, Bitmap> {

		// Se ejecuta en un hilo trabajador.
		@Override
		protected Bitmap doInBackground(String... params) {
			// Obtengo la uri.
			Uri uriFoto = Uri.parse(params[0]);
			// Escalo la imagen a 1/4 de su tamaño.
			BitmapFactory.Options opcionesImagen = new BitmapFactory.Options();
			Bitmap imagen;
			try {
				opcionesImagen.inJustDecodeBounds = false;
				opcionesImagen.inSampleSize = 4;
				InputStream datos = getContentResolver().openInputStream(
						uriFoto);
				imagen = BitmapFactory
						.decodeStream(datos, null, opcionesImagen);
				datos.close();
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
			// Muestro la foto en el visor.
			if (result != null) {
				imgFoto.setImageBitmap(result);
			}
		}

	}

	// Al cambiar la orientación.
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Almaceno la uri de la foto en el bundle.
		outState.putString("uri", uriFoto.toString());
		super.onSaveInstanceState(outState);
	}

	public void btnMetadatosOnClick(View v) {
		// Creo el intent para mostrar la actividad MetadatosActivity.
		Intent i = new Intent(this, MetadatosActivity.class);
		i.setData(uriFoto);
		this.startActivity(i);
	}

	@Override
	protected void onResume() {
		if (uriFoto != null) {
			cargarImagenEscalada();
		}
		super.onResume();
	}

}
