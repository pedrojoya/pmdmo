package es.iessaladillo.pedrojoya.pr085;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    // Vistas.
    private Button btnIniciar;
    private Button btnParar;
    private MenuItem mnuRefrescar;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnParar = (Button) findViewById(R.id.btnParar);
        // La actividad actuará como listener de los click sobre los botones.
        btnIniciar.setOnClickListener(this);
        btnParar.setOnClickListener(this);
    }

    // Al crear al menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // Se obtiene el ítem de menú de refrescar.
        mnuRefrescar = menu.findItem(R.id.mnuRefrescar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
        case R.id.btnIniciar:
            mnuRefrescar.setVisible(true);
            break;
        case R.id.btnParar:
            mnuRefrescar.setVisible(false);
            break;
        default:
            break;
        }

    }

}
