package Practica1.Ejercicio2;

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
                Socket socket = new Socket("localhost", 5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner teclado = new Scanner(System.in);
                )
        {
            // COnectarse al servidor
            System.out.println("Conectado al servidor");
            String opcion;

            while (true) {
                // Mostrar menú
                System.out.println("Menú:" +
                        "\n 1. Incrementar" +
                        "\n 2. Obtener" +
                        "\n 3. Resetear" +
                        "\n Exit");

                // Leemos la opción
                opcion = teclado.nextLine();

                // Comprobar si salimos del bucle (Si es nulo o escribe "Exit")
                if (opcion == null || opcion.equals("Exit")) {
                    if (opcion != null) {
                        // Le enviamos la palabra "Exit" al servidor
                        out.println(opcion);

                        // Leemos la respuesta de despedida ("Adios") y la mostramos
                        System.out.println("Servidor dice: " + in.readLine());
                    }
                    // Rompemos el bucle y cerramos todo
                    break;
                }

                switch (opcion){
                    case "1":
                        // Enviar opción al servidor
                        out.println(opcion);

                        // Recoger y mostrar respuesta del servbidor
                        System.out.println("Servidor: " + in.readLine());
                        break;
                    case "2":
                        // Enviar opción al servidor
                        out.println(opcion);

                        // Recoger y mostrar respuesta del servbidor
                        System.out.println("Servidor: Valor del contador: " + in.readLine());
                        break;
                    case "3":
                        // Enviar opción al servidor
                        out.println(opcion);

                        // Recoger y mostrar respuesta del servbidor
                        System.out.println("Servidor: " + in.readLine());
                        break;
                    default:
                        out.println(opcion);

                        System.out.println("Servidor: " + in.readLine());
                        break;
                }

            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
