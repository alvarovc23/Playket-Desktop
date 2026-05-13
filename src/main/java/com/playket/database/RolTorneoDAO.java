package com.playket.database;

import com.playket.model.Torneo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolTorneoDAO {

    public boolean asignarCoOrganizador(int idUsuario, int idTorneo) {
        String sql = "INSERT INTO ROL_TORNEO (rol, id_usuario, id_torneo) VALUES ('CO_ORGANIZADOR', ?, ?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTorneo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al asignar co-organizador: " + e.getMessage());
            return false;
        }
    }

    public boolean revocarCoOrganizador(int idUsuario, int idTorneo) {
        String sql = "DELETE FROM ROL_TORNEO WHERE id_usuario = ? AND id_torneo = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTorneo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al revocar co-organizador: " + e.getMessage());
            return false;
        }
    }

    public boolean esCoOrganizador(int idUsuario, int idTorneo) {
        String sql = "SELECT id FROM ROL_TORNEO WHERE id_usuario = ? AND id_torneo = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTorneo);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Error al comprobar rol: " + e.getMessage());
            return false;
        }
    }

    public List<Torneo> listarTorneosCoOrganizador(int idUsuario) {
        List<Torneo> lista = new ArrayList<>();
        String sql = "SELECT t.* FROM TORNEO t " +
                "INNER JOIN ROL_TORNEO r ON t.id = r.id_torneo " +
                "WHERE r.id_usuario = ? AND r.rol = 'CO_ORGANIZADOR' " +
                "ORDER BY t.fecha_inicio DESC";
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
            System.err.println("Error al listar torneos co-organizador: " + e.getMessage());
        }
        return lista;
    }
}