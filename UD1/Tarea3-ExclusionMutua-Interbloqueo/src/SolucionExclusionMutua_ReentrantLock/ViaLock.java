package SolucionExclusionMutua_ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class ViaLock {

    // Lock que controla la exclusión mutua
    private ReentrantLock lock = new ReentrantLock();


    public void entrarVia(String nombreCoche) {

        try {
            // Intenta tomar el lock, espera máximo 1 segundo
            if (lock.tryLock(1, TimeUnit.SECONDS)){
                try {

                    System.out.println(nombreCoche + " entra en la vía.");
                    Thread.sleep(2000);
                    System.out.println(nombreCoche + " sale de la vía.");

                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }finally {
                    // Libera el lock
                    lock.unlock();
                }

            }else {
                // Si no consigue el lock en un segundo hace otra cosa.
                System.out.println(nombreCoche + " se cansó de esperar.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
