package com.example.sponsor2;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatTutoresActivity extends AppCompatActivity {

    private RecyclerView rVerMensaje;
    private TextView TxtCreador;
    private EditText TxtMensaje;
    private TextView TxtMateriaTutoria;
    private ImageButton BotonEnviar;
    private DateFormatSymbols db;
    List<MensajeVO> listaMensajes= new ArrayList<>();
    AdaptadorRVMensajes RAdaptadorMensaje;
    //private DateFormatSymbols FirebaseFirestore;
    private DatabaseReference BDChats;
    String[] Tutoria = new String[100];
    int cont=0;
    String usuario, correo;
    String nomUsuario, nomCorreo,nomMateria, nomTutor,nomEstudiante;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;
    Bundle tomarMateria;
    Bundle tomarTutor;
    Bundle tomarEstudiante;


    RecyclerView rvMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_tutores);


        rvMensajes = findViewById(R.id.rvMensajes);
        rvMensajes.setLayoutManager(new GridLayoutManager(this, 1));

        BDChats = FirebaseDatabase.getInstance().getReference("Chats");

        tomarMateria = getIntent().getExtras();
        nomMateria = tomarMateria.getString("materiaTutoria");




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

        tomarEstudiante = getIntent().getExtras();
        nomEstudiante = tomarEstudiante.getString("estudiante");

        Toast.makeText(ChatTutoresActivity.this,"!!!!!!!1"+nomUsuario,Toast.LENGTH_LONG).show();

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
        TxtCreador.setText(nomEstudiante);

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
                //                                        ObtenerMensajes();


            }
        });
    }



    public void ObtenerMensajes(){



        Toast.makeText(this,"Estudiante.."+nomEstudiante,Toast.LENGTH_LONG).show();
        listaMensajes.clear();
        BDChats.child("Materias"+nomEstudiante).child(nomMateria+nomEstudiante).child(nomUsuario+nomEstudiante).child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaMensajes.add(objeto.getValue(MensajeVO.class));
                }
                Toast.makeText(ChatTutoresActivity.this,"Materia Va como.."+nomMateria,Toast.LENGTH_LONG).show();

                RAdaptadorMensaje = new AdaptadorRVMensajes(ChatTutoresActivity.this, listaMensajes, nomUsuario, nomMateria);
                rvMensajes.setAdapter(RAdaptadorMensaje);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //rvMensajes.clearOnChildAttachStateChangeListeners();
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

            BDChats.child("Materias"+nomEstudiante).child(nomMateria+nomEstudiante).child(nomUsuario+nomEstudiante).child("Mensajes").child("MensajeDe"+nomEstudiante+nomUsuario+dia).setValue(contenidoMensaje);
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
