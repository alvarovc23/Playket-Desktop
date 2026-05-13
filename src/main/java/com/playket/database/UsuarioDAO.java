package com.playket.database;

import com.playket.model.Usuario;
import java.sql.*;
import java.time.LocalDate;

public class UsuarioDAO {
    //Guarda un nuevo usuario en la base de datos
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO USUARIO (nombre, apellidos, email, password_hash, " +
                "pregunta_seguridad, respuesta_seg_hash, fecha_registro) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getPreguntaSeguridad());
            ps.setString(6, u.getRespuestaSeg());
            ps.setDate(7, Date.valueOf(LocalDate.now()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }
    //Busca un usuario por su correo electrónico
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM USUARIO WHERE email = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
    //COmprueba si ya existe una cuenta con ese correo
    public boolean emailExiste(String email) {
        return buscarPorEmail(email) != null;
    }
    //Actualiza el nombre, apellidos y contraseña del usuario
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE USUARIO SET nombre = ?, apellidos = ?, password_hash = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("pregunta_seguridad"),
                rs.getString("respuesta_seg_hash"),
                rs.getDate("fecha_registro").toLocalDate()
        );
    }
}