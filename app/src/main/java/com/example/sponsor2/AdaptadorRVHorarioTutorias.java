package com.example.sponsor2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorRVHorarioTutorias  extends RecyclerView.Adapter<AdaptadorRVHorarioTutorias.MensajeHolder> {
    private List<HorarioTutoriaVO> listaHorario;

    public String creador, fecha, hora;

    public AdaptadorRVHorarioTutorias (List<HorarioTutoriaVO> listaHorario) {
        this.listaHorario= listaHorario;
    }


    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup vistaHorario, int i) {
        View Vista = LayoutInflater.from(vistaHorario.getContext()).inflate(R.layout.formamostartutoria,vistaHorario,false);
        return new MensajeHolder(Vista);
    }

    @Override
    public void  onBindViewHolder(@NonNull MensajeHolder poseedorInfo, int i) {
        poseedorInfo.TxtCreador.setText(listaHorario.get(i).getCreador());
        poseedorInfo.TxtFecha.setText(listaHorario.get(i).getFecha());
        poseedorInfo.TxtHora.setText(listaHorario.get(i).getHorario());
    }



    @Override
    public int getItemCount() {
        return listaHorario.size();
    }

    class MensajeHolder extends RecyclerView.ViewHolder{
        private TextView TxtCreador;
        private TextView TxtFecha;
        private TextView TxtHora;

        public MensajeHolder(@NonNull View itemView){
            super(itemView);
            TxtCreador= itemView.findViewById(R.id.TxtCreador);
            TxtFecha = itemView.findViewById(R.id.TxtFecha);
            TxtHora = itemView.findViewById(R.id.TxtHora);
        }
    }
}
