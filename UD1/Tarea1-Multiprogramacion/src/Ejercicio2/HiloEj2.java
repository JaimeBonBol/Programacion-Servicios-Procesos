package Ejercicio2;

public class HiloEj2 extends Thread{

    private String mensaje;
    private int tiempoEjecucion;
    private int iteraciones;


    public HiloEj2(String mensaje, int tiempoEjecucion, int iteraciones){
        this.mensaje = mensaje;
        this.tiempoEjecucion = tiempoEjecucion;
        this.iteraciones = iteraciones;
    }

    @Override
    public void run() {
        // Repite el bucle el num de veces que le pase a la instancia del hilo.
        for (int i = 0; i < iteraciones; i++) {
            // Muestra el mensaje según el mensaje que le pase a la instancia del hilo.
            System.out.println(mensaje);

            try {
                // Espera x segundos (el tiempo que le pase a la instacia del hilo).
                Thread.sleep(tiempoEjecucion * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
