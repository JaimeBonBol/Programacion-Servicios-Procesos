package ProblemaConcurrencia;

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
    public void entrarVia(String nombreCoche){
        System.out.println(nombreCoche + " intenta entrar...");

        if (!ocupada){
            System.out.println(nombreCoche + " entra en la vía.");
            ocupada = true;

            try {
                Thread.sleep(2000); // cruza la vía
            } catch (InterruptedException e) {}

            System.out.println(nombreCoche + " sale de la vía.");
            ocupada = false;
        } else {
            System.out.println("COLISIÓN! " + nombreCoche + " intentó entrar mientras la vía estaba ocupada.");
        }
    }

}
