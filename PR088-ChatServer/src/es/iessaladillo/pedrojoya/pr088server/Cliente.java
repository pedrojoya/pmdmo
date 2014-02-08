package es.iessaladillo.pedrojoya.pr088server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr088mensaje.Mensaje;

// Tarea de comunicación con un cliente.
public class Cliente implements Runnable {

    // Variables.
    private Socket socket;
    private Chat chat;
    private ObjectInputStream lector;
    private ObjectOutputStream escritor;
    private SimpleDateFormat formateador;
    private String ipCliente;

    // Constructor.
    public Cliente(Socket socket, Chat chat) {
        this.socket = socket;
        this.chat = chat;
        formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        ipCliente = socket.getInetAddress().toString();
        // Se crea un lector de objetos Mensaje para el flujo de
        // entrada.
        try {
            lector = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Se crea un escritor de objetos Mensaje para el flujo de salida.
        try {
            escritor = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getLector() {
        return lector;
    }

    public ObjectOutputStream getEscritor() {
        return escritor;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    @Override
    public void run() {
        // Una vez conectados, se muestra información sobre el cliente.
        System.out.println("[" + formateador.format(new Date())
                + "] Conectado al cliente " + "IP:" + ipCliente);
        // Se agrega el cliente al chat.
        chat.agregarCliente(this);
        // Bucle de recepción de mensajes del cliente.
        while (true) {
            // Se lee un objeto.
            Object datos;
            try {
                datos = lector.readObject();
                if (datos instanceof Mensaje) {
                    // Se convierte a mensaje.
                    Mensaje mensaje = (Mensaje) datos;
                    // Se agrega el mensaje al chat.
                    chat.agregarMensaje(this, mensaje);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Se quita el cliente del chat.
        chat.quitarCliente(this);
        // Se cierra el lector, el escritor y el socket.
        try {
            lector.close();
            escritor.close();
            socket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("[" + formateador.format(new Date())
                + "] Conexion cerrada con " + ipCliente);
    }

}
