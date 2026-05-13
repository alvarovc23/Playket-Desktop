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

        // El algoritmo Round Robin necesita un número par de equipos.
        // Si hay número impar, añadimos un equipo ficticio "bye" (null)
        // que representa un descanso — el equipo que juegue contra él no tiene partido esa jornada.
        if (lista.size() % 2 != 0) {
            lista.add(null);
        }

        int n = lista.size();
        int numJornadas = n - 1; // Con N equipos se juegan N-1 jornadas

        boolean todoOk = true;

        for (int jornada = 0; jornada < numJornadas; jornada++) {
            // En cada jornada se enfrentan los equipos de los extremos hacia el centro
            for (int i = 0; i < n / 2; i++) {
                Participante local = lista.get(i);
                Participante visitante = lista.get(n - 1 - i);

                // Si alguno es el equipo ficticio, ese partido no se genera
                if (local == null || visitante == null) continue;

                Partido partido = new Partido();
                partido.setIdTorneo(torneo.getId());
                partido.setIdLocal(local.getId());
                partido.setIdVisitante(visitante.getId());
                partido.setEstado("PENDIENTE");
                if (!partidoDAO.insertar(partido)) todoOk = false;
            }

            // Rotación: el primer equipo se queda fijo,
            // el resto rota una posición para generar los emparejamientos de la siguiente jornada
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