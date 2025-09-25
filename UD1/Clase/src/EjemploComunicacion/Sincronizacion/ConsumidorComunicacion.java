package EjemploComunicacion.Sincronizacion;

public class ConsumidorComunicacion extends Thread{

    private Recurso recurso;

    public ConsumidorComunicacion(Recurso recurso){
        this.recurso = recurso;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            recurso.consumir();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
