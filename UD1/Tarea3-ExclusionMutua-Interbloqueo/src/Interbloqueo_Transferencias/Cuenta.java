package Interbloqueo_Transferencias;

// Creamos la clase de Cuenta en la que solo tenemos el atributo de saldo
public class Cuenta{
    double saldo;
    String nombre;

    public Cuenta(double saldo, String nombre) {
        this.saldo = saldo;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void Retirar(double cantidad){
       saldo -= cantidad;
       notify();
    }

    public void Ingresar(double cantidad){
        saldo += cantidad;
        notify();
    }

}
