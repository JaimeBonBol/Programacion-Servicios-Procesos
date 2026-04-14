package ActivadEvaluableTrivia;

import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;

public class ReceptorHandler extends Thread {
    private Socket socket;
    private BufferedReader entrada;
    private ClienteGUI gui;

    public ReceptorHandler(Socket socket, ClienteGUI gui) {
        this.socket = socket;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                final String msjFinal = mensaje;
                SwingUtilities.invokeLater(() -> procesarMensaje(msjFinal));
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> gui.mostrarError("Desconectado del servidor."));
        }
    }

    private void procesarMensaje(String msj) {
        if (msj.equals("LOGIN_OK")) {
            gui.mostrarPanelEspera();
        } else if (msj.startsWith("USUARIO_UNIDO:")) {
            String[] partes = msj.split(":");
            if (partes.length >= 3) {
                gui.actualizarJugadoresSala(partes[2] + "/10", partes[1] + " se unió.");
            }
        } else if (msj.startsWith("USUARIO_SALIO:")) {
            String[] partes = msj.split(":");
            if (partes.length >= 3) {
                gui.actualizarJugadoresSala(partes[2] + "/10", partes[1] + " salió.");
            }
        } else if (msj.startsWith("CHAT:")) {
            // FORMATO: CHAT:Emisor:Mensaje
            int idx1 = msj.indexOf(":", 5);
            if (idx1 != -1) {
                String emisor = msj.substring(5, idx1);
                String texto = msj.substring(idx1 + 1);
                gui.agregarMensajeChat(emisor + ": " + texto);
            }
        } else if (msj.equals("JUEGO_INICIADO")) {
            gui.mostrarPanelJugar();
        } else if (msj.startsWith("PREGUNTA:")) {
            // PREGUNTA:numero:total:categoria:texto:op0:op1:op2:op3
            String[] p = msj.split(":");
            if (p.length >= 9) {
                gui.mostrarPregunta(p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8]);
            }
        } else if (msj.startsWith("RESULTADO:")) {
            // FORMATO: RESULTADO:ESTADO:PUNTOS_SUMADOS:PUNTOS_TOTALES
            String[] p = msj.split(":");
            if (p.length >= 4) {
                gui.mostrarResultadoRonda(p[1], p[2], p[3]);
            }
        } else if (msj.startsWith("RANKING_FINAL:")) {
            // RANKING_FINAL:Nick,Puntos;Nick2,Puntos2;
            String data = msj.substring(14);
            gui.mostrarPodio(data);
        }
    }
}
