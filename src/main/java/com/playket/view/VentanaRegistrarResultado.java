package com.playket.view;

import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes(mapaParticipantes);
    }

    private void inicializarComponentes(Map<Integer, Participante> mapaParticipantes) {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstiloApp.GRIS_CLARO);

        // Cabecera
        JPanel cabecera = EstiloApp.crearCabecera("¿Quién ha ganado?");

        // Panel central
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(EstiloApp.GRIS_CLARO);
        panelCentro.setBorder(new EmptyBorder(20, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 0.5;

        Participante local = mapaParticipantes.get(partido.getIdLocal());
        Participante visitante = mapaParticipantes.get(partido.getIdVisitante());

        String nombreLocal = local != null ? local.getNombre() : "Local";
        String nombreVisitante = visitante != null ? visitante.getNombre() : "Visitante";

        // Botones de equipos grandes
        btnLocal = new JButton(nombreLocal);
        btnLocal.setBackground(EstiloApp.AZUL_OSCURO);
        btnLocal.setForeground(EstiloApp.BLANCO);
        btnLocal.setFont(EstiloApp.FUENTE_SUBTITULO);
        btnLocal.setFocusPainted(false);
        btnLocal.setBorder(new EmptyBorder(15, 20, 15, 20));
        btnLocal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLocal.setPreferredSize(new Dimension(0, 70));

        btnVisitante = new JButton(nombreVisitante);
        btnVisitante.setBackground(EstiloApp.NARANJA);
        btnVisitante.setForeground(EstiloApp.BLANCO);
        btnVisitante.setFont(EstiloApp.FUENTE_SUBTITULO);
        btnVisitante.setFocusPainted(false);
        btnVisitante.setBorder(new EmptyBorder(15, 20, 15, 20));
        btnVisitante.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVisitante.setPreferredSize(new Dimension(0, 70));

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentro.add(btnLocal, gbc);
        gbc.gridx = 1;
        panelCentro.add(btnVisitante, gbc);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panelCentro.add(lblMensaje, gbc);

        // Botón volver
        JPanel panelBotones = new JPanel(new GridLayout(1, 1));
        panelBotones.setBackground(EstiloApp.GRIS_CLARO);
        panelBotones.setBorder(new EmptyBorder(0, 30, 15, 30));
        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        panelBotones.add(btnVolver);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public JButton getBtnLocal() { return btnLocal; }
    public JButton getBtnVisitante() { return btnVisitante; }
    public JButton getBtnVolver() { return btnVolver; }
}