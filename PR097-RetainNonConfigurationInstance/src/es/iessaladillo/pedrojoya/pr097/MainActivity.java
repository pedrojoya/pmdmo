package es.iessaladillo.pedrojoya.pr097;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    // Variables a nivel de clase.
    private State mEstado;

    // Vistas.
    private TextView lblMarcador;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se obtiene el objeto de estado existente y si no lo hay se crea.
        mEstado = (State) getLastNonConfigurationInstance();
        if (mEstado == null) {
            mEstado = new State();
        }
        // Se actualiza el marcador.
        actualizarMarcador();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        lblMarcador = (TextView) findViewById(R.id.lblMarcador);
        ((Button) findViewById(R.id.btnIncrementar)).setOnClickListener(this);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        // Se retorna el objeto de estado para que se salvaguarde.
        return mEstado;
    }

    // Al hacer click sobre el botón.
    @Override
    public void onClick(View v) {
        // Se incrementa el contador.
        mEstado.incrementContador();
        // Se actualiza el marcador.
        actualizarMarcador();
    }

    // Actualiza la vista marcador.
    private void actualizarMarcador() {
        // Se muestra el valor del contador en el marcador.
        lblMarcador.setText(mEstado.getContador() + "");
    }

    // Clase interna para guardar el estado de la actividad.
    private class State {
        private int contador = 0;

        public int getContador() {
            return contador;
        }

        public void incrementContador() {
            contador++;
        }
    }

}
