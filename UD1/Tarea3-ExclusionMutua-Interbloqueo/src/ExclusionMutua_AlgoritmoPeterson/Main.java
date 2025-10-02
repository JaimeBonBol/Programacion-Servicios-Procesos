package ExclusionMutua_AlgoritmoPeterson;


public class Main {
    public static void main(String[] args) {

        // Se crea la via compartida. (Seccion crítica).
        Via via = new Via();
        ViaSemaforo viaSemaforo = new ViaSemaforo();
        ViaLock viaLock = new ViaLock();

        Coche coche1 = new Coche(0, "Coche1", viaLock);
        Coche coche2 = new Coche(1, "Coche2", viaLock);

        coche1.start();
        coche2.start();

    }

}
