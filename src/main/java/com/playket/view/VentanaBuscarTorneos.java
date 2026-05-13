package com.playket.view;

import com.playket.model.Deporte;
import com.playket.model.Torneo;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaBuscarTorneos extends JFrame {

    private JTextField campoNombre;
    private JComboBox<String> comboEstado;
    private JComboBox<Deporte> comboDeporte;
    private JButton btnBuscar;
    private JButton btnVolver;
    private DefaultTableModel modeloTabla;
    private JTable tablaTorneos;

    public VentanaBuscarTorneos() {
        setTitle("Playket - Buscar torneos");
        setSize(620, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 10));
        panelPrincipal.setBackground(EstiloApp.GRIS_CLARO);

        // Cabecera
        JPanel cabecera = EstiloApp.crearCabecera("Buscar torneos");

        // Panel filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBackground(EstiloApp.BLANCO);
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE), "Filtros"),
                new EmptyBorder(5, 10, 10, 10)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.15;
        panelFiltros.add(EstiloApp.crearEtiqueta("Nombre"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.85; gbc.gridwidth = 3;
        campoNombre = EstiloApp.crearCampoTexto();
        panelFiltros.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.15; gbc.gridwidth = 1;
        panelFiltros.add(EstiloApp.crearEtiqueta("Deporte"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.35;
        comboDeporte = new JComboBox<>();
        comboDeporte.setFont(EstiloApp.FUENTE_NORMAL);
        panelFiltros.add(comboDeporte, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panelFiltros.add(EstiloApp.crearEtiqueta("Estado"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.35;
        comboEstado = new JComboBox<>(new String[]{"Todos", "ABIERTO", "EN_CURSO", "FINALIZADO"});
        comboEstado.setFont(EstiloApp.FUENTE_NORMAL);
        panelFiltros.add(comboEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        btnBuscar = EstiloApp.crearBtnPrimario("Buscar");
        panelFiltros.add(btnBuscar, gbc);

        // Tabla resultados
        modeloTabla = new DefaultTableModel(
                new String[]{"Nombre", "Deporte", "Formato", "Estado", "Fecha inicio"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaTorneos = new JTable(modeloTabla);
        EstiloApp.estilizarTabla(tablaTorneos);
        JScrollPane scroll = EstiloApp.crearScroll(tablaTorneos);

        // Panel central
        JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
        panelCentro.setBackground(EstiloApp.GRIS_CLARO);
        panelCentro.setBorder(new EmptyBorder(10, 15, 0, 15));
        panelCentro.add(panelFiltros, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        // Botón volver
        JPanel panelBotones = new JPanel(new GridLayout(1, 1));
        panelBotones.setBackground(EstiloApp.GRIS_CLARO);
        panelBotones.setBorder(new EmptyBorder(0, 15, 15, 15));
        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        panelBotones.add(btnVolver);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void cargarDeportes(List<Deporte> deportes) {
        comboDeporte.removeAllItems();
        comboDeporte.addItem(new Deporte(0, "Todos"));
        for (Deporte d : deportes) comboDeporte.addItem(d);
    }

    public void mostrarResultados(List<Torneo> torneos, java.util.Map<Integer, String> mapaDeportes) {
        modeloTabla.setRowCount(0);
        for (Torneo t : torneos) {
            modeloTabla.addRow(new Object[]{
                    t.getNombre(),
                    mapaDeportes.getOrDefault(t.getIdDeporte(), ""),
                    t.getFormato(),
                    t.getEstado(),
                    t.getFechaInicio()
            });
        }
    }

    public String getNombreFiltro() { return campoNombre.getText().trim(); }
    public Deporte getDeporteFiltro() { return (Deporte) comboDeporte.getSelectedItem(); }
    public String getEstadoFiltro() {
        String e = (String) comboEstado.getSelectedItem();
        return "Todos".equals(e) ? null : e;
    }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnVolver() { return btnVolver; }
}