package ExclusionMutua_AlgoritmoPeterson;

public class Coche extends Thread{

    private int id;
    private String nombre;
    private ViaLock via;

    public Coche(int id, String nombre, ViaLock via){
        this.id = id;
        this.nombre = nombre;
        this.via = via;
    }

    @Override
    public void run() {
        via.entrarVia( nombre);
    }
}
