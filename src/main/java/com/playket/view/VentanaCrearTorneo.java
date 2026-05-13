package com.playket.view;

import com.playket.model.Deporte;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(440, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelFondo = EstiloApp.crearPanelFondo();
        panelFondo.setLayout(new BorderLayout());

        JPanel cabecera = EstiloApp.crearCabecera("Nuevo torneo");

        JPanel tarjeta = EstiloApp.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridwidth = 2;

        gbc.gridy = 0;
        tarjeta.add(EstiloApp.crearEtiqueta("Nombre *"), gbc);
        campoNombre = EstiloApp.crearCampoTexto();
        gbc.gridy = 1;
        tarjeta.add(campoNombre, gbc);

        gbc.gridy = 2;
        tarjeta.add(EstiloApp.crearEtiqueta("Deporte *"), gbc);
        comboDeporte = new JComboBox<>();
        comboDeporte.setFont(EstiloApp.FUENTE_NORMAL);
        gbc.gridy = 3;
        tarjeta.add(comboDeporte, gbc);

        gbc.gridy = 4;
        tarjeta.add(EstiloApp.crearEtiqueta("Formato *"), gbc);
        comboFormato = new JComboBox<>(new String[]{"ELIMINACION", "LIGA"});
        comboFormato.setFont(EstiloApp.FUENTE_NORMAL);
        gbc.gridy = 5;
        tarjeta.add(comboFormato, gbc);

        gbc.gridy = 6;
        tarjeta.add(EstiloApp.crearEtiqueta("Nº participantes *"), gbc);
        comboParticipantes = new JComboBox<>(new Integer[]{4, 8, 16, 32});
        comboParticipantes.setFont(EstiloApp.FUENTE_NORMAL);
        gbc.gridy = 7;
        tarjeta.add(comboParticipantes, gbc);

        gbc.gridy = 8;
        tarjeta.add(EstiloApp.crearEtiqueta("Fecha de inicio * (dd/mm/aaaa)"), gbc);
        campoFecha = EstiloApp.crearCampoTexto();
        gbc.gridy = 9;
        tarjeta.add(campoFecha, gbc);

        gbc.gridy = 10;
        tarjeta.add(EstiloApp.crearEtiqueta("Descripción (opcional)"), gbc);
        campoDescripcion = new JTextArea(3, 20);
        campoDescripcion.setFont(EstiloApp.FUENTE_NORMAL);
        campoDescripcion.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(campoDescripcion);
        scrollDesc.setBorder(BorderFactory.createLineBorder(EstiloApp.GRIS_BORDE));
        gbc.gridy = 11;
        tarjeta.add(scrollDesc, gbc);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setFont(EstiloApp.FUENTE_PEQUEÑA);
        gbc.gridy = 12;
        tarjeta.add(lblMensaje, gbc);

        btnCrear = EstiloApp.crearBtnPrimario("Crear torneo →");
        gbc.gridy = 13;
        gbc.insets = new Insets(5, 0, 4, 0);
        tarjeta.add(btnCrear, gbc);

        btnVolver = EstiloApp.crearBtnSecundario("Volver");
        gbc.gridy = 14;
        gbc.insets = new Insets(4, 0, 0, 0);
        tarjeta.add(btnVolver, gbc);

        JPanel panelCentro = EstiloApp.crearPanelFondo();
        panelCentro.setLayout(new GridBagLayout());
        panelCentro.setBorder(new EmptyBorder(15, 30, 15, 30));
        panelCentro.add(tarjeta);

        panelFondo.add(cabecera, BorderLayout.NORTH);
        panelFondo.add(new JScrollPane(panelCentro), BorderLayout.CENTER);
        add(panelFondo);
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
    public void setMensaje(String msg) {
        lblMensaje.setForeground(EstiloApp.ROJO_ERROR);
        lblMensaje.setText(msg);
    }
    public JButton getBtnCrear() { return btnCrear; }
    public JButton getBtnVolver() { return btnVolver; }
}