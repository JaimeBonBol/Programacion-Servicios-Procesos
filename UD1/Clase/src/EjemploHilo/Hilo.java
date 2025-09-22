package EjemploHilo;

public class Hilo extends Thread{
    @Override
    public void run(){
        System.out.println("Ejecutando hilo: " + getName());
    }
}
