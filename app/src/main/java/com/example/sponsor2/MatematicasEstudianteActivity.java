package com.example.sponsor2;


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

public class MatematicasEstudianteActivity extends AppCompatActivity {


    Button BtnMatematicas, NuevaTutoria, consultarPorTutor;

    String usuario, correo, indicadorMateria = "Matematicas";

    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;

    DatabaseReference databaseReference,BDTutorias;

    List<Tutorias> listaTutorias= new ArrayList<>();

    AdaptadorTutorias adaptador;

    RecyclerView rvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematicas_estudiante);
        tomarUsuario = getIntent().getExtras();
        usuario = tomarUsuario.getString("usuario");


        tomarCorreo = getIntent().getExtras();
        correo = tomarCorreo.getString("correo");
        tomarUsuarioDeNuevo = getIntent().getExtras();
        if(nomCorreo==null){

            String nomUsuario = tomarUsuarioDeNuevo.getString("TutoriaPara");
            Toast.makeText(this,"Refrescando nomUsuaruo"+nomUsuario,Toast.LENGTH_LONG).show();
        }


        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));


        BDTutorias= FirebaseDatabase.getInstance().getReference("Tutorias");
        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorias");

        BtnMatematicas = findViewById(R.id.btnMatematicas);
        NuevaTutoria = findViewById(R.id.btnElegirTutoria);
        consultarPorTutor = findViewById(R.id.btnConsultaPorTutor);

        Toast.makeText(MatematicasEstudianteActivity.this, "Usuario = "+usuario, Toast.LENGTH_SHORT ).show();



        BtnMatematicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerTutoriasMateria();
            }
        });

    }


    public void ObtenerTutoriasMateria(){

        listaTutorias.clear();
        databaseReference.child("ListadoTutorias").child("Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(MatematicasEstudianteActivity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //limpiarCampos();

    }


}
