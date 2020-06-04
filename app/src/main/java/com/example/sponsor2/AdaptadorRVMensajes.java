package com.example.sponsor2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorRVMensajes extends RecyclerView.Adapter<AdaptadorRVMensajes.MensajesViewHolder> {

        Context context;
        List<MensajeVO> listaMensaje;



        public AdaptadorRVMensajes(ChatActivity context, List<MensajeVO> listaMensaje){
                this.context= context;
                this.listaMensaje= listaMensaje;
        }


        @NonNull
        @Override
        public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.forma_mensajes,null, false);

                return new AdaptadorRVMensajes.MensajesViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
                holder.TxtNombreMensaje.setText(listaMensaje.get(position).getNombre());
                holder.TxtRecibMensaje.setText(listaMensaje.get(position).getMensaje());

        }

        @Override
        public int getItemCount() {
                return listaMensaje.size();
        }

        public class MensajesViewHolder extends RecyclerView.ViewHolder{

                TextView TxtNombreMensaje, TxtRecibMensaje;

                public MensajesViewHolder(@NonNull View itemView) {
                        super(itemView);

                        TxtRecibMensaje = itemView.findViewById(R.id.TxtRecibMensaje);
                        TxtNombreMensaje = itemView.findViewById(R.id.TxtNombreMensaje);


                }
        }


}
