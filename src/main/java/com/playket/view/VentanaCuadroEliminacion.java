package com.playket.view;

import com.playket.model.Participante;
import com.playket.model.Partido;
import com.playket.model.Torneo;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class VentanaCuadroEliminacion extends JFrame {

    private final Torneo torneo;
    private JPanel panelCuadro;
    private JButton btnVolver;
    private JButton btnRegistrarResultado;
    private Partido partidoSeleccionado;

    public VentanaCuadroEliminacion(Torneo torneo) {
        this.torneo = torneo;
        setTitle("Playket - " + torneo.getNombre());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cabecera
        JPanel panelCabecera = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel(torneo.getNombre(), SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel lblInfo = new JLabel("Eliminación | " + torneo.getEstado(), SwingConstants.LEFT);
        lblInfo.setForeground(Color.GRAY);
        panelCabecera.add(lblTitulo, BorderLayout.NORTH);
        panelCabecera.add(lblInfo, BorderLayout.SOUTH);

        // Panel cuadro con scroll horizontal
        panelCuadro = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JScrollPane scroll = new JScrollPane(panelCuadro);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        btnRegistrarResultado = new JButton("Registrar resultado");
        btnVolver = new JButton("Volver");
        panelBotones.add(btnRegistrarResultado);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public void cargarCuadro(List<Partido> partidos, Map<Integer, Participante> mapaParticipantes) {
        panelCuadro.removeAll();

        JPanel ronda = new JPanel();
        ronda.setLayout(new BoxLayout(ronda, BoxLayout.Y_AXIS));
        ronda.setBorder(BorderFactory.createTitledBorder("Ronda 1"));

        for (Partido p : partidos) {
            JPanel panelPartido = crearPanelPartido(p, mapaParticipantes);
            panelPartido.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    partidoSeleccionado = p;
                    panelCuadro.repaint();
                }
            });
            ronda.add(panelPartido);
            ronda.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        panelCuadro.add(ronda);
        panelCuadro.revalidate();
        panelCuadro.repaint();
    }

    private JPanel crearPanelPartido(Partido p, Map<Integer, Participante> mapaParticipantes) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 2, 2));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setPreferredSize(new Dimension(180, 60));
        panel.setBackground(Color.WHITE);

        Participante local = mapaParticipantes.get(p.getIdLocal());
        Participante visitante = mapaParticipantes.get(p.getIdVisitante());

        String nombreLocal = local != null ? local.getNombre() : "TBD";
        String nombreVisitante = visitante != null ? visitante.getNombre() : "TBD";

        JLabel lblLocal = new JLabel("  " + nombreLocal);
        JLabel lblVisitante = new JLabel("  " + nombreVisitante);

        if (p.getIdGanador() != null) {
            if (p.getIdGanador() == p.getIdLocal()) {
                lblLocal.setFont(new Font("Arial", Font.BOLD, 12));
                lblVisitante.setForeground(Color.GRAY);
            } else {
                lblVisitante.setFont(new Font("Arial", Font.BOLD, 12));
                lblLocal.setForeground(Color.GRAY);
            }
        }

        panel.add(lblLocal);
        panel.add(lblVisitante);
        return panel;
    }

    public Partido getPartidoSeleccionado() { return partidoSeleccionado; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnRegistrarResultado() { return btnRegistrarResultado; }
    public Torneo getTorneo() { return torneo; }
}