package EjercicioClase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 1234);
                // Enviar mensaje
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(),true
                );
                // Leer mensaje
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                BufferedReader teclado = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                ){
            // COnectarse al servidor
            System.out.println("Conectado al servidor");
            String opcion;

            while (true){
                // Mostrar menú
                System.out.println("Menú:" +
                        "\n Cifrar" +
                        "\n Descifrar" +
                        "\n Exit");

                // Leemos la opción
                opcion = teclado.readLine();

                // Comprobar si salimos del bucle (Si es nulo o escribe "Exit")
                if (opcion == null || opcion.equals("Exit")){
                    break; // Aquí es donde rompemos el bucle y nos vamos
                }

                // Si llegamos aquí, es porque NO hemos escrito "Exit", así que procesamos
                String respuesta;

                switch (opcion){
                    case "Cifrar":
                    case "Descifrar":
                        // Enviar opción al servidor
                        out.println(opcion);
                        // Recibir la respuesta del servidor
                        respuesta = in.readLine();

                        if (respuesta.equals("Pasame la cadena")){
                            // Enviar cadena
                            System.out.println("Introduzca la cadena");
                            out.println(teclado.readLine());
                            String cadenaDevuelta = in.readLine();
                            System.out.println(opcion + ": " + cadenaDevuelta);
                        }

                        break;
                    default:
                        break;
                }
            }
            out.println("Exit");
            System.out.println(in.readLine());

            /*
            while ((opcion = teclado.readLine())!= null && !opcion.equals("Exit")){
                System.out.println("Menú:" +
                        "\n Cifrar" +
                        "\n Descifrar" +
                        "\n Exit");


                String respuesta;

                switch (opcion){
                    case "Cifrar":
                    case "Descifrar":
                        // Enviar opción al servidor
                        out.println(opcion);
                        // Recibir la respuesta del servidor
                        respuesta = in.readLine();

                        if (respuesta.equals("Pasame la cadena")){
                            // Enviar cadena
                            System.out.println("Introduzca la cadena");
                            out.println(teclado.readLine());
                            String cadenaDevuelta = in.readLine();
                            System.out.println(opcion + ": " + cadenaDevuelta);
                        }

                        break;
                    default:
                        break;
                }
            }
            out.println("Exit");
            System.out.println(in.readLine());
            */

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
