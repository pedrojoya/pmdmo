package es.iessaladillo.pedrojoya.pr013;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    // Variables a nivel de clase.
    private ListView lstRespuestas;
    private TextView lblCuentaAtras;

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
        CountDownTimer contador = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                lblCuentaAtras.setText((millisUntilFinished / 1000) + "");
            }

            public void onFinish() {
                lblCuentaAtras.setText("Fin");
            }
        }.start();

        super.onResume();
    }

    private void getVistas() {
        ((Button) findViewById(R.id.btnComprobar)).setOnClickListener(this);
        lblCuentaAtras = (TextView) findViewById(R.id.lblCuentaAtras);
        lstRespuestas = (ListView) this.findViewById(R.id.lstRespuestas);
        String[] respuestas = new String[] { "Marrón", "Verde", "Blanco",
                "Negro" };
        lstRespuestas.setAdapter(new ArrayAdapter<String>(this,
                R.layout.respuesta, R.id.lblRespuesta, respuestas));
        lstRespuestas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }
}