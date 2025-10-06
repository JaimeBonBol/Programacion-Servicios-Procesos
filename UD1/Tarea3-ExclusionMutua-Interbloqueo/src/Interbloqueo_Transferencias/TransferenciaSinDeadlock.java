package Interbloqueo_Transferencias;

public class TransferenciaSinDeadlock extends Thread{
    private Cuenta origen;
    private Cuenta destino;
    private double cantidad;
    private String nombre;

    public TransferenciaSinDeadlock(Cuenta origen, Cuenta destino, double cantidad, String nombre){
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    @Override
    public void run(){
        // Determinamos el orden del bloqueo
        Cuenta primera = origen;
        Cuenta segunda = destino;

        if (System.identityHashCode(origen) > System.identityHashCode(destino)){
            primera = destino;
            segunda = origen;
        }

        synchronized (primera){
            System.out.println(nombre + " ha bloqueado: " + primera.getNombre());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }

            synchronized (segunda){
                System.out.println(nombre + " ha bloqueado: " + segunda.getNombre());

                if (cantidad <= origen.getSaldo()){
                    origen.Retirar(cantidad);
                    destino.Ingresar(cantidad);
                    System.out.println("Se ha realizado la transferencia con éxito");
                    System.out.println("Cuenta: " + destino.getNombre() + "; Saldo: " + destino.getSaldo());
                } else {
                    System.out.println("ERROR, no se puede retirad " + cantidad + ", supera el saldo de " + origen.getNombre());
                }
            }
        }
    }
}
