package com.playket.view;

import com.playket.model.Usuario;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaPerfil extends JFrame {

    private final Usuario usuario;
    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JPasswordField campoPasswordActual;
    private JPasswordField campoPasswordNueva;
    private JPasswordField campoConfirmarPassword;
    private JButton btnGuardar;
    private JButton btnVolver;
    private JLabel lblMensaje;

    public VentanaPerfil(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Playket - Perfil");
        setSize(440, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelFondo = EstiloApp.crearPanelFondo();
        panelFondo.setLayout(new BorderLayout());

        JPanel cabecera = EstiloApp.crearCabecera("Mi perfil");

        JPanel tarjeta = EstiloApp.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 2;

        // Email (no editable)
        gbc.gridy = 0;
        tarjeta.add(EstiloApp.crearEtiqueta("Correo electrónico"), gbc);
        JTextField campoEmail = new JTextField(usuario.getEmail());
        campoEmail.setEditable(false);
        campoEmail.setForeground(EstiloApp.GRIS_TEXTO);
        campoEmail.setFont(EstiloApp.FUENTE_NORMAL);
        campoEmail.setPreferredSize(new Dimension(0, 32));
        gbc.gridy = 1;
        tarjeta.add(campoEmail, gbc);

        // Nombre
        gbc.gridy = 2;
        tarjeta.add(EstiloApp.crearEtiqueta("Nombre"), gbc);
        campoNombre = EstiloApp.crearCampoTexto();
        campoNombre.setText(usuario.getNombre());
        gbc.gridy = 3;
        tarjeta.add(campoNombre, gbc);

        // Apellidos
        gbc.gridy = 4;
        tarjeta.add(EstiloApp.crearEtiqueta("Apellidos"), gbc);
        campoApellidos = EstiloApp.crearCampoTexto();
        campoApellidos.setText(usuario.getApellidos());
        gbc.gridy = 5;
        tarjeta.add(campoApellidos, gbc);

        // Separador
        gbc.gridy = 6;
        JSeparator sep = new JSeparator();
        sep.setForeground(EstiloApp.GRIS_BORDE);
        tarjeta.add(sep, gbc);

        // Contraseña actual
        gbc.gridy = 7;
        tarjeta.add(EstiloApp.crearEtiqueta("Contraseña actual (solo si quieres cambiarla)"), gbc);
        campoPasswordActual = EstiloApp.crearCampoPassword();
        gbc.gridy = 8;
        tarjeta.add(campoPasswordActual, gbc);

        // Nueva contraseña
        gbc.gridy = 9;
        tarjeta.add(EstiloApp.crearEtiqueta("Nueva contraseña"), gbc);
        campoPasswordNueva = EstiloApp.crearCampoPassword();
        gbc.gridy = 10;
        tarjeta.add(campoPasswordNueva, gbc);

        // Confirmar
        gbc.gridy = 11;
        tarjeta.add(EstiloApp.crearEtiqueta("Confirmar nueva contraseña"), gbc);
        campoConfirmarPassword = EstiloApp.crearCampoPassword();
        gbc.gridy = 12;
        tarjeta.add(campoConfirmarPassword, gbc);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);
        gbc.gridy = 13;
        tarjeta.add(lblMensaje, gbc);

        // Botones
        btnGuardar = EstiloApp.crearBtnPrimario("Guardar cambios");
        gbc.gridy = 14;
        gbc.insets = new Insets(5, 0, 4, 0);
        tarjeta.add(btnGuardar, gbc);

        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        gbc.gridy = 15;
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
    public String getPasswordActual() { return new String(campoPasswordActual.getPassword()); }
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
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnVolver() { return btnVolver; }
}