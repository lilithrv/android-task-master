package com.example.taskmaster;

public class Tarea {
    private String titulo;
    private String categoria;
    private float prioridad;
    private boolean hecha;

    public Tarea(String titulo, String categoria, float prioridad) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.hecha = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public float getPrioridad() {
        return prioridad;
    }

    public boolean isHecha() {
        return hecha;
    }

    public void setHecha(boolean hecha) {
        this.hecha = hecha;
    }
}
