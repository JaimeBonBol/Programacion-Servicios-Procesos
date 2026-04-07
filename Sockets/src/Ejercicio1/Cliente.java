package Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 7000);
                // Enviar mensaje
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(),true
                );
                // Leer mensaje
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
                ){
            // COnectarse al servidor
            System.out.println("Conectado al servidor");

            // Enviar operación
            out.println("Cifrar");
            // Recibir la respuesta del servidor
            String respuesta = in.readLine();

            if (respuesta.equals("Pasame la cadena")){
                // Enviar cadena
                out.println("Hola");
                String cadenaDevuelta = in.readLine();
                System.out.println(cadenaDevuelta);
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
