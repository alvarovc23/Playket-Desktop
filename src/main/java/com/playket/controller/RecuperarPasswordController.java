package com.playket.controller;

import com.playket.database.UsuarioDAO;
import com.playket.model.Usuario;
import com.playket.util.CifradorDES;
import com.playket.view.VentanaLogin;
import com.playket.view.VentanaRecuperarPassword;
import javax.swing.*;

public class RecuperarPasswordController {

    private final VentanaRecuperarPassword vista;
    private final UsuarioDAO usuarioDAO;
    private Usuario usuarioEncontrado;

    public RecuperarPasswordController(VentanaRecuperarPassword vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnComprobar().addActionListener(e -> comprobarEmail());
        vista.getBtnCambiarPassword().addActionListener(e -> cambiarPassword());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void comprobarEmail() {
        String email = vista.getEmail();
        if (email.isEmpty()) {
            vista.setMensaje("Introduce tu correo electrónico");
            return;
        }

        usuarioEncontrado = usuarioDAO.buscarPorEmail(email);
        if (usuarioEncontrado == null) {
            vista.setMensaje("No existe ninguna cuenta con ese correo");
            return;
        }

        vista.setMensaje("");
        vista.mostrarSegundoPaso(usuarioEncontrado.getPreguntaSeguridad());
    }

    private void cambiarPassword() {
        String respuesta = vista.getRespuesta();
        String passwordNueva = vista.getPasswordNueva();
        String confirmar = vista.getConfirmarPassword();

        if (respuesta.isEmpty() || passwordNueva.isEmpty()) {
            vista.setMensaje("Completa todos los campos");
            return;
        }

        if (!CifradorDES.verificar(respuesta, usuarioEncontrado.getRespuestaSeg())) {
            vista.setMensaje("La respuesta no es correcta");
            return;
        }

        if (passwordNueva.length() < 8) {
            vista.setMensaje("La contraseña debe tener al menos 8 caracteres");
            return;
        }

        if (!passwordNueva.equals(confirmar)) {
            vista.setMensaje("Las contraseñas no coinciden");
            return;
        }

        String passwordCifrada = CifradorDES.cifrar(passwordNueva);
        if (passwordCifrada == null) {
            vista.setMensaje("Error al cifrar la contraseña, inténtalo de nuevo");
            return;
        }

        usuarioEncontrado.setPassword(passwordCifrada);
        if (usuarioDAO.actualizar(usuarioEncontrado)) {
            vista.setMensajeVerde("Contraseña cambiada correctamente");
            Timer timer = new javax.swing.Timer(1500, ev -> volver());
            timer.setRepeats(false);
            timer.start();
        } else {
            vista.setMensaje("Error al cambiar la contraseña");
        }
    }

    private void volver() {
        vista.dispose();
        VentanaLogin ventanaLogin = new VentanaLogin();
        new LoginController(ventanaLogin);
        ventanaLogin.setVisible(true);
    }
}