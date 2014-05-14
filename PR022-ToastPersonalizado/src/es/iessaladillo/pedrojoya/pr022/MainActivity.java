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
    private TextView lblConcepto;
    private ImageView[] imgOpcion;
    private TextView[] lblOpcion;
    private RadioButton[] rbOpcion;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se oculta la action bar.
        getActionBar().hide();
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se obtienen los datos del ejercicio.
        ejercicio = BD.getRandomEjercicio();
        // Se muestran los datos del ejercicio.
        showEjercicio();
        // La actividad actuará como listener cuando el sintetizador
        // de voz esté preparado.
        tts = new TextToSpeech(this, this);
    }

    // Al destruir la actividad.
    @Override
    public void onDestroy() {
        // Se para y apaga el sintetizador de voz.
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        lblConcepto = (TextView) findViewById(R.id.lblConcepto);
        lblConcepto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Se lee el concepto en inglés.
                leer(lblConcepto.getText().toString(), Locale.ENGLISH);
            }
        });
        tarjetas = new RelativeLayout[Ejercicio.NUM_RESPUESTAS];
        imgOpcion = new ImageView[Ejercicio.NUM_RESPUESTAS];
        lblOpcion = new TextView[Ejercicio.NUM_RESPUESTAS];
        rbOpcion = new RadioButton[Ejercicio.NUM_RESPUESTAS];
        tarjetas[0] = (RelativeLayout) findViewById(R.id.rlOpcion1);
        tarjetas[1] = (RelativeLayout) findViewById(R.id.rlOpcion2);
        tarjetas[2] = (RelativeLayout) findViewById(R.id.rlOpcion3);
        tarjetas[3] = (RelativeLayout) findViewById(R.id.rlOpcion4);
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            tarjetas[i].setOnClickListener(new TarjetaOnClickListener(i));
            imgOpcion[i] = (ImageView) tarjetas[i].findViewById(R.id.imgOpcion);
            lblOpcion[i] = (TextView) tarjetas[i].findViewById(R.id.lblOpcion);
            rbOpcion[i] = (RadioButton) tarjetas[i].findViewById(R.id.rbOpcion);
        }
        btnCalificar = (Button) findViewById(R.id.btnComprobar);
        btnCalificar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Se comprueba si es la respuesta correcta.
                checkRespuestaCorrecta();
            }
        });
    }

    // Muestra los datos del ejercicio en las correspondientes vistas.
    private void showEjercicio() {
        lblConcepto.setText(ejercicio.getPregunta());
        Respuesta respuesta;
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            respuesta = ejercicio.getRespuesta(i);
            lblOpcion[i].setText(respuesta.getTexto());
            imgOpcion[i].setImageResource(respuesta.getResIdImagen());
            rbOpcion[i].setChecked(false);
        }
        btnCalificar.setEnabled(false);
    }

    private void checkRespuestaCorrecta() {
        if (mRespuestaSeleccionada == ejercicio.getCorrecta()) {
            mostrarTostadaLayout(this,
                    getString(R.string.tu_respuesta_es_correcta),
                    R.drawable.ic_ok, R.layout.toast_correcto,
                    Toast.LENGTH_SHORT);
            // leer(getString(R.string.tu_respuesta_es_correcta), new
            // Locale("es",
            // "ES"));
            ejercicio = BD.getRandomEjercicio();
            showEjercicio();
        } else {
            mostrarTostadaLayout(this,
                    getString(R.string.lo_sentimos_la_respuesta_es_incorrecta),
                    R.drawable.ic_incorrect, R.layout.toast_incorrecto,
                    Toast.LENGTH_SHORT);
            // leer(getString(R.string.lo_sentimos_la_respuesta_es_incorrecta),
            // new Locale("es", "ES"));
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
            mTTSPreparado = true;
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    // Usa el sintetizador para leer en voz alta el texto recibid.
    private void leer(String texto, Locale loc) {
        if (mTTSPreparado) {
            int result = tts.setLanguage(loc);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
            }
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
                rbOpcion[i].setChecked(i == numTarjeta);
            }
            mRespuestaSeleccionada = numTarjeta;
            btnCalificar.setEnabled(true);
            leer(ejercicio.getRespuesta(numTarjeta).getTexto(), new Locale(
                    "es", "ES"));
        }
    }

}
