package es.iessaladillo.pedrojoya.pr039;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final float VELOCIDAD_NORMAL = 1f;
	private static final int PRIORIDAD_MAXIMA = 1;
	private static final float VOLUMEN_MAX = 1f;
	private static final int SIN_BUCLE = 0;
	// Constantes.
	private static final int CALIDAD_NORMAL = 0;
	private static final int PRIORIDAD_NORMAL = 1;

	// Variables miembro.
	private SoundPool reproductor;
	private int idDisparo;
	private int idExplosion;
	private Button btnDisparar;
	private Button btnExplosion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Obtengo las vistas.
		btnDisparar = (Button) this.findViewById(R.id.btnDisparar);
		btnExplosion = (Button) this.findViewById(R.id.btnExplosion);
		// Inicialmente los botones estarán deshabilitados hasta que los sonidos
		// correspondientes estén cargados en el reproductor.
		btnDisparar.setEnabled(false);
		btnExplosion.setEnabled(false);
		// Creo el objeto SoundPool con un límite de 8 sonidos simultáneos y
		// calidad estándar.
		reproductor = new SoundPool(8, AudioManager.STREAM_MUSIC,
				CALIDAD_NORMAL);
		// Cargo los ficheros de sonido (recibe el contexto, el recurso y la
		// prioridad estándar).
		idDisparo = reproductor.load(this, R.raw.disparo, PRIORIDAD_NORMAL);
		idExplosion = reproductor.load(this, R.raw.explosion, PRIORIDAD_NORMAL);
		// Cuando se termine de cargar el sonido, se activa el botón
		// correspondiente.
		reproductor.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				// Activo el botón correspondiente al sonido.
				if (sampleId == idDisparo) {
					btnDisparar.setEnabled(true);
				} else if (sampleId == idExplosion) {
					btnExplosion.setEnabled(true);
				}
			}
		});
	}

	// Al hacer click sobre btnDisparar.
	public void btnDispararOnClick(View v) {
		// Reproduzco el disparo.
		reproductor.play(idDisparo, VOLUMEN_MAX, VOLUMEN_MAX, PRIORIDAD_MAXIMA, SIN_BUCLE, VELOCIDAD_NORMAL);
	}

	// Al hacer click sobre btnExplosion.
	public void btnExplosionOnClick(View v) {
		// Reproduzco la explosión.
		reproductor.play(idExplosion, VOLUMEN_MAX, VOLUMEN_MAX, PRIORIDAD_MAXIMA, SIN_BUCLE, VELOCIDAD_NORMAL);
	}

	@Override
	protected void onDestroy() {
		// Libero los recursos del reproductor.
		if (reproductor != null) {
			reproductor.release();
			reproductor = null;
		}
		super.onDestroy();
	}
	
	
}
