package com.playket;

import com.playket.database.ConexionDB;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection con = ConexionDB.getConexion();
        if (con != null) {
            System.out.println("Conexión exitosa con la base de datos Playket");
        } else {
            System.out.println("Error al conectar con la base de datos");
        }
    }
}