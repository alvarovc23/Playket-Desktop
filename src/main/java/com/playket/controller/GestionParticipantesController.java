package com.playket.controller;

import com.playket.database.ParticipanteDAO;
import com.playket.model.Participante;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaGestionParticipantes;
import com.playket.view.VentanaInicio;

import java.util.List;

public class GestionParticipantesController {

    private final VentanaGestionParticipantes vista;
    private final ParticipanteDAO participanteDAO;
    private final Torneo torneo;
    private final Usuario usuarioActual;
    private List<Participante> participantes;

    public GestionParticipantesController(VentanaGestionParticipantes vista,
                                          Torneo torneo, Usuario usuarioActual) {
        this.vista = vista;
        this.participanteDAO = new ParticipanteDAO();
        this.torneo = torneo;
        this.usuarioActual = usuarioActual;
        cargarParticipantes();
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnAnadir().addActionListener(e -> añadirParticipante());
        vista.getBtnEliminar().addActionListener(e -> eliminarParticipante());
        vista.getBtnGenerar().addActionListener(e -> generarCuadro());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void cargarParticipantes() {
        participantes = participanteDAO.listarPorTorneo(torneo.getId());
        vista.cargarParticipantes(participantes);
    }

    private void añadirParticipante() {
        if (participantes.size() >= torneo.getNumParticipantes()) {
            vista.setMensaje("Ya tienes el máximo de participantes (" +
                    torneo.getNumParticipantes() + ")");
            return;
        }

        String nombre = vista.getNombreParticipante();
        if (nombre.isEmpty()) {
            vista.setMensaje("El nombre es obligatorio");
            return;
        }

        Participante p = new Participante();
        p.setNombre(nombre);
        p.setApellidos(vista.getApellidosParticipante());
        p.setEmail(vista.getEmailParticipante());
        p.setIdTorneo(torneo.getId());

        if (participanteDAO.insertar(p)) {
            vista.limpiarFormulario();
            vista.setMensajeVerde("Participante añadido");
            cargarParticipantes();
        } else {
            vista.setMensaje("Error al añadir participante");
        }
    }

    private void eliminarParticipante() {
        int fila = vista.getFilaSeleccionada();
        if (fila < 0) {
            vista.setMensaje("Selecciona un participante para eliminar");
            return;
        }
        Participante p = participantes.get(fila);
        if (participanteDAO.eliminar(p.getId())) {
            vista.setMensajeVerde("Participante eliminado");
            cargarParticipantes();
        } else {
            vista.setMensaje("Error al eliminar participante");
        }
    }

    private void generarCuadro() {
        if (participantes.size() < 2) {
            vista.setMensaje("Necesitas al menos 2 participantes para generar el cuadro");
            return;
        }
        System.out.println("Generar cuadro");
    }

    private void volver() {
        vista.dispose();
        VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
        new InicioController(ventanaInicio, usuarioActual);
        ventanaInicio.setVisible(true);
    }
}