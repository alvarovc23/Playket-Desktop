package com.playket.view;

import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VentanaCuadroEliminacion extends JFrame {

    private final Torneo torneo;
    private JPanel panelCuadro;
    private JPanel panelSeleccionado = null;
    private JButton btnVolver;
    private JButton btnCerrarTorneo;
    private JButton btnRegistrarResultado;
    private Partido partidoSeleccionado;

    public VentanaCuadroEliminacion(Torneo torneo) {
        this.torneo = torneo;
        setTitle("Playket - " + torneo.getNombre());
        setSize(620, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 10));
        panelPrincipal.setBackground(EstiloApp.GRIS_CLARO);

        // Cabecera
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(EstiloApp.AZUL_OSCURO);
        cabecera.setPreferredSize(new Dimension(0, 65));
        cabecera.setBorder(new EmptyBorder(0, 15, 0, 15));
        JLabel lblTitulo = new JLabel(torneo.getNombre());
        lblTitulo.setFont(EstiloApp.FUENTE_TITULO);
        lblTitulo.setForeground(EstiloApp.BLANCO);
        JLabel lblInfo = new JLabel("Eliminación | " + torneo.getEstado());
        lblInfo.setFont(EstiloApp.FUENTE_PEQUEÑA);
        lblInfo.setForeground(new Color(200, 200, 200));
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setBackground(EstiloApp.AZUL_OSCURO);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblInfo);
        cabecera.add(panelTextos, BorderLayout.CENTER);

        // Panel cuadro
        panelCuadro = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelCuadro.setBackground(EstiloApp.BLANCO);
        JScrollPane scroll = new JScrollPane(panelCuadro);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE));

        // Botones inferiores
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setBackground(EstiloApp.GRIS_CLARO);
        panelBotones.setBorder(new EmptyBorder(0, 15, 15, 15));
        btnRegistrarResultado = EstiloApp.crearBtnPrimario("Registrar resultado");
        btnCerrarTorneo = EstiloApp.crearBtnPeligro("Cerrar torneo");
        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        panelBotones.add(btnRegistrarResultado);
        panelBotones.add(btnCerrarTorneo);
        panelBotones.add(btnVolver);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(EstiloApp.GRIS_CLARO);
        panelCentro.setBorder(new EmptyBorder(10, 15, 0, 15));
        panelCentro.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void cargarCuadro(List<Partido> partidos, Map<Integer, Participante> mapaParticipantes) {
        panelCuadro.removeAll();
        partidoSeleccionado = null;
        panelSeleccionado = null;

        // Agrupar los partidos por ronda manteniendo el orden numérico
        java.util.TreeMap<Integer, List<Partido>> porRonda = new java.util.TreeMap<>();
        for (Partido p : partidos) {
            porRonda.computeIfAbsent(p.getRonda(), k -> new ArrayList<>()).add(p);
        }

        int totalRondas = porRonda.size();

        for (java.util.Map.Entry<Integer, List<Partido>> entrada : porRonda.entrySet()) {
            int numRonda = entrada.getKey();
            List<Partido> partidosRonda = entrada.getValue();

            // Nombre de la ronda: Final si es la última y tiene un solo partido
            String nombreRonda;
            if (numRonda == totalRondas && partidosRonda.size() == 1) {
                nombreRonda = "Final";
            } else if (numRonda == totalRondas - 1 && partidosRonda.size() == 2) {
                nombreRonda = "Semifinales";
            } else {
                nombreRonda = "Ronda " + numRonda;
            }

            JPanel columnaRonda = new JPanel();
            columnaRonda.setLayout(new BoxLayout(columnaRonda, BoxLayout.Y_AXIS));
            columnaRonda.setBackground(EstiloApp.BLANCO);
            columnaRonda.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(EstiloApp.AZUL_OSCURO),
                    nombreRonda
            ));

            for (Partido p : partidosRonda) {
                JPanel panelPartido = crearPanelPartido(p, mapaParticipantes);
                panelPartido.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        if (panelSeleccionado != null) {
                            panelSeleccionado.setBackground(EstiloApp.BLANCO);
                            panelSeleccionado.setBorder(
                                    BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE));
                        }
                        partidoSeleccionado = p;
                        panelSeleccionado = panelPartido;
                        panelPartido.setBackground(EstiloApp.AZUL_SEL);
                        panelPartido.setBorder(
                                BorderFactory.createLineBorder(EstiloApp.AZUL_OSCURO, 2));
                    }
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        if (panelPartido != panelSeleccionado)
                            panelPartido.setBackground(EstiloApp.AZUL_SEL);
                    }
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        if (panelPartido != panelSeleccionado)
                            panelPartido.setBackground(EstiloApp.BLANCO);
                    }
                });
                columnaRonda.add(panelPartido);
                columnaRonda.add(Box.createRigidArea(new Dimension(0, 6)));
            }

            panelCuadro.add(columnaRonda);
        }

        panelCuadro.revalidate();
        panelCuadro.repaint();
    }

    private JPanel crearPanelPartido(Partido p, Map<Integer, Participante> mapaParticipantes) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 2, 2));
        panel.setBorder(BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE));
        panel.setPreferredSize(new Dimension(190, 64));
        panel.setBackground(EstiloApp.BLANCO);

        Participante local = mapaParticipantes.get(p.getIdLocal());
        Participante visitante = mapaParticipantes.get(p.getIdVisitante());

        String nombreLocal = local != null ? local.getNombre() : "TBD";
        String nombreVisitante = visitante != null ? visitante.getNombre() : "TBD";

        JLabel lblLocal = new JLabel("  " + nombreLocal);
        lblLocal.setFont(EstiloApp.FUENTE_NORMAL);
        JLabel lblVisitante = new JLabel("  " + nombreVisitante);
        lblVisitante.setFont(EstiloApp.FUENTE_NORMAL);

        if (p.getIdGanador() != null) {
            if (p.getIdGanador() == p.getIdLocal()) {
                lblLocal.setFont(EstiloApp.FUENTE_SUBTITULO);
                lblLocal.setForeground(EstiloApp.AZUL_OSCURO);
                lblVisitante.setForeground(EstiloApp.GRIS_TEXTO);
            } else {
                lblVisitante.setFont(EstiloApp.FUENTE_SUBTITULO);
                lblVisitante.setForeground(EstiloApp.AZUL_OSCURO);
                lblLocal.setForeground(EstiloApp.GRIS_TEXTO);
            }
        }

        panel.add(lblLocal);
        panel.add(lblVisitante);
        return panel;
    }

    public Partido getPartidoSeleccionado() { return partidoSeleccionado; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnRegistrarResultado() { return btnRegistrarResultado; }
    public JButton getBtnCerrarTorneo() { return btnCerrarTorneo; }
    public Torneo getTorneo() { return torneo; }
}