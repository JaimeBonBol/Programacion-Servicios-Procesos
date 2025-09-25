package EjemploComunicacion;

public class Consumidor extends Thread{

    private Compartido recurso;

    public Consumidor(Compartido recurso){
        this.recurso = recurso;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Consumidor leyó: " + recurso.getValor());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
