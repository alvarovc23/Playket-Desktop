package com.playket.controller;

import com.playket.database.TorneoDAO;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.*;

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
        vista.setTorneoClickListener(torneo -> abrirTorneo(torneo));
        vista.getBtnPerfil().addActionListener(e -> abrirPerfil());
    }

    private void cargarDatos() {
        List<Torneo> misTorneos = torneoDAO.listarPorOrganizador(usuarioActual.getId());
        vista.cargarMisTorneos(misTorneos);
        vista.cargarTorneosSeguidos(java.util.Collections.emptyList());
    }

    private void abrirCrearTorneo() {
        vista.dispose();
        VentanaCrearTorneo ventanaCrear = new VentanaCrearTorneo();
        new CrearTorneoController(ventanaCrear, usuarioActual);
        ventanaCrear.setVisible(true);
    }

    private void abrirTorneo(Torneo torneo) {
        vista.dispose();
        VentanaCuadroEliminacion ventana = new VentanaCuadroEliminacion(torneo);
        new CuadroEliminacionController(ventana, torneo, usuarioActual);
        ventana.setVisible(true);
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