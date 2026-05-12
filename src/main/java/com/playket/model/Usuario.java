package com.playket.model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private String passwordHash;
    private String preguntaSeguridad;
    private String respuestaSegHash;
    private LocalDate fechaRegistro;

    public Usuario() {}

    public Usuario(int id, String nombre, String apellidos, String email,
                   String passwordHash, String preguntaSeguridad,
                   String respuestaSegHash, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.passwordHash = passwordHash;
        this.preguntaSeguridad = preguntaSeguridad;
        this.respuestaSegHash = respuestaSegHash;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getPreguntaSeguridad() { return preguntaSeguridad; }
    public void setPreguntaSeguridad(String p) { this.preguntaSeguridad = p; }
    public String getRespuestaSegHash() { return respuestaSegHash; }
    public void setRespuestaSegHash(String r) { this.respuestaSegHash = r; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}