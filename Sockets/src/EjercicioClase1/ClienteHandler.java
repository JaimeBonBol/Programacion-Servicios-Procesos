package EjercicioClase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClienteHandler extends Thread{
    private Socket cliente;

    public ClienteHandler(Socket socket) {
        this.cliente = socket; // Recibe el socket de la conexión
    }

    @Override
    public void run() {
        try (

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
            String operacion;
            while ((operacion = in.readLine()) != null && !operacion.equals("Exit")){
                // Recuperar la cadena deel cliente
                String cadena = null;
                switch (operacion){
                    case "Cifrar":
                        out.println("Pasame la cadena");
                        cadena = in.readLine();
                        out.println(cifrarCadena(cadena));
                        break;
                    case "Descifrar":
                        out.println("Pasame la cadena");
                        cadena = in.readLine();
                        out.println(descifrarCadena(cadena));
                        break;
                    default:
                        out.println("Operación no válida");
                }
            }
            out.println("Adios");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String cifrarCadena(String cadena) {
        int rango = ('z' - 'a') + 1;
        int desplazamiento = 3;
        String resultado = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (c >= 'a' && c<= 'z'){
                resultado += (char) (((c - 'a' + desplazamiento) % rango) + 'a');
            }else if(c >= 'A' && c<= 'Z'){
                resultado += (char) (((c-'A' + desplazamiento) % rango) + 'A');
            }else {
                resultado += c;
            }
        }
        return resultado;
    }

    public static String descifrarCadena(String cadena) {
        int rango = ('z' - 'a') + 1;
        int desplazamiento = 3;
        String resultado = "";
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            if (c >= 'a' && c<= 'z'){
                resultado += (char) (((c - 'a' - desplazamiento + rango) % rango) + 'a');
            }else if(c >= 'A' && c<= 'Z'){
                resultado += (char) (((c-'A' - desplazamiento + rango) % rango) + 'A');
            }else {
                resultado += c;
            }
        }
        return resultado;
    }

}
