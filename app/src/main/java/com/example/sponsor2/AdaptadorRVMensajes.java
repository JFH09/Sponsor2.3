package com.example.sponsor2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorRVMensajes extends RecyclerView.Adapter<AdaptadorRVMensajes.MensajeHolder> {

        private List<MensajeVO> listaMensaje;

        public String nombre;


        public AdaptadorRVMensajes (List<MensajeVO> listaMensaje) {
                this.listaMensaje= listaMensaje;
        }

        @NonNull
        @Override
        public MensajeHolder onCreateViewHolder(@NonNull ViewGroup vistaMensaje, int i) {
                View Vista = LayoutInflater.from(vistaMensaje.getContext()).inflate(R.layout.forma_mensajes,vistaMensaje,false);
                return new MensajeHolder(Vista);
        }

        @Override
        public void onBindViewHolder(@NonNull MensajeHolder poseedorMensaje, int i) {
                poseedorMensaje.TxtRecibMensaje.setText(listaMensaje.get(i).getMensaje());
                poseedorMensaje.TxtNombreMensaje.setText(listaMensaje.get(i).getNombre());

        }


        @Override
        public int getItemCount() {
                return listaMensaje.size();
        }

        class MensajeHolder extends RecyclerView.ViewHolder{
                private TextView TxtNombreMensaje;
                private TextView TxtRecibMensaje;

                public MensajeHolder(@NonNull View itemView){
                        super(itemView);
                        TxtNombreMensaje= itemView.findViewById(R.id.TxtNombreMensaje);
                        TxtRecibMensaje = itemView.findViewById(R.id.TxtRecibMensaje);
                }
        }


}
