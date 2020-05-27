package com.example.sponsor2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorTutorias extends RecyclerView.Adapter<AdaptadorTutorias.TutoriasViewHolder>{




    Crud1Activity context;
    List<Tutorias> listaTutorias;

    public AdaptadorTutorias(Crud1Activity context, List<Tutorias> listaTutorias){
        this.context= context;
        this.listaTutorias= listaTutorias;
    }


    @NonNull
    @Override
    public TutoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.formamostartutoria,null, false);

        return new AdaptadorTutorias.TutoriasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TutoriasViewHolder holder, int position) {
        holder.TxtFecha.setText(listaTutorias.get(position).getFecha());
        holder.TxtHora.setText(listaTutorias.get(position).getHora());
        holder.TxtCreador.setText(listaTutorias.get(position).getUsuario());
    }

    @Override
    public int getItemCount() {
        return listaTutorias.size();
    }

    public class TutoriasViewHolder extends RecyclerView.ViewHolder{

        TextView TxtCreador,TxtHora, TxtFecha;

        public TutoriasViewHolder(@NonNull View itemView) {
            super(itemView);

            TxtCreador = itemView.findViewById(R.id.TxtCreador);
            TxtHora = itemView.findViewById(R.id.TxtHora);
            TxtFecha = itemView.findViewById(R.id.TxtFecha);

        }
    }

}
