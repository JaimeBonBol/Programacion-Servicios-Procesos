package ActivadEvaluableTrivia;

import java.util.*;

public class AdminHandler extends Thread {
    
    @Override
    public void run() {
        System.out.println(">>> PARTIDA INICIADA con " + Servidor.clientes.size() + " jugador(es)! <<<");
        iniciarJuego();
    }

    private void iniciarJuego() {
        try {
            int tiempoEspera = 5000;
            // Damos 3 segundos para mostrar la pantalla de juego en los clientes
            Thread.sleep(3000);

            for (int i = 0; i < Servidor.preguntasSeleccionadas.size(); i++) {
                Pregunta p = Servidor.preguntasSeleccionadas.get(i);
                System.out.println("\n--- Pregunta " + (i + 1) + " de " + Servidor.preguntasSeleccionadas.size() + " ---");
                System.out.println("Pregunta: " + p.getTextoPregunta());

                synchronized (Servidor.clientes) {
                    for (ClienteHandler c : Servidor.clientes) {
                        c.setRespuestaActual(-1);
                        c.setTiempoRespuesta(-1);
                        
                        // Barajar opciones para anti-copia
                        List<String> opcionesList = new ArrayList<>(Arrays.asList(p.getOpciones()));
                        String respuestaCorrectaTexto = p.getOpciones()[p.getIndiceCorrecta()];
                        Collections.shuffle(opcionesList);
                        
                        int nuevoIndiceCorrecta = opcionesList.indexOf(respuestaCorrectaTexto);
                        c.setIndiceCorrectoActual(nuevoIndiceCorrecta);
                        
                        // Formato: PREGUNTA:numero:total:categoria:textoPregunta:op1:op2:op3:op4
                        String msj = "PREGUNTA:" + (i + 1) + ":" + Servidor.preguntasSeleccionadas.size() + ":" 
                                   + p.getCategoria() + ":" + p.getTextoPregunta() + ":"
                                   + opcionesList.get(0) + ":" + opcionesList.get(1) + ":" 
                                   + opcionesList.get(2) + ":" + opcionesList.get(3);
                        c.enviarMensaje(msj);
                        c.setInicioTiempoPregunta(System.currentTimeMillis());
                    }
                }

                Thread.sleep(tiempoEspera);

                System.out.println("- Evaluando respuestas de la Pregunta " + (i + 1) + " -");
                synchronized (Servidor.clientes) {
                    for (ClienteHandler c : Servidor.clientes) {
                        int index = c.getRespuestaActual();
                        if (index == c.getIndiceCorrectoActual()) {
                            // Calcular puntos por velocidad
                            long tiempoTardado = c.getTiempoRespuesta();
                            int puntosRonda = 1; // base
                            if (tiempoTardado <= 5000) puntosRonda = 3;
                            else if (tiempoTardado <= 10000) puntosRonda = 2;
                            
                            c.sumarPuntos(puntosRonda);
                            c.enviarMensaje("RESULTADO:CORRECTO:" + puntosRonda + ":" + c.getPuntuacion());
                            System.out.println("  " + c.getNick() + " -> CORRECTO (" + puntosRonda + " pts)");
                        } else if (index != -1) {
                            c.enviarMensaje("RESULTADO:INCORRECTO:0:" + c.getPuntuacion());
                            System.out.println("  " + c.getNick() + " -> INCORRECTO");
                        } else {
                            c.enviarMensaje("RESULTADO:TIEMPO:0:" + c.getPuntuacion());
                            System.out.println("  " + c.getNick() + " -> SIN RESPUESTA");
                        }
                    }
                }

                if (i < Servidor.preguntasSeleccionadas.size() - 1) {
                    Thread.sleep(5000);
                }
            }

            Servidor.enviarRankingFinal();
            Servidor.guardarHistorial();

            Thread.sleep(5000);
            System.out.println("\n[INFO] Desconectando clientes y apagando...");
            synchronized (Servidor.clientes) {
                for (ClienteHandler c : Servidor.clientes) {
                    c.cerrarConexion();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
