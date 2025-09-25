package Ejercicio2;

public class Productor extends Thread{

    private Buffer buffer;

    public Productor(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            buffer.producir();
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
