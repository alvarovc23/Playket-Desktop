package com.playket.controller;

import com.playket.database.DeporteDAO;
import com.playket.database.SeguimientoDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Deporte;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaBuscarTorneos;
import com.playket.view.VentanaCuadroEliminacion;
import com.playket.view.VentanaInicio;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuscarTorneosController {

    private final VentanaBuscarTorneos vista;
    private final TorneoDAO torneoDAO;
    private final DeporteDAO deporteDAO;
    private final Usuario usuarioActual;
    private final SeguimientoDAO seguimientoDAO;
    private List<Torneo> resultados;
    private Map<Integer, String> mapaDeportes;

    public BuscarTorneosController(VentanaBuscarTorneos vista, Usuario usuarioActual) {
        this.vista = vista;
        this.torneoDAO = new TorneoDAO();
        this.deporteDAO = new DeporteDAO();
        this.seguimientoDAO = new SeguimientoDAO();
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
        vista.getBtnSeguir().addActionListener(e -> seguirTorneo());
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

    private void seguirTorneo() {
        int fila = vista.getFilaSeleccionada();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un torneo primero");
            return;
        }

        Torneo torneo = resultados.get(fila);

        if (torneo.getIdOrganizador() == usuarioActual.getId()) {
            JOptionPane.showMessageDialog(vista, "No puedes seguir un torneo que tú mismo organizas");
            return;
        }

        if (seguimientoDAO.sigueElTorneo(usuarioActual.getId(), torneo.getId())) {
            int confirmar = JOptionPane.showConfirmDialog(vista,
                    "Ya sigues este torneo. ¿Quieres dejar de seguirlo?",
                    "Dejar de seguir", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                seguimientoDAO.dejarDeSeguir(usuarioActual.getId(), torneo.getId());
                JOptionPane.showMessageDialog(vista, "Has dejado de seguir el torneo");
            }
        } else {
            if (seguimientoDAO.seguir(usuarioActual.getId(), torneo.getId())) {
                JOptionPane.showMessageDialog(vista, "Ahora sigues " + torneo.getNombre());
            } else {
                JOptionPane.showMessageDialog(vista, "Error al seguir el torneo");
            }
        }
    }
}