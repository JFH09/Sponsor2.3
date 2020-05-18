package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class eligetumateriaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnmatematicas;
    private String usuario;
    private String correo;

    Bundle  tomarUsuario;
    Bundle tomarCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligetumateria);

        btnmatematicas = findViewById(R.id.botonMatematicas);

        tomarUsuario = getIntent().getExtras();
        usuario = tomarUsuario.getString("usuario");

        tomarCorreo = getIntent().getExtras();
        correo = tomarCorreo.getString("correo");


        btnmatematicas.setOnClickListener(this);
    }

    public void matematicas(){

        Toast.makeText(this,"Paso.."+ usuario + correo,Toast.LENGTH_LONG).show();
        Intent matematicas = new Intent(getApplication(),MatematicasActivity.class);
        matematicas.putExtra("usuario",usuario);
        matematicas.putExtra("correo",correo);
        startActivity(matematicas);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonMatematicas:
                matematicas();
                break;

        }
    }
}
