package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
    Button btnCerrarSesion;
    ProgressBar pbProgreso;
    TextView tvProgreso;
    RadioGroup rgFiltro;
    RecyclerView rvTareas;

    // Lista maestra con todas las tareas y lista visible
    ArrayList<Tarea> todas = new ArrayList<>();
    ArrayList<Tarea> visibles = new ArrayList<>();
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
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        pbProgreso = findViewById(R.id.pbProgreso);
        tvProgreso = findViewById(R.id.tvProgreso);
        rgFiltro = findViewById(R.id.rgFiltro);
        rvTareas = findViewById(R.id.rvTareas);

        // Llenar el Spinner con las categorías
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.at_categorias, R.layout.item_spinner);
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner);
        spCategoria.setAdapter(spinnerAdapter);

        // Si es el usuario demo, precargar tareas de ejemplo
        boolean esDemo = getIntent().getBooleanExtra("ES_DEMO", false);
        if (esDemo) {
            todas.add(new Tarea("Reunión", "Trabajo", 4));
            todas.add(new Tarea("Estudiar Android", "Estudio", 5));
            todas.add(new Tarea("Pedir hora control médico", "Personal", 3));
        }

        // Configurar el RecyclerView (el adapter muestra la lista visible/filtrada)
        adapter = new TareaAdapter(visibles, this::onTareaCambiada);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        rvTareas.setAdapter(adapter);

        aplicarFiltro();
        actualizarProgreso();

        // Cambiar el filtro vuelve a armar la lista visible
        rgFiltro.setOnCheckedChangeListener((group, checkedId) -> aplicarFiltro());

        // Botón agregar nueva tarea
        btnAgregar.setOnClickListener(v -> {
            String titulo = etTarea.getText().toString().trim();
            if (titulo.isEmpty()) {
                etTarea.setError("Ingrese la tarea");
                return;
            }
            String categoria = spCategoria.getSelectedItem().toString();
            float prioridad = rbPrioridad.getRating();

            todas.add(new Tarea(titulo, categoria, prioridad));
            aplicarFiltro();

            // Limpiar el formulario
            etTarea.setText("");
            rbPrioridad.setRating(0);
            actualizarProgreso();
        });

        // Logout
        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(TasksActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    // usuario marca/desmarca una tarea en la lista
    private void onTareaCambiada() {
        actualizarProgreso();
        aplicarFiltro();
    }

    // Reconstruye la lista visible según el filtro seleccionado
    private void aplicarFiltro() {
        int checkedId = rgFiltro.getCheckedRadioButtonId();
        visibles.clear();
        for (Tarea t : todas) {
            boolean mostrar;
            if (checkedId == R.id.rbPendientes) {
                mostrar = !t.isHecha();
            } else if (checkedId == R.id.rbCompletadas) {
                mostrar = t.isHecha();
            } else {
                mostrar = true; // rbTodas
            }
            if (mostrar) {
                visibles.add(t);
            }
        }
        adapter.notifyDataSetChanged();
    }

    // Actualiza la barra de progreso con el porcentaje de tareas completadas
    private void actualizarProgreso() {
        int total = todas.size();
        int completadas = 0;
        for (Tarea t : todas) {
            if (t.isHecha()) {
                completadas++;
            }
        }
        int porcentaje = (total == 0) ? 0 : (completadas * 100 / total);
        pbProgreso.setProgress(porcentaje);
        tvProgreso.setText("Completadas: " + completadas + "/" + total + " (" + porcentaje + "%)");
    }
}
