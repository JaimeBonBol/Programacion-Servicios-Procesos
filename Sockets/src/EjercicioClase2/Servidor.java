package EjercicioClase2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor levantado y esperando...");
        try (
                ServerSocket serverSocket = new ServerSocket(7000);
                //Bloqueo: Esperando al cliente
                Socket cliente = serverSocket.accept();
                // Para leer mensajes del cliente
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream())
                );
                // Para enviar mensajes al cliente, autoflush en true para limpiar el buffer tras enviar mensaje
                PrintWriter out = new PrintWriter(
                        cliente.getOutputStream(), true
                )
                ) {
            System.out.println("Cliente conectado");
            List<Integer> numeros = new ArrayList<>();

            // Recuperar la operación del cliente según el número recibido
            String operacion;

            while ((operacion = in.readLine()) != null && !operacion.equals("6")) {
                switch (operacion) {
                    case "1":
                        // Le decimos al cliente qué debe hacer
                        out.println("Agregar");
                        String numeroAgregar = in.readLine();
                        numeros.add(Integer.parseInt(numeroAgregar));
                        System.out.println("Número " + numeroAgregar + " agregado a la lista.");
                        break;
                    case "2":
                        if (numeros.isEmpty()) {
                            out.println("La lista está vacía.");
                        } else {
                            out.println("Números en la lista: " + numeros.toString());
                        }
                        break;
                    case "3":
                        if (numeros.isEmpty()) {
                            out.println("No se puede calcular la media porque la lista está vacía.");
                        } else {
                            int total = 0;
                            for (int n : numeros) {
                                total += n;
                            }
                            out.println("La media de los números es: " + (total / numeros.size()));
                        }
                        break;
                    case "4":
                        if (numeros.isEmpty()) {
                            out.println("No hay máximo, la lista está vacía.");
                        } else {
                            // Inicializamos con el primer elemento de la lista para evitar problemas con números negativos
                            int mayor = numeros.get(0);
                            for (int n : numeros) {
                                if (n > mayor) {
                                    mayor = n;
                                }
                            }
                            out.println("El número mayor de la lista es: " + mayor);
                        }
                        break;
                    case "5":
                        numeros.clear();
                        out.println("La lista se ha vaciado correctamente.");
                        break;
                    default:
                        out.println("Operación no válida. Inténtalo de nuevo.");
                }
            }
            out.println("Cerrando sesión...");


        } catch (IOException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        }
    }
}
