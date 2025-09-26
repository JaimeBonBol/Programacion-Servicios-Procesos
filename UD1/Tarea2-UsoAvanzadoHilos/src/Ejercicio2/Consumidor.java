package Ejercicio2;

public class Consumidor extends Thread{

    private Buffer buffer;
    private String nombre;

    public Consumidor(Buffer buffer, String nombre){
        this.buffer = buffer;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            buffer.consumir(nombre);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
