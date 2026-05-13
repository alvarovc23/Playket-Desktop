package com.playket.view;

import com.playket.model.Torneo;
import javax.swing.*;
import java.awt.*;

public class VentanaDesignarCoOrganizador extends JFrame {

    private final Torneo torneo;
    private JTextField campoEmail;
    private JButton btnAsignar;
    private JButton btnVolver;
    private JLabel lblMensaje;
    private JList<String> listaCoOrganizadores;
    private DefaultListModel<String> modeloLista;

    public VentanaDesignarCoOrganizador(Torneo torneo) {
        this.torneo = torneo;
        setTitle("Playket - Co-organizadores: " + torneo.getNombre());
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Designar co-organizador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Buscar usuario por correo electrónico"), gbc);
        campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 2;
        panel.add(campoEmail, gbc);

        btnAsignar = new JButton("Asignar como co-organizador");
        btnAsignar.setPreferredSize(new Dimension(0, 34));
        gbc.gridy = 3;
        panel.add(btnAsignar, gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Co-organizadores actuales"), gbc);
        modeloLista = new DefaultListModel<>();
        listaCoOrganizadores = new JList<>(modeloLista);
        JScrollPane scroll = new JScrollPane(listaCoOrganizadores);
        scroll.setPreferredSize(new Dimension(0, 100));
        gbc.gridy = 5;
        panel.add(scroll, gbc);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 6;
        panel.add(lblMensaje, gbc);

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(0, 34));
        gbc.gridy = 7;
        panel.add(btnVolver, gbc);

        add(panel);
    }

    public void cargarCoOrganizadores(java.util.List<String> emails) {
        modeloLista.clear();
        if (emails.isEmpty()) {
            modeloLista.addElement("No hay co-organizadores asignados");
        } else {
            for (String email : emails) modeloLista.addElement(email);
        }
    }

    public String getEmail() { return campoEmail.getText().trim(); }
    public void setMensaje(String msg) {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText(msg);
    }
    public void setMensajeVerde(String msg) {
        lblMensaje.setForeground(Color.GREEN.darker());
        lblMensaje.setText(msg);
    }
    public JButton getBtnAsignar() { return btnAsignar; }
    public JButton getBtnVolver() { return btnVolver; }
    public Torneo getTorneo() { return torneo; }
}