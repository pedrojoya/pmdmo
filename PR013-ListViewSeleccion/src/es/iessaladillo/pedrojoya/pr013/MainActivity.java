package es.iessaladillo.pedrojoya.pr013;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
        OnItemClickListener {

    // Variables a nivel de clase.
    private ListView lstRespuestas;
    private TextView lblContador;
    private ObjectAnimator animator;
    private Button btnComprobar;
    private View vContador;
    private ArrayAdapter<String> adaptador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Llamo al onCreate del padre.
        super.onCreate(savedInstanceState);
        // Establezco el layout que mostrará la actividad.
        setContentView(R.layout.activity_main);
        // Obtengo las vistas.
        getVistas();
    }

    @Override
    protected void onResume() {
        vContador = (View) findViewById(R.id.vContador);
        animator = ObjectAnimator.ofFloat(vContador, View.ROTATION, 0.0f,
                10 * 360.0f);
        animator.setDuration(30000);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        CountDownTimer contador = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                lblContador.setText((millisUntilFinished / 1000) + "");
            }

            public void onFinish() {
                animator.end();
                btnComprobar.setVisibility(View.VISIBLE);
                lblContador.setVisibility(View.GONE);
                vContador.setVisibility(View.GONE);
            }
        }.start();

        super.onResume();
    }

    private void getVistas() {
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        btnComprobar.setOnClickListener(this);
        lblContador = (TextView) findViewById(R.id.lblContador);
        lstRespuestas = (ListView) this.findViewById(R.id.lstRespuestas);
        ArrayList<String> respuestas = new ArrayList<String>();
        respuestas.add("Marrón");
        respuestas.add("Verde");
        respuestas.add("Blanco");
        respuestas.add("Negro");
        adaptador = new ArrayAdapter<String>(this, R.layout.respuesta,
                R.id.lblRespuesta, respuestas);
        lstRespuestas.setAdapter(adaptador);
        lstRespuestas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstRespuestas.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        int posSeleccionado = lstRespuestas.getCheckedItemPosition();
        String elemSeleccionado = (String) lstRespuestas
                .getItemAtPosition(posSeleccionado);
        if (TextUtils.equals(elemSeleccionado, "Blanco")) {
            Toast.makeText(this, "Bien", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mal", Toast.LENGTH_SHORT).show();
            lstRespuestas.setItemChecked(posSeleccionado, false);
            adaptador.remove(elemSeleccionado);
            adaptador.notifyDataSetChanged();
            btnComprobar.setEnabled(false);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        btnComprobar.setEnabled(true);
    }

}