package com.playket.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partido {
    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private String sede;
    private String estado;
    private String tipoVictoria;
    private int idTorneo;
    private int idLocal;
    private int idVisitante;
    private Integer idGanador;

    public Partido() {}

    public Partido(int id, LocalDate fecha, LocalTime hora, String sede,
                   String estado, String tipoVictoria, int idTorneo,
                   int idLocal, int idVisitante, Integer idGanador) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede;
        this.estado = estado;
        this.tipoVictoria = tipoVictoria;
        this.idTorneo = idTorneo;
        this.idLocal = idLocal;
        this.idVisitante = idVisitante;
        this.idGanador = idGanador;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTipoVictoria() { return tipoVictoria; }
    public void setTipoVictoria(String tipoVictoria) { this.tipoVictoria = tipoVictoria; }
    public int getIdTorneo() { return idTorneo; }
    public void setIdTorneo(int idTorneo) { this.idTorneo = idTorneo; }
    public int getIdLocal() { return idLocal; }
    public void setIdLocal(int idLocal) { this.idLocal = idLocal; }
    public int getIdVisitante() { return idVisitante; }
    public void setIdVisitante(int idVisitante) { this.idVisitante = idVisitante; }
    public Integer getIdGanador() { return idGanador; }
    public void setIdGanador(Integer idGanador) { this.idGanador = idGanador; }
}