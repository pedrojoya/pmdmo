package es.iessaladillo.pedrojoya.encuesta;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Si no hay estado salvado se muestra el fragmento.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment()).commit();
        }
    }

    // Fragmento que muestra la actividad principal.
    public static class MainFragment extends Fragment implements
            OnClickListener {

        // Vistas.
        private Button btnEncuesta;
        private Button btnResultados;

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            getVistas(rootView);
            return rootView;
        }

        // Obtiene e inicializa las vistas.
        private void getVistas(View rootView) {
            btnEncuesta = (Button) rootView.findViewById(R.id.btnEncuesta);
            btnEncuesta.setOnClickListener(this);
            btnResultados = (Button) rootView.findViewById(R.id.btnResultados);
            btnResultados.setOnClickListener(this);
        }

        // Al pulsar sobre un botón.
        @Override
        public void onClick(View v) {
            // Dependiendo del botón pulsado.
            switch (v.getId()) {
            case R.id.btnEncuesta:
                mostrarEncuesta();
                break;
            case R.id.btnResultados:
                mostrarResultados();
                break;
            }

        }

        // Muestra la actividad de encuestas.
        private void mostrarEncuesta() {
            Intent intent = new Intent(getActivity(), EncuestaActivity.class);
            startActivity(intent);
        }

        // Muestra la actividad de resultados.
        private void mostrarResultados() {
            // Intent intent = new Intent(getActivity(),
            // ResultadosActivity.class);
            // startActivity(intent);
        }

    }

}
