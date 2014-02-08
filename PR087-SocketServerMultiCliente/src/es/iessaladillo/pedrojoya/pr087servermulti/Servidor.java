package es.iessaladillo.pedrojoya.pr087servermulti;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Servidor {

    // Constantes.
    final int PUERTO = 8888;

    // Variables a nivel de clase.
    private ServerSocket skServidor;
    private ThreadPoolExecutor ejecutor;

    // Constructor.
    public Servidor() {
        ejecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        try {
            // Se crea el socket servidor.
            skServidor = new ServerSocket(PUERTO);
            System.out.println("************ SERVIDOR ****************");
            System.out.println("Escuchando el puerto " + PUERTO);
            System.out.println("En Espera....");
            // Bucle de conexión de clientes.
            while (true) {
                // Se acepta una conexión con un cliente (bloqueante).
                Socket skConexion = skServidor.accept();
                // Se envía al ejecutor la tarea de comunicación con el cliente.
                ejecutor.execute(new ComunicacionCliente(skConexion));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método main.
    public static void main(String[] args) {
        new Servidor();
    }

}