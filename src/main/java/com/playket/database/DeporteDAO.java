package com.playket.database;

import com.playket.model.Deporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeporteDAO {

    public List<Deporte> listarTodos() {
        List<Deporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM DEPORTE ORDER BY nombre";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Deporte(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar deportes: " + e.getMessage());
        }
        return lista;
    }
}