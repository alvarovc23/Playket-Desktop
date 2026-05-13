package com.playket.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/playket";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root1234";

    private static Connection conexion;

    public static Connection getConexion() {
        try {
            if(conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            }
        } catch(SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}