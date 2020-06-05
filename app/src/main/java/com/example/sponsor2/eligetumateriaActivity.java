package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class eligetumateriaActivity extends AppCompatActivity implements View.OnClickListener {

    private String indicadorMateria;
    private Button btnHorarioTutoria,btnmatematicas, btnBiologia, btnSociales, btnEspañol, btnFisica, btnQuimica, btnIngles;
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
        btnSociales = findViewById(R.id.botonSociales);
        btnEspañol = findViewById(R.id.botonEspañol);
        btnFisica = findViewById(R.id.botonFisica);
        btnQuimica = findViewById(R.id.botonEd_Fisica);
        btnIngles = findViewById(R.id.botonIngles);


        tomarUsuario = getIntent().getExtras();
        usuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        correo = tomarCorreo.getString("correo");

        btnHorarioTutoria.setOnClickListener(this);
        btnmatematicas.setOnClickListener(this);
        btnBiologia.setOnClickListener(this);
        btnSociales.setOnClickListener(this);
        btnEspañol.setOnClickListener(this);
        btnFisica.setOnClickListener(this);
        btnQuimica.setOnClickListener(this);
        btnIngles.setOnClickListener(this);

    }



    public void crud(String indicadorMateria){

        Intent crud =new  Intent(getApplication(), Crud1Activity.class);
        crud.putExtra("usuario",usuario);
        crud.putExtra("correo",correo);
        crud.putExtra("indiMateria",indicadorMateria);
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

            case R.id.botonVerHorario:
                horarioTutor();
                break;
        }
    }
}
