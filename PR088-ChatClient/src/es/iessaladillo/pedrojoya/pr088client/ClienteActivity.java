package es.iessaladillo.pedrojoya.pr088client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import es.iessaladillo.pedrojoya.pr088.R;
import es.iessaladillo.pedrojoya.pr088mensaje.Mensaje;

// Cliente de Sockets
public class ClienteActivity extends Activity implements OnClickListener {

    // Vistas.
    private Button btnConectar;
    private Button btnEnviar;
    private EditText txtIP;
    private EditText txtPuerto;
    private EditText txtMensaje;
    private ImageView imgLeds;

    // Variables a nivel de clase.
    private Socket socket;
    private boolean estaConectado = false;
    private ObjectInputStream lector;
    private ObjectOutputStream escritor;
    private Thread hiloRecepcion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
    }

    // Obtiene las referencias a las vistas y las inicializa.
    private void getVistas() {
        imgLeds = (ImageView) findViewById(R.id.imgLeds);
        txtIP = (EditText) findViewById(R.id.txtIP);
        txtPuerto = (EditText) findViewById(R.id.txtPuerto);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(this);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
    }

    // Actualiza la imagen del led dependiendo de si estamos conectados o no.
    public void actualizarEstado() {
        if (estaConectado) {
            imgLeds.setImageResource(R.drawable.on);
            btnConectar.setText(R.string.desconectar);
        } else {
            imgLeds.setImageResource(R.drawable.off);
            btnConectar.setText(R.string.conectar);
        }
        txtMensaje.setEnabled(estaConectado);
        btnEnviar.setEnabled(estaConectado);
    }

    private void comprobarConexion(boolean conectado) {
        // Se informa al usuario y se actualiza el estado de la conexión.
        estaConectado = conectado;
        if (estaConectado) {
            // Se crea un hilo para la recepción de mensajes.
            hiloRecepcion = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        lector = new ObjectInputStream(socket.getInputStream());
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    while (true) {
                        try {
                            final Mensaje mensaje = (Mensaje) lector
                                    .readObject();
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    mostrarMensaje(mensaje);
                                }

                            });
                            // lector.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            hiloRecepcion.start();
            Toast.makeText(this, R.string.conexion_establecida,
                    Toast.LENGTH_LONG).show();
            // Se crea un lector y un escritor del socket.
            try {
                escritor = new ObjectOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.no_se_ha_podido_conectar,
                    Toast.LENGTH_LONG).show();
        }
        // Actualizamos el estado de conexión.
        actualizarEstado();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnConectar:
            btnConectarOnClick();
            break;
        case R.id.btnEnviar:
            btnEnviarOnClick();
            break;
        default:
            break;
        }

    }

    // Al hacer click sobre btnConectar
    public void btnConectarOnClick() {
        // Conectamos a desconectamos dependiendo del estado actual.
        if (estaConectado) {
            desconectar();
        } else {
            // Intentamos conectar
            conectar();
        }
    }

    // Realiza la conexión.
    public void conectar() {
        // Se ejecuta la tarea asícrona de conexión
        ConexionAsyncTask tarea = new ConexionAsyncTask();
        tarea.execute(txtIP.getText().toString(), txtPuerto.getText()
                .toString());
    }

    private class ConexionAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // Se obtienen los datos de conexión.
            // String ip = params[0];
            String ip = "192.168.1.7";
            // int puerto = Integer.valueOf(params[1]);
            int puerto = 3389;
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
            // Se comprueba la conexión y se actualiza la UI.
            comprobarConexion(result.booleanValue());
        }

    }

    // Envía un mensaje de desconexión por el socket.
    public void desconectar() {
        try {
            // Preparamos mensaje de desconexion
            Mensaje mensaje = new Mensaje("Desconexión", true);
            // Tratamos de enviar el mensaje
            if (enviarMensaje(mensaje)) {
                // Si se ha enviado correctamente informamos al usuario.
                Toast.makeText(this, R.string.conexion_terminada,
                        Toast.LENGTH_LONG).show();
                // Se interrumpe el hilo.
                hiloRecepcion.interrupt();
                // Se cierran el lector y el escritor.
                lector.close();
                escritor.close();
                // Cerramos el socket.
                socket.close();
                // Actualizamos el estado de conexión.
                estaConectado = false;
                socket = null;
            } else {
                // Si se ha producido un error al enviar el mensaje de
                // desconexión
                // informamos al usuario.
                Toast.makeText(this, R.string.error_al_cerrar_la_conexion,
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Actualizamos el estado de conexión.
        actualizarEstado();
    }

    // Al hacer click en btnEnviar.
    public void btnEnviarOnClick() {
        // Preparamos el mensaje a enviar.
        Mensaje mensaje = new Mensaje(txtMensaje.getText().toString(), false);
        // Tratamos de enviar el mensaje e informamos al usuario de como ha ido.
        if (enviarMensaje(mensaje)) {
            // Toast.makeText(this, R.string.mensaje_enviado, Toast.LENGTH_LONG)
            // .show();
        } else {
            Toast.makeText(this, R.string.error_al_enviar_el_mensaje,
                    Toast.LENGTH_LONG).show();
        }
        // Actualizamos el estado de conexión.
        estaConectado = socket.isConnected();
        txtMensaje.setText("");
        actualizarEstado();

    }

    // Envía un objeto Mensaje por el socket. Retorna si se ha enviado o no.
    public boolean enviarMensaje(Mensaje msg) {
        try {
            // Si estamos conectados
            if (socket.isConnected()) {
                // Envio el mensaje por el flujo de salida y retorno que todo ha
                // ido bien.
                escritor.writeObject(msg);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            // Retorno que no se ha podido enviar el mensaje.
            return false;
        }
    }

    @Override
    protected void onPause() {
        // Si estamos conectados, desconectamos.
        if (estaConectado) {
            desconectar();
        }
        super.onPause();
    }

    private void mostrarMensaje(Mensaje mensaje) {
        Toast.makeText(this, mensaje.getTexto(), Toast.LENGTH_SHORT).show();

    }

}