package com.playket.controller;

import com.playket.database.ParticipanteDAO;
import com.playket.database.PartidoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaClasificacionLiga;
import com.playket.view.VentanaDesignarCoOrganizador;
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
        // Mapa: idParticipante -> [PJ, PG, PE, PP, Pts]
        Map<Integer, int[]> stats = new LinkedHashMap<>();
        for (Participante p : participantes) stats.put(p.getId(), new int[5]);

        for (Partido p : partidos) {
            if (!p.getEstado().equals("FINALIZADO")) continue;
            int[] local = stats.get(p.getIdLocal());
            int[] visitante = stats.get(p.getIdVisitante());
            if (local == null || visitante == null) continue;

            local[0]++; visitante[0]++; // PJ

            if (p.getIdGanador() == null) {
                local[2]++; visitante[2]++; // empate
                local[4]++; visitante[4]++;
            } else if (p.getIdGanador() == p.getIdLocal()) {
                local[1]++; local[4] += 3;
                visitante[3]++;
            } else {
                visitante[1]++; visitante[4] += 3;
                local[3]++;
            }
        }

        List<Object[]> filas = new ArrayList<>();
        for (Map.Entry<Integer, int[]> entry : stats.entrySet()) {
            Participante p = mapaParticipantes.get(entry.getKey());
            int[] s = entry.getValue();
            filas.add(new Object[]{p.getNombre(), s[0], s[1], s[2], s[3], s[4]});
        }

        filas.sort((a, b) -> (int) b[5] - (int) a[5]);
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
        vista.getBtnCoOrganizador().addActionListener(e -> abrirCoOrganizador());
    }

    private void registrarResultado() {
        int idPartido = vista.getIdPartidoSeleccionado();
        if (idPartido == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un partido primero");
            return;
        }

        Partido partido = partidos.stream()
                .filter(p -> p.getId() == idPartido)
                .findFirst().orElse(null);

        if (partido == null) return;

        if (partido.getEstado().equals("FINALIZADO")) {
            JOptionPane.showMessageDialog(vista, "Este partido ya tiene resultado");
            return;
        }

        VentanaRegistrarResultado ventana = new VentanaRegistrarResultado(
                partido, mapaParticipantes);
        new RegistrarResultadoLigaController(ventana, partido, torneo,
                usuarioActual, mapaParticipantes);
        ventana.setVisible(true);
        vista.dispose();
    }

    private void abrirCoOrganizador() {
        vista.dispose();
        VentanaDesignarCoOrganizador ventana = new VentanaDesignarCoOrganizador(torneo);
        new DesignarCoOrganizadorController(ventana, torneo, usuarioActual);
        ventana.setVisible(true);
    }

    private void volver() {
        vista.dispose();
        VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
        new InicioController(ventanaInicio, usuarioActual);
        ventanaInicio.setVisible(true);
    }
}