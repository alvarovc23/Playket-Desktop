package com.playket.view;

import com.playket.model.Participante;
import com.playket.model.Torneo;
import javax.swing.*;
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
        setSize(550, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Panel formulario añadir participante
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Añadir participante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panelForm.add(new JLabel("Nombre *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        campoNombre = new JTextField();
        panelForm.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelForm.add(new JLabel("Apellidos"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        campoApellidos = new JTextField();
        panelForm.add(campoApellidos, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panelForm.add(new JLabel("Email"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        campoEmail = new JTextField();
        panelForm.add(campoEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnAnadir = new JButton("+ Añadir participante");
        panelForm.add(btnAnadir, gbc);

        // Tabla participantes
        modeloTabla = new DefaultTableModel(new String[]{"#", "Nombre", "Apellidos", "Email"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaParticipantes = new JTable(modeloTabla);
        tablaParticipantes.getColumnModel().getColumn(0).setMaxWidth(30);
        JScrollPane scrollTabla = new JScrollPane(tablaParticipantes);
        scrollTabla.setPreferredSize(new Dimension(0, 200));

        // Panel contador y botón eliminar
        JPanel panelAcciones = new JPanel(new BorderLayout());
        lblContador = new JLabel("0 / " + torneo.getNumParticipantes() + " participantes");
        lblContador.setFont(new Font("Arial", Font.BOLD, 13));
        btnEliminar = new JButton("Eliminar seleccionado");
        panelAcciones.add(lblContador, BorderLayout.WEST);
        panelAcciones.add(btnEliminar, BorderLayout.EAST);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        btnGenerar = new JButton("Generar cuadro / calendario");
        btnVolver = new JButton("Volver");
        panelBotones.add(btnGenerar);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelForm, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
        panelSur.add(panelAcciones);
        panelSur.add(Box.createRigidArea(new Dimension(0, 8)));
        panelSur.add(lblMensaje);
        panelSur.add(Box.createRigidArea(new Dimension(0, 8)));
        panelSur.add(panelBotones);

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
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(msg);
    }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(Color.GREEN.darker());
        lblMensaje.setText(msg);
    }
    public JButton getBtnAnadir() { return btnAnadir; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnGenerar() { return btnGenerar; }
    public JButton getBtnVolver() { return btnVolver; }
    public Torneo getTorneo() { return torneo; }
}