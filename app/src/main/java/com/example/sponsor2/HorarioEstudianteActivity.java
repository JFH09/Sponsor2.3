package com.example.sponsor2;


import android.os.Bundle;
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

public class HorarioEstudianteActivity extends AppCompatActivity {

    String usuario, correo;
    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;

    DatabaseReference BDHorariosTutorias;
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



        ObtenerHorarioTutorias();
    }



    public void ObtenerHorarioTutorias(){

        listaTutorias.clear();
        BDHorariosTutorias.child("Horario"+nomUsuario).child(nomUsuario+"Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(HorarioEstudianteActivity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //limpiarCampos();

    }
}
