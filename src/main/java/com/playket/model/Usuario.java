package com.playket.model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String preguntaSeguridad;
    private String respuestaSeg;
    private LocalDate fechaRegistro;

    public Usuario() {}

    public Usuario(int id, String nombre, String apellidos, String email,
                   String password, String preguntaSeguridad,
                   String respuestaSeg, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.preguntaSeguridad = preguntaSeguridad;
        this.respuestaSeg = respuestaSeg;
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
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPreguntaSeguridad() { return preguntaSeguridad; }
    public void setPreguntaSeguridad(String p) { this.preguntaSeguridad = p; }
    public String getRespuestaSeg() { return respuestaSeg; }
    public void setRespuestaSeg(String r) { this.respuestaSeg = r; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
