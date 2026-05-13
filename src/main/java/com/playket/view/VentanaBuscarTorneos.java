package com.playket.view;

import com.playket.model.Deporte;
import com.playket.model.Torneo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaBuscarTorneos extends JFrame {

    private JTextField campoNombre;
    private JComboBox<String> comboEstado;
    private JComboBox<Deporte> comboDeporte;
    private JButton btnBuscar;
    private JButton btnVolver;
    private JButton btnSeguir;
    private DefaultTableModel modeloTabla;
    private JTable tablaTorneos;

    public VentanaBuscarTorneos() {
        setTitle("Playket - Buscar torneos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Panel filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        panelFiltros.add(new JLabel("Nombre"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; gbc.gridwidth = 3;
        campoNombre = new JTextField();
        panelFiltros.add(campoNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2; gbc.gridwidth = 1;
        panelFiltros.add(new JLabel("Deporte"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        comboDeporte = new JComboBox<>();
        panelFiltros.add(comboDeporte, gbc);

        gbc.gridx = 2; gbc.weightx = 0.2;
        panelFiltros.add(new JLabel("Estado"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        comboEstado = new JComboBox<>(new String[]{"Todos", "ABIERTO", "EN_CURSO", "FINALIZADO"});
        panelFiltros.add(comboEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(0, 34));
        panelFiltros.add(btnBuscar, gbc);

        // Tabla resultados
        modeloTabla = new DefaultTableModel(
                new String[]{"Nombre", "Deporte", "Formato", "Estado", "Fecha inicio"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaTorneos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaTorneos);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        btnSeguir = new JButton("Seguir torneo seleccionado");
        btnVolver = new JButton("Volver");
        panelBotones.add(btnSeguir);
        panelBotones.add(btnVolver);

        panelPrincipal.add(panelFiltros, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
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
    public int getFilaSeleccionada() { return tablaTorneos.getSelectedRow(); }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnVolver() { return btnVolver; }
    public JButton getBtnSeguir() { return btnSeguir; }
}