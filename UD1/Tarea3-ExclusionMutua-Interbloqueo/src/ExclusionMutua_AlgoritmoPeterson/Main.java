package ExclusionMutua_AlgoritmoPeterson;


public class Main {
    public static void main(String[] args) {

        // Se crea la via compartida. (Seccion crítica).
        Via via = new Via();

        Coche coche1 = new Coche(0, "Coche1", via);
        Coche coche2 = new Coche(1, "Coche2", via);

        coche1.start();
        coche2.start();

    }

}
