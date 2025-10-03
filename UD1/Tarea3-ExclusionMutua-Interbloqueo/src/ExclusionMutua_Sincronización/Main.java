package ExclusionMutua_Sincronización;


public class Main {
    public static void main(String[] args) {

        // Se crea la via compartida. (Seccion crítica).
        Via via = new Via();

        // Se crean varios coches que comparten la misma vía.
        Coche coche1 = new Coche("Coche 1", via);
        Coche coche2 = new Coche("Coche 2", via);
        Coche coche3 = new Coche("Coche 3", via);
        Coche coche4 = new Coche("Coche 4", via);

        // Iniciamos los hilos (coches).
        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();

    }
}
