package ActivadEvaluableTrivia;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.SwingUtilities;

/**
 * Servidor de Trivia en Red.
 */
public class Servidor {

    public static ArrayList<ClienteHandler> clientes = new ArrayList<>();
    public static boolean juegoIniciado = false;
    public static ArrayList<Pregunta> bancoPreguntas = new ArrayList<>();
    public static ArrayList<Pregunta> preguntasSeleccionadas = new ArrayList<>();
    
    // Referencia a la interfaz gráfica del servidor
    public static ServidorGUI gui;

    public static void main(String[] args) {
        
        // 1. Mostrar la GUI del Servidor primero
        SwingUtilities.invokeLater(() -> {
            gui = new ServidorGUI();
            gui.setVisible(true);
        });
        
        // 3. Iniciar el hilo del ServerSocket
        new Thread(() -> iniciarServidorSocket()).start();
    }
    
    private static void iniciarServidorSocket() {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("========================================");
            System.out.println("   SERVIDOR DE TRIVIA EN RED INICIADO");
            System.out.println("   Puerto: 5000");
            System.out.println("========================================");
            System.out.println("Esperando jugadores... (maximo 10)\n");

            // Bucle principal para aceptar conexiones (hasta que inicie el juego)
            while (!juegoIniciado) {
                server.setSoTimeout(1000);
                try {
                    Socket cliente = server.accept();

                    if (clientes.size() >= 10) {
                        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
                        salida.println("SERVIDOR: La sala esta llena (10/10). Intentalo mas tarde.");
                        cliente.close();
                        continue;
                    }

                    if (juegoIniciado) {
                        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
                        salida.println("SERVIDOR: La partida ya ha comenzado. Intentalo mas tarde.");
                        cliente.close();
                        continue;
                    }

                    ClienteHandler handler = new ClienteHandler(cliente);
                    handler.start();

                } catch (SocketTimeoutException e) {
                    // Ignoramos el timeout. Sirve para revisar juegoIniciado con frecuencia.
                }
            }
            
            // El servidor deja de aceptar conexiones cuando inicia el juego
            System.out.println("[INFO] El juego ha comenzado. No se aceptan más conexiones.");
            server.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean cargarPreguntas(File archivo) {
        bancoPreguntas.clear();
        preguntasSeleccionadas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Saltar encabezado
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(";");
                if (p.length == 7) {
                    bancoPreguntas.add(new Pregunta(p[0], p[1], new String[]{p[2], p[3], p[4], p[5]}, p[6]));
                }
            }
            Collections.shuffle(bancoPreguntas);
            for (int i = 0; i < Math.min(5, bancoPreguntas.size()); i++) {
                preguntasSeleccionadas.add(bancoPreguntas.get(i));
            }
            System.out.println("[INFO] " + bancoPreguntas.size() + " preguntas cargadas. 5 seleccionadas.");
            return !bancoPreguntas.isEmpty();
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo cargar el archivo " + archivo.getName());
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized void agregarCliente(ClienteHandler cliente) {
        clientes.add(cliente);
        System.out.println("[+] " + cliente.getNick() + " se ha unido. Jugadores: " + clientes.size() + "/10");
        enviarATodos("USUARIO_UNIDO:" + cliente.getNick() + ":" + clientes.size());
        if (gui != null) gui.actualizarContadorJugadores(clientes.size());
    }

    public static synchronized void eliminarCliente(ClienteHandler cliente) {
        clientes.remove(cliente);
        System.out.println("[-] " + cliente.getNick() + " se ha desconectado. Jugadores: " + clientes.size() + "/10");
        enviarATodos("USUARIO_SALIO:" + cliente.getNick() + ":" + clientes.size());
        if (gui != null) gui.actualizarContadorJugadores(clientes.size());
    }

    public static synchronized void enviarATodos(String mensaje) {
        for (ClienteHandler c : clientes) {
            c.enviarMensaje(mensaje);
        }
    }

    public static synchronized void procesarChat(String emisor, String mensaje) {
        if (!juegoIniciado) {
            enviarATodos("CHAT:" + emisor + ":" + mensaje);
            System.out.println("[CHAT] " + emisor + ": " + mensaje);
        }
    }

    public static synchronized void enviarRankingFinal() {
        ArrayList<ClienteHandler> ordenados = new ArrayList<>(clientes);
        ordenados.sort((a, b) -> b.getPuntuacion() - a.getPuntuacion());

        StringBuilder rankingLog = new StringBuilder();
        StringBuilder rankingCmd = new StringBuilder("RANKING_FINAL:");
        rankingLog.append("\n--- RANKING FINAL ---\n");
        for (ClienteHandler c : ordenados) {
            rankingLog.append("  ").append(c.getNick()).append(" - ").append(c.getPuntuacion()).append(" puntos\n");
            rankingCmd.append(c.getNick()).append(",").append(c.getPuntuacion()).append(";");
        }
        rankingLog.append("----------------------");

        System.out.println(rankingLog.toString());
        enviarATodos(rankingCmd.toString());
    }

    public static synchronized void guardarHistorial() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("Sockets/src/ActivadEvaluableTrivia/historial.txt", true))) {
            pw.println("=== Partida " + new java.util.Date() + " ===");
            ArrayList<ClienteHandler> ordenados = new ArrayList<>(clientes);
            ordenados.sort((a, b) -> b.getPuntuacion() - a.getPuntuacion());
            for (ClienteHandler c : ordenados) {
                pw.println(c.getNick() + " - " + c.getPuntuacion() + " pts");
            }
            pw.println();
            System.out.println("[INFO] Historial guardado correctamente.");
        } catch (IOException e) {
            System.out.println("[ERROR] No se pudo guardar historial.txt");
            e.printStackTrace();
        }
    }
}
