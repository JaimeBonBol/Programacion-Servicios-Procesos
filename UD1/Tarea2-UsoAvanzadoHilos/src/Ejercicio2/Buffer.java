package Ejercicio2;

import java.util.ArrayList;
import java.util.Random;

public class Buffer {

    private ArrayList<Integer> datosBuffer = new ArrayList<>();

    // Método para que el productor añada un número aleatorio al buffer si este tiene hueco.
    // Con el synchronized aseguramos que si hay mas de un hilo que utiliza este mismo recurso, solo ejecuta este método
    // un hilo (el primero que llege hecha el cerrojo).
    public synchronized void producir(){
        // Si el arrayList tiene 3 elementos espera.
        while (datosBuffer.size() == 3){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        Random random = new Random();

        // Producce un número aleatorio entre el 0 al 99.
        int numRandom = random.nextInt(100);
        datosBuffer.addFirst(numRandom);
        System.out.println("Productor añadió al buffer el numero " + numRandom);
        mostrarBuffer();

        notify();

    }

    // Método para que el consumidor obtenga un número del buffer si este no está vacío.
    public synchronized void consumir(){
        // Si el buffer está vacío espera.
        while (datosBuffer.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        int numConsumido = datosBuffer.removeLast();
        System.out.println("El consumidor obtuvo del buffer el número " + numConsumido);
        mostrarBuffer();

        notify();
    }

    public void mostrarBuffer(){
        for (int num : datosBuffer){
            System.out.print(num + " ");
        }
        System.out.println();
    }

}
