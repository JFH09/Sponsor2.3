package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class materiasestudiantesActivity extends AppCompatActivity {

    private Button BtnHorario, BtnMatematicas;
    String correo,usuario;

    Bundle  tomarUsuario;
    Bundle tomarCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiasestudiantes);

        tomarUsuario = getIntent().getExtras();
        usuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        correo = tomarCorreo.getString("correo");

        BtnHorario = findViewById(R.id.botonVerHorario);
        BtnMatematicas = findViewById(R.id.botonMatematicas);

        BtnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChat = new Intent(getApplication(), ChatActivity.class);
                intentChat.putExtra("usuario",usuario);
                intentChat.putExtra("correo",correo);
                startActivity(intentChat);
            }
        });

        BtnMatematicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMatematicasEstud = new Intent(getApplication(), MatematicasEstudianteActivity.class);
                intentMatematicasEstud.putExtra("usuario",usuario);
                intentMatematicasEstud.putExtra("correo",correo);
                startActivity(intentMatematicasEstud);
            }
        });
    }
}
