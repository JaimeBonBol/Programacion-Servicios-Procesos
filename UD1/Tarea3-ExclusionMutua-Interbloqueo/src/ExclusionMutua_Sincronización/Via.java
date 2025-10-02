package ExclusionMutua_Sincronización;

public class Via {
    private boolean ocupada = false;

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Método para entrar en la vía, si no está ocupada entra, simula que tarda 2 segundos en recorrerla y sale. Si está
     * ocupada hay colisión puesto que no pueden pasar dos coches simultáneamente
     * @param nombreCoche
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

        // Simulación recorrido COche en vía.
        System.out.println(nombreCoche + " entra en la vía.");
        ocupada = true;

        // Simula que tarda 2 segundos en cruzar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Salida de la vía.
        System.out.println(nombreCoche + " sale de la vía.");
        ocupada = false;

        notifyAll(); // despierta a los hilos que estaban esperando

    }

}
