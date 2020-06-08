package com.example.sponsor2;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rVerMensaje;
    private TextView TxtCreador;
    private EditText TxtMensaje;
    private TextView TxtMateriaTutoria;
    private ImageButton BotonEnviar;
    private DateFormatSymbols db;
    List<MensajeVO> listaMensajes= new ArrayList<>();
    AdaptadorRVMensajes RAdaptadorMensaje;
    //private DateFormatSymbols FirebaseFirestore;
    private  DatabaseReference BDChats;
    String[] Tutoria = new String[100];
    int cont=0;
    String usuario, correo;
    String nomUsuario, nomCorreo,nomMateria, nomTutor;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;
    Bundle tomarMateria;
    Bundle tomarTutor;


    RecyclerView rvMensajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvMensajes = findViewById(R.id.rvMensajes);
        rvMensajes.setLayoutManager(new GridLayoutManager(this, 1));

        BDChats = FirebaseDatabase.getInstance().getReference("Chats");

        tomarMateria = getIntent().getExtras();
        nomMateria = tomarMateria.getString("materiaTutoria");

        tomarTutor = getIntent().getExtras();
        nomTutor= tomarTutor.getString("tutorTutoria");


        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");
        Toast.makeText(this,"Usuario en = "+nomUsuario,Toast.LENGTH_SHORT).show();
        tomarUsuarioDeNuevo = getIntent().getExtras();
        if(nomCorreo==null){

            nomUsuario = tomarUsuarioDeNuevo.getString("TutoriaPara");
            Toast.makeText(this,"Refrescando nomUsuaruo"+nomUsuario,Toast.LENGTH_LONG).show();
        }




        TxtMateriaTutoria= (TextView) findViewById(R.id.TxtMateria);
        TxtMateriaTutoria.setText(nomMateria);
        TxtMateriaTutoria.setTextColor(Color.WHITE);

        if(nomMateria.equals("Matematicas")) {
            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonrematematicas);

        }

        if(nomMateria.equals("Biologia")){
            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonrebiologia);

        }
        if(nomMateria.equals("Español")){
            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonespanol);

        }
        if(nomMateria.equals("Sociales")){
            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonresociales);

        }
        if(nomMateria.equals("Fisica")){
            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonrefisica);

        }
        if(nomMateria.equals("Quimica")){

            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonreedufisica);
        }
        if(nomMateria.equals("Ingles")){
            TxtMateriaTutoria.setBackgroundResource(R.drawable.botonreingles);

        }
        TxtCreador = findViewById(R.id.TxtNombre);
        TxtCreador.setText(nomUsuario);

        TxtMensaje = findViewById(R.id.TxtMensaje);

        listaMensajes.clear();
        ObtenerMensajes();
        listaMensajes.clear();
        BotonEnviar= findViewById(R.id.botonEnviar);
        BotonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaMensajes.clear();
                EnviarMensaje();

            }
        });


    }

    public void ObtenerMensajes(){

        //Toast.makeText(this,"Entro A obtener Mensajes..",Toast.LENGTH_LONG).show();
        listaMensajes.clear();
       // rvMensajes.clearOnScrollListeners();
        BDChats.child("Materias"+nomUsuario).child(nomMateria+nomUsuario).child(nomTutor+nomUsuario).child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaMensajes.add(objeto.getValue(MensajeVO.class));
                }

                RAdaptadorMensaje = new AdaptadorRVMensajes(ChatActivity.this, listaMensajes, nomUsuario, nomMateria);
                rvMensajes.setAdapter(RAdaptadorMensaje);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listaMensajes.clear();

    }



    public void EnviarMensaje(){

       // Toast.makeText(this,"Enviar!",Toast.LENGTH_LONG).show();
        //String Hora ;
        listaMensajes.clear();
        String dia   =  new Date().toString();

        Toast.makeText(this, "La hora es : " + dia ,Toast.LENGTH_LONG).show();

        String Mensaje = TxtMensaje.getText().toString().trim();

        Toast.makeText(this, "Enviando Mensaje... ",Toast.LENGTH_SHORT).show();

        if(!TextUtils.isEmpty(Mensaje)){

            Toast.makeText(this, "Mensaje Envio ad " ,Toast.LENGTH_LONG ).show();


           // Intent intencionId = new  Intent(this,ChatActivity.class);
            //intencionId.putExtra("MensajeDe",nomUsuario);



            MensajeVO contenidoMensaje = new MensajeVO(nomUsuario,Mensaje);

            BDChats.child("Materias"+nomUsuario).child(nomMateria+nomUsuario)
                    .child(nomTutor+nomUsuario).child("Mensajes").child("MensajeDe"+nomUsuario+nomTutor+dia).setValue(contenidoMensaje);



            //BDChats.child("Tutor"+nomTutor).child("Chat"+nomMateria).
            // Edit.Rapid.BDChats.child("Materias"+nomUsuario).child(nomMateria+nomUsuario).child(nomTutor+nomUsuario).child("Mensajes").child("MensajeDe"+nomUsuario).setValue(contenidoMensaje); PARA EDITAR RAPIDO


        }else{
            Toast.makeText(this,"Falta algun Dato / Error ", Toast.LENGTH_LONG).show();
        }

        TxtMensaje.setText("");
        listaMensajes.clear();
        //rvMensajes.clearOnChildAttachStateChangeListeners();
        ObtenerMensajes();
    }


}
