package com.playket.view;

import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(440, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelFondo = EstiloApp.crearPanelFondo();
        panelFondo.setLayout(new BorderLayout());

        JPanel cabecera = EstiloApp.crearCabecera("Recuperar contraseña");

        JPanel tarjeta = EstiloApp.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 2;

        // Primer paso — email
        gbc.gridy = 0;
        tarjeta.add(EstiloApp.crearEtiqueta("Correo electrónico"), gbc);
        campoEmail = EstiloApp.crearCampoTexto();
        gbc.gridy = 1;
        tarjeta.add(campoEmail, gbc);

        btnComprobar = EstiloApp.crearBtnPrimario("Comprobar");
        gbc.gridy = 2;
        tarjeta.add(btnComprobar, gbc);

        // Segundo paso — oculto inicialmente
        panelSegundoPaso = new JPanel(new GridBagLayout());
        panelSegundoPaso.setBackground(EstiloApp.BLANCO);
        panelSegundoPaso.setVisible(false);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(5, 0, 5, 0);
        gbc2.gridwidth = 2;

        lblPregunta = new JLabel("");
        lblPregunta.setFont(EstiloApp.FUENTE_SUBTITULO);
        lblPregunta.setForeground(EstiloApp.AZUL_OSCURO);
        gbc2.gridy = 0;
        panelSegundoPaso.add(lblPregunta, gbc2);

        gbc2.gridy = 1;
        panelSegundoPaso.add(EstiloApp.crearEtiqueta("Respuesta"), gbc2);
        campoRespuesta = EstiloApp.crearCampoTexto();
        gbc2.gridy = 2;
        panelSegundoPaso.add(campoRespuesta, gbc2);

        gbc2.gridy = 3;
        panelSegundoPaso.add(EstiloApp.crearEtiqueta("Nueva contraseña (mín. 8 caracteres)"), gbc2);
        campoPasswordNueva = EstiloApp.crearCampoPassword();
        gbc2.gridy = 4;
        panelSegundoPaso.add(campoPasswordNueva, gbc2);

        gbc2.gridy = 5;
        panelSegundoPaso.add(EstiloApp.crearEtiqueta("Confirmar nueva contraseña"), gbc2);
        campoConfirmarPassword = EstiloApp.crearCampoPassword();
        gbc2.gridy = 6;
        panelSegundoPaso.add(campoConfirmarPassword, gbc2);

        btnCambiarPassword = EstiloApp.crearBtnPrimario("Cambiar contraseña");
        gbc2.gridy = 7;
        panelSegundoPaso.add(btnCambiarPassword, gbc2);

        gbc.gridy = 3;
        tarjeta.add(panelSegundoPaso, gbc);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);
        gbc.gridy = 4;
        tarjeta.add(lblMensaje, gbc);

        // Volver
        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        gbc.gridy = 5;
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
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setText(msg);
    }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(EstiloApp.VERDE_EXITO);
        lblMensaje.setText(msg);
    }
    public JButton getBtnComprobar() { return btnComprobar; }
    public JButton getBtnCambiarPassword() { return btnCambiarPassword; }
    public JButton getBtnVolver() { return btnVolver; }
}