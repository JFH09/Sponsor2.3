package com.example.sponsor2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.io.Resources;

import org.w3c.dom.Text;

import java.util.List;

public class AdaptadorRVMensajes extends RecyclerView.Adapter<AdaptadorRVMensajes.MensajesViewHolder> {

        Context context;
        List<MensajeVO> listaMensaje;
        String usuario;


        public AdaptadorRVMensajes(Context context, List<MensajeVO> listaMensaje, String usuario){
                this.context= context;
                this.listaMensaje= listaMensaje;
                this.usuario = usuario;
        }


        @NonNull
        @Override
        public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if(context.equals("ChatTutoresActivity")){
                        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.formamensajetutor,null, false);
                        return new AdaptadorRVMensajes.MensajesViewHolder(vista);
                }else{
                        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.forma_mensajes,null, false);
                        return new AdaptadorRVMensajes.MensajesViewHolder(vista);
                }



        }

        @Override
        public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
                holder.TxtNombreMensaje.setText(listaMensaje.get(position).getNombre());
                holder.TxtRecibMensaje.setText(listaMensaje.get(position).getMensaje());

                if(holder.TxtNombreMensaje.getText().toString().equals(usuario)){
                        holder.TxtNombreMensaje.setBackgroundResource(R.drawable.botontutor);
                }else{
                        holder.TxtNombreMensaje.setBackgroundResource(R.drawable.botonredondo);
                }

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
                        if(TxtNombreMensaje.getText().toString().equals("estud99")){
                            TxtNombreMensaje.setBackgroundResource(R.drawable.botontutor);
                        }


                }
        }


}
