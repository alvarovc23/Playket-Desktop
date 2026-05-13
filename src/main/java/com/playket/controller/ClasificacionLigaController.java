package com.playket.controller;

import com.playket.database.ParticipanteDAO;
import com.playket.database.PartidoDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.*;
import com.playket.view.VentanaClasificacionLiga;
import com.playket.view.VentanaInicio;
import com.playket.view.VentanaRegistrarResultado;

import javax.swing.JOptionPane;
import java.util.*;

public class ClasificacionLigaController {

    private final VentanaClasificacionLiga vista;
    private final PartidoDAO partidoDAO;
    private final ParticipanteDAO participanteDAO;
    private final Torneo torneo;
    private final Usuario usuarioActual;
    private Map<Integer, Participante> mapaParticipantes;
    private List<Partido> partidos;

    public ClasificacionLigaController(VentanaClasificacionLiga vista,
                                       Torneo torneo, Usuario usuarioActual) {
        this.vista = vista;
        this.partidoDAO = new PartidoDAO();
        this.participanteDAO = new ParticipanteDAO();
        this.torneo = torneo;
        this.usuarioActual = usuarioActual;
        cargarDatos();
        inicializarEventos();
    }

    private void cargarDatos() {
        partidos = partidoDAO.listarPorTorneo(torneo.getId());
        List<Participante> participantes = participanteDAO.listarPorTorneo(torneo.getId());

        mapaParticipantes = new HashMap<>();
        for (Participante p : participantes) mapaParticipantes.put(p.getId(), p);

        cargarClasificacion(participantes);
        cargarPartidos();
    }

    private void cargarClasificacion(List<Participante> participantes) {
        Map<Integer, EstadisticaLiga> stats = new LinkedHashMap<>();
        for (Participante p : participantes) {
            stats.put(p.getId(), new EstadisticaLiga(p.getNombre()));
        }

        for (Partido p : partidos) {
            if (!p.getEstado().equals("FINALIZADO")) continue;
            EstadisticaLiga local = stats.get(p.getIdLocal());
            EstadisticaLiga visitante = stats.get(p.getIdVisitante());
            if (local == null || visitante == null) continue;

            local.partidosJugados++;
            visitante.partidosJugados++;

            if (p.getIdGanador() == null) {
                local.partidosEmpatados++;
                visitante.partidosEmpatados++;
                local.puntos++;
                visitante.puntos++;
            } else if (p.getIdGanador() == p.getIdLocal()) {
                local.partidosGanados++;
                local.puntos += 3;
                visitante.partidosPerdidos++;
            } else {
                visitante.partidosGanados++;
                visitante.puntos += 3;
                local.partidosPerdidos++;
            }
        }

        List<EstadisticaLiga> lista = new ArrayList<>(stats.values());
        lista.sort((a, b) -> b.puntos - a.puntos);

        List<Object[]> filas = new ArrayList<>();
        for (EstadisticaLiga e : lista) {
            filas.add(new Object[]{
                    e.nombre,
                    e.partidosJugados,
                    e.partidosGanados,
                    e.partidosEmpatados,
                    e.partidosPerdidos,
                    e.puntos
            });
        }

        vista.cargarClasificacion(filas);
    }

    private void cargarPartidos() {
        List<Object[]> filas = new ArrayList<>();
        List<int[]> ids = new ArrayList<>();

        for (Partido p : partidos) {
            Participante local = mapaParticipantes.get(p.getIdLocal());
            Participante visitante = mapaParticipantes.get(p.getIdVisitante());
            String nombreLocal = local != null ? local.getNombre() : "?";
            String nombreVisitante = visitante != null ? visitante.getNombre() : "?";
            filas.add(new Object[]{nombreLocal, nombreVisitante, p.getEstado()});
            ids.add(new int[]{p.getId(), p.getIdLocal(), p.getIdVisitante()});
        }

        vista.cargarPartidos(filas, ids);
    }

    private void inicializarEventos() {
        vista.getBtnVolver().addActionListener(e -> volver());
        vista.getBtnRegistrarResultado().addActionListener(e -> registrarResultado());
        vista.getBtnCerrarTorneo().addActionListener(e -> cerrarTorneo());
    }

    private void registrarResultado() {
        // Primero comprobamos si el torneo está cerrado
        if (torneo.getEstado().equals("FINALIZADO")) {
            JOptionPane.showMessageDialog(vista, "Este torneo ya está cerrado");
            return;
        }

        int idPartido = vista.getIdPartidoSeleccionado();
        if (idPartido == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un partido primero");
            return;
        }

        Partido partido = null;
        for (Partido p : partidos) {
            if (p.getId() == idPartido) {
                partido = p;
                break;
            }
        }

        if (partido == null) return;

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