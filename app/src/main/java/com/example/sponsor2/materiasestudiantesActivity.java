package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class materiasestudiantesActivity extends AppCompatActivity {

    private Button BtnHorario, BtnMatematicas;
    String correo,usuario;
    String nomUsuario, nomCorreo;
    Bundle  tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiasestudiantes);

        tomarUsuario = getIntent().getExtras();
        nomUsuario = tomarUsuario.getString("usuario");

        Toast.makeText(this,"El usuario es = "+nomUsuario,Toast.LENGTH_LONG).show();

        tomarCorreo = getIntent().getExtras();
        nomCorreo = tomarCorreo.getString("correo");

        tomarUsuarioDeNuevo = getIntent().getExtras();
        if(nomCorreo==null){

            nomUsuario = tomarUsuarioDeNuevo.getString("TutoriaPara");
            Toast.makeText(this,"Refrescando nomUsuaruo"+nomUsuario,Toast.LENGTH_LONG).show();
        }
        usuario= nomUsuario;

        BtnHorario = findViewById(R.id.botonVerHorario);
        BtnMatematicas = findViewById(R.id.botonMatematicas);

        BtnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChat = new Intent(getApplication(), HorarioEstudianteActivity.class);
                intentChat.putExtra("usuario",nomUsuario);
                intentChat.putExtra("correo",nomCorreo);
                startActivity(intentChat);
            }
        });

        BtnMatematicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMatematicasEstud = new Intent(getApplication(), MatematicasEstudianteActivity.class);
                intentMatematicasEstud.putExtra("usuario",nomUsuario);
                intentMatematicasEstud.putExtra("correo",nomCorreo);
                startActivity(intentMatematicasEstud);
            }
        });
    }
}
