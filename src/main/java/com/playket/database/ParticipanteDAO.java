package com.playket.database;

import com.playket.model.Participante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {
    //Añade un nuevo participante a un torneo
    public boolean insertar(Participante p) {
        String sql = "INSERT INTO PARTICIPANTE (nombre, apellidos, email, id_torneo) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getEmail());
            ps.setInt(4, p.getIdTorneo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar participante: " + e.getMessage());
            return false;
        }
    }
    //Devuelve todos los participantes de un torneo concreto
    public List<Participante> listarPorTorneo(int idTorneo) {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM PARTICIPANTE WHERE id_torneo = ? ORDER BY nombre";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idTorneo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar participantes: " + e.getMessage());
        }
        return lista;
    }
    //Elimina un participante por su id
    public boolean eliminar(int id) {
        String sql = "DELETE FROM PARTICIPANTE WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar participante: " + e.getMessage());
            return false;
        }
    }

    private Participante mapear(ResultSet rs) throws SQLException {
        return new Participante(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getInt("id_torneo")
        );
    }
}