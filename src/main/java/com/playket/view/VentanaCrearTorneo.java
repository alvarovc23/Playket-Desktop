package com.playket.view;

import com.playket.model.Deporte;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaCrearTorneo extends JFrame {

    private JTextField campoNombre;
    private JComboBox<Deporte> comboDeporte;
    private JComboBox<String> comboFormato;
    private JComboBox<Integer> comboParticipantes;
    private JTextField campoFecha;
    private JTextArea campoDescripcion;
    private JButton btnCrear;
    private JButton btnVolver;
    private JLabel lblMensaje;

    public VentanaCrearTorneo() {
        setTitle("Playket - Nuevo torneo");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 2;

        // Nombre
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre *"), gbc);
        campoNombre = new JTextField();
        campoNombre.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 1;
        panel.add(campoNombre, gbc);

        // Deporte
        gbc.gridy = 2;
        panel.add(new JLabel("Deporte *"), gbc);
        comboDeporte = new JComboBox<>();
        gbc.gridy = 3;
        panel.add(comboDeporte, gbc);

        // Formato
        gbc.gridy = 4;
        panel.add(new JLabel("Formato *"), gbc);
        comboFormato = new JComboBox<>(new String[]{"ELIMINACION", "LIGA"});
        gbc.gridy = 5;
        panel.add(comboFormato, gbc);

        // Número de participantes
        gbc.gridy = 6;
        panel.add(new JLabel("Nº participantes *"), gbc);
        comboParticipantes = new JComboBox<>(new Integer[]{4, 8, 16, 32});
        gbc.gridy = 7;
        panel.add(comboParticipantes, gbc);

        // Fecha
        gbc.gridy = 8;
        panel.add(new JLabel("Fecha de inicio * (dd/mm/aaaa)"), gbc);
        campoFecha = new JTextField();
        campoFecha.setPreferredSize(new Dimension(0, 30));
        gbc.gridy = 9;
        panel.add(campoFecha, gbc);

        // Descripción
        gbc.gridy = 10;
        panel.add(new JLabel("Descripción (opcional)"), gbc);
        campoDescripcion = new JTextArea(3, 20);
        campoDescripcion.setLineWrap(true);
        gbc.gridy = 11;
        panel.add(new JScrollPane(campoDescripcion), gbc);

        // Mensaje
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 12;
        panel.add(lblMensaje, gbc);

        // Botones
        btnCrear = new JButton("Crear torneo →");
        btnCrear.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 13;
        panel.add(btnCrear, gbc);

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(0, 36));
        gbc.gridy = 14;
        panel.add(btnVolver, gbc);

        add(new JScrollPane(panel));
    }

    public void cargarDeportes(List<Deporte> deportes) {
        comboDeporte.removeAllItems();
        for (Deporte d : deportes) comboDeporte.addItem(d);
    }

    public String getNombre() { return campoNombre.getText().trim(); }
    public Deporte getDeporte() { return (Deporte) comboDeporte.getSelectedItem(); }
    public String getFormato() { return (String) comboFormato.getSelectedItem(); }
    public int getNumParticipantes() { return (Integer) comboParticipantes.getSelectedItem(); }
    public String getFecha() { return campoFecha.getText().trim(); }
    public String getDescripcion() { return campoDescripcion.getText().trim(); }
    public void setMensaje(String msg) { lblMensaje.setText(msg); }
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnVolver() { return btnVolver; }
}