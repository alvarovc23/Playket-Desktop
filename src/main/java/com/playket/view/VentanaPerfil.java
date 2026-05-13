package com.playket.view;

import com.playket.model.Usuario;
import javax.swing.*;
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

        // Título
        JLabel lblTitulo = new JLabel("Mi perfil", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        // Email (no editable)
        gbc.gridy = 1;
        panel.add(new JLabel("Correo electrónico"), gbc);
        JTextField campoEmail = new JTextField(usuario.getEmail());
        campoEmail.setEditable(false);
        campoEmail.setForeground(Color.GRAY);
        campoEmail.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 2;
        panel.add(campoEmail, gbc);

        // Nombre
        gbc.gridy = 3;
        panel.add(new JLabel("Nombre"), gbc);
        campoNombre = new JTextField(usuario.getNombre());
        campoNombre.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 4;
        panel.add(campoNombre, gbc);

        // Apellidos
        gbc.gridy = 5;
        panel.add(new JLabel("Apellidos"), gbc);
        campoApellidos = new JTextField(usuario.getApellidos());
        campoApellidos.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 6;
        panel.add(campoApellidos, gbc);

        // Separador
        gbc.gridy = 7;
        panel.add(new JSeparator(), gbc);

        // Contraseña actual
        gbc.gridy = 8;
        panel.add(new JLabel("Contraseña actual (solo si quieres cambiarla)"), gbc);
        campoPasswordActual = new JPasswordField();
        campoPasswordActual.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 9;
        panel.add(campoPasswordActual, gbc);

        // Nueva contraseña
        gbc.gridy = 10;
        panel.add(new JLabel("Nueva contraseña"), gbc);
        campoPasswordNueva = new JPasswordField();
        campoPasswordNueva.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 11;
        panel.add(campoPasswordNueva, gbc);

        // Confirmar
        gbc.gridy = 12;
        panel.add(new JLabel("Confirmar nueva contraseña"), gbc);
        campoConfirmarPassword = new JPasswordField();
        campoConfirmarPassword.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 13;
        panel.add(campoConfirmarPassword, gbc);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 14;
        panel.add(lblMensaje, gbc);

        // Botones
        btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 15;
        panel.add(btnGuardar, gbc);

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 16;
        panel.add(btnVolver, gbc);

        add(new JScrollPane(panel));
    }

    public String getNombre() { return campoNombre.getText().trim(); }
    public String getApellidos() { return campoApellidos.getText().trim(); }
    public String getPasswordActual() { return new String(campoPasswordActual.getPassword()); }
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
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnVolver() { return btnVolver; }
}