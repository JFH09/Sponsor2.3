package com.example.sponsor2;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Crud1Activity extends AppCompatActivity {

    EditText etUsuario, etContrasena, etTelefono, etEmail;
    Button btnConsultar, btnConsultarUsuario, btnAgregar, btnEditar, btnEliminar;
    RecyclerView rvUsuarios;


    DatabaseReference databaseReference;

    List<Tutorias> listaTutorias= new ArrayList<>();

    AdaptadorTutorias adaptador;

    String nomUsuario, nomCorreo;
    Bundle tomarUsuario;
    Bundle tomarCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud1);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultarUsuario = findViewById(R.id.btnConsultarUsuario);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);


        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");

        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));

        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorias");



        etUsuario.setText(nomUsuario);
        obtenerUsuarios();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerTutoriasPorMaterias();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editarUsuario();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUsuario();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUsuarios();
            }
        });

        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUsuario();
            }
        });

    }

    public void obtenerTutoriasPorMaterias(){

        //colocar un filtro por materia
        Toast.makeText(Crud1Activity.this,"---Matematicas--- ",Toast.LENGTH_SHORT).show();
        listaTutorias.clear();
        databaseReference.child("ListadoTutorias").child("Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(Crud1Activity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void obtenerUsuario() {
        listaTutorias.clear();
        String usuario = etUsuario.getText().toString();

        Query query = databaseReference.child("Tutorias").orderByChild(usuario).equalTo(nomUsuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(Crud1Activity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);

                limpiarCampos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void obtenerUsuarios() {
        listaTutorias.clear();
        databaseReference.child("Tutorias"+nomUsuario).child(nomUsuario+"Matematicas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaTutorias.add(objeto.getValue(Tutorias.class));
                }

                adaptador = new AdaptadorTutorias(Crud1Activity.this, listaTutorias);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void editarUsuario() {
        listaTutorias.clear();

        final Tutorias tutorias = new Tutorias(
                etUsuario.getText().toString(),
                etContrasena.getText().toString(),
                etTelefono.getText().toString()

        );

        Query query = databaseReference.child("Tutorias").orderByChild("usuario").equalTo(tutorias.getUsuario());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = "";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey(); // Obtiene el id del registro para poderlo editar
                }

                databaseReference.child("usuario").child(key).setValue(tutorias);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

    public void eliminarUsuario() {
        listaTutorias.clear();
        String usuario = etUsuario.getText().toString();

        Query query = databaseReference.child("Tutorias").orderByChild("usuario").equalTo(usuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    objeto.getRef().removeValue();
                }
                Toast.makeText(Crud1Activity.this, "Se elimino la tutoria", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }



    public void limpiarCampos() {
        etUsuario.setText("");
        etContrasena.setText("");
        etTelefono.setText("");
        etEmail.setText("");
    }

}
