package es.iessaladillo.pedrojoya.pr087server;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.mensaje.Mensaje;

public class Servidor {

    // Constantes.
    final int PUERTO = 8888;

    // Variables a nivel de clase.
    private ServerSocket skServidor;
    private Socket skConexion;
    private Mensaje mensaje = null; // Mensaje recibido.
    private SimpleDateFormat formateador;

    // Constructor.
    public Servidor() {
        formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        try {
            // Se crea el socket servidor.
            skServidor = new ServerSocket(PUERTO);
            System.out.println("************ SERVIDOR ****************");
            System.out.println("Escuchando el puerto " + PUERTO);
            System.out.println("En Espera....");
            // Se acepta una conexión con un cliente (bloqueante).
            skConexion = skServidor.accept();
            // Una vez conectados, se muestra información sobre el cliente.
            String ipCliente = skConexion.getInetAddress().toString();
            System.out.println("[" + formateador.format(new Date())
                    + "] Conectado al cliente " + "IP:" + ipCliente);
            // Se crea un lector de objetos Mensaje para el flujo de
            // entrada.
            ObjectInputStream lector = new ObjectInputStream(
                    skConexion.getInputStream());
            // Bucle de recepción de mensajes desde el cliente.
            while (true) {
                // Se lee un objeto.
                Object datos = lector.readObject();
                if (datos instanceof Mensaje) {
                    // Se convierte a mensaje.
                    mensaje = (Mensaje) datos;
                    // Se muestra el mensaje.
                    if (!mensaje.isFinalConexion()) {
                        System.out.println("[" + formateador.format(new Date())
                                + "] " + ipCliente + " --> "
                                + mensaje.getTexto());
                    } else {
                        // Se sale del bucle de recepción de mensajes.
                        break;
                    }
                } else {
                    // El mensaje no es válido.
                    System.err
                            .println("Los datos recibidos no son de la clase Mensaje.");
                }
            }
            // Se cierra el lector y la conexión con el cliente.
            lector.close();
            skConexion.close();
            System.out.println("[" + formateador.format(new Date())
                    + "] Conexion cerrada con " + ipCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método main.
    public static void main(String[] args) {
        new Servidor();
    }

}