package com.playket.controller;

import com.playket.database.ParticipanteDAO;
import com.playket.database.PartidoDAO;
import com.playket.database.TorneoDAO;
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
        vista.getBtnCerrarTorneo().addActionListener(e -> cerrarTorneo());
    }

    private void registrarResultado() {
        if (torneo.getIdOrganizador() != usuarioActual.getId()) {
            JOptionPane.showMessageDialog(vista,
                    "Solo el organizador puede registrar resultados");
            return;
        }
        if (torneo.getEstado().equals("FINALIZADO")) {
            JOptionPane.showMessageDialog(vista, "Este torneo ya está cerrado");
            return;
        }
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

    private void cerrarTorneo() {
        // Solo el organizador puede cerrar el torneo
        if (torneo.getIdOrganizador() != usuarioActual.getId()) {
            JOptionPane.showMessageDialog(vista,
                    "Solo el organizador puede cerrar el torneo");
            return;
        }

        Object[] opciones = {"Sí", "No"};
        int confirmacion = JOptionPane.showOptionDialog(
                vista,
                "¿Seguro que quieres cerrar el torneo? No podrás registrar más resultados.",
                "Cerrar torneo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );
        if (confirmacion != 0) return;

        TorneoDAO torneoDAO = new TorneoDAO();
        if (torneoDAO.cerrar(torneo.getId())) {
            JOptionPane.showMessageDialog(vista, "Torneo cerrado correctamente");
            vista.dispose();
            VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
            new InicioController(ventanaInicio, usuarioActual);
            ventanaInicio.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al cerrar el torneo");
        }
    }
}