package es.iessaladillo.pedrojoya.pr022;

import java.util.Locale;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnInitListener,
        AnimationListener {

    // Clase interna para almacenar el estado de la actividad.
    private class Estado {
        public Ejercicio ejercicio;
        public int numRespuestaSeleccionada = SIN_SELECCION;
        public boolean[] comprobadas = { false, false, false, false };
    }

    // Constantes.
    private static final int SIN_SELECCION = -1;
    private static final long INTERVALO_ENTRE_EJERCICIOS_MS = 1000;

    // Variables a nivel de clase.
    private Estado mEstado;
    private TextToSpeech mTTS;
    private boolean mTTSPreparado = false;
    private Animation flipToMiddle;
    private Animation flipFromMiddle;

    // Vistas.
    private RelativeLayout[] tarjetas;
    private Button btnComprobar;
    private TextView lblConcepto;
    private ImageView[] imgOpcion;
    private TextView[] lblOpcion;
    private RadioButton[] rbOpcion;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se recupera el estado anterior.
        mEstado = (Estado) getLastNonConfigurationInstance();
        if (mEstado == null) {
            // Se crea el objeto de estado.
            mEstado = new Estado();
            // Se obtienen los datos del ejercicio.
            mEstado.ejercicio = BD.getRandomEjercicio();
        }
        // La actividad actuará como listener cuando el sintetizador
        // de voz esté preparado.
        mTTS = new TextToSpeech(this, this);
        // Se cargan las animaciones de comprobación.
        flipToMiddle = AnimationUtils.loadAnimation(this,
                R.anim.tarjeta_flip_to_middle);
        flipToMiddle.setAnimationListener(this);
        flipFromMiddle = AnimationUtils.loadAnimation(this,
                R.anim.tarjeta_flip_from_middle);
        flipFromMiddle.setAnimationListener(this);
    }

    // Cuando la actividad pasa a primer plano.
    @Override
    protected void onResume() {
        logScreenSizeDPI();
        // Se muestran los datos del ejercicio.
        showEjercicio();
        super.onResume();
    }

    // Al destruir la actividad.
    @Override
    public void onDestroy() {
        // Se para y apaga el sintetizador de voz.
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
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
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        btnComprobar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Se comprueba si es la respuesta correcta.
                checkRespuestaCorrecta();
            }
        });
    }

    // Muestra los datos del ejercicio en las correspondientes vistas.
    private void showEjercicio() {
        // Se escriben los datos en las vistas.
        lblConcepto.setText(mEstado.ejercicio.getPregunta());
        Respuesta respuesta;
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            respuesta = mEstado.ejercicio.getRespuesta(i);
            lblOpcion[i].setText(respuesta.getTexto());
            imgOpcion[i].setImageResource(respuesta.getResIdImagen());
            rbOpcion[i].setChecked(false);
        }
        // Se marca la opción seleccionada (por si venimos de estado anterior).
        if (mEstado.numRespuestaSeleccionada >= 0) {
            marcarTarjeta(mEstado.numRespuestaSeleccionada);
        }
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
        // Se guarda que se ha comprobado la opción.
        mEstado.comprobadas[mEstado.numRespuestaSeleccionada] = true;
        // Se desactiva el botón de comprobación.
        btnComprobar.setEnabled(false);
        // Se realiza la animación inicial.
        tarjetas[mEstado.numRespuestaSeleccionada].startAnimation(flipToMiddle);
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
            int result = mTTS.setLanguage(loc);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "El idioma indicado no está disponible");
            } else {
                // Si todo ha ido bien, se realiza la lectura.
                mTTS.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    // Al cambiar la configuración.
    @Override
    @Deprecated
    public Object onRetainNonConfigurationInstance() {
        // Se preserva el objeto de estado.
        return mEstado;
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
        // Se le quita la selección a la tarjeta que estuviera seleccionada.
        desmarcarTarjeta(mEstado.numRespuestaSeleccionada);
        // Se actualiza la respuesta seleccionada.
        mEstado.numRespuestaSeleccionada = numTarjeta;
        // Se pone en negrita el texto.
        lblOpcion[numTarjeta].setTypeface(null, Typeface.BOLD);
        // Se selecciona el RadioButton.
        rbOpcion[numTarjeta].setChecked(true);
        // Se realiza la animación de la tarjeta.
        Animation agrandar = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.tarjeta_agrandar);
        tarjetas[numTarjeta].startAnimation(agrandar);
        // Se activa el botón de comprobación (ahora que se ha seleccionado
        // alguna tarjeta).
        btnComprobar.setEnabled(true);

    }

    // Resetea el estado y la IU para poder mostrar un nuevo ejercicio.
    private void resetEjercicio() {
        // Se desmarca la tarjeta seleccionada del ejercicio anterior.
        desmarcarTarjeta(mEstado.numRespuestaSeleccionada);
        // Se establece que no hay ninguna seleccionada.
        mEstado.numRespuestaSeleccionada = SIN_SELECCION;
        // Se establece que no se ha comprobado ninguna opción.
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            mEstado.comprobadas[i] = false;
        }
        // Hasta que no se haya seleccionado alguna repuesta no se puede
        // comprobar.
        btnComprobar.setEnabled(false);
    }

    // Escribe un log con la densidad, la anchura y la altura disponible en dpi.
    private void logScreenSizeDPI() {
        final int BASE_DPI = 160;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float densidadDPI = metrics.density * BASE_DPI;
        float anchuraDPI = metrics.widthPixels / metrics.density;
        float alturaDPI = metrics.heightPixels / metrics.density;
        Log.d("metrics", "Densidad: " + densidadDPI + "dpi\nAnchura: "
                + anchuraDPI + "dpi\nAltura: " + alturaDPI);
    }

    // Cuando termina de ejecutarse una animación.
    @Override
    public void onAnimationEnd(Animation animation) {
        // Si ha finalizado la primera animación del flip.
        if (animation == flipToMiddle) {
            // Se cambia la imagen y el texto de la tarjeta dependiendo de si ha
            // acertado o no.
            if (mEstado.numRespuestaSeleccionada == mEstado.ejercicio
                    .getCorrecta()) {
                imgOpcion[mEstado.numRespuestaSeleccionada]
                        .setImageResource(R.drawable.correcto);
            } else {
                imgOpcion[mEstado.numRespuestaSeleccionada]
                        .setImageResource(R.drawable.incorrecto);
            }
            // Se realiza la segunda animación.
            tarjetas[mEstado.numRespuestaSeleccionada].clearAnimation();
            tarjetas[mEstado.numRespuestaSeleccionada]
                    .startAnimation(flipFromMiddle);
        }
        // Si ha finalizado la segunda animación del flip.
        else if (animation == flipFromMiddle) {
            // Si ha acertado.
            if (mEstado.numRespuestaSeleccionada == mEstado.ejercicio
                    .getCorrecta()) {
                // Se espera un segundo y se muestra otro ejercicio.
                btnComprobar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Se resetea el ejercicio.
                        resetEjercicio();
                        // Se obtiene y muestra un nuevo ejercicio.
                        mEstado.ejercicio = BD.getRandomEjercicio();
                        showEjercicio();
                    }
                }, INTERVALO_ENTRE_EJERCICIOS_MS);
            }
            // Si no ha acertado.
            else {
                // Si sólo queda la tarjeta correcta comprobar, se marca y se
                // comprueba automáticamente.
                if (soloQuedaCorrecta()) {
                    marcarTarjeta(mEstado.ejercicio.getCorrecta());
                    checkRespuestaCorrecta();
                } else {
                    // Se habilita el botón de comprobación.
                    btnComprobar.setEnabled(true);
                }
            }
        }
    }

    // Retorna si solo queda por marcar la tarjeta correcta.
    private boolean soloQuedaCorrecta() {
        for (int i = 0; i < Ejercicio.NUM_RESPUESTAS; i++) {
            if (i != mEstado.ejercicio.getCorrecta() && !mEstado.comprobadas[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onAnimationRepeat(Animation arg0) {
    }

    @Override
    public void onAnimationStart(Animation arg0) {
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
            // Se selecciona la tarjeta sobre la que se ha pulsado.
            marcarTarjeta(numTarjeta);
        }

    }
}
