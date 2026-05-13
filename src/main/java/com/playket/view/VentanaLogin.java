package com.playket.view;

import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(420, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        //Fondo gris claro
        JPanel panelFondo = EstiloApp.crearPanelFondo();
        panelFondo.setLayout(new BorderLayout());

        //Cabecera azul oscuro con el logo
        JPanel cabecera = EstiloApp.crearCabecera("Playket");
        cabecera.setPreferredSize(new Dimension(0, 80));

        //Tarjeta blanca central con el formulario
        JPanel tarjeta = EstiloApp.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridwidth = 2;

        //Email
        gbc.gridy = 0;
        tarjeta.add(EstiloApp.crearEtiqueta("Correo electrónico"), gbc);
        campoEmail = EstiloApp.crearCampoTexto();
        gbc.gridy = 1;
        tarjeta.add(campoEmail, gbc);

        //Contraseña
        gbc.gridy = 2;
        tarjeta.add(EstiloApp.crearEtiqueta("Contraseña"), gbc);
        campoPassword = EstiloApp.crearCampoPassword();
        gbc.gridy = 3;
        tarjeta.add(campoPassword, gbc);

        //¿Olvidaste tu contraseña?
        btnOlvidePassword = EstiloApp.crearBtnTexto("¿Olvidaste tu contraseña?");
        gbc.gridy = 4;
        tarjeta.add(btnOlvidePassword, gbc);

        //Mensaje de error
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);
        gbc.gridy = 5;
        tarjeta.add(lblMensaje, gbc);

        //Botones
        btnIniciarSesion = EstiloApp.crearBtnPrimario("Iniciar sesión");
        gbc.gridy = 6;
        tarjeta.add(btnIniciarSesion, gbc);

        gbc.insets = new Insets(4, 0, 0, 0);
        btnCrearCuenta = EstiloApp.crearBtnSecundario("Crear cuenta nueva");
        gbc.gridy = 7;
        tarjeta.add(btnCrearCuenta, gbc);

        //Tarjeta con márgenes laterales
        JPanel panelCentro = EstiloApp.crearPanelFondo();
        panelCentro.setLayout(new GridBagLayout());
        panelCentro.setBorder(new EmptyBorder(20, 30, 20, 30));
        panelCentro.add(tarjeta);

        panelFondo.add(cabecera, BorderLayout.NORTH);
        panelFondo.add(panelCentro, BorderLayout.CENTER);

        add(panelFondo);
    }

    public String getEmail() { return campoEmail.getText().trim(); }
    public String getPassword() { return new String(campoPassword.getPassword()); }
    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    public JButton getBtnOlvidePassword() { return btnOlvidePassword; }
}