package com.playket.view;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoPassword;
    private JButton btnIniciarSesion;
    private JButton btnCrearCuenta;
    private JLabel lblMensaje;

    public VentanaLogin() {
        setTitle("Playket");
        setSize(400, 350);
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

        JLabel titulo = new JLabel("Playket", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(new JLabel("Correo electrónico"), gbc);
        campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(0, 32));
        gbc.gridy = 2;
        panel.add(campoEmail, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Contraseña"), gbc);
        campoPassword = new JPasswordField();
        campoPassword.setPreferredSize(new Dimension(0, 32));
        gbc.gridy = 4;
        panel.add(campoPassword, gbc);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 5;
        panel.add(lblMensaje, gbc);

        btnIniciarSesion = new JButton("Iniciar sesión");
        btnIniciarSesion.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 6;
        panel.add(btnIniciarSesion, gbc);

        btnCrearCuenta = new JButton("Crear cuenta nueva");
        btnCrearCuenta.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 7;
        panel.add(btnCrearCuenta, gbc);

        add(panel);
    }

    public String getEmail() { return campoEmail.getText().trim(); }
    public String getPassword() { return new String(campoPassword.getPassword()); }
    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    public void limpiar() { campoEmail.setText(""); campoPassword.setText(""); }
}