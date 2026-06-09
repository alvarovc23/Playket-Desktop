package com.playket.controller;

import com.playket.CrearTorneoController;
import com.playket.database.TorneoDAO;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.util.PlayketException;
import com.playket.view.VentanaClasificacionLiga;
import com.playket.view.VentanaCuadroEliminacion;
import com.playket.view.VentanaCrearTorneo;
import com.playket.view.VentanaInicio;
import com.playket.view.VentanaBuscarTorneos;
import com.playket.view.VentanaPerfil;
import javax.swing.JOptionPane;
import java.util.List;

public class InicioController {

    private final VentanaInicio vista;
    private final TorneoDAO torneoDAO;
    private final Usuario usuarioActual;

    public InicioController(VentanaInicio vista, Usuario usuarioActual) {
        this.vista = vista;
        this.torneoDAO = new TorneoDAO();
        this.usuarioActual = usuarioActual;
        inicializarEventos();
        cargarDatos();
    }

    private void inicializarEventos() {
        vista.getBtnCrearTorneo().addActionListener(e -> abrirCrearTorneo());
        vista.getBtnBuscar().addActionListener(e -> abrirBuscar());
        vista.getBtnPerfil().addActionListener(e -> abrirPerfil());
        vista.setTorneoClickListener(torneo -> abrirTorneo(torneo));
    }

    private void cargarDatos() {
        try {
            List<Torneo> misTorneos = torneoDAO.listarPorOrganizador(usuarioActual.getId());
            vista.cargarMisTorneos(misTorneos);
        } catch (PlayketException e) {
            JOptionPane.showMessageDialog(vista,
                    "No se pudieron cargar tus torneos. Comprueba la conexión e inténtalo de nuevo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCrearTorneo() {
        vista.dispose();
        VentanaCrearTorneo ventanaCrear = new VentanaCrearTorneo();
        new CrearTorneoController(ventanaCrear, usuarioActual);
        ventanaCrear.setVisible(true);
    }

    private void abrirTorneo(Torneo torneo) {
        vista.dispose();
        if (torneo.getFormato().equals("ELIMINACION")) {
            VentanaCuadroEliminacion ventana = new VentanaCuadroEliminacion(torneo);
            new CuadroEliminacionController(ventana, torneo, usuarioActual);
            ventana.setVisible(true);
        } else {
            VentanaClasificacionLiga ventana = new VentanaClasificacionLiga(torneo);
            new ClasificacionLigaController(ventana, torneo, usuarioActual);
            ventana.setVisible(true);
        }
    }

    private void abrirBuscar() {
        vista.dispose();
        VentanaBuscarTorneos ventanaBuscar = new VentanaBuscarTorneos();
        new BuscarTorneosController(ventanaBuscar, usuarioActual);
        ventanaBuscar.setVisible(true);
    }

    private void abrirPerfil() {
        vista.dispose();
        VentanaPerfil ventanaPerfil = new VentanaPerfil(usuarioActual);
        new PerfilController(ventanaPerfil, usuarioActual);
        ventanaPerfil.setVisible(true);
    }
}