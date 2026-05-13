package com.playket.database;

import com.playket.model.Torneo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAO {

    public boolean seguir(int idUsuario, int idTorneo) {
        String sql = "INSERT INTO SEGUIMIENTO (notificaciones, id_usuario, id_torneo) VALUES (false, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTorneo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al seguir torneo: " + e.getMessage());
            return false;
        }
    }

    public boolean dejarDeSeguir(int idUsuario, int idTorneo) {
        String sql = "DELETE FROM SEGUIMIENTO WHERE id_usuario = ? AND id_torneo = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTorneo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al dejar de seguir torneo: " + e.getMessage());
            return false;
        }
    }

    public boolean sigueElTorneo(int idUsuario, int idTorneo) {
        String sql = "SELECT id FROM SEGUIMIENTO WHERE id_usuario = ? AND id_torneo = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTorneo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al comprobar seguimiento: " + e.getMessage());
            return false;
        }
    }

    public List<Torneo> listarTorneosSeguidos(int idUsuario) {
        List<Torneo> lista = new ArrayList<>();
        String sql = "SELECT t.* FROM TORNEO t " +
                "INNER JOIN SEGUIMIENTO s ON t.id = s.id_torneo " +
                "WHERE s.id_usuario = ? ORDER BY t.fecha_inicio DESC";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new com.playket.model.Torneo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("formato"),
                        rs.getString("estado"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getInt("num_participantes"),
                        rs.getString("imagen_portada"),
                        rs.getInt("id_deporte"),
                        rs.getInt("id_organizador")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar torneos seguidos: " + e.getMessage());
        }
        return lista;
    }
}