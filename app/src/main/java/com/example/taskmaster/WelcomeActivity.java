package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeActivity extends AppCompatActivity {

    TextView tvBienvenida;
    Button btnContinuar;

    String usuario;
    boolean esDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvBienvenida = findViewById(R.id.tvBienvenida);
        btnContinuar = findViewById(R.id.btnContinuar);

        // Recibir datos del Login
        Intent intent = getIntent();
        usuario = intent.getStringExtra("USUARIO");
        esDemo = intent.getBooleanExtra("ES_DEMO", false);

        tvBienvenida.setText("¡Bienvenido, " + usuario + "!");

        // Continuar hacia la vista de Tareas, pasando los datos
        btnContinuar.setOnClickListener(v -> {
            Intent i = new Intent(WelcomeActivity.this, TasksActivity.class);
            i.putExtra("USUARIO", usuario);
            i.putExtra("ES_DEMO", esDemo);
            startActivity(i);
        });
    }
}