package Interbloqueo_Transferencias;

public class Transferencia extends Thread{
    private Cuenta origen;
    private Cuenta destino;
    private double cantidad;
    private String nombre;

    public Transferencia(Cuenta origen, Cuenta destino, double cantidad, String nombre){
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
    }

    // Aquí provocamos el deadlock, ya que cuando en el main lanzamos dos hilos a la vez, entran en un bucle infinito de espera de desbloqueo del otro recurso
    @Override
    public void run(){
        synchronized (origen){
            System.out.println(nombre + " ha bloqueado la cuenta de origen");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
            synchronized (destino){
                System.out.println(nombre + " ha bloqueado la cuenta de destino");

                if (cantidad <= origen.getSaldo()){
                    origen.Retirar(cantidad);
                    destino.Ingresar(cantidad);
                    System.out.println("Se ha realizado la transferenia con éxito");
                    System.out.println("Cuenta: " + destino.getNombre() + "; Saldo: " + destino.getSaldo());
                } else {
                    System.out.println("No se puede retirar " + cantidad + " porque supera al saldo de " + origen.getNombre() + ": " + origen.getSaldo());
                }
            }
        }
    }
}
