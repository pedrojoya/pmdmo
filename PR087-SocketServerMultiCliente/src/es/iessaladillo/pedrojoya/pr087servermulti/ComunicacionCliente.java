package es.iessaladillo.pedrojoya.pr087servermulti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.mensaje.Mensaje;

// Tarea de comunicación con un cliente.
public class ComunicacionCliente implements Runnable {

    // Variables.
    private Socket skConexion;
    private SimpleDateFormat formateador;
    private String ipCliente;
    private ObjectInputStream lector;

    // Constructor.
    public ComunicacionCliente(Socket skConexion) {
        this.skConexion = skConexion;
        formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        ipCliente = skConexion.getInetAddress().toString();
        // Se crea un lector de objetos Mensaje para el flujo de
        // entrada.
        try {
            lector = new ObjectInputStream(skConexion.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Una vez conectados, se muestra información sobre el cliente.
        System.out.println("[" + formateador.format(new Date())
                + "] Conectado al cliente " + "IP:" + ipCliente);
        // Bucle de recepción de mensajes del cliente.
        while (true) {
            // Se lee un objeto.
            Object datos;
            try {
                datos = lector.readObject();
                if (datos instanceof Mensaje) {
                    // Se convierte a mensaje.
                    Mensaje mensaje = (Mensaje) datos;
                    // Se muestra el mensaje.
                    if (!mensaje.isFinalConexion()) {
                        System.out.println("[" + formateador.format(new Date())
                                + "] " + ipCliente + " --> "
                                + mensaje.getTexto());
                    } else {
                        // Se cierra el lector y la conexión con el cliente.
                        lector.close();
                        skConexion.close();
                        System.out.println("[" + formateador.format(new Date())
                                + "] Conexion cerrada con " + ipCliente);
                        // Se sale del bucle de recepción de mensajes.
                        break;
                    }
                } else {
                    // El mensaje no es válido.
                    System.err
                            .println("Los datos recibidos no son de la clase Mensaje.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
