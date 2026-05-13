package com.playket.controller;

import com.playket.database.DeporteDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Deporte;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaBuscarTorneos;
import com.playket.view.VentanaCuadroEliminacion;
import com.playket.view.VentanaInicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuscarTorneosController {

    private final VentanaBuscarTorneos vista;
    private final TorneoDAO torneoDAO;
    private final DeporteDAO deporteDAO;
    private final Usuario usuarioActual;
    private List<Torneo> resultados;
    private Map<Integer, String> mapaDeportes;

    public BuscarTorneosController(VentanaBuscarTorneos vista, Usuario usuarioActual) {
        this.vista = vista;
        this.torneoDAO = new TorneoDAO();
        this.deporteDAO = new DeporteDAO();
        this.usuarioActual = usuarioActual;
        cargarDeportes();
        inicializarEventos();
        buscar();
    }

    private void cargarDeportes() {
        List<Deporte> deportes = deporteDAO.listarTodos();
        mapaDeportes = new HashMap<>();
        for (Deporte d : deportes) mapaDeportes.put(d.getId(), d.getNombre());
        vista.cargarDeportes(deportes);
    }

    private void inicializarEventos() {
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void buscar() {
        String nombre = vista.getNombreFiltro();
        Deporte deporte = vista.getDeporteFiltro();
        String estado = vista.getEstadoFiltro();

        Integer idDeporte = (deporte != null && deporte.getId() != 0) ? deporte.getId() : null;
        resultados = torneoDAO.buscar(nombre, idDeporte, estado);
        vista.mostrarResultados(resultados, mapaDeportes);
    }

    private void volver() {
        vista.dispose();
        VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
        new InicioController(ventanaInicio, usuarioActual);
        ventanaInicio.setVisible(true);
    }
}