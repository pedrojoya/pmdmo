package es.iessaladillo.pedrojoya.pr022;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnInitListener {

    // Constantes.
    private static final int SIN_SELECCION = -1;

    // Variables a nivel de clase.
    private Ejercicio mEjercicio;
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
        mEjercicio = BD.getRandomEjercicio();
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
                Log.d("quillo", "que");
                leer(lblConcepto.getText().toString(), new Locale("en", "GB"));
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
        // Se desmarca la tarjeta seleccionada del ejercicio anterior.
        desmarcarTarjeta(mRespuestaSeleccionada);
        mRespuestaSeleccionada = SIN_SELECCION;
        // Se escriben los datos en las vistas.
        lblConcepto.setText(mEjercicio.getPregunta());
        Respuesta respuesta;
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            respuesta = mEjercicio.getRespuesta(i);
            lblOpcion[i].setText(respuesta.getTexto());
            imgOpcion[i].setImageResource(respuesta.getResIdImagen());
            rbOpcion[i].setChecked(false);
        }
        // Hasta que no se haya seleccionado alguna repuesta no se puede
        // comprobar.
        btnCalificar.setEnabled(false);
        // Se lee el término (se hace con post para que se ejecute una vez
        // finalizada la ejecución del método onCreate().
        lblConcepto.post(new Runnable() {
            @Override
            public void run() {
                leer(lblConcepto.getText().toString(), new Locale("en", "GB"));
            }
        });
    }

    // Comprueba si la respuesta seleccionada es correcta o no.
    private void checkRespuestaCorrecta() {
        // Si la respuesta es correcta.
        if (mRespuestaSeleccionada == mEjercicio.getCorrecta()) {
            // Se informa al usuario.
            showToast(this, getString(R.string.tu_respuesta_es_correcta),
                    R.drawable.ic_ok, R.layout.toast_correcto,
                    Toast.LENGTH_SHORT);
            // Se obtiene y muestra un nuevo ejercicio.
            mEjercicio = BD.getRandomEjercicio();
            showEjercicio();
        } else {
            showToast(this,
                    getString(R.string.lo_sentimos_la_respuesta_es_incorrecta),
                    R.drawable.ic_incorrect, R.layout.toast_incorrecto,
                    Toast.LENGTH_SHORT);
        }
    }

    // Muestra un toast personalizado.
    private void showToast(Context contexto, String mensaje, int resIdIcono,
            int resIdLayout, int duration) {
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

    // Cuando ya está inicializado el sintetizador de voz.
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Se actualiza el indicador de que el sintetizador de voz.
            mTTSPreparado = true;
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    // Usa el sintetizador para leer en voz alta el texto recibido.
    private void leer(String texto, Locale loc) {
        // Si está preparado el sintetizador.
        if (mTTSPreparado) {
            // Se establece el idioma y si hay algún problema se indica.
            int result = tts.setLanguage(loc);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "El idioma indicado no está disponible");
            } else {
                // Si todo ha ido bien, se realiza la lectura.
                tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    // Clase Listener para las tarjetas.
    private class TarjetaOnClickListener implements View.OnClickListener {

        // Propiedades.
        private int numTarjeta;

        // Constructor.
        public TarjetaOnClickListener(int numTarjeta) {
            this.numTarjeta = numTarjeta;
        }

        // Al hacer click sobre la tarjeta.
        @Override
        public void onClick(View v) {
            // Se le quita la selección a la tarjeta que estuviera seleccionada.
            desmarcarTarjeta(mRespuestaSeleccionada);
            // Se selecciona la tarjeta sobre la que se ha pulsado.
            marcarTarjeta(numTarjeta);
            // Se activa el botón de comprobación (ahora que se ha seleccionado
            // alguna tarjeta).
            btnCalificar.setEnabled(true);
        }

    }

    // Desmarca una tarjeta que estaba seleccionada.
    private void desmarcarTarjeta(int numTarjeta) {
        if (numTarjeta >= 0) {
            // Se quita el negrita del texto.
            lblOpcion[numTarjeta].setTypeface(null, Typeface.NORMAL);
            // Se desmarca el RadioButton.
            rbOpcion[numTarjeta].setChecked(false);
            // Se realiza la animación de la tarjeta.
            Animation disminuir = AnimationUtils.loadAnimation(
                    MainActivity.this, R.anim.tarjeta_disminuir);
            tarjetas[numTarjeta].startAnimation(disminuir);
        }
    }

    // Marca una tarjeta como seleccionada.
    private void marcarTarjeta(int numTarjeta) {
        // Se actualiza la respuesta seleccionada.
        mRespuestaSeleccionada = numTarjeta;
        // Se pone en negrita el texto.
        lblOpcion[numTarjeta].setTypeface(null, Typeface.BOLD);
        // Se selecciona el RadioButton.
        rbOpcion[numTarjeta].setChecked(true);
        // Se realiza la animación de la tarjeta.
        Animation agrandar = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.tarjeta_agrandar);
        tarjetas[numTarjeta].startAnimation(agrandar);
    }
}
