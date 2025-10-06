package SolucionExclusionMutua_Sincronización;


public class Coche extends Thread{

    private String nombre;
    private Via via;

    public Coche(String nombre, Via via){
        this.nombre = nombre;
        this.via = via;
    }

    @Override
    public void run() {
        via.entrarVia(nombre);
        System.out.println();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Via getVia() {
        return via;
    }

    public void setVia(Via via) {
        this.via = via;
    }
}
