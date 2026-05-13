package com.playket.view;

import com.playket.model.Torneo;
import com.playket.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaInicio extends JFrame {

    private final Usuario usuarioActual;
    private JPanel panelMisTorneos;
    private JPanel panelTorneosSeguidos;
    private JButton btnCrearTorneo;
    private JButton btnBuscar;
    private JButton btnPerfil;
    private java.util.function.Consumer<Torneo> torneoClickListener;

    public void setTorneoClickListener(java.util.function.Consumer<Torneo> listener) {
        this.torneoClickListener = listener;
    }

    public VentanaInicio(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Playket - Inicio");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Barra superior
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel lblTitulo = new JLabel("Playket", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        btnPerfil = new JButton("[ " + usuarioActual.getNombre() + " ]");
        btnPerfil.setBorderPainted(false);
        btnPerfil.setContentAreaFilled(false);
        panelTop.add(lblTitulo, BorderLayout.WEST);
        panelTop.add(btnPerfil, BorderLayout.EAST);

        // Contenido central
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Mis torneos
        JLabel lblMisTorneos = new JLabel("Mis torneos");
        lblMisTorneos.setFont(new Font("Arial", Font.BOLD, 15));
        panelMisTorneos = new JPanel();
        panelMisTorneos.setLayout(new BoxLayout(panelMisTorneos, BoxLayout.Y_AXIS));

        btnCrearTorneo = new JButton("+ Crear nuevo torneo");
        btnCrearTorneo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCrearTorneo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        // Torneos que sigo
        JLabel lblSeguidos = new JLabel("Torneos que sigo");
        lblSeguidos.setFont(new Font("Arial", Font.BOLD, 15));
        lblSeguidos.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        panelTorneosSeguidos = new JPanel();
        panelTorneosSeguidos.setLayout(new BoxLayout(panelTorneosSeguidos, BoxLayout.Y_AXIS));

        panelContenido.add(lblMisTorneos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContenido.add(panelMisTorneos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContenido.add(btnCrearTorneo);
        panelContenido.add(lblSeguidos);
        panelContenido.add(panelTorneosSeguidos);

        // Barra inferior
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        btnBuscar = new JButton("Buscar torneos");
        panelBottom.add(btnBuscar);

        panelPrincipal.add(panelTop, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(panelContenido), BorderLayout.CENTER);
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public void cargarMisTorneos(List<Torneo> torneos) {
        panelMisTorneos.removeAll();
        if (torneos.isEmpty()) {
            panelMisTorneos.add(new JLabel("No tienes torneos creados todavía"));
        } else {
            for (Torneo t : torneos) {
                panelMisTorneos.add(crearFilaTorneo(t));
            }
        }
        panelMisTorneos.revalidate();
        panelMisTorneos.repaint();
    }

    public void cargarTorneosSeguidos(List<Torneo> torneos) {
        panelTorneosSeguidos.removeAll();
        if (torneos.isEmpty()) {
            panelTorneosSeguidos.add(new JLabel("No sigues ningún torneo todavía"));
        } else {
            for (Torneo t : torneos) {
                panelTorneosSeguidos.add(crearFilaTorneo(t));
            }
        }
        panelTorneosSeguidos.revalidate();
        panelTorneosSeguidos.repaint();
    }

    private JPanel crearFilaTorneo(Torneo t) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel nombre = new JLabel(t.getNombre());
        JLabel estado = new JLabel(t.getEstado());
        estado.setForeground(Color.GRAY);
        fila.add(nombre, BorderLayout.WEST);
        fila.add(estado, BorderLayout.EAST);
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (torneoClickListener != null) torneoClickListener.accept(t);
            }
        });
        return fila;
    }

    public JButton getBtnCrearTorneo() { return btnCrearTorneo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnPerfil() { return btnPerfil; }
    public Usuario getUsuarioActual() { return usuarioActual; }
}