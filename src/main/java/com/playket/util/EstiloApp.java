package com.playket.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class EstiloApp {

    // Colores corporativos de Playket
    public static final Color AZUL_OSCURO  = new Color(27, 47, 94);
    public static final Color NARANJA      = new Color(232, 80, 10);
    public static final Color BLANCO       = Color.WHITE;
    public static final Color GRIS_CLARO   = new Color(245, 245, 245);
    public static final Color GRIS_BORDE   = new Color(220, 220, 220);
    public static final Color GRIS_TEXTO   = new Color(100, 100, 100);
    public static final Color ROJO_ERROR   = new Color(200, 50, 50);
    public static final Color VERDE_EXITO  = new Color(60, 140, 60);
    public static final Color AZUL_SEL     = new Color(184, 207, 229);

    // Fuentes — usamos Segoe UI igual que el proyecto del instituto
    public static final Font FUENTE_TITULO    = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_PEQUEÑA   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_TABLA     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_CABECERA  = new Font("Segoe UI", Font.BOLD, 13);

    // Crea un botón principal (azul oscuro) — igual que crearBoton() del instituto
    public static JButton crearBtnPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(AZUL_OSCURO);
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_SUBTITULO);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Crea un botón secundario (naranja)
    public static JButton crearBtnSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_SUBTITULO);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Crea un botón de texto sin fondo
    public static JButton crearBtnTexto(String texto) {
        JButton btn = new JButton(texto);
        btn.setForeground(AZUL_OSCURO);
        btn.setFont(FUENTE_NORMAL);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Crea un botón de peligro (rojo) para acciones como cerrar torneo
    public static JButton crearBtnPeligro(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(180, 40, 40));
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_SUBTITULO);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Estiliza una tabla — igual que estilizarTabla() del instituto
    public static void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(28);
        tabla.setFont(FUENTE_TABLA);
        tabla.setGridColor(GRIS_BORDE);
        tabla.setSelectionBackground(AZUL_SEL);
        tabla.setSelectionForeground(Color.BLACK);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(FUENTE_CABECERA);
        header.setBackground(AZUL_OSCURO);
        header.setForeground(BLANCO);
    }

    // Crea un JScrollPane con borde sutil para las tablas
    public static JScrollPane crearScroll(JTable tabla) {
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));
        return scroll;
    }

    // Panel blanco con borde sutil — efecto tarjeta
    public static JPanel crearPanelTarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(BLANCO);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        return panel;
    }

    // Panel con fondo gris claro (fondo general de la ventana)
    public static JPanel crearPanelFondo() {
        JPanel panel = new JPanel();
        panel.setBackground(GRIS_CLARO);
        return panel;
    }

    // Crea la cabecera azul oscuro con el título en blanco
    public static JPanel crearCabecera(String titulo) {
        JPanel cabecera = new JPanel(new GridBagLayout());
        cabecera.setBackground(AZUL_OSCURO);
        cabecera.setPreferredSize(new Dimension(0, 70));
        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(FUENTE_TITULO);
        lbl.setForeground(BLANCO);
        cabecera.add(lbl);
        return cabecera;
    }

    // Crea una etiqueta de campo de formulario
    public static JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_NORMAL);
        lbl.setForeground(new Color(60, 60, 60));
        return lbl;
    }

    // Crea un campo de texto estilizado
    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_NORMAL);
        campo.setPreferredSize(new Dimension(0, 32));
        return campo;
    }

    // Crea un campo de contraseña estilizado
    public static JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(FUENTE_NORMAL);
        campo.setPreferredSize(new Dimension(0, 32));
        return campo;
    }
}