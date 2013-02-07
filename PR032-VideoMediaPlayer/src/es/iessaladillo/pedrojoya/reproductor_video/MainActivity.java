package es.iessaladillo.pedrojoya.reproductor_video;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnPreparedListener, OnCompletionListener {

	// Variables a nivel de clase.
    private SurfaceView swVisor;
	private SurfaceHolder ventanaReproduccion;
	private EditText txtPath;
	private SeekBar skbBarra;
	private MediaPlayer reproductor;
	private boolean enPausa = false;
	private final Handler manejador = new Handler();
	private Runnable notificacion;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	// Llamo al onCreate del padre.
        super.onCreate(savedInstanceState);
        // Establezco el layout de la actividad.
        setContentView(R.layout.main);
        // Obtengo la referencia a las vistas.
        getVistas();
        // Cuando se desplaza la seekbar.
        skbBarra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Si ha sido el usuario el que ha cambiado la barra.
				if (fromUser && reproductor.isPlaying()) {
					// Coloco el reproductor en esa posición.
					reproductor.seekTo(seekBar.getProgress());
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }
    
    // Obtiene las referencias a las vistas del layout.
    private void getVistas() {
    	txtPath = (EditText) findViewById(R.id.txtPath);
    	swVisor = (SurfaceView) this.findViewById(R.id.swVisor);
		// Obtengo la ventana de reproducción.
		ventanaReproduccion = swVisor.getHolder();
		/* Para vídeo y cámara es necesario que sea del tipo PUSH_BUFFERS.
		 * TIENE QUE PONERSE DESPUÉS DL ADDCALLBACK.
		 */
    	ventanaReproduccion.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    	skbBarra = (SeekBar) this.findViewById(R.id.skbBarra);
    }

    // Al hacer click en el botón btnPlay.
    public void btnPlayOnClick(View v) {
			// Si es una nueva reproducción, la inicio.
			prepararReproductor();
    }
    
    // Al hacer click en el botón btnPause.
	public void btnPauseOnClick(View v) {
		if (reproductor != null) {
			if (!enPausa) {
				// Si no estaba en modo pausa, pauso el reproductor.
				reproductor.pause();
				enPausa = true;
			}
			else {
				/* Si ya estaba en modo pausa continuo la reproducción 
				 * (dejando el modo pausa).
				 */
				reproductor.start();
				actualizarProgreso();
				enPausa = false;
			}
		}
		
	}

	// Al hacer click en el botón btnStop.
	public void btnStopOnClick(View v) {
		if (reproductor != null) {
			// Paro la reproducción y en cualquier caso dejo el modo Pausa.
			reproductor.stop();
			enPausa = false;
			skbBarra.setProgress(0);
			manejador.removeCallbacks(notificacion);
		}
	}

	// Prepara al reproductor para poder reproducir.
	private void prepararReproductor() {
    	// Obtengo la ruta.
    	String ruta = txtPath.getText().toString();
    	if (!("".equals(ruta))) {
			// Creo el objeto MediaPlayer.
	    	if (reproductor != null) {
	    		reproductor.reset();
	    		reproductor.release();
	    		reproductor = null;
	    	}
	    	reproductor = new MediaPlayer();
	    	try {
	        	// Establezco la ruta del fichero a reproducir en el reproductor.
				reproductor.setDataSource(ruta);
				// Establezco el stream de audio que utilizará el reproductor. 
				reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
				/* Cuando ya esté preparado el reproductor se generará un evento OnPrepared
				 * que deberá ser gestionado por un Listener.
				 * Indico que será la propia actividad quien implementará la interfaz
				 * del Listener y gestionará el evento.
				 */
				reproductor.setOnPreparedListener(this);
				reproductor.setOnCompletionListener(this);
				// Realizo la inicialización (preparación) del reproductor.
				//reproductor.prepare();	// síncrona.
				reproductor.prepareAsync(); // asíncrona.
				// Establezco la ventana de reproducción que utilizará el reproductor.
				reproductor.setDisplay(ventanaReproduccion);
			} catch (Exception e) {
				Log.d("Reproductor", "ERROR: " + e.getMessage());
			}
    	}
	}

    // Cuando el reproductor ya está preparado para reproducir.
	@Override
	public void onPrepared(MediaPlayer repr) {
		// Obtengo la anchura y altura del vídeo y de la pantalla.
		int anchuraVideo = repr.getVideoWidth();
		int alturaVideo = repr.getVideoHeight();
		int anchuraVisor = swVisor.getWidth();
		int alturaVisor = swVisor.getHeight();
		// Calculo las proporciones
		float proporcionAnchura = (float) anchuraVisor / (float) anchuraVideo; 
		float proporcionAltura = (float) alturaVisor / (float) alturaVideo;
		float proporcionAspecto = anchuraVideo / alturaVideo;
		// Obtenemos los parámetros de anchura y altura de la ventana de reproducción.
		LayoutParams lp = swVisor.getLayoutParams();
		/* Si la proporción de ancho es más grande que la proporción de alto es 
		 * porque el visor es más ancho que alto y por tanto pondremos el alto 
		 * de la ventana al alto del visor y el ancho lo calculamos en base a esta altura, 
		 * aplicándole la proporción de aspecto.
		 */
		if (proporcionAnchura > proporcionAltura) {
			lp.height = alturaVisor;
			lp.width = (int) (alturaVisor * proporcionAspecto);
		}
		else {
			lp.width = anchuraVisor;
			lp.height = (int) (anchuraVisor / proporcionAspecto);
		}
		// Le asigno los parámetros al visor.
		swVisor.setLayoutParams(lp);	
		// Establezo el máximo de la seekbar.
		skbBarra.setMax(reproductor.getDuration());
		skbBarra.setProgress(0);
		// No estoy en modo pausa.
		enPausa = false;
		// Comienzo la reproducción.
		repr.start();
		// Inicio el hilo de notificación para actualizar la barra.
		actualizarProgreso();
	}

	// Actualiza la barra en base al pogreso del contenido del mediaplayer.
	private void actualizarProgreso() {
		// Actualizo la posición en la barra.
		skbBarra.setProgress(reproductor.getCurrentPosition());
		// Si está reproduciéndose.
		if (reproductor.isPlaying()) {
			// Creo un hilo de notificación para que se ejecuta de nuevo.
			notificacion = new Runnable() {
				public void run() {
					actualizarProgreso();
				}
			};
			// Hago que el manejador ejecute el hilo de notificación trás un segundo.
			manejador.postDelayed(notificacion, 50);
		}
		// Si ya se ha terminado de reproducir.
		else {
			if (!enPausa) {
				// Pongo la barra al principio.
				skbBarra.setProgress(0);
			}
		}		
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// Reinicio la barra.
		skbBarra.setProgress(0);
		// Cancelo los mensajes al hilo de notificación.
		manejador.removeCallbacks(notificacion);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Libero los recursos del reproductor y el propio objeto.
		if (reproductor != null) {
			reproductor.release();
			reproductor = null;
		}
		// Dejo de enviar mensajes al hilo de notificación.
		manejador.removeCallbacks(notificacion);
	}

	@Override
	protected void onResume() {
		super.onResume();
		skbBarra.setProgress(0);
	}

}