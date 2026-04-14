package ActivadEvaluableTrivia;

import java.io.*;
import java.net.*;

public class ClienteHandler extends Thread {
    private Socket cliente;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nick;
    
    private int puntuacion = 0;
    
    private int indiceCorrectoActual = -1;
    private int respuestaActual = -1; // 0, 1, 2, 3
    
    private long inicioTiempoPregunta = 0;
    private long tiempoRespuesta = -1; // ms

    public ClienteHandler(Socket socket) {
        this.cliente = socket;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream(), "UTF-8"));
            salida = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream(), "UTF-8"), true);

            // Leer primer mensaje de LOGIN:nick
            String msjInit = entrada.readLine();
            if (msjInit != null && msjInit.startsWith("LOGIN:")) {
                nick = msjInit.substring(6).trim();
                if (nick.isEmpty()) nick = "Jugador_" + System.currentTimeMillis();
            } else {
                nick = "Jugador_" + System.currentTimeMillis();
            }
            
            Servidor.agregarCliente(this);
            salida.println("LOGIN_OK");

            String mensajeCliente;
            while ((mensajeCliente = entrada.readLine()) != null) {
                if (mensajeCliente.startsWith("CHAT:")) {
                    String textoChat = mensajeCliente.substring(5);
                    Servidor.procesarChat(nick, textoChat);
                }
                else if (mensajeCliente.startsWith("RESPUESTA:")) {
                    if (respuestaActual == -1) {
                        try {
                            int resp = Integer.parseInt(mensajeCliente.substring(10));
                            if (resp >= 0 && resp <= 3) {
                                respuestaActual = resp;
                                tiempoRespuesta = System.currentTimeMillis() - inicioTiempoPregunta;
                            }
                        } catch (NumberFormatException e) {
                            // ignore
                        }
                    }
                }
                else if (mensajeCliente.equalsIgnoreCase("SALIR")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("[!] " + nick + " se desconecto inesperadamente.");
        } finally {
            Servidor.eliminarCliente(this);
            cerrarConexion();
        }
    }

    public void enviarMensaje(String mensaje) {
        if (salida != null) salida.println(mensaje);
    }

    public void cerrarConexion() {
        try {
            if (cliente != null && !cliente.isClosed()) cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() { return nick; }
    public synchronized int getPuntuacion() { return puntuacion; }
    public synchronized void sumarPuntos(int pts) { puntuacion += pts; }

    public synchronized int getRespuestaActual() { return respuestaActual; }
    public synchronized void setRespuestaActual(int respuestaActual) { this.respuestaActual = respuestaActual; }

    public synchronized int getIndiceCorrectoActual() { return indiceCorrectoActual; }
    public synchronized void setIndiceCorrectoActual(int indiceCorrectoActual) { this.indiceCorrectoActual = indiceCorrectoActual; }

    public synchronized long getTiempoRespuesta() { return tiempoRespuesta; }
    public synchronized void setTiempoRespuesta(long tiempoRespuesta) { this.tiempoRespuesta = tiempoRespuesta; }

    public synchronized void setInicioTiempoPregunta(long inicio) { this.inicioTiempoPregunta = inicio; }
}
