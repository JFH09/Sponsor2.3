package com.example.sponsor2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorTutorias extends RecyclerView.Adapter<AdaptadorTutorias.TutoriasViewHolder> implements View.OnClickListener{




    Context context;
    List<Tutorias> listaTutorias;
    private View.OnClickListener listener;

    public AdaptadorTutorias(Context context, List<Tutorias> listaTutorias){
        this.context= context;
        this.listaTutorias= listaTutorias;
    }


    @NonNull
    @Override
    public TutoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.formamostartutoria,null, false);
        vista.setOnClickListener(this);
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class TutoriasViewHolder extends RecyclerView.ViewHolder {

        TextView TxtCreador,TxtHora, TxtFecha;

        public TutoriasViewHolder(@NonNull View itemView) {
            super(itemView);

            TxtCreador = itemView.findViewById(R.id.TxtCreador);
            TxtHora = itemView.findViewById(R.id.TxtHora);
            TxtFecha = itemView.findViewById(R.id.TxtFecha);

        }



    }

}
