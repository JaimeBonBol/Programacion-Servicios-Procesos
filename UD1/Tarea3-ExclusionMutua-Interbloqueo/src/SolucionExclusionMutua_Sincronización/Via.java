package SolucionExclusionMutua_Sincronización;

public class Via {

    // Indica si la vía está en uso.
    private boolean ocupada = false;

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Método sincronizado para que un coche entre en la vía.
     * Si la vía está ocupada, el hilo (coche) espera hasta que quede libre.
     *
     * @param nombreCoche nombre del coche que intenta entrar.
     */
    public synchronized void entrarVia(String nombreCoche){
        System.out.println(nombreCoche + " intenta entrar...");

        // Si ya hay coche dentro, espera.
        while (ocupada) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        // En este punto la vía está libre por lo tanto el coche entra en la vía.
        System.out.println(nombreCoche + " entra en la vía.");
        ocupada = true;

        // Simula que tarda 2 segundos en cruzar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Salida de la vía, libera la vía.
        System.out.println(nombreCoche + " sale de la vía.");
        ocupada = false;

        // Avisa a los hilos (coches) que estaban esperando en wait()
        notifyAll();

    }

}
