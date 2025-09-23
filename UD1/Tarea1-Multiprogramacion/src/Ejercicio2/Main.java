package Ejercicio2;

public class Main {
    public static void main(String[] args) {
        try {
            HiloEj2 hiloA = new HiloEj2("Descargando datos...", 2, 3);
            HiloEj2 hiloB = new HiloEj2("Procesando...", 3, 2);
            HiloEj2 hiloC = new HiloEj2("Guardando...", 1, 5);

            // Llamar a el método run, que se ejectura las veces x dependiendo del num de iteraciones que se haya pasado
            // por parámetros, igual que el mensaje y el tiempo entr intervalo.
            hiloA.start();
            hiloB.start();
            hiloC.start();

            // Esperar a que terminen los tres hilos.
            hiloA.join();
            hiloB.join();
            hiloC.join();

            System.out.println("Todas las tareas finalizadas.");
        } catch (InterruptedException e) {
            System.out.println("Error: " +e.getMessage());
            e.printStackTrace();
        }
    }
}
