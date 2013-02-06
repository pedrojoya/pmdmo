package test.Sockettest;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	// Variables a nivel de clase.
	Socket skCliente;			// Socket del cliente.
	ServerSocket skServidor;	// Socket del servidor.
	final int PUERTO = 8888;	// Puerto de conexión (tanto para servidor como para cliente)
	String ipCliente;			// IP del cliente.
	Mensaje mensaje = null;		// Mensaje recibido.
	String timeStamp;			// Código de tiempo actual.

	// Constructor.
	Server() {
		try {
			// Mostramos info en la salida estándar.
			System.out.println("************ SERVIDOR ****************");
			// Creamos un socket servidor ServerSocket indicando el puerto de conexión.
			skServidor = new ServerSocket(PUERTO);
			// Mostramos info en la salida estándar.
			System.out.println("Escuchando el puerto " + PUERTO);
			System.out.println("En Espera....");
			try {
				// Hago que el socket servidor acepte una conexión,
				// obteniendo como resultado el socket cliente (se bloquea hasta que conecta).
				skCliente = skServidor.accept();
				// Obtengo el timeStamp (código de tiempo) actual para la info.
				timeStamp = new java.util.Date().toString();
				// Una vez conectados, obtengo la IP del cliente.
				ipCliente = skCliente.getInetAddress().toString();
				// Muestro la infor de la IP en la salida estándar.
				System.out.println("[" + timeStamp + "] Conectado al cliente "
						+ "IP:" + ipCliente);
				// Bucle de recepción de mensajes desde el cliente.
				while (true) {
					// Creo el flujo de entrada de objetos a partir del flujo del propio socket de cliente.
					// A través del socket se van a enviar objetos Mensaje.
					ObjectInputStream entrada = new ObjectInputStream(
							skCliente.getInputStream());
					// Leemos del flujo de entrada el mensaje enviado por el cliente.
					Object datos = entrada.readObject();
					// Si los datos recibidos son de la clase Mensaje
					if (datos instanceof Mensaje) {
						// Convertimos los datos en un mensaje (cast).
						mensaje = (Mensaje) datos;
						// Si no es el mensaje de final de conexión.
						if (!mensaje.finalConexion) {
							// Es un mensaje de texto. Muestro la info del mensaje
							System.out.println("[" + timeStamp + "] "
									+ "Mensaje de [" + ipCliente + "]--> "
									+ mensaje.texto);
						}
						else {
							// Si es el mensaje de fin de la conexión.
							// Cierro el socket, el flujo de entrada y muestro la info.
							skCliente.close();
							entrada.close();
							System.out
									.println("["
											+ timeStamp
											+ "] Last_msg detected Conexion cerrada, gracias vuelva pronto");
							// Salgo del bucle de recepción de mensajes.
							break;
						}
					} 
					else {
						// Informo de que los datos recibidos no son de la clase Mensaje.
						System.err.println("Los datos recibidos no son de la clase Mensaje.");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[" + timeStamp + "] Error ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + timeStamp + "] Error ");
		}
	}

	// Método main.
	public static void main(String[] args) {
		new Server();
	}
}	