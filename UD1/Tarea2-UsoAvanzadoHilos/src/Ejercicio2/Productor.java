package Ejercicio2;

public class Productor extends Thread{

    private Buffer buffer;
    private String nombre;

    public Productor(Buffer buffer, String nombre){
        this.buffer = buffer;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            buffer.producir(nombre);

            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
