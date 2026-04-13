package EjercicioClase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor levantado y esperando");
        try {
            ServerSocket serverSocket = new ServerSocket(7000);
            System.out.println("Servidor concurrente escuchando en el puerto 7000...");

            while (true){
                // Bloqueo: Esperando cliente
                Socket cliente = serverSocket.accept();
                System.out.println("¡Nuevo cliente conectado desde " + cliente.getInetAddress() + "!");

                // Instacioamos el hilo sin bloquear el servidor
                new ClienteHandler(cliente).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

