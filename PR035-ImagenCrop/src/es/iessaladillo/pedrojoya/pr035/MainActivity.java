package es.iessaladillo.pedrojoya.pr035;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

// Captura una foto y la recorta.
public class MainActivity extends Activity {

	// Constantes.
	final int RC_CAMARA = 1;
	final int RC_RECORTAR = 2;

	// Uri de la imagen capturada.
	private Uri uriImagen;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// Al hacer click sobre btnCapturar.
	public void btnCapturarOnClick(View v) {
		try {
			// Capturo la imagen.
			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, RC_CAMARA);
		} catch (ActivityNotFoundException anfe) {
			// Muestro un mensaje indicando que no se puede capturar imágenes.
			Toast.makeText(this, R.string.no_se_pueden_capturar_imagenes,
					Toast.LENGTH_SHORT).show();
		}
	}

	// Cuando se regresa de otra actividad.
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Si todo ha ido bien.
		if (resultCode == RESULT_OK) {
			// Retorno de la captura de la imagen.
			if (requestCode == RC_CAMARA) {
				// Obtengo la uri de la imagen capturada.
				uriImagen = data.getData();
				// Realizo el recortado de imagen.
				recortarImagen();
			}
			// Retorno del recorte de la imagen.
			else if (requestCode == RC_RECORTAR) {
				// Obtengo el bitmap resultante.
				Bitmap thePic = data.getExtras().getParcelable("data");
				// Muestro la imagen recortada en el visor.
				ImageView imgImagen = (ImageView) findViewById(R.id.imgImagen);
				imgImagen.setImageBitmap(thePic);
			}
		}
	}

	// Llama a un intent para recortar la imagen
	private void recortarImagen() {
		try {
			// Creo un intent estándar para el recortado de imágenes.
			Intent i = new Intent("com.android.camera.action.CROP");
			// Indico la uri de la imagen y el tipo.
			i.setDataAndType(uriImagen, "image/*");
			// Indico que se debe recortar.
			i.putExtra("crop", "true");
			// Indico el ratio ancho/alto del recuadro de recorte.
			i.putExtra("aspectX", 1);
			i.putExtra("aspectY", 1);
			// Indico los píxeles de salida de la imagen recortada.
			i.putExtra("outputX", 256);
			i.putExtra("outputY", 256);
			// Indico que se devuelva la imagen recortada.
			i.putExtra("return-data", true);
			// Inicio la actividad esperando el resultado.
			startActivityForResult(i, RC_RECORTAR);
		} catch (ActivityNotFoundException anfe) {
			// Algunos dispositivos no permiten el recorte de la imagen.
			Toast.makeText(this, R.string.no_se_permite_el_recorte_de_imagenes,
					Toast.LENGTH_SHORT).show();
		}
	}
}