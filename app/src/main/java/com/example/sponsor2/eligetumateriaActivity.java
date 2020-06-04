package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class eligetumateriaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnHorarioTutoria,btnmatematicas, btnBiologia;
    private String usuario;
    private String correo;

    Bundle  tomarUsuario;
    Bundle tomarCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligetumateria);

        btnHorarioTutoria = findViewById(R.id.botonVerHorario);
        btnmatematicas = findViewById(R.id.botonMatematicas);
        btnBiologia = findViewById(R.id.botonBiologia);


        tomarUsuario = getIntent().getExtras();
        usuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        correo = tomarCorreo.getString("correo");

        btnHorarioTutoria.setOnClickListener(this);
        btnmatematicas.setOnClickListener(this);
        btnBiologia.setOnClickListener(this);
    }

    public void matematicas(){

        Toast.makeText(this,"Paso.."+ correo,Toast.LENGTH_LONG).show();
        Intent matematicas = new Intent(getApplication(),MatematicasActivity.class);
        matematicas.putExtra("usuario",usuario);
        matematicas.putExtra("correo",correo);
        startActivity(matematicas);
    }

    public void crud(){

        Intent crud =new  Intent(getApplication(), Crud1Activity.class);
        crud.putExtra("usuario",usuario);
        crud.putExtra("correo",correo);
        startActivity(crud);
    }


    public void horarioTutor(){
        Intent horario = new Intent(getApplication(), HorarioTutoresActivity.class);
        horario.putExtra("usuario",usuario);
        horario.putExtra("correo",correo);
        startActivity(horario);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonMatematicas:
                matematicas();
                break;
            case R.id.botonBiologia:
                crud();
                break;

            case R.id.botonVerHorario:
                horarioTutor();
                break;
        }
    }
}
