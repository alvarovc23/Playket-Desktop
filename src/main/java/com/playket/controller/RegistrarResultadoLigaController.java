package com.playket.controller;

import com.playket.database.PartidoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaClasificacionLiga;
import com.playket.view.VentanaRegistrarResultado;

import javax.swing.JOptionPane;
import java.util.Map;

public class RegistrarResultadoLigaController {

    private final VentanaRegistrarResultado vista;
    private final PartidoDAO partidoDAO;
    private final Partido partido;
    private final Torneo torneo;
    private final Usuario usuarioActual;
    private final Map<Integer, Participante> mapaParticipantes;

    public RegistrarResultadoLigaController(VentanaRegistrarResultado vista,
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
        if (confirmacion != 0) return;

        if (partidoDAO.actualizarResultado(partido.getId(), idGanador, "normal")) {
            JOptionPane.showMessageDialog(vista, "Resultado registrado correctamente");
            volver();
        } else {
            vista.setMensaje("Error al registrar el resultado");
        }
    }

    private void volver() {
        vista.dispose();
        VentanaClasificacionLiga ventana = new VentanaClasificacionLiga(torneo);
        new ClasificacionLigaController(ventana, torneo, usuarioActual);
        ventana.setVisible(true);
    }
}