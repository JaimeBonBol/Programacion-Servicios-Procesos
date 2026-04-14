package ActivadEvaluableTrivia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServidorGUI extends JFrame {
    
    private JTextArea areaLogs;
    private JLabel lblEstado;
    private JLabel lblJugadores;
    private JButton btnIniciar;
    private JButton btnCargarPreguntas;
    private JButton btnVerHistorial;
    
    public ServidorGUI() {
        configurarVentana();
        inicializarComponentes();
        redirigirConsola();
    }
    
    private void configurarVentana() {
        setTitle("Panel de Control - Servidor Trivia");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Al cerrar la ventana, se apaga el servidor
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("[INFO] Cerrando servidor...");
                System.exit(0);
            }
        });
    }
    
    private void inicializarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(40, 45, 52));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel Superior (Estado y Botones)
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        panelInfo.setOpaque(false);
        
        lblEstado = new JLabel("Estado: Esperando jugadores...");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 20));
        lblEstado.setForeground(Color.CYAN);
        
        lblJugadores = new JLabel("Jugadores Conectados: 0 / 10");
        lblJugadores.setFont(new Font("Arial", Font.PLAIN, 16));
        lblJugadores.setForeground(Color.LIGHT_GRAY);
        
        panelInfo.add(lblEstado);
        panelInfo.add(lblJugadores);
        
        btnCargarPreguntas = new JButton("CARGAR PREGUNTAS");
        btnCargarPreguntas.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarPreguntas.setBackground(new Color(70, 130, 180)); // Azul
        btnCargarPreguntas.setForeground(Color.WHITE);
        btnCargarPreguntas.setFocusPainted(false);
        btnCargarPreguntas.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            fileChooser.setDialogTitle("Selecciona el archivo de preguntas (txt)");
            int val = fileChooser.showOpenDialog(this);
            if (val == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                if (Servidor.cargarPreguntas(archivo)) {
                    btnIniciar.setEnabled(true);
                    btnIniciar.setBackground(new Color(60, 150, 80));
                    lblEstado.setText("Estado: Preguntas cargadas. ¡Listo!");
                    btnCargarPreguntas.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Error cargando preguntas. Revisa el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIniciar = new JButton("INICIAR PARTIDA");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIniciar.setBackground(Color.GRAY);
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setEnabled(false); // Deshabilitado hasta cargar preguntas
        btnIniciar.addActionListener(e -> {
            if (Servidor.clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay jugadores conectados. Espera a que se conecte al menos uno.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            btnIniciar.setEnabled(false);
            btnIniciar.setBackground(Color.GRAY);
            lblEstado.setText("Estado: PARTIDA EN CURSO");
            lblEstado.setForeground(Color.GREEN);
            
            // Iniciar lógica del juego
            Servidor.juegoIniciado = true;
            Servidor.enviarATodos("JUEGO_INICIADO");
            
            // Lanzamos el hilo que gestiona las preguntas
            new AdminHandler().start();
        });
        
        btnVerHistorial = new JButton("VER HISTORIAL");
        btnVerHistorial.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerHistorial.setBackground(new Color(200, 150, 0)); // Naranja/Dorado
        btnVerHistorial.setForeground(Color.WHITE);
        btnVerHistorial.setFocusPainted(false);
        btnVerHistorial.addActionListener(e -> mostrarHistorial());

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 5));
        panelBotones.setOpaque(false);
        panelBotones.add(btnCargarPreguntas);
        panelBotones.add(btnVerHistorial);
        panelBotones.add(btnIniciar);
        
        panelTop.add(panelInfo, BorderLayout.CENTER);
        panelTop.add(panelBotones, BorderLayout.EAST);
        
        mainPanel.add(panelTop, BorderLayout.NORTH);
        
        // Panel Central (Logs del Servidor)
        areaLogs = new JTextArea();
        areaLogs.setEditable(false);
        areaLogs.setBackground(new Color(25, 30, 35));
        areaLogs.setForeground(new Color(200, 200, 200));
        areaLogs.setFont(new Font("Consolas", Font.PLAIN, 14));
        
        JScrollPane scrollLogs = new JScrollPane(areaLogs);
        scrollLogs.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Logs del Sistema", 
            0, 0, new Font("Arial", Font.PLAIN, 14), Color.LIGHT_GRAY));
            
        mainPanel.add(scrollLogs, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    // Método para redirigir System.out y System.err al JTextArea
    private void redirigirConsola() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                SwingUtilities.invokeLater(() -> {
                    areaLogs.append(String.valueOf((char) b));
                    areaLogs.setCaretPosition(areaLogs.getDocument().getLength());
                });
            }
        };
        PrintStream ps = new PrintStream(out, true);
        System.setOut(ps);
        System.setErr(ps);
    }
    
    // Método llamado por Servidor para actualizar contador
    public void actualizarContadorJugadores(int count) {
        SwingUtilities.invokeLater(() -> {
            lblJugadores.setText("Jugadores Conectados: " + count + " / 10");
        });
    }

    private void mostrarHistorial() {
        File f = new File("Sockets/src/ActivadEvaluableTrivia/historial.txt");
        if (!f.exists()) {
            JOptionPane.showMessageDialog(this, "El historial está vacío o no se ha creado aún.", "Historial", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())), "UTF-8");
            JTextArea textArea = new JTextArea(contenido);
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            textArea.setBackground(new Color(40, 45, 52));
            textArea.setForeground(new Color(220, 220, 220));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Historial de Partidas", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el historial.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
