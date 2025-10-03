package ExclusionMutua_AlgoritmoPeterson;

import java.util.concurrent.Semaphore;

public class ViaSemaforo {

    // Sólo un coche a la vez
    private Semaphore semaphore = new Semaphore(1);

    public void entrarVia(String nombreCoche){

        try {

            semaphore.acquire(); // espera hasta que haya permiso

            System.out.println(nombreCoche + " entra en la vía.");
            Thread.sleep(2000);
            System.out.println(nombreCoche + " sale de la vía.");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // libera el permiso
        }

    }
}
