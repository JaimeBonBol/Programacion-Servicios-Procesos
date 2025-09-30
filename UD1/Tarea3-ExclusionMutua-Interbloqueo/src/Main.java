import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Coche coche1 = new Coche("Coche1");
        Coche coche2 = new Coche("Coche2");
        Coche coche3 = new Coche("Coche3");
        Coche coche4 = new Coche("Coche4");

        Via via = new Via();

        ArrayList<Coche> coches = new ArrayList<>();
        coches.add(coche1);
        coches.add(coche2);
        coches.add(coche3);
        coches.add(coche4);


        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();

        for (Coche coche : coches){
            if (coche.isPasando()){
                ocuparVia(via);
            }
        }

    }

    public static void ocuparVia(Via via){
        via.setOcupada(true);
    }

    public static void colision(Via via, ArrayList<Coche> coches){
        for (Coche coche : coches){
            if (coche.isPasando() && via.isOcupada()){
                System.out.println("Colisión");
                break;
            }
        }

    }

}
