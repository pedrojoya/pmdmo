package es.iessaladillo.pedrojoya.pr089.servicios;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;

public class MusicaOnlineService extends Service implements
        OnCompletionListener, OnPreparedListener {

    public static final String EXTRA_URL_CANCION = "url";
    public static final String ACTION_COMPLETADA = "es.iessaladillo.pedrojoya.pr089.action_completada";
    public static final String EXTRA_PACKAGE_NAME = "package_name";
    public static final String EXTRA_RESPUESTA = "respuesta";

    private MediaPlayer reproductor;
    private String packageName;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea y configura el reproductor.
        reproductor = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        // Se para la reproducción y se liberan los recursos.
        if (reproductor != null) {
            reproductor.stop();
            reproductor.release();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Se obtiene el nombre del paquete.
        packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME);
        // Se prepara la reproducción de la canción.
        if (reproductor != null) {
            reproductor.reset();
            reproductor.setLooping(false);
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            reproductor.setOnPreparedListener(this);
            reproductor.setOnCompletionListener(this);
            String urlCancion = intent.getStringExtra(EXTRA_URL_CANCION);
            try {
                reproductor.setDataSource(urlCancion);
                reproductor.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // El servicio NO se reiniciará automáticamente y es matado por el
        // sistema.
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // El servicio NO es vinculado.
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        // Se inicia la reproducción.
        reproductor.start();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        // Se envía un intent de respuesta al receptor, indicando el paquete
        // para que no lo puedan recibir componentes de otras aplicaciones.
        Intent intentRespuesta = new Intent(ACTION_COMPLETADA);
        intentRespuesta.putExtra(EXTRA_RESPUESTA, "Canción finalizada");
        intentRespuesta.setPackage(packageName);
        sendBroadcast(intentRespuesta);
        // Se finaliza el servicio.
        stopSelf();
    }
}
