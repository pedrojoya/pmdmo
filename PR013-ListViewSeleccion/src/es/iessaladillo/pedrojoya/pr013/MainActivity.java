package es.iessaladillo.pedrojoya.pr013;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
        OnItemClickListener {

    // Vistas.
    private ListView lstRespuestas;
    private TextView lblContador;
    private Button btnComprobar;
    private View vContador;

    // Variables.
    private ObjectAnimator mAnimador;
    private ArrayAdapter<String> mAdaptador;
    private int mPuntuacion = 100;

    // Constantes.
    private long CONTADOR_INICIAL = 5000;
    private long CONTADOR_INTERVALO = 1000;
    private TextView lblPuntuacion;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Al mostrar la actividad.
    @Override
    protected void onResume() {
        // Se anima el fondo del contador.
        animarFondoContador();
        // Se inicia la cuenta atrás.
        CountDownTimer contador = new CountDownTimer(CONTADOR_INICIAL,
                CONTADOR_INTERVALO) {
            // Cuando pasa un intervalo
            public void onTick(long millisUntilFinished) {
                // Se actualiza el TextView con el valor del contador.
                lblContador.setText((millisUntilFinished / 1000) + "");
            }

            // Cuando la cuenta atrás llega a su fin.
            public void onFinish() {
                // Se finaliza la animación del fono del contador.
                mAnimador.end();
                // Se hace visible el botón de comprobación y se oculta el
                // contador.
                btnComprobar.setVisibility(View.VISIBLE);
                lblContador.setVisibility(View.GONE);
                vContador.setVisibility(View.GONE);
            }
        }.start();
        super.onResume();
    }

    // Inicia la animación de rotación del fondo del contador.
    private void animarFondoContador() {
        mAnimador = ObjectAnimator.ofFloat(vContador, View.ROTATION, 0.0f,
                (CONTADOR_INICIAL / 1000) * 360.0f);
        mAnimador.setDuration(CONTADOR_INICIAL);
        mAnimador.setRepeatMode(ObjectAnimator.RESTART);
        mAnimador.setRepeatCount(ObjectAnimator.INFINITE);
        mAnimador.setInterpolator(new LinearInterpolator());
        mAnimador.start();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        vContador = (View) findViewById(R.id.vContador);
        lblPuntuacion = (TextView) findViewById(R.id.lblPuntuacion);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        // La actividad actuará como listener cuando se pulse el botón.
        btnComprobar.setOnClickListener(this);
        lblContador = (TextView) findViewById(R.id.lblContador);
        lstRespuestas = (ListView) this.findViewById(R.id.lstRespuestas);
        // Se crea y asigna el adaptador a la lista.
        ArrayList<String> respuestas = new ArrayList<String>();
        respuestas.add("Marrón");
        respuestas.add("Verde");
        respuestas.add("Blanco");
        respuestas.add("Negro");
        mAdaptador = new ArrayAdapter<String>(this, R.layout.respuesta,
                R.id.lblRespuesta, respuestas);
        lstRespuestas.setAdapter(mAdaptador);
        // Se trata de una lista de selección simple.
        lstRespuestas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // La actividad actuará como listener cuando se pulse sobre un
        // elemento de la lista.
        lstRespuestas.setOnItemClickListener(this);
    }

    // Cuando se pulsa el botón.
    @Override
    public void onClick(View arg0) {
        // Se obtiene el elemento seleccionado.
        int posSeleccionado = lstRespuestas.getCheckedItemPosition();
        String elemSeleccionado = (String) lstRespuestas
                .getItemAtPosition(posSeleccionado);
        // Se comprueba si la respuesta es correcta.
        if (TextUtils.equals(elemSeleccionado, "Blanco")) {
            // Se muestra la puntuación.
            lblPuntuacion.setText("+ " + mPuntuacion);
            lblPuntuacion.setBackgroundResource(R.drawable.puntuacion_fondo);
            animarPuntuacion();
        } else {
            // Se quita la puntuación.
            int disminucion = mPuntuacion == 100 ? 50 : 25;
            mPuntuacion -= disminucion;
            lblPuntuacion.setText("- " + disminucion);
            lblPuntuacion
                    .setBackgroundResource(R.drawable.puntuacion_fondo_incorrecto);
            animarPuntuacion();
            // Se quita la selección.
            lstRespuestas.setItemChecked(posSeleccionado, false);
            // Se elimina dicha respuesta del adaptador y se fuerza
            // el repintado de la lista.
            mAdaptador.remove(elemSeleccionado);
            mAdaptador.notifyDataSetChanged();
            // Se desactiva el botón.
            btnComprobar.setEnabled(false);
        }

    }

    private void animarPuntuacion() {
        lblPuntuacion.setVisibility(View.VISIBLE);
        lblPuntuacion.animate().scaleX(1.5f).scaleY(1.5f).translationY(50)
                .setDuration(3000).setInterpolator(new BounceInterpolator())
                .setListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recolocarPuntuacion();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                });
    }

    private void recolocarPuntuacion() {
        lblPuntuacion.setVisibility(View.INVISIBLE);
        lblPuntuacion.setScaleX(1);
        lblPuntuacion.setScaleY(1);
        lblPuntuacion.setTranslationX(0);
        lblPuntuacion.setTranslationY(0);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        btnComprobar.setEnabled(true);
    }

}