package com.playket.controller;

import com.playket.database.TorneoDAO;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaInicio;

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
    }

    private void cargarDatos() {
        List<Torneo> misTorneos = torneoDAO.listarPorOrganizador(usuarioActual.getId());
        vista.cargarMisTorneos(misTorneos);
        vista.cargarTorneosSeguidos(java.util.Collections.emptyList());
    }

    private void abrirCrearTorneo() {
        System.out.println("Abrir crear torneo");
    }

    private void abrirBuscar() {
        System.out.println("Abrir buscar");
    }
}