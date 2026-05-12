package com.playket.controller;

import com.playket.database.UsuarioDAO;
import com.playket.model.Usuario;
import com.playket.util.PasswordHasher;
import com.playket.view.VentanaLogin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    private final VentanaLogin vista;
    private final UsuarioDAO usuarioDAO;

    private static final int MAX_INTENTOS = 3;
    private static final int MINUTOS_BLOQUEO = 5;
    private final Map<String, Integer> intentosFallidos = new HashMap<>();
    private final Map<String, LocalDateTime> tiempoBloqueo = new HashMap<>();

    public LoginController(VentanaLogin vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnIniciarSesion().addActionListener(e -> iniciarSesion());
        vista.getBtnCrearCuenta().addActionListener(e -> abrirRegistro());
    }

    private void iniciarSesion() {
        String email = vista.getEmail();
        String password = vista.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            vista.setMensaje("Completa todos los campos");
            return;
        }

        if (estaBloqueado(email)) {
            vista.setMensaje("Acceso bloqueado 5 min por intentos fallidos");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null || !PasswordHasher.verificar(password, usuario.getPasswordHash())) {
            registrarIntentoFallido(email);
            int restantes = MAX_INTENTOS - intentosFallidos.getOrDefault(email, 0);
            if (restantes <= 0) {
                vista.setMensaje("Acceso bloqueado 5 min por intentos fallidos");
            } else {
                vista.setMensaje("Credenciales incorrectas. Intentos restantes: " + restantes);
            }
            return;
        }

        intentosFallidos.remove(email);
        tiempoBloqueo.remove(email);
        vista.setMensaje("");
        abrirInicio(usuario);
    }

    private boolean estaBloqueado(String email) {
        if (!tiempoBloqueo.containsKey(email)) return false;
        LocalDateTime desbloqueo = tiempoBloqueo.get(email).plusMinutes(MINUTOS_BLOQUEO);
        if (LocalDateTime.now().isAfter(desbloqueo)) {
            tiempoBloqueo.remove(email);
            intentosFallidos.remove(email);
            return false;
        }
        return true;
    }

    private void registrarIntentoFallido(String email) {
        int intentos = intentosFallidos.getOrDefault(email, 0) + 1;
        intentosFallidos.put(email, intentos);
        if (intentos >= MAX_INTENTOS) {
            tiempoBloqueo.put(email, LocalDateTime.now());
        }
    }

    private void abrirInicio(Usuario usuario) {
        vista.dispose();
        System.out.println("Login correcto: " + usuario.getNombre());
    }

    private void abrirRegistro() {
        vista.dispose();
        System.out.println("Abrir registro");
    }
}