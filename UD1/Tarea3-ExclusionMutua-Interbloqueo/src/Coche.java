public class Coche extends Thread{

    private String nombre;
    private boolean pasando;

    public Coche(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Pasando por la vía");
        pasando = true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPasando() {
        return pasando;
    }

    public void setPasando(boolean pasando) {
        this.pasando = pasando;
    }
}
