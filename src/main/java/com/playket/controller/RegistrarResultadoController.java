package com.playket.controller;

import com.playket.database.PartidoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaClasificacionLiga;
import com.playket.view.VentanaCuadroEliminacion;
import com.playket.view.VentanaRegistrarResultado;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrarResultadoController {

    private final VentanaRegistrarResultado vista;
    private final PartidoDAO partidoDAO;
    private final Partido partido;
    private final Torneo torneo;
    private final Usuario usuarioActual;
    private final Map<Integer, Participante> mapaParticipantes;

    public RegistrarResultadoController(VentanaRegistrarResultado vista,
                                        Partido partido, Torneo torneo,
                                        Usuario usuarioActual,
                                        Map<Integer, Participante> mapaParticipantes) {
        this.vista = vista;
        this.partidoDAO = new PartidoDAO();
        this.partido = partido;
        this.torneo = torneo;
        this.usuarioActual = usuarioActual;
        this.mapaParticipantes = mapaParticipantes;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnLocal().addActionListener(e -> registrar(partido.getIdLocal()));
        vista.getBtnVisitante().addActionListener(e -> registrar(partido.getIdVisitante()));
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void registrar(int idGanador) {
        Object[] opciones = {"Sí", "No"};
        int confirmacion = JOptionPane.showOptionDialog(
                vista,
                "¿Confirmas este resultado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        if (confirmacion != 0) return; // 0 = "Sí", 1 = "No"

        if (partidoDAO.actualizarResultado(partido.getId(), idGanador, "normal")) {
            // Si es eliminación, comprobar si hay que generar la siguiente ronda
            if (torneo.getFormato().equals("ELIMINACION")) {
                avanzarRondaSiProcede(partido.getRonda());
            }
            JOptionPane.showMessageDialog(vista, "Resultado registrado correctamente");
            volver();
        } else {
            vista.setMensaje("Error al registrar el resultado");
        }
    }

    // Comprueba si todos los partidos de la ronda actual están finalizados.
    // Si es así y quedan más de un ganador, crea los partidos de la ronda siguiente.
    private void avanzarRondaSiProcede(int rondaActual) {
        List<Partido> todos = partidoDAO.listarPorTorneo(torneo.getId());

        // Separar los partidos de la ronda actual
        List<Partido> deEstaRonda = new ArrayList<>();
        for (Partido p : todos) {
            if (p.getRonda() == rondaActual) {
                deEstaRonda.add(p);
            }
        }

        // Comprobar que todos están finalizados
        for (Partido p : deEstaRonda) {
            if (!p.getEstado().equals("FINALIZADO")) {
                return; // Aún hay partidos pendientes en esta ronda
            }
        }

        // Recoger los ganadores de la ronda
        List<Integer> ganadores = new ArrayList<>();
        for (Partido p : deEstaRonda) {
            if (p.getIdGanador() != null) {
                ganadores.add(p.getIdGanador());
            }
        }

        // Si solo queda un ganador, la final ya se ha jugado; no hay siguiente ronda
        if (ganadores.size() <= 1) {
            return;
        }

        // Crear los partidos de la siguiente ronda emparejando ganadores de dos en dos
        int siguienteRonda = rondaActual + 1;
        for (int i = 0; i < ganadores.size() - 1; i += 2) {
            Partido nuevo = new Partido();
            nuevo.setIdTorneo(torneo.getId());
            nuevo.setIdLocal(ganadores.get(i));
            nuevo.setIdVisitante(ganadores.get(i + 1));
            nuevo.setEstado("PENDIENTE");
            nuevo.setRonda(siguienteRonda);
            partidoDAO.insertar(nuevo);
        }
    }

    private void volver() {
        vista.dispose();
        if (torneo.getFormato().equals("ELIMINACION")) {
            VentanaCuadroEliminacion ventana = new VentanaCuadroEliminacion(torneo);
            new CuadroEliminacionController(ventana, torneo, usuarioActual);
            ventana.setVisible(true);
        } else {
            VentanaClasificacionLiga ventana = new VentanaClasificacionLiga(torneo);
            new ClasificacionLigaController(ventana, torneo, usuarioActual);
            ventana.setVisible(true);
        }
    }
}