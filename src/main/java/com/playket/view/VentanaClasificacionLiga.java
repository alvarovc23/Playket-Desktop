package com.playket.view;

import com.playket.model.Torneo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaClasificacionLiga extends JFrame {

    private final Torneo torneo;
    private DefaultTableModel modeloClasificacion;
    private DefaultTableModel modeloPartidos;
    private JTable tablaClasificacion;
    private JTable tablaPartidos;
    private JButton btnRegistrarResultado;
    private JButton btnVolver;
    private JButton btnCerrarTorneo;
    private int idPartidoSeleccionado = -1;
    private int idLocalSeleccionado = -1;
    private int idVisitanteSeleccionado = -1;

    public VentanaClasificacionLiga(Torneo torneo) {
        this.torneo = torneo;
        setTitle("Playket - " + torneo.getNombre());
        setSize(650, 600);
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
        JLabel lblInfo = new JLabel("Liga | " + torneo.getEstado(), SwingConstants.LEFT);
        lblInfo.setForeground(Color.GRAY);
        panelCabecera.add(lblTitulo, BorderLayout.NORTH);
        panelCabecera.add(lblInfo, BorderLayout.SOUTH);

        // Panel central con clasificación y partidos
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 0, 10));

        // Tabla clasificación
        modeloClasificacion = new DefaultTableModel(
                new String[]{"#", "Equipo", "PJ", "PG", "PE", "PP", "Pts"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaClasificacion = new JTable(modeloClasificacion);
        tablaClasificacion.getColumnModel().getColumn(0).setMaxWidth(30);
        JScrollPane scrollClasif = new JScrollPane(tablaClasificacion);
        scrollClasif.setBorder(BorderFactory.createTitledBorder("Clasificación"));

        // Tabla partidos
        modeloPartidos = new DefaultTableModel(
                new String[]{"Local", "Visitante", "Estado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPartidos = new JTable(modeloPartidos);
        JScrollPane scrollPartidos = new JScrollPane(tablaPartidos);
        scrollPartidos.setBorder(BorderFactory.createTitledBorder("Partidos"));

        panelCentral.add(scrollClasif);
        panelCentral.add(scrollPartidos);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        btnRegistrarResultado = new JButton("Registrar resultado");
        btnCerrarTorneo = new JButton("Cerrar torneo");
        btnVolver = new JButton("Volver");
        panelBotones.add(btnRegistrarResultado);
        panelBotones.add(btnCerrarTorneo);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public void cargarClasificacion(java.util.List<Object[]> filas) {
        modeloClasificacion.setRowCount(0);
        int pos = 1;
        for (Object[] fila : filas) {
            Object[] row = new Object[7];
            row[0] = pos++;
            System.arraycopy(fila, 0, row, 1, fila.length);
            modeloClasificacion.addRow(row);
        }
    }

    public void cargarPartidos(java.util.List<Object[]> filas,
                               java.util.List<int[]> ids) {
        modeloPartidos.setRowCount(0);
        for (Object[] fila : filas) modeloPartidos.addRow(fila);

        tablaPartidos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaPartidos.getSelectedRow();
            if (fila >= 0 && fila < ids.size()) {
                idPartidoSeleccionado = ids.get(fila)[0];
                idLocalSeleccionado = ids.get(fila)[1];
                idVisitanteSeleccionado = ids.get(fila)[2];
            }
        });
    }

    public int getIdPartidoSeleccionado() { return idPartidoSeleccionado; }
    public int getIdLocalSeleccionado() { return idLocalSeleccionado; }
    public int getIdVisitanteSeleccionado() { return idVisitanteSeleccionado; }
    public JButton getBtnRegistrarResultado() { return btnRegistrarResultado; }
    public JButton getBtnVolver() { return btnVolver; }
    public Torneo getTorneo() { return torneo; }
    public JButton getBtnCerrarTorneo() { return btnCerrarTorneo; }
}