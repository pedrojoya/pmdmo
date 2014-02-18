package es.iessaladillo.pedrojoya.pr088client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr088.R;
import es.iessaladillo.pedrojoya.pr088mensaje.Mensaje;

// Cliente de Sockets
public class ClienteActivity extends Activity implements OnClickListener {

    // Constantes.
    private static final String PREF_AUTOR = "prefAutor";
    private static final String PREF_IP = "prefIP";
    private static final String PREF_PUERTO = "prefPuerto";

    // Vistas.
    private ImageView btnEnviar;
    private EditText txtMensaje;
    private ListView lstMensajes;

    // Variables a nivel de clase.
    private Socket socket;
    private ObjectInputStream lector;
    private ObjectOutputStream escritor;
    private Thread hiloRecepcion;
    private String autor;
    private String ip;
    private String puerto;
    private ArrayList<Mensaje> mensajes;
    private MensajesAdapter adaptador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
    }

    @Override
    protected void onResume() {
        getPreferencias();
        mensajes = new ArrayList<Mensaje>();
        adaptador = new MensajesAdapter(this, mensajes, autor);
        lstMensajes.setAdapter(adaptador);
        conectar();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // Se descconecta.
        if (estaConectado()) {
            desconectar();
        }
        super.onPause();
    }

    // Obtiene las referencias a las vistas y las inicializa.
    private void getVistas() {
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (ImageView) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
        lstMensajes = (ListView) findViewById(R.id.lstMensajes);
    }

    private void getPreferencias() {
        SharedPreferences preferencias = PreferenceManager
                .getDefaultSharedPreferences(this);
        autor = preferencias.getString(PREF_AUTOR, "Baldomero");
        ip = preferencias.getString(PREF_IP, "192.168.1.3");
        puerto = preferencias.getString(PREF_PUERTO, "3389");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.mnuPreferencias:
            mostrarPreferencias();
            return true;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarPreferencias() {
        // Se llama a la actividad de preferencias.
        Intent intent = new Intent(this, PreferenciasActivity.class);
        startActivity(intent);
    }

    // Al hacer click sobre btnEnviar.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnEnviar:
            btnEnviarOnClick();
            break;
        default:
            break;
        }

    }

    // Realiza la conexión.
    public void conectar() {
        // Se ejecuta la tarea asícrona de conexión
        ConexionAsyncTask tarea = new ConexionAsyncTask();
        tarea.execute(ip, puerto);
    }

    private class ConexionAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // Se obtienen los datos de conexión.
            String ip = params[0];
            int puerto = Integer.valueOf(params[1]);
            // Se establece la conexión a través del socket
            try {
                socket = new Socket(ip, puerto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Retorna si está conectado o no.
            return Boolean.valueOf(socket != null && socket.isConnected());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Se inicializa la conexión.
            initConexion();
        }

    }

    // Inicializa la conexión.
    private void initConexion() {
        if (estaConectado()) {
            // Se crea el hilo para la recepción de mensajes.
            hiloRecepcion = new HiloRecepcionMensajes();
            hiloRecepcion.start();
            // Se crea el escritor del socket.
            try {
                escritor = new ObjectOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this, R.string.conexion_establecida,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.no_se_ha_podido_conectar,
                    Toast.LENGTH_LONG).show();
        }
        // Se actualiza la UI en base al estado de la conexión.
        actualizarUI();
    }

    // Actualiza la UI dependiendo del estado de la conexión.
    public void actualizarUI() {
        boolean conectado = estaConectado();
        txtMensaje.setEnabled(conectado);
        btnEnviar.setEnabled(conectado);
    }

    // Envía un mensaje de desconexión por el socket.
    public void desconectar() {
        if (estaConectado()) {
            try {
                // Se envía el mensaje de desconexión.
                Mensaje mensaje = new Mensaje(
                        getString(R.string.se_ha_desconectado), autor,
                        new Date(), true);
                escritor.writeObject(mensaje);
                // Se interrumpe el hilo de recepción de mensajes.
                hiloRecepcion.interrupt();
                // Se cierra la conexión.
                lector.close();
                escritor.close();
                socket.close();
                socket = null;
            } catch (IOException e) {
                Toast.makeText(this, R.string.error_al_enviar_el_mensaje,
                        Toast.LENGTH_LONG).show();
            }
            // Se actualiza la IU en base al estado de la conexión.
            actualizarUI();
        }
    }

    // Al hacer click en btnEnviar.
    public void btnEnviarOnClick() {
        if (estaConectado()) {
            // Se envía el mensaje.
            try {
                Mensaje mensaje = new Mensaje(txtMensaje.getText().toString(),
                        autor, new Date(), false);
                escritor.writeObject(mensaje);
            } catch (IOException e) {
                Toast.makeText(this, R.string.error_al_enviar_el_mensaje,
                        Toast.LENGTH_LONG).show();
            }
            txtMensaje.setText("");
        }
    }

    // Retorna si la conexión ha sido establecida o no.
    private boolean estaConectado() {
        return (socket != null && socket.isConnected());
    }

    // Clase para hilo de recepción de mensajes.
    private class HiloRecepcionMensajes extends Thread {

        @Override
        public void run() {
            try {
                lector = new ObjectInputStream(socket.getInputStream());
                while (!isInterrupted()) {
                    final Mensaje mensaje = (Mensaje) lector.readObject();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mostrarMensaje(mensaje);
                        }

                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // Muestra el mensaje recién recibido.
    private void mostrarMensaje(Mensaje mensaje) {
        // Se añade el mensaje al adaptador y se notifica que hay cambios.
        adaptador.add(mensaje);
        adaptador.notifyDataSetChanged();
    }
}