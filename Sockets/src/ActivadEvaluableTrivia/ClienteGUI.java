package ActivadEvaluableTrivia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClienteGUI extends JFrame {
    private Cliente cliente;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // --- LOGIN ---
    private JTextField txtNick;
    private JButton btnConectar;
    
    // --- ESPERA ---
    private JLabel lblJugadores;
    private JTextArea txtEstatus;
    private JTextField txtChat;
    private JTextArea areaChat;
    
    // --- JUEGO ---
    private JLabel lblCategoria;
    private JLabel lblPreguntaTitulo;
    private JTextArea txtPregunta;
    private JButton[] btnOpciones = new JButton[4];
    private JLabel lblTemporizador;
    private JLabel lblInfoPuntos;
    private Timer timerPregunta;
    private int tiempoRestante;
    
    // --- PODIO ---
    private JTextArea txtPodio;
    
    public ClienteGUI(Cliente cliente) {
        this.cliente = cliente;
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Trivia en Red");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
        
        // Cierre con envío de SALIR
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cliente.desconectar();
            }
        });
    }
    
    private void inicializarComponentes() {
        mainPanel.add(crearPanelLogin(), "LOGIN");
        mainPanel.add(crearPanelEspera(), "ESPERA");
        mainPanel.add(crearPanelJuego(), "JUEGO");
        mainPanel.add(crearPanelPodio(), "PODIO");
        
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    // ==============================================================
    // PANELES
    // ==============================================================
    
    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 40));
        
        JLabel titulo = new JLabel("TRIVIA EN RED");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        
        JLabel lblNick = new JLabel("Introduce tu Nick:");
        lblNick.setForeground(Color.LIGHT_GRAY);
        lblNick.setFont(new Font("Arial", Font.PLAIN, 18));
        
        txtNick = new JTextField(15);
        txtNick.setFont(new Font("Arial", Font.PLAIN, 18));
        txtNick.setHorizontalAlignment(JTextField.CENTER);
        
        btnConectar = new JButton("Conectar al Servidor");
        btnConectar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConectar.setBackground(new Color(70, 130, 180));
        btnConectar.setForeground(Color.WHITE);
        btnConectar.setFocusPainted(false);
        btnConectar.addActionListener(e -> {
            String nick = txtNick.getText().trim();
            btnConectar.setEnabled(false);
            cliente.conectar("localhost", 5000, nick);
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(titulo, gbc);
        
        gbc.gridy = 1;
        panel.add(lblNick, gbc);
        
        gbc.gridy = 2;
        panel.add(txtNick, gbc);
        
        gbc.gridy = 3;
        panel.add(btnConectar, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelEspera() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 30, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Cabecera
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("SALA DE ESPERA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblJugadores = new JLabel("Jugadores: ?/10", SwingConstants.CENTER);
        lblJugadores.setFont(new Font("Arial", Font.PLAIN, 18));
        lblJugadores.setForeground(Color.LIGHT_GRAY);
        topPanel.add(lblTitulo);
        topPanel.add(lblJugadores);
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Centro (Logs + Chat)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setOpaque(false);
        
        txtEstatus = new JTextArea();
        txtEstatus.setEditable(false);
        txtEstatus.setBackground(new Color(40, 40, 50));
        txtEstatus.setForeground(Color.GREEN);
        JScrollPane scrollEstatus = new JScrollPane(txtEstatus);
        scrollEstatus.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Eventos", 0, 0, new Font("Arial", Font.PLAIN, 14), Color.WHITE));
        
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setBackground(new Color(40, 40, 50));
        areaChat.setForeground(Color.WHITE);
        JScrollPane scrollChat = new JScrollPane(areaChat);
        scrollChat.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Chat de Sala", 0, 0, new Font("Arial", Font.PLAIN, 14), Color.WHITE));
        
        centerPanel.add(scrollEstatus);
        centerPanel.add(scrollChat);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Pie (Enviar Chat)
        JPanel botPanel = new JPanel(new BorderLayout(5, 0));
        botPanel.setOpaque(false);
        txtChat = new JTextField();
        JButton btnChat = new JButton("Enviar");
        ActionListener envChat = e -> {
            String c = txtChat.getText().trim();
            if(!c.isEmpty()) {
                cliente.enviarMensaje("CHAT:" + c);
                txtChat.setText("");
            }
        };
        btnChat.addActionListener(envChat);
        txtChat.addActionListener(envChat); // Enviar con ENTER
        botPanel.add(txtChat, BorderLayout.CENTER);
        botPanel.add(btnChat, BorderLayout.EAST);
        panel.add(botPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 30, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Cabecera
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setOpaque(false);
        lblCategoria = new JLabel("Categoría", SwingConstants.CENTER);
        lblCategoria.setFont(new Font("Arial", Font.ITALIC, 16));
        lblCategoria.setForeground(Color.ORANGE);
        lblPreguntaTitulo = new JLabel("Pregunta X/Y", SwingConstants.CENTER);
        lblPreguntaTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblPreguntaTitulo.setForeground(Color.WHITE);
        lblTemporizador = new JLabel("Tiempo: 15s", SwingConstants.CENTER);
        lblTemporizador.setFont(new Font("Arial", Font.BOLD, 20));
        lblTemporizador.setForeground(Color.RED);
        topPanel.add(lblCategoria);
        topPanel.add(lblPreguntaTitulo);
        topPanel.add(lblTemporizador);
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Centro (Texto de la pregunta)
        txtPregunta = new JTextArea();
        txtPregunta.setEditable(false);
        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setBackground(new Color(40, 40, 50));
        txtPregunta.setForeground(Color.WHITE);
        txtPregunta.setFont(new Font("Arial", Font.PLAIN, 20));
        txtPregunta.setMargin(new Insets(20, 20, 20, 20));
        panel.add(new JScrollPane(txtPregunta), BorderLayout.CENTER);
        
        // Pie (Opciones)
        JPanel botPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        botPanel.setOpaque(false);
        for(int i=0; i<4; i++) {
            final int index = i;
            btnOpciones[i] = new JButton("Opcion " + i);
            btnOpciones[i].setFont(new Font("Arial", Font.BOLD, 16));
            btnOpciones[i].setBackground(new Color(70, 130, 180));
            btnOpciones[i].setForeground(Color.WHITE);
            btnOpciones[i].setFocusPainted(false);
            btnOpciones[i].addActionListener(e -> {
                enviarRespuesta(index);
            });
            botPanel.add(btnOpciones[i]);
        }
        
        JPanel pnlSur = new JPanel(new BorderLayout(0, 10));
        pnlSur.setOpaque(false);
        pnlSur.add(botPanel, BorderLayout.CENTER);
        lblInfoPuntos = new JLabel("Puntos: 0", SwingConstants.CENTER);
        lblInfoPuntos.setForeground(Color.LIGHT_GRAY);
        lblInfoPuntos.setFont(new Font("Arial", Font.PLAIN, 16));
        pnlSur.add(lblInfoPuntos, BorderLayout.SOUTH);
        
        panel.add(pnlSur, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel crearPanelPodio() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 30, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("FIN DEL JUEGO - RANKING", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        txtPodio = new JTextArea();
        txtPodio.setEditable(false);
        txtPodio.setBackground(new Color(40, 40, 50));
        txtPodio.setForeground(Color.YELLOW);
        txtPodio.setFont(new Font("Consolas", Font.BOLD, 20));
        txtPodio.setMargin(new Insets(20, 20, 20, 20));
        panel.add(new JScrollPane(txtPodio), BorderLayout.CENTER);
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setBackground(new Color(200, 50, 50));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.addActionListener(e -> System.exit(0));
        panel.add(btnSalir, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // ==============================================================
    // METODOS DE ACTUALIZACION DESDE RECEPTOR
    // ==============================================================
    
    public void mostrarError(String err) {
        JOptionPane.showMessageDialog(this, err, "Error", JOptionPane.ERROR_MESSAGE);
        btnConectar.setEnabled(true);
    }
    
    public void mostrarPanelEspera() {
        cardLayout.show(mainPanel, "ESPERA");
    }
    
    public void actualizarJugadoresSala(String cantidad, String evento) {
        lblJugadores.setText("Jugadores: " + cantidad);
        txtEstatus.append(evento + "\n");
        txtEstatus.setCaretPosition(txtEstatus.getDocument().getLength());
    }
    
    public void agregarMensajeChat(String mensaje) {
        areaChat.append(mensaje + "\n");
        areaChat.setCaretPosition(areaChat.getDocument().getLength());
    }
    
    public void mostrarPanelJugar() {
        cardLayout.show(mainPanel, "JUEGO");
    }
    
    public void mostrarPregunta(String num, String total, String categoria, String texto, String op0, String op1, String op2, String op3) {
        lblPreguntaTitulo.setText("Pregunta " + num + " / " + total);
        lblCategoria.setText(categoria);
        txtPregunta.setText(texto);
        
        btnOpciones[0].setText(op0);
        btnOpciones[1].setText(op1);
        btnOpciones[2].setText(op2);
        btnOpciones[3].setText(op3);
        
        for(JButton b : btnOpciones) {
            b.setEnabled(true);
            b.setBackground(new Color(70, 130, 180));
        }
        
        iniciarTemporizador(15);
    }
    
    private void enviarRespuesta(int index) {
        // Bloquear botones
        for(JButton b : btnOpciones) b.setEnabled(false);
        btnOpciones[index].setBackground(new Color(200, 150, 0)); // Color de "seleccionado"
        cliente.enviarMensaje("RESPUESTA:" + index);
    }
    
    private void iniciarTemporizador(int s) {
        if(timerPregunta != null) timerPregunta.stop();
        tiempoRestante = s;
        lblTemporizador.setText("Tiempo: " + tiempoRestante + "s");
        
        timerPregunta = new Timer(1000, e -> {
            tiempoRestante--;
            lblTemporizador.setText("Tiempo: " + tiempoRestante + "s");
            if(tiempoRestante <= 0) {
                timerPregunta.stop();
                for(JButton b : btnOpciones) b.setEnabled(false);
            }
        });
        timerPregunta.start();
    }
    
    public void mostrarResultadoRonda(String estado, String puntosObtenidos, String puntosTotales) {
        if(timerPregunta != null) timerPregunta.stop();
        lblInfoPuntos.setText("Puntos totales: " + puntosTotales);
        
        if (estado.equals("CORRECTO")) {
            JOptionPane.showMessageDialog(this, "¡Respuesta Correcta!\n+" + puntosObtenidos + " Puntos");
        } else if (estado.equals("INCORRECTO")) {
            JOptionPane.showMessageDialog(this, "Respuesta Incorrecta :(");
        } else {
            JOptionPane.showMessageDialog(this, "Se acabó el tiempo.");
        }
    }
    
    public void mostrarPodio(String datos) {
        cardLayout.show(mainPanel, "PODIO");
        txtPodio.setText("");
        // datos = Nick,Pts;Nick2,Pts2;
        String[] lineas = datos.split(";");
        int pos = 1;
        for(String l : lineas) {
            if(!l.isEmpty()) {
                String[] p = l.split(",");
                if(p.length >= 2) {
                    txtPodio.append(pos + ". " + p[0] + " \t -> " + p[1] + " puntos\n");
                    pos++;
                }
            }
        }
    }
}
