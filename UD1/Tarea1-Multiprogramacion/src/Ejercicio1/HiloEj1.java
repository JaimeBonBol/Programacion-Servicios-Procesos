package Ejercicio1;

import java.io.IOException;

public class HiloEj1 extends Thread{

    @Override
    public void run() {
        try {
            // Proceso para lanzar firefox
            ProcessBuilder pb = new ProcessBuilder("firefox");

            /**
             * Posible solución para cuando ya exista un proceso de firefox abierto
             */
            //ProcessBuilder pb = new ProcessBuilder("firefox", "--new-instance");

            // Lanza el proceso
            Process proceso = pb.start();

            System.out.println("Se ha lanzado firefox, cierra la ventana para continuar.");
            int exitCode = proceso.waitFor();
            System.out.println("Firefox se cerró, código de salida: " + exitCode);
        } catch (IOException e) {
            System.out.println("Error: " +e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Error: " +e.getMessage());
            e.printStackTrace();
        }

    }
}
