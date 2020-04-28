package com.example.sponsor2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SaludotutorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludotutor);

        btnRegistro = (Button) findViewById(R.id.botonContinuar);

        btnRegistro.setOnClickListener(this);
    }

    public void Continuar(){
        Intent continuarTutor = new Intent(getApplication(),RegistrotutorActivity.class);
        startActivity(continuarTutor);
    }
    @Override
    public void onClick(View v) {
        Continuar();
    }
}
