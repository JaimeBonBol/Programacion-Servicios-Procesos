package Interbloqueo_Transferencias;

public class Main {
    public static void main(String[] args) {
        Cuenta cuenta1 = new Cuenta(5000, "Cuenta Amparo"); // Se inicializa la cuenta1 con un saldo de 5000
        Cuenta cuenta2 = new Cuenta(1000, "Cuenta Meli"); // Se inicializa la cuenta2 con un saldo de 1000
        String nombre = "Pago hipoteca";
        String nombre2 = "Bizum";

//        Transferencia t1 = new Transferencia(cuenta1, cuenta2, 500, nombre);
//        Transferencia t2 = new Transferencia(cuenta2, cuenta1, 50, nombre2);
//
//        t1.start();
//        t2.start();

        TransferenciaSinDeadlock t3 = new TransferenciaSinDeadlock(cuenta1, cuenta2, 500, nombre);
        TransferenciaSinDeadlock t4 = new TransferenciaSinDeadlock(cuenta2, cuenta1, 60, nombre2);

        t3.start();
        t4.start();
    }
}
