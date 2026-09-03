package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
    // Interfaz para avisar a la Activity cuando cambia una tarea (marcar/desmarcar).
    public interface OnTareaCambiadaListener {
        void onTareaCambiada();
    }

    private final List<Tarea> tareas;
    private final OnTareaCambiadaListener listener;

    public TareaAdapter(List<Tarea> tareas, OnTareaCambiadaListener listener) {
        this.tareas = tareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvCategoria.setText(tarea.getCategoria());
        holder.rbPrioridad.setRating(tarea.getPrioridad());

        // Se quita el listener antes de setChecked para evitar disparos al reciclar la vista.
        holder.cbHecha.setOnCheckedChangeListener(null);
        holder.cbHecha.setChecked(tarea.isHecha());
        holder.cbHecha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tarea.setHecha(isChecked);
            if (listener != null) {
                listener.onTareaCambiada();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    // ViewHolder: guarda las referencias a las vistas de cada item.
    static class TareaViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbHecha;
        TextView tvTitulo;
        TextView tvCategoria;
        RatingBar rbPrioridad;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            cbHecha = itemView.findViewById(R.id.cbHecha);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            rbPrioridad = itemView.findViewById(R.id.rbPrioridad);
        }
    }
}
