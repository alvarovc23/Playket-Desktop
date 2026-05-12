package com.playket.model;

import java.time.LocalDate;

public class Torneo {
    private int id;
    private String nombre;
    private String descripcion;
    private String formato;
    private String estado;
    private LocalDate fechaInicio;
    private int numParticipantes;
    private String imagenPortada;
    private int idDeporte;
    private int idOrganizador;

    public Torneo() {}

    public Torneo(int id, String nombre, String descripcion, String formato,
                  String estado, LocalDate fechaInicio, int numParticipantes,
                  String imagenPortada, int idDeporte, int idOrganizador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.formato = formato;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.numParticipantes = numParticipantes;
        this.imagenPortada = imagenPortada;
        this.idDeporte = idDeporte;
        this.idOrganizador = idOrganizador;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public int getNumParticipantes() { return numParticipantes; }
    public void setNumParticipantes(int numParticipantes) { this.numParticipantes = numParticipantes; }
    public String getImagenPortada() { return imagenPortada; }
    public void setImagenPortada(String imagenPortada) { this.imagenPortada = imagenPortada; }
    public int getIdDeporte() { return idDeporte; }
    public void setIdDeporte(int idDeporte) { this.idDeporte = idDeporte; }
    public int getIdOrganizador() { return idOrganizador; }
    public void setIdOrganizador(int idOrganizador) { this.idOrganizador = idOrganizador; }
}