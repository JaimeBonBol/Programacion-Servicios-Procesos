package ExclusionMutua_AlgoritmoPeterson;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ViaLock {

    private ReentrantLock lock = new ReentrantLock();

    public void entrarVia(String nombreCoche) {


        try {
            // intenta tomar el lock, espera máximo 1 segundo
            if (lock.tryLock(1, TimeUnit.SECONDS)){
                try {

                    System.out.println(nombreCoche + " entra en la vía.");
                    Thread.sleep(2000);
                    System.out.println(nombreCoche + " sale de la vía.");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }finally {
                    lock.unlock(); // libera el lock
                }


            }else {
                System.out.println(nombreCoche + " se cansó de esperar.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
