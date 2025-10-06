package SolucionFallidaExclusionMutua_AlgoritmoPeterson;

public class Coche extends Thread{

    private int id;
    private String nombre;
    private Via via;

    public Coche(int id, String nombre, Via via){
        this.id = id;
        this.nombre = nombre;
        this.via = via;
    }

    @Override
    public void run() {
        via.entrarVia(id, nombre);
    }
}
