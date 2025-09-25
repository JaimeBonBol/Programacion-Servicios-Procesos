package Procesos;

import java.io.IOException;
import java.util.Scanner;

public class EjecutarProceso {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int proceso;

        do {

            System.out.println("Que proceso quieres ejecutar:" +
                    "\n 1. Terminal" +
                    "\n 2. Firefox" +
                    "\n 3. Ejecutar proceso secuencial con contador" +
                    "\n 4. Ejecutar proceso concurrente con 2 hilos en paralelo" +
                    "\n 0. Salir");
            proceso = sc.nextInt();

            switch (proceso){
                case 1:
                    ejemploProceso1();
                    break;
                case 2:
                    ejemploProceso2();
                    break;

                case 3:
                    ejemploSecuencial3();
                    break;

                case 4:
                    ejemploConcurrente3();
                    break;

                case 0:
                    System.out.println("Saliendo... ");
                    break;

                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }while (proceso != 0);

    }

    // lanza una terminal
    public static void ejemploProceso1(){
        try {
            ProcessBuilder pb = new ProcessBuilder("x-terminal-emulator");
            Process proceso = pb.start();

            System.out.println("Se ha lanzado una terminal");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Lanza firefox
    public static void ejemploProceso2(){
        try {
            // Lnazar firefox
            ProcessBuilder pb = new ProcessBuilder("firefox");
            Process proceso = pb.start();

            System.out.println("Se ha lanzado firefox. Cierra la ventana para continuar.");

            // Esperar a que se cierre la aplicación (proceso)
            int exitCode = proceso.waitFor();
            System.out.println("Firefox se cerró. Código de salida: " + exitCode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ejecución secuencial
     */
    public static void ejemploSecuencial3(){
        try {

            System.out.println(" --- Ejecunción secuencial ---");
            long inicioTiempo = System.currentTimeMillis();

            Contador contador1 = new Contador("Contador 1");
            Contador contador2 = new Contador("Contador 2");

            contador1.secuencialRun();
            contador2.secuencialRun();

            long finTiempo = System.currentTimeMillis();

            System.out.println("Timepo total secuencial: " + ((finTiempo - inicioTiempo)/1000));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ejecución concurrente
     */
    public static void ejemploConcurrente3(){
        try {

            System.out.println(" --- Ejecunción concurrente ---");
            long inicioTiempo = System.currentTimeMillis();

            Contador hilo1 = new Contador("Contador 1");
            Contador hilo2 = new Contador("Contador 2");

            hilo1.start(); // Llama al método run de la clase Contador
            hilo2.start(); // Llama al método run de la clase Contador

            // Esperar a que terminen ambos hilos
            hilo1.join();
            hilo2.join();

            long finTiempo = System.currentTimeMillis();

            System.out.println("Timepo total concurrente: " + ((finTiempo - inicioTiempo)/1000));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
