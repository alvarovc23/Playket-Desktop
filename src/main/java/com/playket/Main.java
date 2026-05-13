package com.playket;

import com.playket.controller.LoginController;
import com.playket.view.VentanaLogin;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventana = new VentanaLogin();
            new LoginController(ventana);
            ventana.setVisible(true);
        });
    }
}