package ActivadEvaluableTrivia;

import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;

public class Cliente {
    private Socket socket;
    private PrintWriter salida;
    private ClienteGUI gui;

    public void iniciar() {
        SwingUtilities.invokeLater(() -> {
            gui = new ClienteGUI(this);
            gui.setVisible(true);
        });
    }

    public void conectar(String ip, int puerto, String nick) {
        try {
            socket = new Socket(ip, puerto);
            salida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            ReceptorHandler receptor = new ReceptorHandler(socket, gui);
            receptor.start();

            // Enviar LOGIN
            enviarMensaje("LOGIN:" + nick);

        } catch (ConnectException e) {
            if (gui != null) gui.mostrarError("Servidor no encontrado en " + ip + ":" + puerto);
        } catch (Exception e) {
            e.printStackTrace();
            if (gui != null) gui.mostrarError("Error al conectar: " + e.getMessage());
        }
    }

    public void enviarMensaje(String msj) {
        if (salida != null) {
            salida.println(msj);
        }
    }

    public void desconectar() {
        try {
            if (salida != null) salida.println("SALIR");
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente().iniciar();
    }
}
