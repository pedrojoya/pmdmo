package es.iessaladillo.pedrojoya.pr068;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText txtNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
    }

    private void getVistas() {
        txtNumero = (EditText) this.findViewById(R.id.txtNumero);
    }

    // Al pulsar sobre un número.
    public void btnNumeroOnClick(View v) {
        // Concatena el número del botón al TextView
        txtNumero.append(((Button) v).getText());
    }

    // Al pulsar sobre el botón Borrar.
    public void btnBorrarOnClick(View v) {
        // Quita el último carácter del EditText.
        String texto = txtNumero.getText().toString().trim();
        if (texto.length()!= 0)
            texto = texto.substring(0, texto.length()-1);
            txtNumero.setText(texto);
        }
    }
    
}
