package com.example.sponsor2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorRVHorarioTutorias  extends RecyclerView.Adapter<AdaptadorRVHorarioTutorias.ViewHolder> {




    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView  fecha, hora;
        private TextView creador;

        public ViewHolder (View itemView){
            super(itemView);

            fecha = (TextView) itemView.findViewById(R.id.TxtFecha);
            hora = (TextView) itemView.findViewById(R.id.TxtHora);
            creador = (TextView) itemView.findViewById(R.id.TxtCreador);

        }

    }

    public List<HorarioTutoriaVO> listaHorarios;

    public AdaptadorRVHorarioTutorias(MatematicasActivity matematicasActivity, List<HorarioTutoriaVO> listaHorarios){
        this.listaHorarios=listaHorarios;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formamostartutoria,null,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fecha.setText(listaHorarios.get(position).getFecha());
        holder.hora.setText(listaHorarios.get(position).getHorario());
        holder.creador.setText(listaHorarios.get(position).getCreador());

    }

    @Override
    public int getItemCount() {
        return listaHorarios.size();
    }


}
