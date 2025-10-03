package ExclusionMutua_Semaphore;


import ExclusionMutua_AlgoritmoPeterson.Via;

public class Main {
    public static void main(String[] args) {

        // Se crea la via compartida. (Seccion crítica).
        ViaSemaforo viaSemaforo = new ViaSemaforo();

        // Se crean varios coches que comparten la misma vía.
        Coche coche1 = new Coche("Coche1", viaSemaforo);
        Coche coche2 = new Coche("Coche2", viaSemaforo);
        Coche coche3 = new Coche("Coche3", viaSemaforo);
        Coche coche4 = new Coche("Coche4", viaSemaforo);

        // Iniciamos los hilos (coches).
        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();

    }

}
