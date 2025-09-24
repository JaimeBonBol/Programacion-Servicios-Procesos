package Ejercicio3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("¿De qué forma quieres ejecutarlo?" +
                    "\n1. Secuencial" +
                    "\n2. Concurrente." +
                    "\n0. Salir");

            opcion = sc.nextInt();

            switch (opcion){
                case 1:
                    maneraSecuencial();
                    System.out.println();
                    break;

                case 2:
                    maneraConcurrente();
                    System.out.println();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }while (opcion != 0);

    }

    public static void maneraSecuencial(){
        String rutaArchivo1 = "Tarea1-Multiprogramacion/src/Ejercicio3/registros1.txt";
        String rutaArchivo2 = "Tarea1-Multiprogramacion/src/Ejercicio3/registros2.txt";

        HiloEj3 hilo1 = new HiloEj3("Hilo 1", rutaArchivo1);
        HiloEj3 hilo2 = new HiloEj3("Hilo 2", rutaArchivo2);

        try {
            // --- SECUENCIAL ---
            System.out.println("--- Comenzando de forma SECUENCIAL ---");
            long inicioTiempo = System.currentTimeMillis();

            hilo1.secuencialRun();
            hilo2.secuencialRun();

            long finTiempo = System.currentTimeMillis();

            System.out.println();
            System.out.println("Fin de forma SECUENCIAL, timepo transcurrido: " + (finTiempo - inicioTiempo) / 1000.0 );

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void maneraConcurrente(){
        String rutaArchivo1 = "Tarea1-Multiprogramacion/src/Ejercicio3/registros1.txt";
        String rutaArchivo2 = "Tarea1-Multiprogramacion/src/Ejercicio3/registros2.txt";

        HiloEj3 hilo1 = new HiloEj3("Hilo 1", rutaArchivo1);
        HiloEj3 hilo2 = new HiloEj3("Hilo 2", rutaArchivo2);

        try {
            // CONCURRENTE
            System.out.println("--- Comenzando de forma CONCURRENTE ---");
            long inicioTiempo = System.currentTimeMillis();

            // Llama al método run.
            hilo1.start();
            hilo2.start();

            // Esperar a que los 2 hilos terminen.
            hilo1.join();
            hilo2.join();

            long finTiempo = System.currentTimeMillis();

            System.out.println();
            System.out.println("Fin de forma CONCURRENTE, tiempo transcurrido: " + (finTiempo - inicioTiempo) / 1000.0);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
