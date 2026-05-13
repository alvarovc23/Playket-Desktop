package com.playket.util;

import com.playket.database.PartidoDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import java.util.List;

public class GeneradorLiga {

    private final PartidoDAO partidoDAO;
    private final TorneoDAO torneoDAO;

    public GeneradorLiga() {
        this.partidoDAO = new PartidoDAO();
        this.torneoDAO = new TorneoDAO();
    }

    // Genera todos los partidos del torneo (cada equipo juega contra todos)
    public boolean generarCalendario(Torneo torneo, List<Participante> participantes) {
        boolean todoOk = true;

        for(int i = 0; i < participantes.size(); i++) {
            for(int j = i + 1; j < participantes.size(); j++) {
                Participante local = participantes.get(i);
                Participante visitante = participantes.get(j);

                Partido partido = new Partido();
                partido.setIdTorneo(torneo.getId());
                partido.setIdLocal(local.getId());
                partido.setIdVisitante(visitante.getId());
                partido.setEstado("PENDIENTE");

                if(!partidoDAO.insertar(partido)) {
                    todoOk = false;
                }
            }
        }

        if(todoOk) {
            torneo.setEstado("EN_CURSO");
            torneoDAO.actualizar(torneo);
        }

        return todoOk;
    }
}