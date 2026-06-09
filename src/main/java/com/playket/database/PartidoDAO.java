package com.playket.database;

import com.playket.model.Partido;
import com.playket.util.PlayketException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    public boolean insertar(Partido p) {
        String sql = "INSERT INTO PARTIDO (fecha, hora, sede, estado, tipo_victoria, " +
                "id_torneo, id_local, id_visitante, id_ganador, ronda) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setDate(1, p.getFecha() != null ? Date.valueOf(p.getFecha()) : null);
            ps.setTime(2, p.getHora() != null ? Time.valueOf(p.getHora()) : null);
            ps.setString(3, p.getSede());
            ps.setString(4, p.getEstado());
            ps.setString(5, p.getTipoVictoria());
            ps.setInt(6, p.getIdTorneo());
            ps.setInt(7, p.getIdLocal());
            ps.setInt(8, p.getIdVisitante());
            if (p.getIdGanador() != null) ps.setInt(9, p.getIdGanador());
            else ps.setNull(9, Types.INTEGER);
            ps.setInt(10, p.getRonda());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PlayketException("Error al guardar el partido", e);
        }
    }

    public List<Partido> listarPorTorneo(int idTorneo) {
        List<Partido> lista = new ArrayList<>();
        String sql = "SELECT * FROM PARTIDO WHERE id_torneo = ? ORDER BY id";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idTorneo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new PlayketException("Error al cargar los partidos del torneo", e);
        }
        return lista;
    }

    public boolean actualizarResultado(int idPartido, int idGanador, String tipoVictoria) {
        String sql = "UPDATE PARTIDO SET id_ganador = ?, tipo_victoria = ?, estado = 'FINALIZADO' WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idGanador);
            ps.setString(2, tipoVictoria);
            ps.setInt(3, idPartido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PlayketException("Error al guardar el resultado del partido", e);
        }
    }

    private Partido mapear(ResultSet rs) throws SQLException {
        Partido p = new Partido();
        p.setId(rs.getInt("id"));
        p.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
        p.setHora(rs.getTime("hora") != null ? rs.getTime("hora").toLocalTime() : null);
        p.setSede(rs.getString("sede"));
        p.setEstado(rs.getString("estado"));
        p.setTipoVictoria(rs.getString("tipo_victoria"));
        p.setIdTorneo(rs.getInt("id_torneo"));
        p.setIdLocal(rs.getInt("id_local"));
        p.setIdVisitante(rs.getInt("id_visitante"));
        int ganador = rs.getInt("id_ganador");
        p.setIdGanador(rs.wasNull() ? null : ganador);
        p.setRonda(rs.getInt("ronda"));
        return p;
    }
}