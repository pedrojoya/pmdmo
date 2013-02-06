package test.Sockettest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

// Cliente de Sockets
public class Sockettest extends Activity {

	// Vistas.
	private Button btnConectar;
	private Button btnEnviar;
	private EditText txtIP;
	private EditText txtPuerto;
	private EditText txtMensaje;
	private ImageView imgLeds;
	
	// Variables a nivel de clase.
	Socket skCliente;						// Socket del cliente.
	private boolean estaConectado = false;	// Indicador de si estamos conectados o no.
	private Handler manejador;				// Manejador de retorno para el hilo de conexión.
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Llamo al onCreate del padre.
		super.onCreate(savedInstanceState);
		// Establezco el layout que mostrará la actividad.
		setContentView(R.layout.main);
		// Obtengo las referencias a las vistas.
		getVistas();
		// Creo el objeto manejador.
		manejador = new Handler();
	}
	
	// Obtiene las referencias a las vistas y las inicializa.
	private void getVistas() {
		imgLeds = (ImageView) findViewById(R.id.imgLeds);
		txtIP = (EditText) findViewById(R.id.txtIP);
		txtPuerto = (EditText) findViewById(R.id.txtPuerto);
		txtMensaje = (EditText) findViewById(R.id.txtMensaje);
		btnConectar = (Button) findViewById(R.id.btnConectar);
		btnEnviar = (Button) findViewById(R.id.btnEnviar);
	}
	
	// Actualiza la imagen del led dependiendo de si estamos conectados o no. 
	public void actualizarEstado() {
		if (estaConectado) {
			imgLeds.setImageResource(R.drawable.on);
			btnConectar.setText(R.string.desconectar);
		}
		else {
			imgLeds.setImageResource(R.drawable.off);
			btnConectar.setText(R.string.conectar);
		}
		txtMensaje.setEnabled(estaConectado);
		btnEnviar.setEnabled(estaConectado);
	}

	// Conectamos con el socket. Retorna si estamos conectados o no.
	public void conectar() {
		// Obtengo los datos de conexión introducidos por el usuario.
		final String ip = txtIP.getText().toString();
		final int puerto = Integer.valueOf(txtPuerto.getText().toString());
		// Creamos el socket.
		skCliente = new Socket();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Conectamos con el socket indicando la ip, el puerto y el timeout.
					skCliente.connect(new InetSocketAddress(ip, puerto), 8000);
				} catch (Exception e) {
					Log.e("Sockettest", "Error al crear el socket");
				}				
				// Cuando estemos listos enviamos un mensaje al manejador del hilo.
				manejador.post(new Runnable() {
					@Override
					public void run() {
						comprobarConexion();
					}});

			}}).start();
	}

	private void comprobarConexion() {
		estaConectado = skCliente.isConnected();
		// Informamos al usario si hemos conectado o no.
		estaConectado = skCliente.isConnected();
		if (estaConectado) {
			Toast.makeText(this, R.string.conexion_establecida, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, R.string.no_se_ha_podido_conectar, Toast.LENGTH_LONG).show();
		}									
		// Actualizamos el estado de conexión.
		actualizarEstado();
	}
	
	// Envía un mensaje de desconexión por el socket.
	public void desconectar() {
		try {
			// Preparamos mensaje de desconexion
			Mensaje mensaje = new Mensaje();
			mensaje.texto = "";
			mensaje.finalConexion = true;
			// Tratamos de enviar el mensaje
			if (enviarMensaje(mensaje)) {
				// Si se ha enviado correctamente informamos al usuario.
				Toast.makeText(this, R.string.conexion_terminada, Toast.LENGTH_LONG).show();
				// Cerramos el socket.
				skCliente.close();
				// Actualizamos el estado de conexión.
				estaConectado = false;
			}
			else {
				// Si se ha producido un error al enviar el mensaje de desconexión
				// informamos al usuario.
				Toast.makeText(this, R.string.error_al_cerrar_la_conexion, Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Actualizamos el estado de conexión.
		actualizarEstado();		
	}

	// Al hacer click en btnEnviar. 
	public void btnEnviarOnClick(View v) {
		// Preparamos el mensaje a enviar.
		Mensaje mensaje = new Mensaje();
		mensaje.texto = txtMensaje.getText().toString();
		mensaje.finalConexion = false;
		// Tratamos de enviar el mensaje e informamos al usuario de como ha ido.
		if (enviarMensaje(mensaje)) {
			Toast.makeText(this, R.string.mensaje_enviado, Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, R.string.error_al_enviar_el_mensaje, Toast.LENGTH_LONG).show();
		}
		// Actualizamos el estado de conexión.
		estaConectado = skCliente.isConnected();
		txtMensaje.setText("");
		actualizarEstado();
		
	}
	
	// Envía un objeto Mensaje por el socket. Retorna si se ha enviado o no.
	public boolean enviarMensaje(Mensaje msg) {
		try {
			// Creo un flujo de salida de objetos a partir del flujo de salida del socket.
			ObjectOutputStream salida = new ObjectOutputStream(skCliente.getOutputStream());
			// Si estamos conectados
			if (skCliente.isConnected()) {
				// Envio el mensaje por el flujo de salida y retorno que todo ha ido bien.
				salida.writeObject(msg);
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			// Retorno que no se ha podido enviar el mensaje.
			return false;
		}
	}
	
	// Al hacer click sobre btnConectar
	public void btnConectarOnClick(View v) {
		// Si ya estamos conectados.
		if (estaConectado) {
			desconectar();
		}
		else {
			// Intentamos conectar
			conectar();
		}
	}
		
}