package com.example.taskmaster;

public class Usuario {
    private String usuario;
    private String password;
    private String nombre;
    private String email;

    public Usuario(String usuario, String password, String nombre, String email) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
