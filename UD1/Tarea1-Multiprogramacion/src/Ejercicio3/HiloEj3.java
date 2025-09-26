package Ejercicio3;

import java.io.*;

public class HiloEj3 extends Thread{

    private String nombre;
    private String rutaArchivo;

    public HiloEj3( String nombre, String rutaArchivo) {
        this.nombre = nombre;
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * Manera Secuencial
     */
    public void secuencialRun(){
        try {
            // Se lee el archivo desde la ruta que le pasemos al hilo.
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            File archivo = new File(rutaArchivo);

            String linea;
            int numLineas = 0;
            int numInfo = 0;
            int numWarn = 0;
            int numError = 0;

            // Se almacenan las palabras por línea y se van contando las líneas que tiene el archivo.
            // También se cuentan los tipos de registros (INFO, WARN, ERROR).
            while ((linea = br.readLine()) != null){
                String[] palabrasLinea = linea.split(";");

                if (palabrasLinea[0].equalsIgnoreCase("INFO")){
                    numInfo ++;
                } else if (palabrasLinea[0].equalsIgnoreCase("WARN")) {
                    numWarn ++;
                } else if (palabrasLinea[0].equalsIgnoreCase("ERROR")) {
                    numError ++;
                }

                numLineas ++;
            }

            br.close();
            System.out.println("Número total de líneas (registros) en el archivo " + archivo.getName() +": " + numLineas);
            System.out.println("INFO: " + numInfo);
            System.out.println("WARN: " + numWarn);
            System.out.println("ERROR: " + numError);

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Concurrente
     */
    @Override
    public void run() {
        secuencialRun();
    }
}
