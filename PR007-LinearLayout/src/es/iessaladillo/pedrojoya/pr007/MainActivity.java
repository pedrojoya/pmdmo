package es.iessaladillo.pedrojoya.pr007;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    // Vistas.
    private EditText txtUsuario;
    private EditText txtClave;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        ((Button) findViewById(R.id.btnAceptar)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnCancelar)).setOnClickListener(this);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
    }

    // Al hacer click sobre un botón.
    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
        case R.id.btnAceptar:
            // Se informa de la conexión
            Toast.makeText(
                    this,
                    "Conectando con el usuario "
                            + txtUsuario.getText().toString() + "...",
                    Toast.LENGTH_SHORT).show();
            break;
        case R.id.btnCancelar:
            // Se resetean las vistas.
            resetVistas();
            break;
        }
    }

    // Resetea las vistas.
    private void resetVistas() {
        txtUsuario.setText("");
        txtClave.setText("");
    }

}