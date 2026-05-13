package com.playket.view;

import javax.swing.*;
import java.awt.*;

public class VentanaRecuperarPassword extends JFrame {

    private JTextField campoEmail;
    private JLabel lblPregunta;
    private JTextField campoRespuesta;
    private JPasswordField campoPasswordNueva;
    private JPasswordField campoConfirmarPassword;
    private JButton btnComprobar;
    private JButton btnCambiarPassword;
    private JButton btnVolver;
    private JLabel lblMensaje;
    private JPanel panelSegundoPaso;

    public VentanaRecuperarPassword() {
        setTitle("Playket - Recuperar contraseña");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Recuperar contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        // Primer paso — email
        gbc.gridy = 1;
        panel.add(new JLabel("Correo electrónico"), gbc);
        campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 2;
        panel.add(campoEmail, gbc);

        btnComprobar = new JButton("Comprobar");
        btnComprobar.setPreferredSize(new Dimension(0, 34));
        gbc.gridy = 3;
        panel.add(btnComprobar, gbc);

        // Segundo paso — pregunta y nueva contraseña (oculto inicialmente)
        panelSegundoPaso = new JPanel(new GridBagLayout());
        panelSegundoPaso.setVisible(false);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(5, 0, 5, 0);
        gbc2.gridwidth = 2;

        lblPregunta = new JLabel("");
        lblPregunta.setFont(new Font("Arial", Font.BOLD, 13));
        gbc2.gridy = 0;
        panelSegundoPaso.add(lblPregunta, gbc2);

        gbc2.gridy = 1;
        panelSegundoPaso.add(new JLabel("Respuesta"), gbc2);
        campoRespuesta = new JTextField();
        campoRespuesta.setPreferredSize(new Dimension(0, 30));
        gbc2.gridy = 2;
        panelSegundoPaso.add(campoRespuesta, gbc2);

        gbc2.gridy = 3;
        panelSegundoPaso.add(new JLabel("Nueva contraseña (mín. 8 caracteres)"), gbc2);
        campoPasswordNueva = new JPasswordField();
        campoPasswordNueva.setPreferredSize(new Dimension(0, 30));
        gbc2.gridy = 4;
        panelSegundoPaso.add(campoPasswordNueva, gbc2);

        gbc2.gridy = 5;
        panelSegundoPaso.add(new JLabel("Confirmar nueva contraseña"), gbc2);
        campoConfirmarPassword = new JPasswordField();
        campoConfirmarPassword.setPreferredSize(new Dimension(0, 30));
        gbc2.gridy = 6;
        panelSegundoPaso.add(campoConfirmarPassword, gbc2);

        btnCambiarPassword = new JButton("Cambiar contraseña");
        btnCambiarPassword.setPreferredSize(new Dimension(0, 34));
        gbc2.gridy = 7;
        panelSegundoPaso.add(btnCambiarPassword, gbc2);

        gbc.gridy = 4;
        panel.add(panelSegundoPaso, gbc);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 5;
        panel.add(lblMensaje, gbc);

        // Volver
        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(0, 34));
        gbc.gridy = 6;
        panel.add(btnVolver, gbc);

        add(new JScrollPane(panel));
    }

    public void mostrarSegundoPaso(String pregunta) {
        lblPregunta.setText(pregunta);
        panelSegundoPaso.setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    public String getEmail() { return campoEmail.getText().trim(); }
    public String getRespuesta() { return campoRespuesta.getText().trim(); }
    public String getPasswordNueva() { return new String(campoPasswordNueva.getPassword()); }
    public String getConfirmarPassword() { return new String(campoConfirmarPassword.getPassword()); }
    public void setMensaje(String msg) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(msg);
    }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(Color.GREEN.darker());
        lblMensaje.setText(msg);
    }
    public JButton getBtnComprobar() { return btnComprobar; }
    public JButton getBtnCambiarPassword() { return btnCambiarPassword; }
    public JButton getBtnVolver() { return btnVolver; }
}