package Practica1;

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
                // para leer mensajes del servidor
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                // para enviar mensajes al servidor
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ){
            System.out.println("Conectado al servidor");
            Scanner sc = new Scanner(System.in);
            boolean login = false;

            // Autenticación
            String respuestaAutenticacion;
            // Bucle de Autenticación
            while ((respuestaAutenticacion = in.readLine()) != null) {
                if (respuestaAutenticacion.equals("LOGIN_OK")) {
                    System.out.println("¡Login correcto! Accediendo al sistema...");
                    login = true; // Guardamos que el login fue un éxito
                    break;        // Rompemos el bucle para ir al menú
                }
                else if (respuestaAutenticacion.equals("LOGIN_BAD")) {
                    System.out.println("Demasiados intentos incorrectos. El servidor ha rechazado la conexión.");
                    break;        // Rompemos el bucle, login se queda en false
                }
                else if (respuestaAutenticacion.equals("Usuario")) {
                    System.out.print("Introduce usuario: ");
                    out.println(sc.nextLine());
                }
                else if (respuestaAutenticacion.equals("password")) {
                    System.out.print("Introduce contraseña: ");
                    out.println(sc.nextLine());
                }
                else if (respuestaAutenticacion.equals("CREDENTIALS_BAD")) {
                    System.out.println("Credenciales erróneas. Vuelve a intentarlo.");
                }
            }

            String opcion = "";
            // Bucle para mantener al cliente vivo hasat que introduzca "Cerrar"
            // Bucle del menú: Solo entra si el login fue exitoso
            while (!opcion.equals("5") && login){
                // Imprimimos el menú en el lado del cliente para que pueda verlo
                System.out.println("\n--- Menú Principal ---" +
                        "\n1. Sumar" +
                        "\n2. Contador" +
                        "\n3. Invierte" +
                        "\n4. EsPrimo" +
                        "\n5. Salir");

                System.out.print("Introduce una opción: ");

                // Recoger opción y enviar al servidor
                opcion = sc.nextLine();
                out.println(opcion);

                // Si la opción es 1, sabemos que el servidor nos va a hablar 3 veces
                if (opcion.equals("1")) {

                    // 1. Recibir petición del primer número
                    String orden1 = in.readLine();
                    if (orden1.equals("num1_sumar")){
                        System.out.print("Introduce el primer número a sumar: ");
                        out.println(sc.nextLine());
                    }

                    // 2. Recibir petición del segundo número
                    String orden2 = in.readLine();
                    if (orden2.equals("num2_sumar")) {
                        System.out.print("Introduce el segundo número a sumar: ");
                        out.println(sc.nextLine());
                    }

                    // 3. Recibir el resultado final e imprimirlo
                    String resultadoFinal = in.readLine();
                    System.out.println("Servidor dice que el resultado es: " + resultadoFinal);
                }

                if (opcion.equals("2")){
                    // 1. Recibir petición de la palabra
                    String orden1 = in.readLine();
                    if (orden1.equals("palabra_contador")){
                        System.out.print("Introduce la palabra para contar sus vocales: ");
                        out.println(sc.nextLine());
                    }

                    // 2. Recibir el resultado final e imprimirlo
                    String numeroVocales = in.readLine();
                    System.out.println("Servidor dice que son " + numeroVocales + " vocales");
                }

                if (opcion.equals("3")){
                    // 1. Recibir petición del texot
                    String orden1 = in.readLine();
                    if (orden1.equals("texto_invertir")){
                        System.out.println("Introduce el texto para invertirlo: ");
                        out.println(sc.nextLine());
                    }

                    // 2. Recibir el resultado final e imprimirlo
                    String textoInvertido = in.readLine();
                    System.out.println("Servidor dice que el texto invertido es: " + textoInvertido);
                }

                if (opcion.equals("4")){
                    // 1. Recibir petición del texot
                    String orden1 = in.readLine();
                    if (orden1.equals("numero_primo")){
                        System.out.println("Introduce un número para compronar si es primo: ");
                        out.println(sc.nextLine());
                    }

                    // 2. Recibir el resultado final e imprimirlo
                    String numeroPrimo = in.readLine();
                    System.out.println( numeroPrimo );
                }
            }
            System.out.println("Desconectado del servidor.");

        } catch (UnknownHostException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        }
    }
}
