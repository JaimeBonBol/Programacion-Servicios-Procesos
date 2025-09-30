package ProblemaConcurrencia;

public class Main {
    public static void main(String[] args) {

        // Se crea la via compartida. (Seccion crítica).
        Via via = new Via();

        // Se crean los coches.
        Coche coche1 = new Coche("Coche1");
        Coche coche2 = new Coche("Coche2");
        Coche coche3 = new Coche("Coche3");
        Coche coche4 = new Coche("Coche4");

        // Se le asignan a los coches la via.
        coche1.setVia(via);
        coche2.setVia(via);
        coche3.setVia(via);
        coche4.setVia(via);

        // Iniciamos los hilos (coches).
        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();


    }

}
