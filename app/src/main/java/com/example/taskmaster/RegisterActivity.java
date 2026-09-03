package com.example.taskmaster;

import android.os.Bundle;
import android.widget.Toast;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {


    EditText etUsuario, etNombre, etEmail, etPassword;
    CheckBox cbTerminos;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a las vistas
        etUsuario = findViewById(R.id.etUsuario);
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbTerminos = findViewById(R.id.cbTerminos);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> {
            // Validación de todos los campos
            if (Validaciones.esVacio(etUsuario)) {
                etUsuario.setError("Ingrese el usuario");
                return;
            }
            if (Validaciones.esVacio(etNombre)) {
                etNombre.setError("Ingrese el nombre");
                return;
            }
            if (Validaciones.esVacio(etEmail)) {
                etEmail.setError("Ingrese el email");
                return;
            }
            if (Validaciones.esVacio(etPassword)) {
                etPassword.setError("Ingrese la contraseña");
                return;
            }

            if (!cbTerminos.isChecked()) {
                Toast.makeText(this, "Debe aceptar los términos", Toast.LENGTH_SHORT).show();
                return;
            }

            String usuario = etUsuario.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // No permitir usuarios repetidos
            if (UsuarioRepositorio.existe(usuario)) {
                etUsuario.setError("El usuario ya existe");
                return;
            }

            // Guardar en memoria y volver al Login
            Usuario nuevo = new Usuario(usuario, password, nombre, email);
            UsuarioRepositorio.registrar(nuevo);
            Toast.makeText(this, "Usuario registrado, inicia sesión", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}