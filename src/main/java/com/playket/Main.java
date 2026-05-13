package com.playket;

import com.playket.controller.LoginController;
import com.playket.view.VentanaLogin;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Activamos Nimbus igual que en el proyecto del instituto
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("No se pudo cargar Nimbus: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventana = new VentanaLogin();
            new LoginController(ventana);
            ventana.setVisible(true);
        });
    }
}