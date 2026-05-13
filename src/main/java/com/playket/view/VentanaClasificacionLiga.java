package com.playket.view;

import com.playket.model.Torneo;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaClasificacionLiga extends JFrame {

    private final Torneo torneo;
    private DefaultTableModel modeloClasificacion;
    private DefaultTableModel modeloPartidos;
    private JTable tablaClasificacion;
    private JTable tablaPartidos;
    private JButton btnRegistrarResultado;
    private JButton btnCerrarTorneo;
    private JButton btnVolver;
    private int idPartidoSeleccionado = -1;

    public VentanaClasificacionLiga(Torneo torneo) {
        this.torneo = torneo;
        setTitle("Playket - " + torneo.getNombre());
        setSize(660, 620);
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
        JLabel lblInfo = new JLabel("Liga | " + torneo.getEstado());
        lblInfo.setFont(EstiloApp.FUENTE_PEQUEÑA);
        lblInfo.setForeground(new Color(200, 200, 200));
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setBackground(EstiloApp.AZUL_OSCURO);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblInfo);
        cabecera.add(panelTextos, BorderLayout.CENTER);

        // Tabla clasificación
        modeloClasificacion = new DefaultTableModel(
                new String[]{"#", "Equipo", "PJ", "PG", "PE", "PP", "Pts"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaClasificacion = new JTable(modeloClasificacion);
        EstiloApp.estilizarTabla(tablaClasificacion);
        tablaClasificacion.getColumnModel().getColumn(0).setMaxWidth(30);
        JScrollPane scrollClasif = EstiloApp.crearScroll(tablaClasificacion);
        scrollClasif.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE), "Clasificación"));

        // Tabla partidos
        modeloPartidos = new DefaultTableModel(
                new String[]{"Local", "Visitante", "Estado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPartidos = new JTable(modeloPartidos);
        EstiloApp.estilizarTabla(tablaPartidos);
        JScrollPane scrollPartidos = EstiloApp.crearScroll(tablaPartidos);
        scrollPartidos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE), "Partidos"));

        // Panel central
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 0, 10));
        panelCentral.setBackground(EstiloApp.GRIS_CLARO);
        panelCentral.setBorder(new EmptyBorder(10, 15, 0, 15));
        panelCentral.add(scrollClasif);
        panelCentral.add(scrollPartidos);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setBackground(EstiloApp.GRIS_CLARO);
        panelBotones.setBorder(new EmptyBorder(0, 15, 15, 15));
        btnRegistrarResultado = EstiloApp.crearBtnPrimario("Registrar resultado");
        btnCerrarTorneo = EstiloApp.crearBtnPeligro("Cerrar torneo");
        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        panelBotones.add(btnRegistrarResultado);
        panelBotones.add(btnCerrarTorneo);
        panelBotones.add(btnVolver);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void cargarClasificacion(java.util.List<Object[]> filas) {
        modeloClasificacion.setRowCount(0);
        int pos = 1;
        for(Object[] fila : filas) {
            Object[] row = new Object[7];
            row[0] = pos++;
            row[1] = fila[0];
            row[2] = fila[1];
            row[3] = fila[2];
            row[4] = fila[3];
            row[5] = fila[4];
            row[6] = fila[5];
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
            }
        });
    }

    public int getIdPartidoSeleccionado() { return idPartidoSeleccionado; }
    public JButton getBtnRegistrarResultado() { return btnRegistrarResultado; }
    public JButton getBtnCerrarTorneo() { return btnCerrarTorneo; }
    public JButton getBtnVolver() { return btnVolver; }
    public Torneo getTorneo() { return torneo; }
}