package com.example.taskmaster;

import android.util.Patterns;
import android.widget.EditText;

public class Validaciones {
    // Devuelve true si el campo está vacío (ignorando espacios)
    public static boolean esVacio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }

    public static boolean esEmailInvalido(EditText campo) {
        String email = campo.getText().toString().trim();
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
