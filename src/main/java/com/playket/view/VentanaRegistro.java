package com.playket.view;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro extends JFrame {

    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JTextField campoEmail;
    private JPasswordField campoPassword;
    private JPasswordField campoConfirmarPassword;
    private JComboBox<String> comboPregunta;
    private JTextField campoRespuesta;
    private JButton btnCrearCuenta;
    private JButton btnVolver;
    private JLabel lblMensaje;

    public VentanaRegistro() {
        setTitle("Playket - Registro");
        setSize(420, 500);
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

        // Nombre y apellidos
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre"), gbc);
        campoNombre = new JTextField();
        campoNombre.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 1;
        panel.add(campoNombre, gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Apellidos"), gbc);
        campoApellidos = new JTextField();
        campoApellidos.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 3;
        panel.add(campoApellidos, gbc);

        // Email
        gbc.gridy = 4;
        panel.add(new JLabel("Correo electrónico"), gbc);
        campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 5;
        panel.add(campoEmail, gbc);

        // Contraseña
        gbc.gridy = 6;
        panel.add(new JLabel("Contraseña (mín. 8 caracteres)"), gbc);
        campoPassword = new JPasswordField();
        campoPassword.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 7;
        panel.add(campoPassword, gbc);

        gbc.gridy = 8;
        panel.add(new JLabel("Confirmar contraseña"), gbc);
        campoConfirmarPassword = new JPasswordField();
        campoConfirmarPassword.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 9;
        panel.add(campoConfirmarPassword, gbc);

        // Pregunta de seguridad
        gbc.gridy = 10;
        panel.add(new JLabel("Pregunta de seguridad"), gbc);
        String[] preguntas = {
                "¿Nombre de tu primera mascota?",
                "¿Ciudad donde naciste?",
                "¿Nombre de tu colegio?",
                "¿Deporte favorito?"
        };
        comboPregunta = new JComboBox<>(preguntas);
        gbc.gridy = 11;
        panel.add(comboPregunta, gbc);

        gbc.gridy = 12;
        panel.add(new JLabel("Respuesta"), gbc);
        campoRespuesta = new JTextField();
        campoRespuesta.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 13;
        panel.add(campoRespuesta, gbc);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 14;
        panel.add(lblMensaje, gbc);

        // Botones
        btnCrearCuenta = new JButton("Crear cuenta");
        btnCrearCuenta.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 15;
        panel.add(btnCrearCuenta, gbc);

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 16;
        panel.add(btnVolver, gbc);

        add(new JScrollPane(panel));
    }

    public String getNombre() { return campoNombre.getText().trim(); }
    public String getApellidos() { return campoApellidos.getText().trim(); }
    public String getEmail() { return campoEmail.getText().trim(); }
    public String getPassword() { return new String(campoPassword.getPassword()); }
    public String getConfirmarPassword() { return new String(campoConfirmarPassword.getPassword()); }
    public String getPreguntaSeguridad() { return (String) comboPregunta.getSelectedItem(); }
    public String getRespuesta() { return campoRespuesta.getText().trim(); }
    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(Color.GREEN.darker());
        lblMensaje.setText(msg);
    }
    public JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    public JButton getBtnVolver() { return btnVolver; }
}