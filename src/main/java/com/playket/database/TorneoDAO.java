package com.playket.database;

import com.playket.model.Torneo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TorneoDAO {

    public boolean insertar(Torneo t) {
        String sql = "INSERT INTO TORNEO (nombre, descripcion, formato, estado, " +
                "fecha_inicio, num_participantes, imagen_portada, id_deporte, id_organizador) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getFormato());
            ps.setString(4, t.getEstado());
            ps.setDate(5, Date.valueOf(t.getFechaInicio()));
            ps.setInt(6, t.getNumParticipantes());
            ps.setString(7, t.getImagenPortada());
            ps.setInt(8, t.getIdDeporte());
            ps.setInt(9, t.getIdOrganizador());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar torneo: " + e.getMessage());
            return false;
        }
    }

    public List<Torneo> listarPorOrganizador(int idOrganizador) {
        List<Torneo> lista = new ArrayList<>();
        String sql = "SELECT * FROM TORNEO WHERE id_organizador = ? ORDER BY fecha_inicio DESC";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idOrganizador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar torneos: " + e.getMessage());
        }
        return lista;
    }

    public List<Torneo> buscar(String nombre, Integer idDeporte, String estado) {
        StringBuilder sql = new StringBuilder("SELECT * FROM TORNEO WHERE 1=1");
        if (nombre != null && !nombre.isEmpty())  sql.append(" AND nombre LIKE ?");
        if (idDeporte != null)                    sql.append(" AND id_deporte = ?");
        if (estado != null && !estado.isEmpty())  sql.append(" AND estado = ?");
        sql.append(" ORDER BY fecha_inicio DESC");

        List<Torneo> lista = new ArrayList<>();
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql.toString())) {
            int i = 1;
            if (nombre != null && !nombre.isEmpty())  ps.setString(i++, "%" + nombre + "%");
            if (idDeporte != null)                    ps.setInt(i++, idDeporte);
            if (estado != null && !estado.isEmpty())  ps.setString(i++, estado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al buscar torneos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Torneo t) {
        String sql = "UPDATE TORNEO SET nombre=?, descripcion=?, estado=?, " +
                "fecha_inicio=?, imagen_portada=? WHERE id=?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado());
            ps.setDate(4, Date.valueOf(t.getFechaInicio()));
            ps.setString(5, t.getImagenPortada());
            ps.setInt(6, t.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar torneo: " + e.getMessage());
            return false;
        }
    }

    public boolean cerrar(int idTorneo) {
        String sql = "UPDATE TORNEO SET estado = 'FINALIZADO' WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idTorneo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cerrar torneo: " + e.getMessage());
            return false;
        }
    }

    private Torneo mapear(ResultSet rs) throws SQLException {
        return new Torneo(
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
        );
    }

    public Torneo buscarUltimoPorOrganizador(int idOrganizador) {
        String sql = "SELECT * FROM TORNEO WHERE id_organizador = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idOrganizador);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar último torneo: " + e.getMessage());
        }
        return null;
    }
}
