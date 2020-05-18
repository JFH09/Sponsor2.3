package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class AdminActivity extends AppCompatActivity {

    FloatingActionsMenu grupoBotones;
    FloatingActionButton fabAgregar, fabEditar, fabEliminar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        grupoBotones = findViewById(R.id.grupoFab);
        fabAgregar= findViewById(R.id.idFabAgregar);
        fabEditar = findViewById(R.id.idFabEditar);
        fabAgregar = findViewById(R.id.idFabEliminar);

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplication(),MatematicasActivity.class);
                startActivity(intento);
            }
        });

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplication(),ChatActivity.class);
                startActivity(intento);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplication(),eligetumateriaActivity.class);
                startActivity(intento);
            }
        });


    }

}
