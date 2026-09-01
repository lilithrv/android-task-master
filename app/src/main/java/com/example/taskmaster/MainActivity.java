package com.example.taskmaster;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a las vistas
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validación de campos vacíos
            if (Validaciones.esVacio(etUsuario)) {
                etUsuario.setError("Ingrese el usuario");
                return;
            }
            if (Validaciones.esVacio(etPassword)) {
                etPassword.setError("Ingrese la contraseña");
                return;
            }

            // Validación de credenciales (usuario demo o registrado)
            if (UsuarioRepositorio.validar(usuario, password)) {
                boolean esDemo = UsuarioRepositorio.esDemo(usuario);

                // Paso de datos a la vista de Bienvenido
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.putExtra("USUARIO", usuario);
                intent.putExtra("ES_DEMO", esDemo);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}