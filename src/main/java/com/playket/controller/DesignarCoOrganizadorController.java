package com.playket.controller;

import com.playket.database.RolTorneoDAO;
import com.playket.database.UsuarioDAO;
import com.playket.model.Torneo;
import com.playket.model.Usuario;
import com.playket.view.VentanaCuadroEliminacion;
import com.playket.view.VentanaDesignarCoOrganizador;

import javax.swing.JOptionPane;
import java.util.List;

public class DesignarCoOrganizadorController {

    private final VentanaDesignarCoOrganizador vista;
    private final RolTorneoDAO rolTorneoDAO;
    private final UsuarioDAO usuarioDAO;
    private final Torneo torneo;
    private final Usuario usuarioActual;

    public DesignarCoOrganizadorController(VentanaDesignarCoOrganizador vista,
                                           Torneo torneo, Usuario usuarioActual) {
        this.vista = vista;
        this.rolTorneoDAO = new RolTorneoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.torneo = torneo;
        this.usuarioActual = usuarioActual;
        cargarCoOrganizadores();
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnAsignar().addActionListener(e -> asignar());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void cargarCoOrganizadores() {
        List<String> emails = usuarioDAO.listarEmailsCoOrganizadores(torneo.getId());
        vista.cargarCoOrganizadores(emails);
    }

    private void asignar() {
        String email = vista.getEmail();
        if (email.isEmpty()) {
            vista.setMensaje("Introduce el correo del usuario");
            return;
        }

        if (email.equals(usuarioActual.getEmail())) {
            vista.setMensaje("No puedes asignarte a ti mismo");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) {
            vista.setMensaje("No existe ningún usuario con ese correo");
            return;
        }

        if (rolTorneoDAO.esCoOrganizador(usuario.getId(), torneo.getId())) {
            Object[] opciones = {"Sí", "No"};
            int confirmar = JOptionPane.showOptionDialog(
                    vista,
                    "Este usuario ya es co-organizador. ¿Quieres revocarle el rol?",
                    "Revocar rol",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );
            if (confirmar == 0) {
                rolTorneoDAO.revocarCoOrganizador(usuario.getId(), torneo.getId());
                vista.setMensajeVerde("Rol revocado correctamente");
                cargarCoOrganizadores();
            }
            return;
        }

        if (rolTorneoDAO.asignarCoOrganizador(usuario.getId(), torneo.getId())) {
            vista.setMensajeVerde("Co-organizador asignado correctamente");
            cargarCoOrganizadores();
        } else {
            vista.setMensaje("Error al asignar co-organizador");
        }
    }

    private void volver() {
        vista.dispose();
        VentanaCuadroEliminacion ventana = new VentanaCuadroEliminacion(torneo);
        new CuadroEliminacionController(ventana, torneo, usuarioActual);
        ventana.setVisible(true);
    }
}