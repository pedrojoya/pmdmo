package es.iessaladillo.pedrojoya.videoview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	// Variables a nivel de clase.
	VideoView vvReproductor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al OnCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout de la actividad.
		setContentView(R.layout.main);
		// Obtengo la referencia al reproductor VideoView.
		vvReproductor = (VideoView) this.findViewById(R.id.vvReproductor);
		/* Descarga "Test_Movie_iPhone.m4v desde
		 * http://www.mobvcasting.com/android/video/Test_Movie_iPhone.m4v
		 * y guárdalo en la raiz de la tarjeta SD de tu dispositivo.
		 */
		/* Obtengo la URI del vídeo a partir del directorio raíz del almacenamiento
		 * externo y del nombre del archivo.
		 */
		Uri uriVideo = Uri.parse(Environment.getExternalStorageDirectory()
				.getPath() + "/Test_Movie_iPhone.m4v");
		// Establezco la uri en el reproductor.
		vvReproductor.setVideoURI(uriVideo);
		// Indico que el reproductor debe utilizar un nuevo objeto MediaController.
		vvReproductor.setMediaController(new MediaController(this));
		// Inicio la reproducción.
		vvReproductor.start();
	}
}
