package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

public class HorarioEstudianteActivity extends AppCompatActivity  {

    String materiaTutoria, tutorTutoria;

    String usuario, correo;
    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;


    Button BtnChat;

    DatabaseReference BDHorariosTutorias, BDChats,BDTutorias;
    List<Tutorias> listaTutorias= new ArrayList<>();

    AdaptadorTutorias adaptador;

    RecyclerView rvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_estudiante);



        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        Toast.makeText(this,"Usuario"+nomUsuario,Toast.LENGTH_LONG).show();

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");



        tomarUsuarioDeNuevo = getIntent().getExtras();
        if(nomCorreo==null){

            nomUsuario = tomarUsuarioDeNuevo.getString("TutoriaPara");
            Toast.makeText(this,"Refrescando nomUsuaruo"+nomUsuario,Toast.LENGTH_LONG).show();
        }
        usuario= nomUsuario;

        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));



        BDHorariosTutorias= FirebaseDatabase.getInstance().getReference("HorariosTutorias");
        BDChats = FirebaseDatabase.getInstance().getReference("Chats");
        BDTutorias = FirebaseDatabase.getInstance().getReference("Tutorias");

        ObtenerHorarioTutorias();


        BtnChat = findViewById(R.id.btnChat);

        BtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Click en la tutoria la cual quieres entrar...",Toast.LENGTH_LONG).show();
            }
        });
    }



    public void ObtenerHorarioTutorias(){

        listaTutorias.clear();
        BDHorariosTutorias.child("Horario"+nomUsuario).child("listaTutorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(HorarioEstudianteActivity.this, listaTutorias);

                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Tutor"+
                                listaTutorias.get(rvUsuarios.getChildAdapterPosition(view)).getUsuario(),Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplicationContext(), " Materia= " +
                                listaTutorias.get(rvUsuarios.getChildAdapterPosition(view)).getIndicadorMateria(),Toast.LENGTH_SHORT).show();

                        materiaTutoria= listaTutorias.get(rvUsuarios.getChildAdapterPosition(view)).getIndicadorMateria();

                        tutorTutoria = listaTutorias.get(rvUsuarios.getChildAdapterPosition(view)).getUsuario();

                        Intent intentChat = new Intent(getApplication(), ChatActivity.class);
                        intentChat.putExtra("materiaTutoria",materiaTutoria);
                        intentChat.putExtra("tutorTutoria",tutorTutoria);
                        intentChat.putExtra("usuario",nomUsuario);
                        intentChat.putExtra("correo",nomCorreo);


                        MensajeVO mensajeUno = new MensajeVO(nomUsuario,"Hola Soy "+nomUsuario);
                        NombreChat estudiantes = new NombreChat(nomUsuario);

                        //BDChats.child("PersonasEnChat").child("NombreChat").child(materiaTutoria).setValue(estudiantes);
                        BDTutorias.child("Tutorias"+tutorTutoria).child(tutorTutoria+materiaTutoria).child("Estudiantes").setValue(estudiantes);
                        startActivity(intentChat);






                    }
                });

                rvUsuarios.setAdapter(adaptador);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //limpiarCampos();

    }


}
