package Ejemplo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 7000);
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), true
             );
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())
             );
            ){
            // Conectarse al servidor
            System.out.println("conectado al servidor");

            // Enviar un mensaje
            // 2º parámetro para el flush, par que limpie el stream nada más enviar el mensaje

            out.println("Div:2:0");

            // Leer mensaje del cliente

            String respuesta = in.readLine();
            if (respuesta == null){
                throw new ArithmeticException();
            }else {
                System.out.println(in.readLine());
            }

            // socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        } catch (ArithmeticException e) {
            System.err.println("División entre 0");
        }
    }
}
