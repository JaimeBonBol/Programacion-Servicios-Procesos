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
                String letra = String.valueOf(c);

                // Paso la vocal a minúscula para asegurar que entran en los case ya sea mayuscula o minuscula.
                // En cada caso comprueba con los métodos auxiliares si está entre las opciones (normal,. con tilde, diéresis)
                switch (vocalBuscar.toLowerCase()) {
                    case "a":
                        if (opcionesA(letra)){
                            contadorVocal++;
                        }
                        break;
                    case "e":
                        if (opcionesE(letra)){
                            contadorVocal++;
                        }
                        break;
                    case "i":
                        if (opcionesI(letra)){
                            contadorVocal++;
                        }
                        break;
                    case "o":
                        if (opcionesO(letra)){
                            contadorVocal++;
                        }
                        break;
                    case "u":
                        if (opcionesU(letra)){
                            contadorVocal++;
                        }
                        break;
                }
            }

            String contajeVocal = String.valueOf(contadorVocal);
            bw.write("Contaje " + vocalBuscar + ":" + contajeVocal);

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

    public boolean opcionesA(String vocal){
        if (vocal.equalsIgnoreCase("a") || vocal.equalsIgnoreCase("á")){
            return true;
        }
        return false;
    }

    public boolean opcionesE(String vocal){
        if (vocal.equalsIgnoreCase("e") || vocal.equalsIgnoreCase("é")){
            return true;
        }
        return false;
    }

    public boolean opcionesI(String vocal){
        if (vocal.equalsIgnoreCase("i") || vocal.equalsIgnoreCase("í")){
            return true;
        }
        return false;
    }

    public boolean opcionesO(String vocal){
        if (vocal.equalsIgnoreCase("o") || vocal.equalsIgnoreCase("ó")){
            return true;
        }
        return false;
    }

    public boolean opcionesU(String vocal){
        if (vocal.equalsIgnoreCase("u") || vocal.equalsIgnoreCase("ú") || vocal.equalsIgnoreCase("ü")){
            return true;
        }
        return false;
    }
}
