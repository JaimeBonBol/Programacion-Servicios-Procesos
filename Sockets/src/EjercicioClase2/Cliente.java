package EjercicioClase2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 7000);
                // Para leer mensajes del servidor
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                // Para enviar mensajes al servidor
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true
                );
                ){
            System.out.println("Conectado al servidor");
            Scanner sc = new Scanner(System.in);

            // Bucle para mantener al cliente vivo hasta que introduzca "6"
            String opcion = "";
            while (!opcion.equals("6")) {
                System.out.println(
                        "Menú ejercicio clase 2, envía al servidor el número correspondiente para realizar la operación deseada:" +
                                "\n 1.Agregar" +
                                "\n 2.Mostrar" +
                                "\n 3.Calcular media" +
                                "\n 4.Buscar máximo" +
                                "\n 5.Borrar lista" +
                                "\n 6.Cerrar sesión"
                );
                opcion = sc.nextLine();
                // Enviamos la opción al servidor
                out.println(opcion);

                // Recibir la respuesta del servidor
                String respuesta = in.readLine();
                // Si el servidor nos pide "Agregar", hacemos el paso extra
                if ("Agregar".equals(respuesta)) {
                    System.out.println("Introduce un número para que el servidor lo añada a la lista:");
                    out.println(sc.nextLine());
                    System.out.println("Número enviado.");
                } else {
                    // Para cualquier otra respuesta, simplemente la mostramos
                    System.out.println("Servidor: " + respuesta);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        }
    }
}
