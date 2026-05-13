package com.playket.controller;

import com.playket.database.ParticipanteDAO;
import com.playket.database.PartidoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaCuadroEliminacion;
import com.playket.view.VentanaInicio;
import com.playket.view.VentanaRegistrarResultado;

import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuadroEliminacionController {

    private final VentanaCuadroEliminacion vista;
    private final PartidoDAO partidoDAO;
    private final ParticipanteDAO participanteDAO;
    private final Torneo torneo;
    private final Usuario usuarioActual;
    private Map<Integer, Participante> mapaParticipantes;

    public CuadroEliminacionController(VentanaCuadroEliminacion vista,
                                       Torneo torneo, Usuario usuarioActual) {
        this.vista = vista;
        this.partidoDAO = new PartidoDAO();
        this.participanteDAO = new ParticipanteDAO();
        this.torneo = torneo;
        this.usuarioActual = usuarioActual;
        cargarCuadro();
        inicializarEventos();
    }

    private void cargarCuadro() {
        List<Partido> partidos = partidoDAO.listarPorTorneo(torneo.getId());
        List<Participante> participantes = participanteDAO.listarPorTorneo(torneo.getId());

        mapaParticipantes = new HashMap<>();
        for (Participante p : participantes) {
            mapaParticipantes.put(p.getId(), p);
        }

        vista.cargarCuadro(partidos, mapaParticipantes);
    }

    private void inicializarEventos() {
        vista.getBtnVolver().addActionListener(e -> volver());
        vista.getBtnRegistrarResultado().addActionListener(e -> registrarResultado());
    }

    private void registrarResultado() {
        Partido partido = vista.getPartidoSeleccionado();
        if (partido == null) {
            JOptionPane.showMessageDialog(vista, "Selecciona un partido del cuadro primero");
            return;
        }
        if (partido.getEstado().equals("FINALIZADO")) {
            JOptionPane.showMessageDialog(vista, "Este partido ya tiene resultado");
            return;
        }
        VentanaRegistrarResultado ventana = new VentanaRegistrarResultado(
                partido, mapaParticipantes);
        new RegistrarResultadoController(ventana, partido, torneo,
                usuarioActual, mapaParticipantes);
        ventana.setVisible(true);
        vista.dispose();
    }

    private void volver() {
        vista.dispose();
        VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
        new InicioController(ventanaInicio, usuarioActual);
        ventanaInicio.setVisible(true);
    }
}