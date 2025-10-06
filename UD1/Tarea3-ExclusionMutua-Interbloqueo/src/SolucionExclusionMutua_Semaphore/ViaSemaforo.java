package SolucionExclusionMutua_Semaphore;

import java.util.concurrent.Semaphore;

public class ViaSemaforo {

    // Semáforo binario, solo permite un coche en la vía al mismo tiempo.
    private Semaphore semaphore = new Semaphore(1);


    /**
     * Método que controla la entrada de un coche a la vía.
     * Usa acquire() para tomar un permiso antes de entrar, y release() para liberarlo al salir.
     *
     * @param nombreCoche nombre del coche que intenta entrar.
     */
    public void entrarVia(String nombreCoche){

        try {
            // El coche intenta tomar un permiso, si no hay permisos disponibles, queda bloqueado hasta que alguien libere uno.
            semaphore.acquire();

            System.out.println(nombreCoche + " entra en la vía.");
            Thread.sleep(2000);
            System.out.println(nombreCoche + " sale de la vía.");

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            // Libera el permiso al salir de la vía.
            semaphore.release();
        }

    }
}
