package com.playket.model;

public class Deporte {
    private int id;
    private String nombre;

    public Deporte(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() { return nombre; }
}