package es.iessaladillo.pedrojoya.encuesta;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class EncuestaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        // Se muestra el fragmento.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new EncuestaFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encuesta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Fragmento de la encuesta.
    public static class EncuestaFragment extends Fragment implements
            OnClickListener, OnCheckedChangeListener {

        // Variables.
        private int[] rbIds = { R.id.rbOpcion1, R.id.rbOpcion2, R.id.rbOpcion3,
                R.id.rbOpcion4 };
        private int mNumPregunta = 0;
        private String[] mPreguntas;
        private int[] mRespuestas;

        // Vistas.
        private RadioGroup rgRespuesta;

        private ImageButton btnSiguiente;

        private ImageButton btnAnterior;
        private TextView lblPregunta;

        // Constructor.
        public EncuestaFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            // El fragmento retendrá toda su información entre cambios de
            // configuración.
            setRetainInstance(true);
            mPreguntas = new String[3];
            mRespuestas = new int[3];
            mPreguntas[0] = "1.- Comienza y acaba puntualmente las clases";
            mPreguntas[1] = "2.- En sus explicaciones se ajusta bien al nivel de conocimiento de los estudiantes";
            mPreguntas[2] = "3.- Las clases están bien preparadas, organizadas y estructuradas";
            for (int i = 0; i < 3; i++) {
                mRespuestas[i] = 0;
            }
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_encuesta,
                    container, false);
            getVistas(rootView);
            mostrarPregunta(mNumPregunta);
            return rootView;
        }

        // Muestra una pregunta.
        private void mostrarPregunta(int numPregunta) {
            // Se guarda el número de pregunta en la que estamos.
            mNumPregunta = numPregunta;
            // Se muestra la respuesta (si ya hubiera una).
            int opcion = mRespuestas[mNumPregunta] - 1;
            rgRespuesta.check(opcion < 0 ? -1 : rbIds[opcion]);
            // Se comprueba la activación de los botones Siguiente y
            // Anterior.
            checkSiguiente();
            checkAnterior();
            // Se escribe la pregunta.
            lblPregunta.setText(mPreguntas[mNumPregunta]);
        }

        // Activa o desactiva el botón de Siguiente según condición.
        private void checkSiguiente() {
            btnSiguiente.setEnabled(mNumPregunta < mPreguntas.length - 1
                    && rgRespuesta.getCheckedRadioButtonId() != -1);
        }

        // Activa o desactiva el botón Anterior según condición.
        private void checkAnterior() {
            btnAnterior.setEnabled(mNumPregunta > 0
                    && rgRespuesta.getCheckedRadioButtonId() != -1);
        }

        // Obtiene e inicializa las vistas.
        private void getVistas(View rootView) {
            lblPregunta = (TextView) rootView.findViewById(R.id.lblPregunta);
            rgRespuesta = (RadioGroup) rootView.findViewById(R.id.rgRespuesta);
            rgRespuesta.setOnCheckedChangeListener(this);
            btnSiguiente = (ImageButton) rootView
                    .findViewById(R.id.btnSiguiente);
            btnSiguiente.setOnClickListener(this);
            btnAnterior = (ImageButton) rootView.findViewById(R.id.btnAnterior);
            btnAnterior.setOnClickListener(this);
        }

        // Al hacer click sobre un botón.
        @Override
        public void onClick(View v) {
            // Dependiendo del botón pulsado.
            switch (v.getId()) {
            case R.id.btnSiguiente:
                siguientePregunta();
                break;
            case R.id.btnAnterior:
                anteriorPregunta();
                break;
            }
        }

        // Salva la respuesta y pasa a la anterior pregunta
        private void anteriorPregunta() {
            // Se salva la respuesta.
            salvarRespuesta();
            // Se cambia el número de pregunta y se muestra.
            mostrarPregunta(mNumPregunta - 1);
        }

        private void salvarRespuesta() {
            int valorRespuesta = getValorRespuesta();
            Toast.makeText(getActivity(),
                    "P" + (mNumPregunta + 1) + " - R" + valorRespuesta,
                    Toast.LENGTH_SHORT).show();
            mRespuestas[mNumPregunta] = valorRespuesta;
        }

        private int getValorRespuesta() {
            // Dependiendo de la opción seleccionada.
            int valor = 0;
            switch (rgRespuesta.getCheckedRadioButtonId()) {
            case R.id.rbOpcion1:
                valor = 1;
                break;
            case R.id.rbOpcion2:
                valor = 2;
                break;
            case R.id.rbOpcion3:
                valor = 3;
                break;
            case R.id.rbOpcion4:
                valor = 4;
                break;
            }
            return valor;
        }

        private void siguientePregunta() {
            // Se salva la respuesta.
            salvarRespuesta();
            // Se cambia el número de pregunta y se muestra.
            mostrarPregunta(mNumPregunta + 1);
        }

        // Cuando se cambia la respuesta.
        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {
            // Se comprueba la activación de los botones Siguiente y
            // Anterior.
            checkSiguiente();
            checkAnterior();
        }

    }

}
