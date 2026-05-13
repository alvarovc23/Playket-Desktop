package com.playket.util;

import com.playket.database.PartidoDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;

import java.util.ArrayList;
import java.util.List;

public class GeneradorLiga {

    private final PartidoDAO partidoDAO;
    private final TorneoDAO torneoDAO;

    public GeneradorLiga() {
        this.partidoDAO = new PartidoDAO();
        this.torneoDAO = new TorneoDAO();
    }

    public boolean generarCalendario(Torneo torneo, List<Participante> participantes) {
        List<Participante> lista = new ArrayList<>(participantes);

        // Si número impar añade un bye (descanso)
        if (lista.size() % 2 != 0) {
            lista.add(null);
        }

        int n = lista.size();
        int numJornadas = n - 1;
        boolean todoOk = true;

        for (int jornada = 0; jornada < numJornadas; jornada++) {
            for (int i = 0; i < n / 2; i++) {
                Participante local = lista.get(i);
                Participante visitante = lista.get(n - 1 - i);

                // Salta si alguno es bye
                if (local == null || visitante == null) continue;

                Partido partido = new Partido();
                partido.setIdTorneo(torneo.getId());
                partido.setIdLocal(local.getId());
                partido.setIdVisitante(visitante.getId());
                partido.setEstado("PENDIENTE");
                if (!partidoDAO.insertar(partido)) todoOk = false;
            }

            // Rotación para la siguiente jornada (fijo el primero)
            Participante ultimo = lista.remove(n - 1);
            lista.add(1, ultimo);
        }

        if (todoOk) {
            torneo.setEstado("EN_CURSO");
            torneoDAO.actualizar(torneo);
        }

        return todoOk;
    }
}