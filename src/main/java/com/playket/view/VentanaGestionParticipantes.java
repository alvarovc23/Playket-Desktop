package com.playket.view;

import com.playket.model.Participante;
import com.playket.model.Torneo;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionParticipantes extends JFrame {

    private final Torneo torneo;
    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JTextField campoEmail;
    private JButton btnAnadir;
    private JButton btnEliminar;
    private JButton btnGenerar;
    private JButton btnVolver;
    private JLabel lblMensaje;
    private JLabel lblContador;
    private DefaultTableModel modeloTabla;
    private JTable tablaParticipantes;

    public VentanaGestionParticipantes(Torneo torneo) {
        this.torneo = torneo;
        setTitle("Playket - Participantes: " + torneo.getNombre());
        setSize(570, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstiloApp.GRIS_CLARO);

        // Cabecera
        JPanel cabecera = EstiloApp.crearCabecera("Participantes: " + torneo.getNombre());

        // Panel formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(EstiloApp.BLANCO);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE),
                        "Añadir participante"
                ),
                new EmptyBorder(5, 10, 10, 10)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panelForm.add(EstiloApp.crearEtiqueta("Nombre *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        campoNombre = EstiloApp.crearCampoTexto();
        panelForm.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelForm.add(EstiloApp.crearEtiqueta("Apellidos"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        campoApellidos = EstiloApp.crearCampoTexto();
        panelForm.add(campoApellidos, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panelForm.add(EstiloApp.crearEtiqueta("Email"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        campoEmail = EstiloApp.crearCampoTexto();
        panelForm.add(campoEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnAnadir = EstiloApp.crearBtnPrimario("+ Añadir participante");
        panelForm.add(btnAnadir, gbc);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new String[]{"#", "Nombre", "Apellidos", "Email"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaParticipantes = new JTable(modeloTabla);
        EstiloApp.estilizarTabla(tablaParticipantes);
        tablaParticipantes.getColumnModel().getColumn(0).setMaxWidth(30);
        JScrollPane scrollTabla = EstiloApp.crearScroll(tablaParticipantes);
        scrollTabla.setPreferredSize(new Dimension(0, 180));

        // Contador y eliminar
        JPanel panelAcciones = new JPanel(new BorderLayout());
        panelAcciones.setBackground(EstiloApp.GRIS_CLARO);
        panelAcciones.setBorder(new EmptyBorder(6, 0, 6, 0));
        lblContador = new JLabel("0 / " + torneo.getNumParticipantes() + " participantes");
        lblContador.setFont(EstiloApp.FUENTE_SUBTITULO);
        lblContador.setForeground(EstiloApp.AZUL_OSCURO);
        btnEliminar = EstiloApp.crearBtnPeligro("Eliminar seleccionado");
        panelAcciones.add(lblContador, BorderLayout.WEST);
        panelAcciones.add(btnEliminar, BorderLayout.EAST);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(EstiloApp.GRIS_CLARO);
        btnGenerar = EstiloApp.crearBtnPrimario("Generar cuadro / calendario");
        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        panelBotones.add(btnGenerar);
        panelBotones.add(btnVolver);

        // Panel sur
        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
        panelSur.setBackground(EstiloApp.GRIS_CLARO);
        panelSur.setBorder(new EmptyBorder(0, 15, 10, 15));
        panelSur.add(panelAcciones);
        panelSur.add(lblMensaje);
        panelSur.add(Box.createRigidArea(new Dimension(0, 6)));
        panelSur.add(panelBotones);

        JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
        panelCentro.setBackground(EstiloApp.GRIS_CLARO);
        panelCentro.setBorder(new EmptyBorder(10, 15, 0, 15));
        panelCentro.add(panelForm, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void cargarParticipantes(List<Participante> participantes) {
        modeloTabla.setRowCount(0);
        int i = 1;
        for (Participante p : participantes) {
            modeloTabla.addRow(new Object[]{
                    i++,
                    p.getNombre(),
                    p.getApellidos() != null ? p.getApellidos() : "",
                    p.getEmail() != null ? p.getEmail() : ""
            });
        }
        lblContador.setText(participantes.size() + " / " + torneo.getNumParticipantes() + " participantes");
    }

    public String getNombreParticipante() { return campoNombre.getText().trim(); }
    public String getApellidosParticipante() { return campoApellidos.getText().trim(); }
    public String getEmailParticipante() { return campoEmail.getText().trim(); }
    public int getFilaSeleccionada() { return tablaParticipantes.getSelectedRow(); }
    public void limpiarFormulario() {
        campoNombre.setText("");
        campoApellidos.setText("");
        campoEmail.setText("");
    }
    public void setMensaje(String msg) {
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setText(msg);
    }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(EstiloApp.VERDE_EXITO);
        lblMensaje.setText(msg);
    }
    public JButton getBtnAnadir() { return btnAnadir; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnGenerar() { return btnGenerar; }
    public JButton getBtnVolver() { return btnVolver; }
    public Torneo getTorneo() { return torneo; }
}