package es.iessaladillo.pedrojoya.imagencapturaescala;

import java.io.File;
import es.iessaladillo.pedrojoya.imagencapturaescala.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	// Constantes.
	final static int RC_CAPTURAR_IMAGEN = 0;	// Request code del intent de captura.

	// Variables a nivel de clase.
	private ImageView imgFoto;
	private String imgPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initVistas();
	}

	// Al hacer click en el botón btnCapturar.
	public void btnCapturarOnClick(View v) {
		// Obtenemos el nombre del archivo con el que debe almacenarse la foto.
		imgPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/mifoto.jpg";
		// Creo el descriptor del archivo.
		File ficheroFoto = new File(imgPath);
		// Obtengo la URI del archivo a partir de su descriptor.
		Uri uriFoto = Uri.fromFile(ficheroFoto);
		// Creo el intent con la acción de captura de imagen.
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// Paso como dato extra del intent la uri debe debe almacenarse la foto.
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uriFoto);
		// Envío el intent esperando respuesta.
		startActivityForResult(i, RC_CAPTURAR_IMAGEN);
	}
	
	// Obtengo las referencias e inicializo las vistas.
	private void initVistas() {
		imgFoto = (ImageView) findViewById(R.id.imgFoto);
	}

	// Al retornar resultados.
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		// Si todo ha ido bien y el código de petición corresponde al de captura.
		if (resultCode == RESULT_OK && requestCode == RC_CAPTURAR_IMAGEN) {
			mostrarImagen();
		}
	}

	// Escala y muestra la imagen en el visor.
	private void mostrarImagen() {
		// Obtengo las dimensiones del visor.
		int anchuraVisor = imgFoto.getWidth();
		int alturaVisor = imgFoto.getHeight();
		// Obtengo las dimensiones de la imagen (inJustDecodeBounds = true)
		BitmapFactory.Options opcionesImagen = new BitmapFactory.Options();
		opcionesImagen.inJustDecodeBounds = true;	// Se simula.
		Bitmap imagen = BitmapFactory.decodeFile(imgPath, opcionesImagen);
		// Obtengo la ratio de la imagen respecto al tamaño de la pantalla.
		int ratioAltura = (int) Math.ceil(opcionesImagen.outHeight
				/ (float) alturaVisor);
		int ratioAnchura = (int) Math.ceil(opcionesImagen.outWidth
				/ (float) anchuraVisor);
		Log.v("Ratio de Altura", "" + ratioAltura);
		Log.v("Ratio de Anchura", "" + ratioAnchura);
		/* Si ambos ratios son mayores que 1 es porque uno de los lados de
		 * la imagen es más grande que la imagen.
		 */
		if (ratioAltura > 1 && ratioAnchura > 1) {
			// Escalo la imagen a la ratio mayor.
			if (ratioAltura > ratioAnchura) {
				opcionesImagen.inSampleSize = ratioAltura;
			} else {
				opcionesImagen.inSampleSize = ratioAnchura;
			}
		}
		opcionesImagen.inJustDecodeBounds = false;	// Se hace realmente el escalado.
		imagen = BitmapFactory.decodeFile(imgPath, opcionesImagen);
		// Muestro la foto
		imgFoto.setImageBitmap(imagen);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// Muestro la foto
		imgPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/mifoto.jpg";
		Bitmap imagen = BitmapFactory.decodeFile(imgPath, null);
		imgFoto.setImageBitmap(imagen);
		super.onRestoreInstanceState(savedInstanceState);
	}


}
