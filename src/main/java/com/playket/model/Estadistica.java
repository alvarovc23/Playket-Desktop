package com.playket.model;

public class Estadistica {
    private int id;
    private String tipo;
    private String valor;
    private int idPartido;
    private int idParticipante;

    public Estadistica() {}

    public Estadistica(int id, String tipo, String valor,
                       int idPartido, int idParticipante) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.idPartido = idPartido;
        this.idParticipante = idParticipante;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    public int getIdPartido() { return idPartido; }
    public void setIdPartido(int idPartido) { this.idPartido = idPartido; }
    public int getIdParticipante() { return idParticipante; }
    public void setIdParticipante(int idParticipante) { this.idParticipante = idParticipante; }
}