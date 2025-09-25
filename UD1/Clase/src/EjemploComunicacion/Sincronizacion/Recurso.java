package EjemploComunicacion.Sincronizacion;

public class Recurso {
    private int valor;
    private boolean disponible = false;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Método para que el productor escriba un valor
    // Con el synchronized aseguramos que si hay mas de un hilo que utiliza este mismo recurso, solo ejecuta este método
    // un hilo (el primero que llege hecha el cerrojo).
    public synchronized void producir(int nuevoValor){
        // Si ya hay dato esperando, el productor espera.
        while (disponible) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        valor = nuevoValor;
        disponible = true;

        System.out.println("Productor produjo " + valor);

        notify(); // Avisa al consumidor.
    }

    // Método para que el conusmidor lea un valor
    public synchronized int consumir(){
        // Si no hay dato aún, el conusmidor espera.
        while (!disponible){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        disponible = false;
        System.out.println("Consumidor consumió: " + valor);

        notify();   // Avia al productor.

        return valor;
    }

}
