package EjemploComunicacion;

import EjemploComunicacion.Sincronizacion.ConsumidorComunicacion;
import EjemploComunicacion.Sincronizacion.ProductorComunicacion;
import EjemploComunicacion.Sincronizacion.Recurso;

public class Main {
    public static void main(String[] args) {
        // ejemploComunicacionBasico();

        ejemploSinc();
    }

    public static void ejemploComunicacionBasico(){
        Compartido recurso = new Compartido();  // int valor

        Productor productor = new Productor(recurso);
        Consumidor consumidor = new Consumidor(recurso);

        productor.start();
        consumidor.start();

    }

    public static void ejemploSinc(){
        Recurso recurso = new Recurso();

        ProductorComunicacion productorComunicacion = new ProductorComunicacion(recurso);
        ConsumidorComunicacion consumidorComunicacion = new ConsumidorComunicacion(recurso);

        productorComunicacion.start();
        consumidorComunicacion.start();
    }
}
