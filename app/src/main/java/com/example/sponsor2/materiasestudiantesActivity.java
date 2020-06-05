package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;

public class materiasestudiantesActivity extends AppCompatActivity implements View.OnClickListener {



    private Button BtnHorario,btnmatematicas, btnBiologia, btnSociales, btnEspañol, btnFisica, btnQuimica, btnIngles;
    String correo,usuario;
    String nomUsuario, nomCorreo;
    String indicadorMateria;
    Bundle  tomarUsuario;
    Bundle tomarCorreo;
    Bundle tomarUsuarioDeNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiasestudiantes);

        btnmatematicas = findViewById(R.id.botonMatematicas);
        btnBiologia = findViewById(R.id.botonBiologia);
        btnSociales = findViewById(R.id.botonSociales);
        btnEspañol = findViewById(R.id.botonEspañol);
        btnFisica = findViewById(R.id.botonFisica);
        btnQuimica = findViewById(R.id.botonEd_Fisica);
        btnIngles = findViewById(R.id.botonIngles);

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


        BtnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChat = new Intent(getApplication(), HorarioEstudianteActivity.class);
                intentChat.putExtra("usuario",nomUsuario);
                intentChat.putExtra("correo",nomCorreo);
                startActivity(intentChat);
            }
        });


        btnmatematicas.setOnClickListener(this);
        btnBiologia.setOnClickListener(this);
        btnSociales.setOnClickListener(this);
        btnEspañol.setOnClickListener(this);
        btnFisica.setOnClickListener(this);
        btnQuimica.setOnClickListener(this);
        btnIngles.setOnClickListener(this);
    }

    public void crud(String indicadorMateria){
        Toast.makeText(materiasestudiantesActivity.this,"Si entro,,,",Toast.LENGTH_SHORT).show();
        Intent intentEstud = new Intent(getApplication(), MatematicasEstudianteActivity.class);
        intentEstud.putExtra("usuario",nomUsuario);
        intentEstud.putExtra("correo",nomCorreo);
        intentEstud.putExtra("materia",indicadorMateria);
        startActivity(intentEstud);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonMatematicas:
                Toast.makeText(materiasestudiantesActivity.this,"Si entro,,,",Toast.LENGTH_SHORT).show();
                indicadorMateria = "Matematicas";
                crud(indicadorMateria);

                break;
            case R.id.botonBiologia:
                indicadorMateria = "Biologia";
                crud(indicadorMateria);
                break;

            case R.id.botonSociales:
                indicadorMateria = "Sociales";
                crud(indicadorMateria);
                break;

            case R.id.botonEspañol:
                indicadorMateria = "Español";
                crud(indicadorMateria);
                break;

            case R.id.botonFisica:
                indicadorMateria = "Fisica";
                crud(indicadorMateria);
                break;

            case R.id.botonEd_Fisica:
                indicadorMateria = "Quimica";
                crud(indicadorMateria);
                break;

            case R.id.botonIngles:
                indicadorMateria = "Ingles";
                crud(indicadorMateria);
                break;


        }
    }


}
