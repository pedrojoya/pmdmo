package es.iessaladillo.pedrojoya.pr080;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
        BuscarAsyncTask.Callbacks, EcoAsyncTask.Callbacks {

    // Vistas.
    private EditText txtNombre;
    private Button btnBuscar;
    private Button btnEco;

    // Variables.
    private BuscarAsyncTask tareaBuscar;
    private MenuItem mnuActualizar;
    private EcoAsyncTask tareaEco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);
        btnEco = (Button) findViewById(R.id.btnEco);
        btnEco.setOnClickListener(this);
    }

    // Retorna si hay conexión a la red o no.
    private boolean isConnectionAvailable() {
        // Se obtiene del gestor de conectividad la información de red.
        ConnectivityManager gestorConectividad = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();
        // Se retorna si hay conexión.
        return (infoRed != null && infoRed.isConnected());
    }

    // Cuando se hace click en un botón.
    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
        case R.id.btnBuscar:
            // Se busca el nombre en Google.
            buscar();
            break;
        case R.id.btnEco:
            // Se envía el nombre al servidor de eco.
            eco();
            break;
        default:
            break;
        }
    }

    // Crea una tarea para buscar el término en Internet.
    private void buscar() {
        // Si hay un nombre a buscar.
        String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                // Se lanza la tarea asíncrona de búsqueda pasándole el nombre
                // que debe buscarse.
                mnuActualizar.setVisible(true);
                tareaBuscar = new BuscarAsyncTask(this);
                tareaBuscar.execute(nombre);
            } else {
                mostrarToast(getString(R.string.no_hay_conexion_a_internet));
            }
        }
    }

    // Envía el nombre a un servidor de eco.
    private void eco() {
        // Si hay un nombre a enviar.
        String nombre = txtNombre.getText().toString();
        if (!TextUtils.equals(nombre, "")) {
            // Si hay conexión a Internet.
            if (isConnectionAvailable()) {
                // Se lanza la tarea asíncrona de búsqueda pasándole el nombre
                // que debe buscarse.
                mnuActualizar.setVisible(true);
                tareaEco = new EcoAsyncTask(this);
                tareaEco.execute(nombre);
            } else {
                mostrarToast(getString(R.string.no_hay_conexion_a_internet));
            }
        }
    }

    // Muestra un toast con duración larga.
    private void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
                .show();
    }

    // Al ser pausada la actividad.
    @Override
    protected void onPause() {
        super.onPause();
        // Se cancela la tarea secundaria de buscar.
        if (tareaBuscar != null) {
            tareaBuscar.cancel(true);
            tareaBuscar = null;
        }
        // Se cancela la tarea secundaria de eco.
        if (tareaEco != null) {
            tareaEco.cancel(true);
            tareaEco = null;
        }
    }

    // Cuando se ha finalizado de realizar la tarea secundaria de buscar.
    @Override
    public void onPostExecute(BuscarAsyncTask object, String result) {
        mnuActualizar.setVisible(false);
        // Si hay resultado.
        if (!TextUtils.equals(result, "")) {
            // Se muestra un toast con el resultado.
            mostrarToast(result + " " + getString(R.string.entradas));
        }
    }

    // Cuando se ha finalizado de realizar la tarea secundaria de eco.
    @Override
    public void onPostExecute(EcoAsyncTask objeto, String result) {
        mnuActualizar.setVisible(false);
        // Si hay resultado.
        if (!TextUtils.equals(result, "")) {
            // Se muestra un toast con el resultado.
            mostrarToast(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mnuActualizar = menu.findItem(R.id.mnuActualizar);
        mnuActualizar.setVisible(false);
        mnuActualizar.setActionView(R.layout.actionview_progreso);
        return super.onCreateOptionsMenu(menu);
    }
}
