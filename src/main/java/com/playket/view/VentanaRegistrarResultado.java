package com.playket.view;

import com.playket.model.Participante;
import com.playket.model.Partido;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VentanaRegistrarResultado extends JFrame {

    private final Partido partido;
    private JButton btnLocal;
    private JButton btnVisitante;
    private JButton btnVolver;
    private JLabel lblMensaje;

    public VentanaRegistrarResultado(Partido partido, Map<Integer, Participante> mapaParticipantes) {
        this.partido = partido;
        setTitle("Playket - Registrar resultado");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes(mapaParticipantes);
    }

    private void inicializarComponentes(Map<Integer, Participante> mapaParticipantes) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("¿Quién ha ganado?", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        Participante local = mapaParticipantes.get(partido.getIdLocal());
        Participante visitante = mapaParticipantes.get(partido.getIdVisitante());

        String nombreLocal = local != null ? local.getNombre() : "Local";
        String nombreVisitante = visitante != null ? visitante.getNombre() : "Visitante";

        gbc.gridwidth = 1;
        gbc.weightx = 0.5;

        btnLocal = new JButton(nombreLocal);
        btnLocal.setPreferredSize(new Dimension(0, 60));
        btnLocal.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(btnLocal, gbc);

        btnVisitante = new JButton(nombreVisitante);
        btnVisitante.setPreferredSize(new Dimension(0, 60));
        btnVisitante.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnVisitante, gbc);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(lblMensaje, gbc);

        btnVolver = new JButton("Volver");
        gbc.gridy = 3;
        panel.add(btnVolver, gbc);

        add(panel);
    }

    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public JButton getBtnLocal() { return btnLocal; }
    public JButton getBtnVisitante() { return btnVisitante; }
    public JButton getBtnVolver() { return btnVolver; }
}