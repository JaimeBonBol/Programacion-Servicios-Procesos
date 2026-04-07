package Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor levantado y esperando");
        try (
                ServerSocket serverSocket = new ServerSocket(7000);
                // Bloqueo: Esperando cliente
                Socket cliente = serverSocket.accept();
                // Leer mensaje del cliente
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream())
                );
                // Enviar un mensaje al cliente
                PrintWriter out = new PrintWriter(
                        cliente.getOutputStream(), true
                )
                ) {

            System.out.println("Cliente conectado");
            // Recuperar operación del cliente
            String operacion = in.readLine();

            // Recuperar la cadena deel cliente
            String cadena = null;
            switch (operacion){
                case "Cifrar":
                    out.println("Pasame la cadena");
                    cadena = in.readLine();
                    out.println(CifradoCesar.cifrarCadena(cadena));
                    break;
                case "Descifrar":
                    out.println("Pasame la cadena");
                    cadena = in.readLine();
                    out.println(CifradoCesar.descifrarCadena(cadena));
                    break;
                default:
                    out.println("Operación no válida");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String cifrarCadena(String cadena) {
        return cadena;
    }

    public static String descifrarCadena(String cadena) {
        return cadena;
    }
}

