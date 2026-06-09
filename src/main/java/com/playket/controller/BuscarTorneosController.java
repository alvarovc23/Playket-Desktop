package com.playket.controller;

import com.playket.database.DeporteDAO;
import com.playket.database.TorneoDAO;
import com.playket.model.Deporte;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.util.PlayketException;
import com.playket.view.VentanaCrearTorneo;
import com.playket.view.VentanaGestionParticipantes;
import com.playket.view.VentanaInicio;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CrearTorneoController {

    private final VentanaCrearTorneo vista;
    private final TorneoDAO torneoDAO;
    private final DeporteDAO deporteDAO;
    private final Usuario usuarioActual;

    public CrearTorneoController(VentanaCrearTorneo vista, Usuario usuarioActual) {
        this.vista = vista;
        this.torneoDAO = new TorneoDAO();
        this.deporteDAO = new DeporteDAO();
        this.usuarioActual = usuarioActual;
        cargarDeportes();
        inicializarEventos();
    }

    private void cargarDeportes() {
        try {
            List<Deporte> deportes = deporteDAO.listarTodos();
            vista.cargarDeportes(deportes);
        } catch (PlayketException e) {
            JOptionPane.showMessageDialog(vista,
                    "No se pudieron cargar los deportes. Comprueba la conexión e inténtalo de nuevo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarEventos() {
        vista.getBtnCrear().addActionListener(e -> crearTorneo());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void crearTorneo() {
        String nombre = vista.getNombre();
        Deporte deporte = vista.getDeporte();
        String formato = vista.getFormato();
        int numParticipantes = vista.getNumParticipantes();
        String fechaStr = vista.getFecha();
        String descripcion = vista.getDescripcion();

        if (nombre.isEmpty() || fechaStr.isEmpty()) {
            vista.setMensaje("Completa todos los campos obligatorios");
            return;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            vista.setMensaje("Formato de fecha incorrecto (dd/mm/aaaa)");
            return;
        }

        Torneo torneo = new Torneo();
        torneo.setNombre(nombre);
        torneo.setDescripcion(descripcion);
        torneo.setFormato(formato);
        torneo.setEstado("ABIERTO");
        torneo.setFechaInicio(fecha);
        torneo.setNumParticipantes(numParticipantes);
        torneo.setIdDeporte(deporte.getId());
        torneo.setIdOrganizador(usuarioActual.getId());

        try {
            if (torneoDAO.insertar(torneo)) {
                Torneo torneoCreado = torneoDAO.buscarUltimoPorOrganizador(usuarioActual.getId());
                vista.dispose();
                VentanaGestionParticipantes ventanaParticipantes =
                        new VentanaGestionParticipantes(torneoCreado);
                new GestionParticipantesController(ventanaParticipantes, torneoCreado, usuarioActual);
                ventanaParticipantes.setVisible(true);
            } else {
                vista.setMensaje("Error al crear el torneo, inténtalo de nuevo");
            }
        } catch (PlayketException e) {
            vista.setMensaje("No se pudo crear el torneo. Comprueba la conexión e inténtalo de nuevo.");
        }
    }

    private void volver() {
        vista.dispose();
        VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
        new InicioController(ventanaInicio, usuarioActual);
        ventanaInicio.setVisible(true);
    }
}