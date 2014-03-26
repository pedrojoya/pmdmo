package es.iessaladillo.pedrojoya.pr091.servicios;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import es.iessaladillo.pedrojoya.pr091.modelos.Cancion;

public class MusicaOnlineService extends Service implements
        OnCompletionListener, OnPreparedListener {

    // Constantes.
    public static String EXTRA_POS_ACTUAL = "posActual";
    public static String ACTION_REPRODUCIENDO = "es.iessaladillo.pedrojoya.pr089.action_reproduciendo";

    // Variables.
    private MediaPlayer reproductor;
    private LocalBinder binder;
    private ArrayList<Cancion> canciones;
    private int posActual;

    // Clase que actúa como Binder con el servicio.
    public class LocalBinder extends Binder {
        // Retorna la instancia del servicio para que el cliente pueda
        // llamar a sus métodos públicos.
        public MusicaOnlineService getService() {
            return MusicaOnlineService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea el binder con el que se vinculará la actividad.
        binder = new LocalBinder();
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

    public void reproducirCancion(int position) {
        if (canciones != null) {
            posActual = position;
            reproducirCancion(canciones.get(position).getUrl());
            enviarBroadcast();
        }
    }

    private void enviarBroadcast() {
        // Se envía un intent de respuesta al receptor
        Intent intentRespuesta = new Intent(ACTION_REPRODUCIENDO);
        intentRespuesta.putExtra(EXTRA_POS_ACTUAL, posActual);
        // El intent será recibido por un Receiver local registrado en el gestor
        // para dicha acción.
        LocalBroadcastManager gestor = LocalBroadcastManager.getInstance(this);
        gestor.sendBroadcast(intentRespuesta);
    }

    public void reproducirCancion(String url) {
        // Se prepara la reproducción de la canción.
        if (reproductor != null) {
            reproductor.reset();
            reproductor.setLooping(false);
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            reproductor.setOnPreparedListener(this);
            reproductor.setOnCompletionListener(this);
            try {
                reproductor.setDataSource(url);
                reproductor.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Se retorna el binder con el servicio.
        return binder;
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        // Se inicia la reproducción.
        reproductor.start();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        siguienteCancion();
    }

    // Reproduce la siguiente canción a la actual.
    public void siguienteCancion() {
        if (canciones != null) {
            reproducirCancion((posActual + 1) % canciones.size());
        }
    }

    // Reproduce la anterior canción a la actual.
    public void anteriorCancion() {
        if (canciones != null) {
            int anterior = posActual - 1;
            if (anterior < 0) {
                anterior = canciones.size() - 1;
            }
            reproducirCancion(anterior);
        }
    }

    // Pausa la reproducción.
    public void pausarReproduccion() {
        reproductor.pause();
    }

    // Para la reproducción.
    public void pararReproduccion() {
        reproductor.stop();
    }

    // Establece la lista de canciones.
    public void setLista(ArrayList<Cancion> list) {
        canciones = list;
    }

    // Limpia la lista de canciones.
    public void limpiarLista() {
        canciones = null;
    }

    // Agrega una canción a la lista de canciones.
    public void agregarCancion(Cancion cancion) {
        if (canciones == null) {
            canciones = new ArrayList<Cancion>();
        }
        canciones.add(cancion);
    }

    public int getPosCancionActual() {
        return posActual;
    }

}