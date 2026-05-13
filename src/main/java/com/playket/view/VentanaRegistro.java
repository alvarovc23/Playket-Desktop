package com.playket.view;

import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(440, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelFondo = EstiloApp.crearPanelFondo();
        panelFondo.setLayout(new BorderLayout());

        JPanel cabecera = EstiloApp.crearCabecera("Crear cuenta");

        JPanel tarjeta = EstiloApp.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 2;

        gbc.gridy = 0;
        tarjeta.add(EstiloApp.crearEtiqueta("Nombre"), gbc);
        campoNombre = EstiloApp.crearCampoTexto();
        gbc.gridy = 1;
        tarjeta.add(campoNombre, gbc);

        gbc.gridy = 2;
        tarjeta.add(EstiloApp.crearEtiqueta("Apellidos"), gbc);
        campoApellidos = EstiloApp.crearCampoTexto();
        gbc.gridy = 3;
        tarjeta.add(campoApellidos, gbc);

        gbc.gridy = 4;
        tarjeta.add(EstiloApp.crearEtiqueta("Correo electrónico"), gbc);
        campoEmail = EstiloApp.crearCampoTexto();
        gbc.gridy = 5;
        tarjeta.add(campoEmail, gbc);

        gbc.gridy = 6;
        tarjeta.add(EstiloApp.crearEtiqueta("Contraseña (mín. 8 caracteres)"), gbc);
        campoPassword = EstiloApp.crearCampoPassword();
        gbc.gridy = 7;
        tarjeta.add(campoPassword, gbc);

        gbc.gridy = 8;
        tarjeta.add(EstiloApp.crearEtiqueta("Confirmar contraseña"), gbc);
        campoConfirmarPassword = EstiloApp.crearCampoPassword();
        gbc.gridy = 9;
        tarjeta.add(campoConfirmarPassword, gbc);

        gbc.gridy = 10;
        tarjeta.add(EstiloApp.crearEtiqueta("Pregunta de seguridad"), gbc);
        String[] preguntas = {
                "¿Nombre de tu primera mascota?",
                "¿Ciudad donde naciste?",
                "¿Nombre de tu colegio?",
                "¿Deporte favorito?"
        };
        comboPregunta = new JComboBox<>(preguntas);
        comboPregunta.setFont(EstiloApp.FUENTE_NORMAL);
        gbc.gridy = 11;
        tarjeta.add(comboPregunta, gbc);

        gbc.gridy = 12;
        tarjeta.add(EstiloApp.crearEtiqueta("Respuesta"), gbc);
        campoRespuesta = EstiloApp.crearCampoTexto();
        gbc.gridy = 13;
        tarjeta.add(campoRespuesta, gbc);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);
        gbc.gridy = 14;
        tarjeta.add(lblMensaje, gbc);

        btnCrearCuenta = EstiloApp.crearBtnPrimario("Crear cuenta");
        gbc.gridy = 15;
        gbc.insets = new Insets(5, 0, 4, 0);
        tarjeta.add(btnCrearCuenta, gbc);

        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        gbc.gridy = 16;
        gbc.insets = new Insets(4, 0, 0, 0);
        tarjeta.add(btnVolver, gbc);

        JPanel panelCentro = EstiloApp.crearPanelFondo();
        panelCentro.setLayout(new GridBagLayout());
        panelCentro.setBorder(new EmptyBorder(15, 30, 15, 30));
        panelCentro.add(tarjeta);

        panelFondo.add(cabecera, BorderLayout.NORTH);
        panelFondo.add(new JScrollPane(panelCentro), BorderLayout.CENTER);

        add(panelFondo);
    }

    public String getNombre() { return campoNombre.getText().trim(); }
    public String getApellidos() { return campoApellidos.getText().trim(); }
    public String getEmail() { return campoEmail.getText().trim(); }
    public String getPassword() { return new String(campoPassword.getPassword()); }
    public String getConfirmarPassword() { return new String(campoConfirmarPassword.getPassword()); }
    public String getPreguntaSeguridad() { return (String) comboPregunta.getSelectedItem(); }
    public String getRespuesta() { return campoRespuesta.getText().trim(); }
    public void setMensaje(String msg) {
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setText(msg);
    }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(EstiloApp.VERDE_EXITO);
        lblMensaje.setText(msg);
    }
    public JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    public JButton getBtnVolver() { return btnVolver; }
}