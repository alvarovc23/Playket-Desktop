package com.playket.view;

import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.util.EstiloApp;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class VentanaInicio extends JFrame {

    // Interfaz sencilla para detectar cuando se pulsa un torneo
    public interface OnTorneoClick {
        void torneoSeleccionado(Torneo torneo);
    }

    private final Usuario usuarioActual;
    private JPanel panelMisTorneos;
    private JButton btnCrearTorneo;
    private JButton btnBuscar;
    private JButton btnPerfil;
    private OnTorneoClick torneoClickListener;

    public VentanaInicio(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Playket - Inicio");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstiloApp.GRIS_CLARO);

        // Cabecera azul con título y botón de perfil
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(EstiloApp.AZUL_OSCURO);
        cabecera.setPreferredSize(new Dimension(0, 60));
        cabecera.setBorder(new EmptyBorder(0, 15, 0, 15));
        JLabel lblTitulo = new JLabel("Playket");
        lblTitulo.setFont(EstiloApp.FUENTE_TITULO);
        lblTitulo.setForeground(EstiloApp.BLANCO);
        btnPerfil = EstiloApp.crearBtnTexto("[ " + usuarioActual.getNombre() + " ]");
        btnPerfil.setForeground(EstiloApp.BLANCO);
        cabecera.add(lblTitulo, BorderLayout.WEST);
        cabecera.add(btnPerfil, BorderLayout.EAST);

        // Contenido central
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(EstiloApp.GRIS_CLARO);
        panelContenido.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblMisTorneos = new JLabel("Mis torneos");
        lblMisTorneos.setFont(EstiloApp.FUENTE_SUBTITULO);
        lblMisTorneos.setForeground(EstiloApp.AZUL_OSCURO);
        lblMisTorneos.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMisTorneos = new JPanel();
        panelMisTorneos.setLayout(new BoxLayout(panelMisTorneos, BoxLayout.Y_AXIS));
        panelMisTorneos.setBackground(EstiloApp.GRIS_CLARO);
        panelMisTorneos.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCrearTorneo = EstiloApp.crearBtnPrimario("+ Crear nuevo torneo");
        btnCrearTorneo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCrearTorneo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        panelContenido.add(lblMisTorneos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContenido.add(panelMisTorneos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenido.add(btnCrearTorneo);

        // Barra inferior
        JPanel panelBottom = new JPanel(new GridLayout(1, 1));
        panelBottom.setBackground(EstiloApp.GRIS_CLARO);
        panelBottom.setBorder(new EmptyBorder(5, 15, 10, 15));
        btnBuscar = EstiloApp.crearBtnSecundario("Buscar torneos");
        panelBottom.add(btnBuscar);

        // Panel centrador: envuelve el contenido para que quede centrado horizontalmente
        JPanel panelCentrador = new JPanel(new GridBagLayout());
        panelCentrador.setBackground(EstiloApp.GRIS_CLARO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelCentrador.add(panelContenido, gbc);

        JScrollPane scroll = new JScrollPane(panelCentrador);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloApp.GRIS_CLARO);

        panelPrincipal.add(cabecera, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    public void cargarMisTorneos(List<Torneo> torneos) {
        panelMisTorneos.removeAll();
        if(torneos.isEmpty()) {
            JLabel lbl = new JLabel("No tienes torneos creados todavía");
            lbl.setFont(EstiloApp.FUENTE_NORMAL);
            lbl.setForeground(EstiloApp.GRIS_TEXTO);
            panelMisTorneos.add(lbl);
        } else {
            for(Torneo t : torneos) {
                panelMisTorneos.add(crearFilaTorneo(t));
            }
        }
        panelMisTorneos.revalidate();
        panelMisTorneos.repaint();
    }

    private JPanel crearFilaTorneo(Torneo t) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(EstiloApp.BLANCO);
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloApp.GRIS_BORDE),
                new EmptyBorder(8, 10, 8, 10)
        ));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JLabel nombre = new JLabel(t.getNombre());
        nombre.setFont(EstiloApp.FUENTE_NORMAL);
        JLabel estado = new JLabel(t.getEstado());
        estado.setFont(EstiloApp.FUENTE_PEQUEÑA);
        estado.setForeground(EstiloApp.GRIS_TEXTO);

        fila.add(nombre, BorderLayout.WEST);
        fila.add(estado, BorderLayout.EAST);
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        fila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(torneoClickListener != null) {
                    torneoClickListener.torneoSeleccionado(t);
                }
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
                fila.setBackground(EstiloApp.AZUL_SEL);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                fila.setBackground(EstiloApp.BLANCO);
            }
        });
        return fila;
    }

    public void setTorneoClickListener(OnTorneoClick listener) {
        this.torneoClickListener = listener;
    }

    public JButton getBtnCrearTorneo() { return btnCrearTorneo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnPerfil() { return btnPerfil; }
}