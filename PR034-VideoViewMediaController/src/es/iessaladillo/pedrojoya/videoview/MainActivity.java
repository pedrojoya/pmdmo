package es.iessaladillo.pedrojoya.videoview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	// Constantes.
	private static final int RC_SELECCIONAR = 0;
	
	// Variables a nivel de clase.
	private VideoView vvReproductor;
	private Uri uriVideo;
	private int posicion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al OnCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout de la actividad.
		setContentView(R.layout.main);
		// Obtengo la referencia al reproductor VideoView.
		vvReproductor = (VideoView) this.findViewById(R.id.vvReproductor);
		vvReproductor.setEnabled(false);
		// Si ya tenía una uri muestro el vídeo.
		if (savedInstanceState != null) {
			uriVideo = Uri.parse(savedInstanceState.getString("uri"));
			posicion = savedInstanceState.getInt("posicion");
			mostrarVideo();
		}
	}
	
	public void btnSeleccionarOnClick(View v) {
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		i.setType("video/*");
		startActivityForResult(i, RC_SELECCIONAR);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == RC_SELECCIONAR) {
			uriVideo = data.getData();
			posicion = 0;
			mostrarVideo();
		}
	}
	
	private void mostrarVideo() {
		// Establezco la uri en el reproductor.
		vvReproductor.setEnabled(true);
		vvReproductor.setVideoURI(uriVideo);
		// Indico que el reproductor debe utilizar un nuevo objeto MediaController.
		vvReproductor.setMediaController(new MediaController(this));
		vvReproductor.seekTo(posicion);
		// Inicio la reproducción.
		vvReproductor.start();					
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (uriVideo != null) {
			outState.putString("uri", uriVideo.toString());
			outState.putInt("posicion", vvReproductor.getCurrentPosition());
		}
		super.onSaveInstanceState(outState);
	}
	
	
}
