package Ejercicio2;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Productor productor1 = new Productor(buffer);
        Consumidor consumidor1  = new Consumidor(buffer);

        /*Productor productor2 = new Productor(buffer);
        Consumidor consumidor2 = new Consumidor(buffer);*/

        productor1.start();
        consumidor1.start();
        /*productor2.start();
        consumidor2.start();*/
    }
}
