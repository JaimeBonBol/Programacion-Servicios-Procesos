package EjemploRunnable;

public class HiloR implements Runnable{

    private String nombre;

    public HiloR(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(nombre + " ejecuta paso " + i);
            try {
                Thread.sleep(1000); // pausa de 1 segundo

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
