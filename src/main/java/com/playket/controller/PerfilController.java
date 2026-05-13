package com.playket.controller;

import com.playket.database.UsuarioDAO;
import com.playket.model.Usuario;
import com.playket.view.VentanaInicio;
import com.playket.view.VentanaPerfil;
import javax.swing.*;

public class PerfilController {

    private final VentanaPerfil vista;
    private final UsuarioDAO usuarioDAO;
    private final Usuario usuarioActual;

    public PerfilController(VentanaPerfil vista, Usuario usuarioActual) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioActual = usuarioActual;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnGuardar().addActionListener(e -> guardar());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void guardar() {
        String nombre = vista.getNombre();
        String apellidos = vista.getApellidos();
        String passwordActual = vista.getPasswordActual();
        String passwordNueva = vista.getPasswordNueva();
        String confirmar = vista.getConfirmarPassword();

        if (nombre.isEmpty() || apellidos.isEmpty()) {
            vista.setMensaje("El nombre y los apellidos son obligatorios");
            return;
        }

        // Si quiere cambiar la contraseña
        if (!passwordActual.isEmpty() || !passwordNueva.isEmpty()) {
            if (!passwordActual.equals(usuarioActual.getPassword())) {
                vista.setMensaje("La contraseña actual no es correcta");
                return;
            }
            if (passwordNueva.length() < 8) {
                vista.setMensaje("La nueva contraseña debe tener al menos 8 caracteres");
                return;
            }
            if (!passwordNueva.equals(confirmar)) {
                vista.setMensaje("Las contraseñas no coinciden");
                return;
            }
            usuarioActual.setPassword(passwordNueva);
        }

        usuarioActual.setNombre(nombre);
        usuarioActual.setApellidos(apellidos);

        if (usuarioDAO.actualizar(usuarioActual)) {
            vista.setMensajeVerde("Perfil actualizado correctamente");
            Timer timer = new javax.swing.Timer(1500, ev -> volver());
            timer.setRepeats(false);
            timer.start();
        } else {
            vista.setMensaje("Error al guardar los cambios");
        }
    }

    private void volver() {
        vista.dispose();
        VentanaInicio ventanaInicio = new VentanaInicio(usuarioActual);
        new InicioController(ventanaInicio, usuarioActual);
        ventanaInicio.setVisible(true);
    }
}