package com.example.taskmaster;

import android.widget.EditText;

public class Validaciones {
    // Devuelve true si el campo está vacío (ignorando espacios)
    public static boolean esVacio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }
}
