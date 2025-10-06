package SolucionExclusionMutua_Semaphore;

public class Coche extends Thread{

    private String nombre;
    private ViaSemaforo via;

    public Coche(String nombre, SolucionExclusionMutua_Semaphore.ViaSemaforo via){
        this.nombre = nombre;
        this.via = via;
    }

    @Override
    public void run() {
        via.entrarVia( nombre);
    }
}
