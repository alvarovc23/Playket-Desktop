package com.playket.model;

public class EstadisticaLiga {
    public String nombre;
    public int partidosJugados;
    public int partidosGanados;
    public int partidosEmpatados;
    public int partidosPerdidos;
    public int puntos;

    public EstadisticaLiga(String nombre) {
        this.nombre = nombre;
    }
}