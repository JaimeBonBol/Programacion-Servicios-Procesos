package Ejemplo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor levantado y esperando");
        try ( ServerSocket server = new ServerSocket(7000);
              // BLOQUEO:  Esperando clienetes.
              Socket cliente = server.accept();
              // Leer mensaje del cliente
              BufferedReader in = new BufferedReader(
                      new InputStreamReader(cliente.getInputStream())
              );
              // Enviar un mensaje
              PrintWriter out = new PrintWriter(
                      cliente.getOutputStream(), true
              );
        ) {
            System.out.println("¡Cliente conectado!");

            // Suma:Numero1:Numero2
            // Resta:Numero1:Numero2
            // Exit
            String mensaje = in.readLine();
            String operacion = mensaje.split(":")[0];
            int num1 = Integer.parseInt(mensaje.split(":")[1]);
            int num2 = Integer.parseInt(mensaje.split(":")[2]);

            switch (operacion){
                case "Suma":
                    out.println("Suma de los números " + num1 + " y " + num2 + ": " + (num1 + num2));
                    break;

                case "Resta":
                    out.println("Resta de los números " + num1 + " y " + num2 + ": " + (num1 - num2));
                    break;

                case "Div":
                    out.println("División de los números " + num1 + " y " + num2 + ": " + (num1 / num2));
                    break;
                default:
                    out.println("No válido");
            }

            //server.close();
            //cliente.close();

        } catch (ArithmeticException e) {
            System.err.println("División sobre 0");
        } catch (IOException e) {
            System.err.println("Error de conexión");
        }
    }
}
