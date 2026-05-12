package com.playket.util;

import com.playket.database.PartidoDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneradorCuadro {

    private final PartidoDAO partidoDAO;
    private final TorneoDAO torneoDAO;

    public GeneradorCuadro() {
        this.partidoDAO = new PartidoDAO();
        this.torneoDAO = new TorneoDAO();
    }

    public boolean generarEliminacion(Torneo torneo, List<Participante> participantes) {
        // Mezcla aleatoria para el sorteo
        List<Participante> mezclados = new ArrayList<>(participantes);
        Collections.shuffle(mezclados);

        // Genera los partidos de primera ronda
        boolean todoOk = true;
        for (int i = 0; i < mezclados.size() - 1; i += 2) {
            Partido partido = new Partido();
            partido.setIdTorneo(torneo.getId());
            partido.setIdLocal(mezclados.get(i).getId());
            partido.setIdVisitante(mezclados.get(i + 1).getId());
            partido.setEstado("PENDIENTE");
            if (!partidoDAO.insertar(partido)) {
                todoOk = false;
            }
        }

        // Cambia el estado del torneo a EN_CURSO
        if (todoOk) {
            torneo.setEstado("EN_CURSO");
            torneoDAO.actualizar(torneo);
        }

        return todoOk;
    }
}