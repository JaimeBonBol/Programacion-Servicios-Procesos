package EjemploComunicacion.Sincronizacion;

public class ProductorComunicacion extends Thread{

    private Recurso recurso;

    public ProductorComunicacion(Recurso recurso){
        this.recurso = recurso;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            recurso.producir(i);

            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }

        }
    }
}
