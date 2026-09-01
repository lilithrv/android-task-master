package com.example.taskmaster;

import java.util.ArrayList;

public class UsuarioRepositorio {
    // Usuario y clave de prueba (demo)
    public static final String USUARIO_DEMO = "admin";
    public static final String PASSWORD_DEMO = "1234";

    // Lista static: se mantiene mientras la app esté abierta
    private static final ArrayList<Usuario> usuarios = new ArrayList<>();

    // Registra un usuario nuevo en memoria
    public static void registrar(Usuario u) {
        usuarios.add(u);
    }

    // Indica si un nombre de usuario ya está ocupado.
    public static boolean existe(String usuario) {
        if (USUARIO_DEMO.equalsIgnoreCase(usuario)) {
            return true;
        }
        for (Usuario u : usuarios) {
            if (u.getUsuario().equalsIgnoreCase(usuario)) {
                return true;
            }
        }
        return false;
    }

    // Valida credenciales contra el usuario demo y los registrados
    public static boolean validar(String usuario, String password) {
        if (USUARIO_DEMO.equals(usuario) && PASSWORD_DEMO.equals(password)) {
            return true;
        }
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Indica si el usuario es el de prueba (para precargar tareas)
    public static boolean esDemo(String usuario) {
        return USUARIO_DEMO.equals(usuario);
    }
}
