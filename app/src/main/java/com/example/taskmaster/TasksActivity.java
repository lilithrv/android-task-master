package com.example.taskmaster;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {

    EditText etTarea;
    Spinner spCategoria;
    RatingBar rbPrioridad;
    Button btnAgregar;
    ProgressBar pbProgreso;
    TextView tvProgreso;
    RecyclerView rvTareas;

    ArrayList<Tarea> tareas = new ArrayList<>();
    TareaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a las vistas
        etTarea = findViewById(R.id.etTarea);
        spCategoria = findViewById(R.id.spCategoria);
        rbPrioridad = findViewById(R.id.rbPrioridad);
        btnAgregar = findViewById(R.id.btnAgregar);
        pbProgreso = findViewById(R.id.pbProgreso);
        tvProgreso = findViewById(R.id.tvProgreso);
        rvTareas = findViewById(R.id.rvTareas);

        // Llenar el Spinner con las categorías
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.at_categorias, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(spinnerAdapter);

        // Si es el usuario demo, precargar tareas de ejemplo
        boolean esDemo = getIntent().getBooleanExtra("ES_DEMO", false);
        if (esDemo) {
            tareas.add(new Tarea("Reunión", "Trabajo", 4));
            tareas.add(new Tarea("Estudiar Android", "Estudio", 5));
            tareas.add(new Tarea("Pedir hora control médico", "Personal", 3));
        }

        // Configurar el RecyclerView
        adapter = new TareaAdapter(tareas, this::actualizarProgreso);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        rvTareas.setAdapter(adapter);

        actualizarProgreso();

        // Botón agregar nueva tarea
        btnAgregar.setOnClickListener(v -> {
            String titulo = etTarea.getText().toString().trim();
            if (titulo.isEmpty()) {
                etTarea.setError("Ingrese la tarea");
                return;
            }
            String categoria = spCategoria.getSelectedItem().toString();
            float prioridad = rbPrioridad.getRating();

            tareas.add(new Tarea(titulo, categoria, prioridad));
            adapter.notifyItemInserted(tareas.size() - 1);

            // Limpiar el formulario
            etTarea.setText("");
            rbPrioridad.setRating(0);
            actualizarProgreso();
        });
    }

    // Actualiza la barra de progreso con el porcentaje de tareas completadas.
    private void actualizarProgreso() {
        int total = tareas.size();
        int completadas = 0;
        for (Tarea t : tareas) {
            if (t.isHecha()) {
                completadas++;
            }
        }
        int porcentaje = (total == 0) ? 0 : (completadas * 100 / total);
        pbProgreso.setProgress(porcentaje);
        tvProgreso.setText("Completadas: " + completadas + "/" + total + " (" + porcentaje + "%)");
    }
}