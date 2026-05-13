package com.playket.view;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoPassword;
    private JButton btnIniciarSesion;
    private JButton btnCrearCuenta;
    private JButton btnOlvidePassword;
    private JLabel lblMensaje;

    public VentanaLogin() {
        setTitle("Playket");
        setSize(400, 380);
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
        gbc.insets = new Insets(6, 0, 6, 0);

        // Título
        JLabel titulo = new JLabel("Playket", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Email
        gbc.gridy = 1;
        panel.add(new JLabel("Correo electrónico"), gbc);
        campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(0, 32));
        gbc.gridy = 2;
        panel.add(campoEmail, gbc);

        // Contraseña
        gbc.gridy = 3;
        panel.add(new JLabel("Contraseña"), gbc);
        campoPassword = new JPasswordField();
        campoPassword.setPreferredSize(new Dimension(0, 32));
        gbc.gridy = 4;
        panel.add(campoPassword, gbc);

        // ¿Olvidaste tu contraseña?
        btnOlvidePassword = new JButton("¿Olvidaste tu contraseña?");
        btnOlvidePassword.setBorderPainted(false);
        btnOlvidePassword.setContentAreaFilled(false);
        btnOlvidePassword.setForeground(Color.BLUE);
        btnOlvidePassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        panel.add(btnOlvidePassword, gbc);

        // Mensaje de error/info
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 6;
        panel.add(lblMensaje, gbc);

        // Botón iniciar sesión
        btnIniciarSesion = new JButton("Iniciar sesión");
        btnIniciarSesion.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 7;
        panel.add(btnIniciarSesion, gbc);

        // Botón crear cuenta
        btnCrearCuenta = new JButton("Crear cuenta nueva");
        btnCrearCuenta.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 8;
        panel.add(btnCrearCuenta, gbc);

        add(panel);
    }

    public String getEmail() { return campoEmail.getText().trim(); }
    public String getPassword() { return new String(campoPassword.getPassword()); }
    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    public JButton getBtnOlvidePassword() { return btnOlvidePassword; }
}