package es.iessaladillo.pedrojoya.pr022;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnInitListener {

    // Constantes.
    private static final int SIN_SELECCION = -1;

    // Variables a nivel de clase.
    private Ejercicio ejercicio;
    private int mRespuestaSeleccionada = SIN_SELECCION;
    private TextToSpeech tts;
    private boolean mTTSPreparado = false;

    // Vistas.
    private RelativeLayout[] tarjetas;
    private Button btnCalificar;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se obtienen los datos del ejercicio.
        ejercicio = getEjercicio();
        // Se muestran los datos del ejercicio.
        showEjercicio();
        // La actividad actuará como listener cuando el sintetizador
        // de voz esté preparado.
        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        tarjetas = new RelativeLayout[Ejercicio.NUM_RESPUESTAS];
        tarjetas[0] = (RelativeLayout) findViewById(R.id.rlOpcion1);
        tarjetas[1] = (RelativeLayout) findViewById(R.id.rlOpcion2);
        tarjetas[2] = (RelativeLayout) findViewById(R.id.rlOpcion3);
        tarjetas[3] = (RelativeLayout) findViewById(R.id.rlOpcion4);
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            tarjetas[i].setOnClickListener(new TarjetaOnClickListener(i));
        }
        btnCalificar = (Button) findViewById(R.id.btnCalificar);
        btnCalificar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Se comprueba si es la respuesta correcta.
                checkRespuestaCorrecta();
            }
        });
    }

    // Obtiene los datos del ejercicio.
    private Ejercicio getEjercicio() {
        Respuesta[] respuestas = new Respuesta[] {
                new Respuesta("apple", R.drawable.manzana),
                new Respuesta("strawberry", R.drawable.fresa),
                new Respuesta("banana", R.drawable.banana),
                new Respuesta("pear", R.drawable.pear) };
        return new Ejercicio("manzana", respuestas, 0);
    }

    // Muestra los datos del ejercicio en las correspondientes vistas.
    private void showEjercicio() {
        ((TextView) findViewById(R.id.lblConcepto)).setText(ejercicio
                .getPregunta());
        Respuesta respuesta;
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            respuesta = ejercicio.getRespuesta(i);
            ((TextView) tarjetas[i].findViewById(R.id.lblOpcion))
                    .setText(respuesta.getTexto());
            ((ImageView) tarjetas[i].findViewById(R.id.imgOpcion))
                    .setImageResource(respuesta.getResIdImagen());
        }
    }

    private void checkRespuestaCorrecta() {
        if (mRespuestaSeleccionada == ejercicio.getCorrecta()) {
            mostrarTostadaLayout(this,
                    getString(R.string.tu_respuesta_es_correcta),
                    R.drawable.ic_ok, R.layout.toast_correcto,
                    Toast.LENGTH_SHORT);
        } else {
            mostrarTostadaLayout(this,
                    getString(R.string.lo_sentimos_la_respuesta_es_incorrecta),
                    R.drawable.ic_incorrect, R.layout.toast_incorrecto,
                    Toast.LENGTH_SHORT);
        }
    }

    // Al hacer click sobre btnToastDinamico.
    public void btnToastDinamicoOnClick(View v) {
        // Muestro un toast creado dinámicamente.
        mostrarTostada(R.string.toast_creado_dinamicamente,
                R.drawable.ic_launcher);
    }

    // Recrea el layout res/layout/transient_notification.xml de la
    // plataforma.
    private void mostrarTostada(int stringResId, int drawableResId) {
        // Obtengo el contexto.
        Context contexto = getApplicationContext();
        // Creo un objeto tostada.
        Toast tostada = new Toast(contexto);
        // Creo un LinearLayout como padre del layout para la tostada.
        LinearLayout padre = new LinearLayout(contexto);
        padre.setBackgroundResource(R.drawable.toast_frame);
        // Creo un TextView, le asigno el texto del mensaje y
        // el icono y se lo añado al LinearLayout.
        TextView texto = new TextView(contexto);
        texto.setText(stringResId);
        texto.setTextAppearance(contexto, android.R.style.TextAppearance_Small);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 0);
        texto.setLayoutParams(params);
        texto.setGravity(Gravity.CENTER);
        texto.setShadowLayer(2.75f, 0, 0, Color.parseColor("#BB000000"));
        texto.setCompoundDrawablesWithIntrinsicBounds(drawableResId, 0, 0, 0);
        texto.setCompoundDrawablePadding(10);
        padre.addView(texto);
        // Establezco el LinarLayout como la vista
        // que debe mostrar la tostada.
        tostada.setView(padre);
        // Establezco la duración de la tostada.
        tostada.setDuration(Toast.LENGTH_SHORT);
        // Muestro la tostada.
        tostada.show();
    }

    private void mostrarTostadaLayout(Context contexto, String mensaje,
            int resIdIcono, int resIdLayout, int duration) {
        // Se infla el layout para el toast.
        View v = LayoutInflater.from(contexto).inflate(resIdLayout, null);
        // Se obtienen las vistas y se establecen sus datos.
        TextView lblMensaje = (TextView) v.findViewById(R.id.lblMensaje);
        lblMensaje.setText(mensaje);
        lblMensaje.setCompoundDrawablesWithIntrinsicBounds(contexto
                .getResources().getDrawable(resIdIcono), null, null, null);
        // Se crea y se muestra el toast, estableciendo su vista.
        Toast toast = new Toast(contexto);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    // Al iniciarse el sintetizador de voz.
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                mTTSPreparado = true;
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    // Usa el sintetizador para leer en voz alta el texto recibid.
    private void leer(String texto) {
        if (mTTSPreparado) {
            tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private class TarjetaOnClickListener implements View.OnClickListener {

        // Propiedades.
        private int numTarjeta;

        // Constructor.
        public TarjetaOnClickListener(int numTarjeta) {
            this.numTarjeta = numTarjeta;
        }

        // Al hacer click.
        @Override
        public void onClick(View v) {
            for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
                boolean valor = (i == numTarjeta);
                ((RadioButton) tarjetas[i].findViewById(R.id.rbOpcion))
                        .setChecked(valor);
            }
            mRespuestaSeleccionada = numTarjeta;
            btnCalificar.setEnabled(true);
            leer(ejercicio.getRespuesta(numTarjeta).getTexto());
        }
    }

}
