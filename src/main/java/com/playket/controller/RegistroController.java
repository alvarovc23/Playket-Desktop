package com.playket.controller;

import com.playket.database.UsuarioDAO;
import com.playket.model.Usuario;
import com.playket.util.CifradorDES;
import com.playket.util.PlayketException;
import com.playket.view.VentanaLogin;
import com.playket.view.VentanaRegistro;
import javax.swing.*;

public class RegistroController {

    private final VentanaRegistro vista;
    private final UsuarioDAO usuarioDAO;

    public RegistroController(VentanaRegistro vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnCrearCuenta().addActionListener(e -> registrar());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    private void registrar() {
        String nombre = vista.getNombre();
        String apellidos = vista.getApellidos();
        String email = vista.getEmail();
        String password = vista.getPassword();
        String confirmar = vista.getConfirmarPassword();
        String pregunta = vista.getPreguntaSeguridad();
        String respuesta = vista.getRespuesta();

        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()
                || password.isEmpty() || respuesta.isEmpty()) {
            vista.setMensaje("Completa todos los campos obligatorios");
            return;
        }

        if (password.length() < 8) {
            vista.setMensaje("La contraseña debe tener al menos 8 caracteres");
            return;
        }

        if (!password.equals(confirmar)) {
            vista.setMensaje("Las contraseñas no coinciden");
            return;
        }

        try {
            if (usuarioDAO.emailExiste(email)) {
                vista.setMensaje("Este correo ya está registrado");
                return;
            }

            String passwordCifrada = CifradorDES.cifrar(password);
            String respuestaCifrada = CifradorDES.cifrar(respuesta);

            if (passwordCifrada == null || respuestaCifrada == null) {
                vista.setMensaje("Error al cifrar los datos, inténtalo de nuevo");
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setEmail(email);
            usuario.setPassword(passwordCifrada);
            usuario.setPreguntaSeguridad(pregunta);
            usuario.setRespuestaSeg(respuestaCifrada);

            if (usuarioDAO.insertar(usuario)) {
                vista.setMensajeVerde("Cuenta creada correctamente");
                Timer timer = new javax.swing.Timer(1500, ev -> volver());
                timer.setRepeats(false);
                timer.start();
            } else {
                vista.setMensaje("Error al crear la cuenta, inténtalo de nuevo");
            }

        } catch (PlayketException e) {
            vista.setMensaje("No se pudo crear la cuenta. Comprueba la conexión e inténtalo de nuevo.");
        }
    }

    private void volver() {
        vista.dispose();
        VentanaLogin ventanaLogin = new VentanaLogin();
        new LoginController(ventanaLogin);
        ventanaLogin.setVisible(true);
    }
}