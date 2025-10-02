package ExclusionMutua_AlgoritmoPeterson;

public class Via {

    /**
     * solicitud[i] para indicar si cada coche quiere entrar. Cada coche tendrá un indice en, el 0 y el 1.
     * turno indica a quién le toca el turno, si ambos quieren el turno se le da prioridad al otro hilo.
     */
    private boolean[] solicitud = new boolean[2];
    private int turno;

    public void entrarVia(int indiceSolicitud, String nombreCoche){
        System.out.println(nombreCoche + " intenta entrar...");

        // PAra saber el indice del otro coche.
        int otro = 1 - indiceSolicitud;

        // Coche quiere entrar.
        solicitud[indiceSolicitud] = true;
        turno = otro;

        // Mientras que el otro coche ha solicitado y sea su turno hace una espera activa (busy waiting).
        while (solicitud[otro] == true && turno == otro){

        }

        // Simulación recorrido coche en vía
        System.out.println(nombreCoche + " entra en la vía.");

        // Simula que tarda 2 segundos en cruzar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Salida de la vía.
        System.out.println(nombreCoche + " sale de la vía.");

        // Avisa que ha terminado.
        solicitud[indiceSolicitud] = false;

    }

}
