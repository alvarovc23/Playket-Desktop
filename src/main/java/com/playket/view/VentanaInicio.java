package com.playket.view;

import com.playket.model.Torneo;
import com.playket.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaInicio extends JFrame {

    private final Usuario usuarioActual;
    private JPanel panelMisTorneos;
    private JButton btnCrearTorneo;
    private JButton btnBuscar;
    private JButton btnPerfil;
    private java.util.function.Consumer<Torneo> torneoClickListener;

    public VentanaInicio(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Playket - Inicio");
        setSize(500, 500);
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

        panelContenido.add(lblMisTorneos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContenido.add(panelMisTorneos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 8)));
        panelContenido.add(btnCrearTorneo);

        // Barra inferior
        JPanel panelBottom = new JPanel(new GridLayout(1, 1));
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
            for (Torneo t : torneos) panelMisTorneos.add(crearFilaTorneo(t));
        }
        panelMisTorneos.revalidate();
        panelMisTorneos.repaint();
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

    public void setTorneoClickListener(java.util.function.Consumer<Torneo> listener) {
        this.torneoClickListener = listener;
    }

    public JButton getBtnCrearTorneo() { return btnCrearTorneo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnPerfil() { return btnPerfil; }
}