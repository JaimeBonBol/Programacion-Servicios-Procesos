package Ejercicio1;

import java.io.*;

public class AlmacenadorVocal extends Thread{

    private String nombre;

    private String vocalBuscar;

    private File ficheroLectura;

    private File ficheroAlmacenar;

    public AlmacenadorVocal(String nombre, String vocalBuscar, File ficheroLectura, File ficheroAlmacenar){
        this.nombre = nombre;
        this.vocalBuscar = vocalBuscar;
        this.ficheroLectura = ficheroLectura;
        this.ficheroAlmacenar = ficheroAlmacenar;
    }

    public void secuencialRun(){
        try {
            System.out.println(nombre + " comienza a buscar la vocal " + vocalBuscar + " en el archivo " + ficheroLectura.getName());
            long tiempoInicio = System.currentTimeMillis();

            BufferedReader br = new BufferedReader(new FileReader(ficheroLectura));
            BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroAlmacenar));

            int contadorVocal = 0;

            // Leer carácter por carácter
            int caracter;
            while ((caracter = br.read()) != -1){
                char c = (char) caracter;
                if (String.valueOf(c).equalsIgnoreCase(vocalBuscar)) {
                    contadorVocal++;
                }
            }

            bw.write(String.valueOf(contadorVocal));
            bw.close();

            br.close();

            long tiempoFin = System.currentTimeMillis();

            System.out.println(nombre + " ha encontrado un total de " + contadorVocal + " veces la vocal " + vocalBuscar + " en un tiempo de " + (tiempoFin - tiempoInicio) / 1000.0 + " segundos.");
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " comienza a buscar la vocal " + vocalBuscar + " en el archivo " + ficheroLectura.getName());
            long tiempoInicio = System.currentTimeMillis();

            BufferedReader br = new BufferedReader(new FileReader(ficheroLectura));
            BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroAlmacenar));

            int contadorVocal = 0;

            // Leer carácter por carácter
            int caracter;
            while ((caracter = br.read()) != -1){
                char c = (char) caracter;
                if (String.valueOf(c).equalsIgnoreCase(vocalBuscar)) {
                    contadorVocal++;
                }
            }

            bw.write(String.valueOf(contadorVocal));
            bw.close();

            br.close();

            long tiempoFin = System.currentTimeMillis();

            System.out.println(nombre + " ha encontrado un total de " + contadorVocal + " veces la vocal " + vocalBuscar + " en un tiempo de " + (tiempoFin - tiempoInicio) / 1000.0 + " segundos.");
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
