package es.iessaladillo.pedrojoya.pr088server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import es.iessaladillo.pedrojoya.pr088mensaje.Mensaje;

public class Chat {

    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
    private ReentrantLock lockMensajes = new ReentrantLock(true);
    private ReentrantLock lockClientes = new ReentrantLock(true);

    // Agrega el cliente a la lista de clientes.
    public void agregarCliente(Cliente cliente) {
        // Se agrega el cliente.
        lockClientes.lock();
        clientes.add(cliente);
        lockClientes.unlock();
        // Se le envían todos los mensajes.
        enviarMensajes(cliente);
    }

    // Quita un cliente de la lista de clientes.
    public void quitarCliente(Cliente cliente) {
        lockClientes.lock();
        clientes.remove(cliente);
        lockClientes.unlock();
    }

    public void agregarMensaje(Cliente cliente, Mensaje mensaje) {
        // Se agrega el mensaje a la lista de mensajes.
        lockMensajes.lock();
        mensajes.add(mensaje);
        lockMensajes.unlock();
        // Se envia el mensaje a todos los clientes.
        enviarAClientes(mensaje);
    }

    // Envía todos los mensajes del chat a un cliente.
    private void enviarMensajes(Cliente cliente) {
        try {
            // Se obtiene el escritor del cliente.
            ObjectOutputStream escritor = cliente.getEscritor();
            // Se escriben todos los mensajes del chat.
            lockMensajes.lock();
            for (Mensaje m : mensajes) {
                escritor.writeObject(m);
            }
            lockMensajes.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarAClientes(Mensaje mensaje) {
        // Por cada cliente.
        lockClientes.lock();
        for (Cliente c : clientes) {
            // Se obtiene el escritor del cliente.
            ObjectOutputStream escritor = c.getEscritor();
            // Se envía el mensaje al cliente.
            try {
                escritor.writeObject(mensaje);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        lockClientes.unlock();
    }

}