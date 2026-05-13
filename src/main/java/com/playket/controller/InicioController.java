package com.playket.controller;

import com.playket.database.RolTorneoDAO;
import com.playket.database.SeguimientoDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.*;

import java.util.List;

public class InicioController {

    private final VentanaInicio vista;
    private final TorneoDAO torneoDAO;
    private final SeguimientoDAO seguimientoDAO;
    private final RolTorneoDAO rolTorneoDAO;
    private final Usuario usuarioActual;

    public InicioController(VentanaInicio vista, Usuario usuarioActual) {
        this.vista = vista;
        this.torneoDAO = new TorneoDAO();
        this.seguimientoDAO = new SeguimientoDAO();
        this.rolTorneoDAO = new RolTorneoDAO();
        this.usuarioActual = usuarioActual;
        inicializarEventos();
        cargarDatos();
    }

    private void inicializarEventos() {
        vista.getBtnCrearTorneo().addActionListener(e -> abrirCrearTorneo());
        vista.getBtnBuscar().addActionListener(e -> abrirBuscar());
        vista.setTorneoClickListener(torneo -> abrirTorneo(torneo));
        vista.getBtnPerfil().addActionListener(e -> abrirPerfil());
    }

    private void cargarDatos() {
        List<Torneo> misTorneos = torneoDAO.listarPorOrganizador(usuarioActual.getId());
        vista.cargarMisTorneos(misTorneos);

        List<Torneo> torneosSeguidos = seguimientoDAO.listarTorneosSeguidos(usuarioActual.getId());
        vista.cargarTorneosSeguidos(torneosSeguidos);

        List<Torneo> torneosCoOrg = rolTorneoDAO.listarTorneosCoOrganizador(usuarioActual.getId());
        vista.cargarTorneosCoOrganizador(torneosCoOrg);
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