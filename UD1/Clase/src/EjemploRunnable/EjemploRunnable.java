package EjemploRunnable;

public class EjemploRunnable {
    public static void main(String[] args) {

        ejemploRunnable();

    }

    public static void ejemploRunnable(){
        HiloR tarea1 = new HiloR("Hilo con runnable 1");
        HiloR tarea2 = new HiloR("Hilo con runnable 2");

        // Creamos los hilos pasándole las tareas(hilos con interfaz runnable)
        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);

        hilo1.start();
        hilo2.start();

        System.out.println("Hilos siguen ejecutandose");

    }
}
