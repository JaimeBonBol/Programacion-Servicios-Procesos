package Ejercicio1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String rutaFicheroLectura = "Tarea2-UsoAvanzadoHilos/src/Ejercicio1/texto.txt";
        String rutaFicheroAlmacenarA = "Tarea2-UsoAvanzadoHilos/src/Ejercicio1/vocalA.txt";
        String rutaFicheroAlmacenarE = "Tarea2-UsoAvanzadoHilos/src/Ejercicio1/vocalE.txt";
        String rutaFicheroAlmacenarI = "Tarea2-UsoAvanzadoHilos/src/Ejercicio1/vocalI.txt";
        String rutaFicheroAlmacenarO = "Tarea2-UsoAvanzadoHilos/src/Ejercicio1/vocalO.txt";
        String rutaFicheroAlmacenarU = "Tarea2-UsoAvanzadoHilos/src/Ejercicio1/vocalU.txt";

        File ficheroLectura = new File(rutaFicheroLectura);
        File ficheroA = new File(rutaFicheroAlmacenarA);
        File ficheroE = new File(rutaFicheroAlmacenarE);
        File ficheroI = new File(rutaFicheroAlmacenarI);
        File ficheroO = new File(rutaFicheroAlmacenarO);
        File ficheroU = new File(rutaFicheroAlmacenarU);

        AlmacenadorVocal h1 = new AlmacenadorVocal("Hilo1", "a", ficheroLectura, ficheroA);
        AlmacenadorVocal h2 = new AlmacenadorVocal("Hilo2", "e", ficheroLectura, ficheroE);
        AlmacenadorVocal h3 = new AlmacenadorVocal("Hilo3", "i", ficheroLectura, ficheroI);
        AlmacenadorVocal h4 = new AlmacenadorVocal("Hilo4", "o", ficheroLectura, ficheroO);
        AlmacenadorVocal h5 = new AlmacenadorVocal("Hilo5", "u", ficheroLectura, ficheroU);

        // Comienzan a buscar vocales los 5 hilos al mismo tiempo y almacenan los subtotales en ficheroA, ficheroB, etc.
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        // Esperar a que todos los hilos hayan terminado de contar la vocal correspondiente.
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();

            System.out.println();
            // Cuenta los subtotales de cada fichero y luego se suman para obtener el total.
            int subtotalFicheroA = recuperarResultado(ficheroA);
            int subtotalFicheroE = recuperarResultado(ficheroE);
            int subtotalFicheroI = recuperarResultado(ficheroI);
            int subtotalFicheroO= recuperarResultado(ficheroO);
            int subtotalFicheroU = recuperarResultado(ficheroU);

            // Calcular el total
            int total = subtotalFicheroA + subtotalFicheroE + subtotalFicheroI + subtotalFicheroO + subtotalFicheroU;

            System.out.println("El total de vocales en el fichero " +ficheroLectura.getName() + " es de " + total);

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }

    public static int recuperarResultado(File fichero){
        int contaje = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));


            String linea;
            while ((linea = br.readLine()) != null){
                contaje = Integer.parseInt(linea);
            }

            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return contaje;
    }
}
